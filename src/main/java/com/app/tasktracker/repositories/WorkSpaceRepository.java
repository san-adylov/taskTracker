package com.app.tasktracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.tasktracker.models.WorkSpace;

import java.util.Optional;

public interface WorkSpaceRepository extends JpaRepository<WorkSpace,Long> {

    Optional<WorkSpace> getWorkSpaceByAdminIdAndId(Long adminId, Long id);
}
