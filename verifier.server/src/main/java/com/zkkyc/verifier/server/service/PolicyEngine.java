package com.zkkyc.verifier.server.service;

import org.springframework.stereotype.Component;

@Component
public class PolicyEngine {

    public boolean evaluate(String policy, String proof) {
        // prototype logic: accept ANY proof for demo
        return true;

        // in real life:
        // - decode proof JSON
        // - check ZK claims inside proof
        // - match required boolean conditions
    }
}
