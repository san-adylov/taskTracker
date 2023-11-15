package com.app.tasktracker.repositories.customRepository;

import com.app.tasktracker.dto.response.FavoriteResponse;

public interface CustomFavoriteRepository {

    FavoriteResponse getAll();
}