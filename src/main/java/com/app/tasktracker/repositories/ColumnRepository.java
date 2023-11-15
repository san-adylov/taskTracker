package com.app.tasktracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.tasktracker.models.Column;

public interface ColumnRepository  extends JpaRepository<Column,Long> {

}