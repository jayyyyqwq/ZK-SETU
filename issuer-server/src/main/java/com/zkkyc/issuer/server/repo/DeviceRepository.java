package com.zkkyc.issuer.server.repo;

import com.zkkyc.issuer.server.model.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, String> {
}
