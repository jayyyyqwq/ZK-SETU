package com.zkkyc.verifier.server.controller;

import com.zkkyc.verifier.server.model.request.NewSessionRequest;
import com.zkkyc.verifier.server.service.NonceGenerator;
import com.zkkyc.verifier.server.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;
    private final NonceGenerator nonceGenerator;

    public SessionController(SessionService sessionService, NonceGenerator nonceGenerator) {
        this.sessionService = sessionService;
        this.nonceGenerator = nonceGenerator;
    }

    @PostMapping("/new")
    public ResponseEntity<?> create(@RequestBody NewSessionRequest req) {

        String nonce = nonceGenerator.generate();
        var session = sessionService.createSession(req, nonce);

        return ResponseEntity.ok(
                new Response(
                        session.sessionId,
                        session.nonce,
                        1,              // expected version — prototype
                        session.expiresAt.toString()
                )
        );
    }

    public record Response(
            String sessionId,
            String challengeNonce,
            int expectedVersion,
            String expiresAt
    ) {}
}
