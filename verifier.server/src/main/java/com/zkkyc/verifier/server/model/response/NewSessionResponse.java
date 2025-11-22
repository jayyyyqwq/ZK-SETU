package com.zkkyc.verifier.server.model.response;

public record NewSessionResponse(
        String sessionId,
        String challengeNonce,
        int expectedVersion,
        String expiresAt,
        String signedProofRequest
) {}
