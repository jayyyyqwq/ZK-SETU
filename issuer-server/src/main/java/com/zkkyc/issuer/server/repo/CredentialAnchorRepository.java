package com.zkkyc.issuer.server.repo;

import com.zkkyc.issuer.server.model.entity.CredentialAnchor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialAnchorRepository extends JpaRepository<CredentialAnchor, String> {
}
