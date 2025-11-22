package com.zkkyc.issuer.server.controller;

import com.zkkyc.issuer.server.model.entity.Registration;
import com.zkkyc.issuer.server.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wallet")
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        Registration r = walletService.createRegistration(req.devicePubKey(), req.phoneNumber(), req.attributesHash());
        // for demo we return OTP (in prod, do not)
        return ResponseEntity.ok(Map.of(
                "registrationId", r.getRegistrationId(),
                "otpSent", true,
                "demoOtp", r.getOtp()
        ));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest req) {
        Optional<Registration> opt = walletService.verifyOtp(req.registrationId(), req.otp());
        if (opt.isEmpty()) return ResponseEntity.status(400).body(Map.of("error", "invalid otp or registrationId"));
        Registration r = opt.get();
        // return simple token (demo)
        String token = "regtoken-" + java.util.UUID.randomUUID();
        return ResponseEntity.ok(Map.of(
                "userId", r.getUserId(),
                "registrationToken", token
        ));
    }

    // DTOs as records
    public static record RegisterRequest(String devicePubKey, String phoneNumber, String attributesHash) {}
    public static record VerifyOtpRequest(String registrationId, String otp) {}
}
