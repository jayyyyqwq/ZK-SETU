package com.zkkyc.verifier.server.controller;

import com.zkkyc.verifier.server.model.request.ProofVerifyRequest;
import com.zkkyc.verifier.server.service.ProofService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proof")
public class ProofController {

    private final ProofService proofService;

    public ProofController(ProofService proofService) {
        this.proofService = proofService;
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody ProofVerifyRequest req) {

        var result = proofService.verify(req);

        return ResponseEntity.ok(
                new Response(
                        result.valid(),
                        result.reason(),
                        result.verifiedAt()
                )
        );
    }

    public record Response(
            boolean valid,
            String reason,
            String verifiedAt
    ) {}
}
