package com.tenutz.storemngsim.domain.user;

import com.tenutz.storemngsim.domain.user.id.ManagerId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, ManagerId> {
}
