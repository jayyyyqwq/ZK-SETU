package com.zkkyc.issuer.server.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "registrations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Registration {
    @Id
    private String registrationId;
    private String phoneNumber;
    private String devicePubKey;
    private String otp;
    private boolean verified;
    private String userId;
}
