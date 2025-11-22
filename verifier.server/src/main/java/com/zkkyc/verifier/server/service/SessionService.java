package com.zkkyc.verifier.server.service;

import com.zkkyc.verifier.server.model.request.NewSessionRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {

    public static class Session {
        public String sessionId;
        public String verifierId;
        public String policy;
        public String nonce;
        public Instant expiresAt;

        public Session(String sessionId, String verifierId, String policy, String nonce, Instant expiresAt) {
            this.sessionId = sessionId;
            this.verifierId = verifierId;
            this.policy = policy;
            this.nonce = nonce;
            this.expiresAt = expiresAt;
        }
    }

    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    public Session createSession(NewSessionRequest req, String nonce) {
        String sessionId = "sess-" + UUID.randomUUID();
        Instant expiry = Instant.now().plusSeconds(60); // 60 sec validity

        Session s = new Session(sessionId, req.verifierId(), req.policy(), nonce, expiry);
        sessions.put(sessionId, s);
        return s;
    }

    public Session getValidSession(String sessionId) {
        Session s = sessions.get(sessionId);
        if (s == null) return null;
        if (Instant.now().isAfter(s.expiresAt)) return null;
        return s;
    }
}
