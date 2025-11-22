package com.zkkyc.issuer.server.repo;

import com.zkkyc.issuer.server.model.entity.RestoreSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestoreSessionRepository extends JpaRepository<RestoreSession, String> {
}
