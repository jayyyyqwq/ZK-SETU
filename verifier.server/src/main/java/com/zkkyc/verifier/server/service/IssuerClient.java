package com.zkkyc.verifier.server.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Component
public class IssuerClient {

    private final RestTemplate rest = new RestTemplate();
    private final String issuerBase = "http://localhost:8080";

    public Map getAnchor(String credentialId) {
        return rest.getForObject(issuerBase + "/credential/anchor/" + credentialId, Map.class);
    }

    public Map getRevocation(String credentialId) {
        return rest.getForObject(issuerBase + "/credential/revoke/" + credentialId, Map.class);
    }
}
