package com.zkkyc.issuer.server.model;

public record IssueRequest(
        String userId,
        String devicePubKey,
        String attributesHash
) {}
