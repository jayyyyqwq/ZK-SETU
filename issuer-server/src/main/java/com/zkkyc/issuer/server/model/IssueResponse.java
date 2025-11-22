package com.zkkyc.issuer.server.model;

public record IssueResponse(
        String credentialId,
        String credentialHash,
        String ciphertext,
        String wrappedDEK,
        String kid,
        int version,
        String issuerTimestamp
) {}

