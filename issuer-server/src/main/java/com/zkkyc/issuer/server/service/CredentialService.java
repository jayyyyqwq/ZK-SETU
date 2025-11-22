package com.zkkyc.issuer.server.service;

import com.zkkyc.issuer.server.model.entity.Backup;
import com.zkkyc.issuer.server.model.entity.CredentialAnchor;
import com.zkkyc.issuer.server.model.entity.Revocation;
import com.zkkyc.issuer.server.repo.BackupRepository;
import com.zkkyc.issuer.server.repo.CredentialAnchorRepository;
import com.zkkyc.issuer.server.repo.RevocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CredentialService {

    private final CredentialAnchorRepository anchorRepository;
    private final BackupRepository backupRepository;
    private final RevocationRepository revocationRepository;

    public CredentialAnchor storeAnchor(String credentialId, String credentialHash, String devicePubKey, String phone) {
        CredentialAnchor a = CredentialAnchor.builder()
                .credentialId(credentialId)
                .credentialHash(credentialHash)
                .version(1)
                .issuerTimestamp(Instant.now().toString())
                .kid("mk-v1")
                .devicePubKey(devicePubKey)
                .registeredPhone(phone)
                .build();

        return anchorRepository.save(a);
    }

    public Backup storeBackup(String credentialId, String ciphertext, String wrappedDEK) {
        Backup b = Backup.builder()
                .credentialId(credentialId)
                .ciphertext(ciphertext)
                .wrappedDEK(wrappedDEK)
                .kid("mk-v1")
                .version(1)
                .build();

        return backupRepository.save(b);
    }

    public Optional<CredentialAnchor> getAnchor(String credentialId) {
        return anchorRepository.findById(credentialId);
    }

    public Revocation markRevoked(String credentialId) {
        Revocation r = Revocation.builder()
                .credentialId(credentialId)
                .revoked(true)
                .revokedAt(Instant.now().toString())
                .build();

        return revocationRepository.save(r);
    }

    public Optional<Revocation> getRevocation(String credentialId) {
        return revocationRepository.findById(credentialId);
    }
}
