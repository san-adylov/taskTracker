package com.app.tasktracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.tasktracker.models.Favorite;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> getFavoriteByMemberId(Long userId);
}