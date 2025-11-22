package com.zkkyc.verifier.server.model.response;

public record ProofVerifyResponse(
        boolean valid,
        String reason,
        String verifiedAt
) {}
