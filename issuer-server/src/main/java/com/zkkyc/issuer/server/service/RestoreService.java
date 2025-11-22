package com.zkkyc.issuer.server.service;

import com.zkkyc.issuer.server.model.entity.RestoreSession;
import com.zkkyc.issuer.server.repo.RestoreSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestoreService {

    private final RestoreSessionRepository restoreSessionRepository;

    public RestoreService() { this.restoreSessionRepository = null; }

    public RestoreSession initiateRestore(String userId, String method) {
        String id = "rest-" + UUID.randomUUID();
        RestoreSession s = RestoreSession.builder()
                .restoreId(id)
                .userId(userId)
                .method(method)
                .completed(false)
                .build();
        restoreSessionRepository.save(s);
        return s;
    }

    public Optional<RestoreSession> completeRestore(String restoreId) {
        var opt = restoreSessionRepository.findById(restoreId);
        if (opt.isPresent()) {
            RestoreSession s = opt.get();
            s.setCompleted(true);
            restoreSessionRepository.save(s);
            return Optional.of(s);
        }
        return Optional.empty();
    }
}
