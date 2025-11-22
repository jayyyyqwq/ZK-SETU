# Verifier Server — README (prototype)

## Overview

The Verifier Server is the backend component used by banks, fintechs, and telecoms to **request zero-knowledge proofs**, **validate those proofs**, and **confirm credential integrity** with the Issuer Server.

This server does **not store any PII**, has **no database**, and operates purely in **stateless mode**. All verification is based on:

* ZK proofs
* Issuer cloud anchor
* Nonce/session validation
* Revocation status

It is purposely lightweight so it can be deployed rapidly in a hackathon or demo.

---

## Architecture Summary

### Components:

* **SessionController**: Creates new proof sessions
* **ProofController**: Validates proofs received from the user’s phone
* **PolicyEngine**: Verifies if the proof meets the requested access policy
* **IssuerClient**: Fetches credential anchor + revocation from Issuer
* **NonceGenerator**: Issues cryptographically strong nonces
* **SessionService**: Manages temporary in-memory session store
* **ProofService**: Validates ZK proofs + issuer metadata

---

## Endpoints

### 1. `POST /session/new` — Create a Proof Session

Used by the verifier (bank/fintech) to request a new ZK proof.

**Request:**

* verifierId
* policy
* deliveryMethod

**Response:**

* sessionId
* challengeNonce
* expectedVersion
* expiresAt
* proofRequest (signed blob or JSON)

### 2. `POST /proof/verify` — Validate ZK Proof

This endpoint receives the proof from the user’s wallet.

**Request:**

* sessionId
* proof
* credentialId
* version
* issuerTimestamp

**Response:**

* valid (true/false)
* reason
* verifiedAt
* optional: newVersion info

---

## Internal Logic

### Session Flow

1. Verifier asks for proof
2. Server generates session + nonce
3. Wallet receives request and generates proof offline
4. Wallet sends proof
5. Verifier Server checks:

   * ZK proof validity
   * Nonce match
   * Issuer cloud anchor hash
   * Credential version
   * Revocation status
   * Policy correctness

### Proof Validation Flow

* Verify proof mathematically (placeholder in demo)
* Fetch anchor from issuer
* Compare version + timestamp
* Check revocation
* Check policy rules
* Return result

---

## Configuration

Using Spring Boot 3.5.7, Java 21
Dependencies:

* Spring Web
* Spring Boot DevTools (optional)

No database required.

Security: a simple `SecurityConfig` disables authentication for the prototype.

---

## File Structure

```
src/main/java/com/zkkyc/verifier/server
 ├── config
 │    └── SecurityConfig.java
 ├── controller
 │    ├── SessionController.java
 │    └── ProofController.java
 ├── model
 │    ├── request/
 │    │     ├── NewSessionRequest.java
 │    │     └── ProofVerifyRequest.java
 │    └── response/
 │          ├── NewSessionResponse.java
 │          └── ProofVerifyResponse.java
 ├── service
 │    ├── SessionService.java
 │    ├── ProofService.java
 │    ├── PolicyEngine.java
 │    ├── NonceGenerator.java
 │    └── IssuerClient.java
 └── Application.java
```

---

## How To Run

1. `mvn clean install`
2. `mvn spring-boot:run`
3. Server runs at **[http://localhost:8080](http://localhost:8080)**

Test endpoints using Postman.

---

## Future Extensions

* Real zk-SNARK verification
* BBS+ signature verification
* JWT-based verifier authentication
* Redis session store

---

## Notes

This is a **prototype**, optimized for:

* speed
* demo stability
* clarity

It avoids unnecessary complexity so the focus stays on cryptographic workflow, not engineering overhead.
