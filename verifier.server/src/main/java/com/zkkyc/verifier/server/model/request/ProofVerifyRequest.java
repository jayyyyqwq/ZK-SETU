package com.zkkyc.verifier.server.model.request;

public record ProofVerifyRequest(
        String sessionId,
        String proof,
        String credentialId,
        int version,
        String issuerTimestamp
) {}
