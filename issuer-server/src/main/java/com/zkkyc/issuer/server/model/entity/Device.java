package com.zkkyc.issuer.server.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "devices")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Device {
    @Id
    private String devicePubKey; // PK = device public key
    private String userId;
    private String phoneNumber;
    private boolean active;
}
