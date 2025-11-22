package com.zkkyc.issuer.server.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "credential_anchors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CredentialAnchor {
    @Id
    private String credentialId;
    private String credentialHash;
    private int version;
    private String issuerTimestamp;
    private String kid;
    private String devicePubKey;
    private String registeredPhone;
}
