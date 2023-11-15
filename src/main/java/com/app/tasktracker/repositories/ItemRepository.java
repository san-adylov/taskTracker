package com.app.tasktracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.tasktracker.models.Item;

public interface ItemRepository extends JpaRepository<Item,Long> {

}
