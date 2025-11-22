package com.zkkyc.verifier.server.model.request;

public record NewSessionRequest(
        String verifierId,
        String policy,
        String deliveryMethod
) {}
