# Issuer Server API – Full Developer Guide

A complete explanation of how the issuer prototype works, written so even someone new to Spring Boot can follow it easily.

---

## 1. Introduction

This project is a **demo issuer server** for a decentralized KYC / credential system. It exposes simple REST APIs to:

* Register a device using phone + OTP
* Verify OTP and bind device to a user
* Issue credentials
* Retrieve credential anchors
* Revoke credentials
* Create / fetch backups
* Start / complete restore flow

All data is stored in an **in‑memory H2 database**, created automatically when the server runs.

---

## 2. Project Structure Overview

Spring Boot projects are usually divided into layers. Your project follows this clean layered structure:

```
com.zkkyc.issuer.server
│
├── config/              → Security configuration
├── controller/          → REST API endpoints
├── model/
│   └── entity/          → Database models (tables)
├── repo/                → Repository interfaces (database access)
├── service/             → Business logic
└── IssuerServerApplication.java → Main Spring Boot application
```

Here’s what each layer does in simple terms:

### **Controller Layer**

This is where all HTTP endpoints live. Incoming requests go here first.
Example: `/wallet/register`.

### **Service Layer**

This contains business logic. Controllers call into these services.
Example: verifying an OTP.

### **Repository Layer**

These are interfaces that talk to the database.
Example: `RegistrationRepository` stores registrations.

### **Model Layer (Entities)**

These classes represent tables in the H2 database.
Example: the `Device` table or `Registration` table.

### **Config Layer**

Contains security configuration for allowing all requests during demo.

---

## 3. Entities (Database Tables)

Each entity becomes a table in the database. Below are the key ones.

### **1. Registration**

Represents a phone number beginning registration.
Contains:

* registrationId
* phoneNumber
* devicePubKey
* otp
* verified
* userId

### **2. Device**

Stores which device belongs to which user.

* devicePubKey
* userId
* phoneNumber
* active

### **3. CredentialAnchor**

Metadata about an issued credential.

* credentialId
* credentialHash
* devicePubKey
* issuerTimestamp

### **4. Revocation**

Tracks whether a credential is revoked.

* credentialId
* revoked
* revokedAt

### **5. Backup**

Encrypted user backup blob.

### **6. RestoreSession**

Tracks ongoing restore operations.

These entities automatically generate tables thanks to Spring Data JPA.

---

## 4. API Endpoints

This section describes each endpoint, its purpose, request/response formats, and how it fits into the flow.

---

# PART A — Wallet Registration APIs

These are used when the mobile wallet wants to register a new device.

## **1. POST /wallet/register**

Starts the registration. Generates an OTP.

### **Request Body:**

```
{
  "devicePubKey": "devKey123",
  "phoneNumber": "+91XXXXXXXXXX",
  "attributesHash": "sha256-abc"
}
```

### **Response:**

```
{
  "registrationId": "reg-uuid",
  "otpSent": true,
  "demoOtp": "123456"
}
```

### Behind the scenes:

* Creates a `Registration` record
* Generates random OTP
* Saves to DB

---

## **2. POST /wallet/verify-otp**

Verifies OTP and binds device to a user.

### **Request Body:**

```
{
  "registrationId": "reg-uuid",
  "otp": "123456"
}
```

### **Response:**

```
{
  "userId": "u-uuid",
  "registrationToken": "regtoken-uuid"
}
```

### Behind the scenes:

* Checks registration
* Verifies OTP
* Marks registration as verified
* Creates a new `Device` entry
* Returns registration token (demo only)

---

# PART B — Credential Issuance APIs

Mobile wallet requests a credential using its device key.

## **3. POST /credential/issue**

Issues a new credential.

### Request Body:

```
{
  "userId": "u-123",
  "devicePubKey": "devKey123",
  "attributesHash": "hash-xyz"
}
```

### Response:

```
{
  "credentialId": "cred-uuid",
  "credentialHash": "hash-uuid",
  "ciphertext": "encrypted-blob",
  "wrappedDEK": "wrapped-key",
  "kid": "mk-v1",
  "version": 1,
  "issuerTimestamp": "timestamp"
}
```

### Behind the scenes:

* Creates a credential ID
* Creates credential anchor
* Creates a backup blob
* Returns encrypted credential bundle

---

# PART C — Credential Metadata APIs

Used to fetch metadata later.

## **4. GET /credential/anchor/{credentialId}**

Returns stored anchor.

### Response Example:

```
{
  "credentialId": "cred-xyz",
  "credentialHash": "hash-abc",
  "devicePubKey": "devKey1",
  "issuerTimestamp": "..."
}
```

---

## **5. GET /credential/revoke/{credentialId}**

Returns revocation status.

### Response Example:

```
{
  "revoked": false,
  "revokedAt": null
}
```

### Behind the scenes:

Looks in `Revocation` table.

---

## **6. POST /credential/revoke/{credentialId}**

Marks a credential as revoked.

### Response:

```
{
  "credentialId": "cred-xyz",
  "revoked": true,
  "revokedAt": "timestamp"
}
```

---

# PART D — Restore APIs

Allows restoring wallet on a new device.

## **7. POST /restore/start**

Starts a restore session.
Returns restoreId.

## **8. POST /restore/complete**

Completes restore and returns backup blob.

---

# 5. Security

Your `SecurityConfig` disables all security for demo purposes:

* All endpoints are publicly accessible
* No login, no HTTP Basic
* CSRF disabled

This makes it easy to test everything using Postman.
In production you’d restrict endpoints.

---

# 6. Database

You’re using **H2 in‑memory database**, so:

* Data resets every time the app restarts
* Tables auto‑generate
* Console available at: `/h2-console`

---

# 7. Running the Application

1. Open the project in IntelliJ
2. Run `IssuerServerApplication.java`
3. Server starts on: **[http://localhost:8080](http://localhost:8080)**
4. Test endpoints using Postman

---

# 8. Summary

This prototype issuer:

* Demonstrates full credential lifecycle
* Has clean Spring Boot layering
* Is readable even for beginners
* Supports device → user → credential flows
* Provides a realistic backend for your Harbinger submission

If you want, I can generate a **Postman collection**, **architecture diagram**, or **sequence diagrams** next.
