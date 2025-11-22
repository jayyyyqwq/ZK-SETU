package com.zkkyc.issuer.server.repo;

import com.zkkyc.issuer.server.model.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, String> {
}
