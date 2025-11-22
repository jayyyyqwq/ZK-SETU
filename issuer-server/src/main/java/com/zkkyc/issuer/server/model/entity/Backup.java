package com.zkkyc.issuer.server.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "backups")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Backup {
    @Id
    private String credentialId;
    private String ciphertext;
    private String wrappedDEK;
    private String kid;
    private int version;
}
