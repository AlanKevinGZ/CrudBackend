package com.blog.videogames.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blog.videogames.models.Categorys;
import com.blog.videogames.models.VideoGames;

public interface ICategoryRepository extends JpaRepository<Categorys,Long> {

    
}
