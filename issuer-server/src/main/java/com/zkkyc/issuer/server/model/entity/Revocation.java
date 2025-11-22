package com.zkkyc.issuer.server.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "revocations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Revocation {
    @Id
    private String credentialId;
    private boolean revoked;
    private String revokedAt; // ISO timestamp or null
}
