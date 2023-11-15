package com.app.tasktracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.tasktracker.models.Estimation;

public interface EstimationRepository extends JpaRepository<Estimation,Long> {

}