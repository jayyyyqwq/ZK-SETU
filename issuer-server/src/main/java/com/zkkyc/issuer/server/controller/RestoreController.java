package com.zkkyc.issuer.server.controller;

import com.zkkyc.issuer.server.model.entity.RestoreSession;
import com.zkkyc.issuer.server.service.RestoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/restore")
public class RestoreController {

    private final RestoreService restoreService;

    @PostMapping("/initiate")
    public ResponseEntity<?> initiate(@RequestBody InitiateRequest req) {
        // in real flow validate userId + attestation
        RestoreSession s = restoreService.initiateRestore(req.userId(), req.method());
        return ResponseEntity.ok(Map.of(
                "restoreId", s.getRestoreId(),
                "otpSent", true // demo
        ));
    }

    @PostMapping("/complete")
    public ResponseEntity<?> complete(@RequestBody CompleteRequest req) {
        Optional<RestoreSession> opt = restoreService.completeRestore(req.restoreId());
        if (opt.isEmpty()) return ResponseEntity.status(400).body(Map.of("error", "invalid restoreId"));
        return ResponseEntity.ok(Map.of("status", "restored", "restoreId", req.restoreId()));
    }

    public static record InitiateRequest(String userId, String deviceAttestation, String method) {}
    public static record CompleteRequest(String restoreId, String otp, String devicePubKey) {}
}
