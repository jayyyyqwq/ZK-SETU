package com.zkkyc.verifier.server.service;

import com.zkkyc.verifier.server.model.request.ProofVerifyRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
public class ProofService {

    private final SessionService sessionService;
    private final IssuerClient issuerClient;
    private final PolicyEngine policyEngine;

    public ProofService(SessionService sessionService, IssuerClient issuerClient, PolicyEngine policyEngine) {
        this.sessionService = sessionService;
        this.issuerClient = issuerClient;
        this.policyEngine = policyEngine;
    }

    public Result verify(ProofVerifyRequest req) {

        // 1) Validate session
        var session = sessionService.getValidSession(req.sessionId());
        if (session == null) {
            return new Result(false, "invalid_or_expired_session", now());
        }

        // 2) Fetch issuer anchor
        Map anchor = issuerClient.getAnchor(req.credentialId());
        if (anchor == null) {
            return new Result(false, "anchor_not_found", now());
        }

        int anchorVersion = (int) anchor.get("version");
        if (anchorVersion != req.version()) {
            return new Result(false, "version_mismatch", now());
        }

        // 3) Check revocation
        Map revoke = issuerClient.getRevocation(req.credentialId());
        boolean revoked = (boolean) revoke.get("revoked");
        if (revoked) {
            return new Result(false, "credential_revoked", now());
        }

        // 4) Policy evaluation (always true in demo)
        boolean ok = policyEngine.evaluate(session.policy, req.proof());
        if (!ok) {
            return new Result(false, "policy_failed", now());
        }

        // Everything valid
        return new Result(true, "policy_satisfied", now());
    }

    public record Result(boolean valid, String reason, String verifiedAt) {}

    private String now() {
        return Instant.now().toString();
    }
}
