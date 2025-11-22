package com.zkkyc.issuer.server.controller;

import com.zkkyc.issuer.server.model.entity.Revocation;
import com.zkkyc.issuer.server.service.CredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/credential")
public class CredentialController {

    private final CredentialService credentialService;

    @GetMapping("/anchor/{credentialId}")
    public ResponseEntity<?> getAnchor(@PathVariable String credentialId) {

        return credentialService.getAnchor(credentialId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity
                        .status(404)
                        .body(Map.of("error", "not found")));
    }

    @GetMapping("/revoke/{credentialId}")
    public ResponseEntity<?> getRevocation(@PathVariable String credentialId) {

        return credentialService.getRevocation(credentialId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.ok(
                        Map.of(
                                "revoked", false,
                                "revokedAt", null
                        )
                ));
    }

    @PostMapping("/revoke/{credentialId}")
    public ResponseEntity<?> revoke(@PathVariable String credentialId) {
        Revocation rev = credentialService.markRevoked(credentialId);
        return ResponseEntity.ok(rev);
    }
}
