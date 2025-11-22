package com.zkkyc.issuer.server.service;

import com.zkkyc.issuer.server.model.entity.Device;
import com.zkkyc.issuer.server.model.entity.Registration;
import com.zkkyc.issuer.server.repo.DeviceRepository;
import com.zkkyc.issuer.server.repo.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final RegistrationRepository registrationRepository;
    private final DeviceRepository deviceRepository;
    private final Random random = new Random();

    public Registration createRegistration(String devicePubKey, String phoneNumber, String attributesHash) {
        String regId = "reg-" + UUID.randomUUID();
        String otp = String.format("%06d", random.nextInt(1_000_000));
        Registration r = Registration.builder()
                .registrationId(regId)
                .devicePubKey(devicePubKey)
                .phoneNumber(phoneNumber)
                .otp(otp)
                .verified(false)
                .build();
        registrationRepository.save(r);

        // For demo, we "send" OTP but we just return it in logs/response
        return r;
    }

    public Optional<Registration> verifyOtp(String registrationId, String otp) {
        var opt = registrationRepository.findById(registrationId);
        if (opt.isEmpty()) return Optional.empty();
        Registration reg = opt.get();
        if (reg.getOtp() != null && reg.getOtp().equals(otp)) {
            reg.setVerified(true);
            String userId = "u-" + UUID.randomUUID();
            reg.setUserId(userId);
            registrationRepository.save(reg);

            // create device binding
            Device d = Device.builder()
                    .devicePubKey(reg.getDevicePubKey())
                    .userId(userId)
                    .phoneNumber(reg.getPhoneNumber())
                    .active(true)
                    .build();
            deviceRepository.save(d);
            return Optional.of(reg);
        }
        return Optional.empty();
    }
}
