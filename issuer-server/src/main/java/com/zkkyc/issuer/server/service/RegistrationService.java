package com.zkkyc.issuer.server.service;

import com.zkkyc.issuer.server.model.entity.Registration;
import com.zkkyc.issuer.server.repo.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository repo;

    public Registration startRegistration(String phoneNumber, String devicePubKey) {

        String otp = "123456"; // prototype only

        Registration reg = Registration.builder()
                .registrationId(UUID.randomUUID().toString())
                .phoneNumber(phoneNumber)
                .devicePubKey(devicePubKey)
                .otp(otp)
                .verified(false)
                .userId(null)
                .build();

        return repo.save(reg);
    }
}
