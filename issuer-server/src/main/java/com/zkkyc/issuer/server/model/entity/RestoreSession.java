package com.zkkyc.issuer.server.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "restore_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestoreSession {
    @Id
    private String restoreId;
    private String userId;
    private String method; // e.g. "otp"
    private boolean completed;
}
