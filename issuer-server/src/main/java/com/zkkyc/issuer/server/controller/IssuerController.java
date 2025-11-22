package com.zkkyc.issuer.server.controller;

import com.zkkyc.issuer.server.model.entity.Backup;
import com.zkkyc.issuer.server.model.entity.CredentialAnchor;
import com.zkkyc.issuer.server.service.CredentialService;
import com.zkkyc.issuer.server.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/credential")
public class IssuerController {

    private final WalletService walletService;
    private final CredentialService credentialService;

    public static record IssueRequest(String userId, String devicePubKey, String attributesHash) {}
    public static record IssueResponse(String credentialId, String credentialHash, String ciphertext, String wrappedDEK, String kid, int version, String issuerTimestamp) {}

    @PostMapping("/issue")
    public ResponseEntity<?> issue(@RequestBody IssueRequest req) {
        // Basic validation demo
        if (req.userId() == null || req.devicePubKey() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "missing fields"));
        }

        // generate credential and placeholders (in real life: build credential, sign with HSM)
        String credentialId = "cred-" + UUID.randomUUID();
        String credentialHash = "hash-" + UUID.randomUUID();
        String ciphertext = "encrypted-blob-placeholder";
        String wrappedDEK = "wrapped-key-placeholder";
        String kid = "mk-v1";
        int version = 1;
        String issuerTimestamp = Instant.now().toString();

        // store anchor & backup via service
        CredentialAnchor anchor = credentialService.storeAnchor(credentialId, credentialHash, req.devicePubKey(), "demo-phone");
        Backup backup = credentialService.storeBackup(credentialId, ciphertext, wrappedDEK);

        IssueResponse resp = new IssueResponse(credentialId, credentialHash, ciphertext, wrappedDEK, kid, version, issuerTimestamp);
        return ResponseEntity.ok(resp);
    }
}
