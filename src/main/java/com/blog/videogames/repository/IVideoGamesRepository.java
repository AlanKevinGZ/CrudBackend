package com.blog.videogames.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blog.videogames.models.VideoGames;

public interface IVideoGamesRepository extends JpaRepository<VideoGames, Long> {

    @Query("SELECT vg FROM VideoGames vg LEFT JOIN FETCH vg.categories")
    List<VideoGames> findAllWithCategories();

    Optional<VideoGames> findByName(String name);

}
