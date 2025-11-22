package com.zkkyc.issuer.server.repo;

import com.zkkyc.issuer.server.model.entity.Backup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BackupRepository extends JpaRepository<Backup, String> {
}
