package com.zkkyc.issuer.server.service;

import com.zkkyc.issuer.server.model.IssueRequest;
import com.zkkyc.issuer.server.model.IssueResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class IssuerService {

    public IssueResponse issue(IssueRequest req) {

        String credentialId = "cred-" + UUID.randomUUID();
        String credentialHash = "hash-" + UUID.randomUUID();
        String ciphertext = "encrypted-blob-placeholder";
        String wrappedDEK = "wrapped-key-placeholder";
        String kid = "mk-v1";

        return new IssueResponse(
                credentialId,
                credentialHash,
                ciphertext,
                wrappedDEK,
                kid,
                1,
                Instant.now().toString()
        );
    }

}
