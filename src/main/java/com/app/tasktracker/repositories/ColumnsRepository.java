package com.app.tasktracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.tasktracker.models.Column;

public interface ColumnsRepository extends JpaRepository<Column,Long> {


}
