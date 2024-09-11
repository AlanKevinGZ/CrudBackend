package com.blog.videogames.controllers;

import java.util.List;

import com.blog.videogames.DTO.VideoGameCreateDTO;
import com.blog.videogames.Menssage.MenssageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.blog.videogames.DTO.VideoGameDTO;
import com.blog.videogames.models.VideoGames;
import com.blog.videogames.services.VideoGmesServices;

@RestController
@RequestMapping("/videogames")
@CrossOrigin
public class ListController {

    @Autowired
    VideoGmesServices gamesServices; //inyeccion de dependecia instancia la clase sin inicializarla

    @GetMapping("/get")
    public List<VideoGameDTO> getGames() {
        return gamesServices.getGame();
    }

    @GetMapping("/get/{id}")
    public VideoGameDTO getGamesById(@PathVariable("id") long id) {
        return gamesServices.getGameById(id);
    }

    @PostMapping("/create")
    //ResponseEntity<T> es una clase en Spring que representa una respuesta HTTP
    public ResponseEntity<?> createVideoGame(@RequestBody VideoGameCreateDTO videoGameCreateDTO) {
        try {
            VideoGameDTO createdGameDTO = gamesServices.createVideoGame(videoGameCreateDTO);
            return new ResponseEntity<>(createdGameDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Manejo de la excepción cuando el videojuego ya existe
            return new ResponseEntity<>(new MenssageInfo(e.getMessage()), HttpStatus.CONFLICT);
        } catch (Exception e) {
            // Manejo de excepciones genéricas
            return new ResponseEntity<>(new MenssageInfo("Error interno del servidor"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateVideoGame(@PathVariable long id, @RequestBody VideoGameCreateDTO videoGameCreateDTO) {
        try {
            VideoGameDTO updatedGameDTO = gamesServices.updateVideoGame(id, videoGameCreateDTO);
            return new ResponseEntity<>(updatedGameDTO, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Manejo de la excepción cuando el videojuego no existe
            return new ResponseEntity<>(new MenssageInfo(e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Manejo de excepciones genéricas
            return new ResponseEntity<>(new MenssageInfo("Error interno del servidor"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteVideoGame(@PathVariable long id) {
        try {
            gamesServices.deleteVideoGameById(id);
            return new ResponseEntity<>("Videojuego eliminado correctamente", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Manejo de la excepción si el videojuego no existe
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Manejo de otras excepciones posibles
            return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
