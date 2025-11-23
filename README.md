# ZK-SETU  
### Zero-Document KYC Wallet Prototype  
> Cryptographic KYC verification without revealing documents.

![Status](https://img.shields.io/badge/status-prototype-blue)
![Platform](https://img.shields.io/badge/platform-Android-green)
![Tech](https://img.shields.io/badge/tech-ZKP%20%7C%20Kotlin%20%7C%20Compose-orange)

---

## 🌐 What is ZK-SETU?

ZK-SETU is a **concept prototype** that demonstrates how a user can complete KYC **without sharing any document**, by generating and sending **mathematical proofs instead of personal data**.

A user imports Aadhaar offline XML → the app extracts only a **secure hash** → later, when a bank asks for a proof (like age > 18), the app responds with a **Zero-Knowledge style proof** (mocked) rather than a document.

This simulates future-ready “privacy-preserving KYC”.

---

## 🧩 Components in This Repository
1. **androidapp/**  
   Mobile ZK-KYC wallet (Jetpack Compose). Contains screens, activities, UI components.
2. **issuer-server/**  
   Prototype issuer backend. Issues credentials after validating Aadhaar hash.
3. **verifier-server/**  
   Prototype verifier backend. Sends proof requests & verifies incoming ZK proofs.

---

## 🔄 High-Level Workflow (ASCII Diagram)

```text
                  +----------------------------------------+
                  |        1) Aadhaar / CKYC Download       |
                  |   User gets Offline XML/ZIP (UIDAI/DL)  |
                  +-------------------------+--------------+
                                            |
                                            v
                  +----------------------------------------+
                  |     2) Local Verification (Offline)     |
                  |  Signature check | XML-DSIG | Hashing    |
                  |    No backend | No UIDAI calls          |
                  +-------------------------+----------------+
                                            |
                                            v
+----------------------------+      +-----------------------------+
|       Issuer Backend       |      |     3) Issuer Issues        |
|  - Parses attributes       | ----> |   Signed KYC Credential     |
|  - Validates fields        |       |  (Stores only revocation ID)|
+----------------------------+       +-------------+---------------+
                                                    |
                                                    v
                  +----------------------------------------+
                  |     4) Phone Stores Everything          |
                  |  - Raw attributes (local only)           |
                  |  - Signed credential (Keystore AES)      |
                  +-------------------------+----------------+
                                            |
                                            |  When verifier asks
                                            v
                  +----------------------------------------+
                  |        5) Verifier Sends Request        |
                  |   Policy | Nonce | Session ID | Intent  |
                  +-------------------------+----------------+
                                            |
                                            v
                  +----------------------------------------+
                  |     6) Phone Generates ZK Proof (Local) |
                  |   → Over 18? Aadhaar valid? PAN linked? |
                  |   No server, no cloud computation        |
                  +-------------------------+----------------+
                                            |
                                            v
+-----------------------------+     +-----------------------------+
|    7) Phone Delivers Proof  | --> |   8) Verifier Validates     |
| QR / NFC / HTTPS / Links    |     |  - zk proof correctness     |
|                             |     |  - issuer signature         |
|                             |     |  - revocation list          |
+-----------------------------+     |  - timestamp + policy match |
                                    +-----------------------------+
```

### Security Notes
- Only hashes are stored  
- All processing on-device  
- ZK proofs are **mocked** (prototype only)

---

## 🔐 Zero-Knowledge KYC Logic (Simplified)

### Credential (Prototype)
```json
{
  "credentialId": "cred-001",
  "attributesHash": "SHA256(AadhaarData)",
  "version": 1
}
```

---

## 🔍 Example Proof Request (from Verifier)

```json
{
  "verifier": "State Bank of India",
  "purpose": "age_over_18",
  "nonce": "XJ3921AB"
}
```

---

## 🧪 App-Generated ZK Proof (Prototype)

```json
{
  "credentialId": "cred-001",
  "proofType": "age_over_18",
  "nonce": "XJ3921AB",
  "zkProofBlob": "ABCD1234FAKEPROOF"
}
```

---

## 🏦 Issuer Server (Prototype Overview)

**Role**  
Issues digital credentials after validating Aadhaar hash.

**Flow**
- App sends SHA-256 hash  
- Issuer returns a credential object  
- No personal identity data is transmitted

---

## 🏢 Verifier Server (Prototype Overview)

**Role**  
Acts like a bank requesting and verifying proofs.

**Flow**
- `/request-proof` → sends proof request  
- `/verify-proof` → checks:  
  - Nonce match  
  - Credential ID match  
  - Mock proof blob validity

---

## 🎨 UI Notes
- Light-theme only  
- Gradient “ZK SETU” branding  
- Simple onboarding with 5-step privacy explanation  
- All screens animated with subtle fade/slide transitions

---

## ⚠️ Disclaimer

This is a **prototype** demonstrating flows and UX.  
Not production-ready.  
ZK proofs are mocked.  
No real Aadhaar processing, no real identity, no backend trust validation.

---

## 📄 License
MIT (Prototype – free to use & modify)

