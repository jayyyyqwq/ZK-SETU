package com.zkkyc.issuer.server.repo;

import com.zkkyc.issuer.server.model.entity.Revocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevocationRepository extends JpaRepository<Revocation, String> {
}
