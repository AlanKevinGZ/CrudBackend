package com.blog.videogames.services;

import java.util.*;

import java.util.stream.Collectors;

import com.blog.videogames.DTO.VideoGameCreateDTO;
import com.blog.videogames.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.videogames.DTO.CategoryDTO;
import com.blog.videogames.DTO.VideoGameDTO;
import com.blog.videogames.models.Categorys;
import com.blog.videogames.models.VideoGames;
import com.blog.videogames.repository.IVideoGamesRepository;

@Service
public class VideoGmesServices {

    @Autowired
    private IVideoGamesRepository gamesRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

    public List<VideoGameDTO> getGame(){
        List<VideoGames> games = gamesRepository.findAllWithCategories();
        return games.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList()); 
    }

    public VideoGameDTO getGameById(long id){
        //Optional  contenedor que puede o no contener un valor no nulo
        Optional<VideoGames> optionalGame = gamesRepository.findById(id);
        //mapea los datos en formato convertToDTO
        return optionalGame.map(this::convertToDTO).orElse(null);
    }


    public VideoGameDTO  createVideoGame(VideoGameCreateDTO videoGameCreateDTO) {

        // Verificar si el videojuego ya existe por nombre
        Optional<VideoGames> existingGame = gamesRepository.findByName(videoGameCreateDTO.getName());

        if (videoGameCreateDTO.getName() == null || videoGameCreateDTO.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ir vacío");
        }

        if (existingGame.isPresent()) {
            throw new IllegalArgumentException("El videojuego con el nombre ya existe");
        }

        // Validar que el costo sea mayor a cero
        if (videoGameCreateDTO.getCost() <= 0) {
            throw new IllegalArgumentException("El costo debe ser mayor a cero");
        }

        // Obtener las categorías por sus IDs desde el DTO
        List<Categorys> categoriesList = categoryRepository.findAllById(videoGameCreateDTO.getCategoryIds());

        // Validar que haya al menos una categoría
        if (categoriesList.isEmpty()) {
            throw new IllegalArgumentException("Debe haber al menos una categoría asociada");
        }

        // Convertir la lista de categorías a un Set y asignarlo al videojuego
        Set<Categorys> categoriesSet = new HashSet<>(categoriesList);
        VideoGames videoGame = new VideoGames(videoGameCreateDTO.getName(), videoGameCreateDTO.getCost(), videoGameCreateDTO.getImg());
        videoGame.setCategories(categoriesSet);

        // Guardar el videojuego y convertirlo a DTO
        VideoGames savedGame = gamesRepository.save(videoGame);

        // Guardar y retornar el videojuego
        return convertToDTO(savedGame);
    }


    public VideoGameDTO  EditVideoGame(VideoGameCreateDTO videoGameCreateDTO) {

        // Verificar si el videojuego ya existe por nombre
        Optional<VideoGames> existingGame = gamesRepository.findByName(videoGameCreateDTO.getName());
        if (existingGame.isPresent()) {
            throw new IllegalArgumentException("El videojuego con el nombre ya existe");
        }

        VideoGames videoGame = new VideoGames(videoGameCreateDTO.getName(), videoGameCreateDTO.getCost(), videoGameCreateDTO.getImg());
        // Obtener las categorías por sus IDs desde el DTO
        List<Categorys> categoriesList = categoryRepository.findAllById(videoGameCreateDTO.getCategoryIds());

        // Convertir la lista de categorías a un Set y asignarlo al videojuego
        Set<Categorys> categoriesSet = new HashSet<>(categoriesList);
        videoGame.setCategories(categoriesSet);

        // Guardar el videojuego y convertirlo a DTO
        VideoGames savedGame = gamesRepository.save(videoGame);

        // Guardar y retornar el videojuego
        return convertToDTO(savedGame);
    }

    public VideoGameDTO updateVideoGame(long id, VideoGameCreateDTO videoGameCreateDTO) {
        // Buscar el videojuego existente por ID
        Optional<VideoGames> existingGameOpt = gamesRepository.findById(id);
        if (!existingGameOpt.isPresent()) { //valores que pueden o no estar presentes, y así evitar el uso excesivo de null
            throw new IllegalArgumentException("El videojuego con el ID proporcionado no existe");
        }

        VideoGames existingGame = existingGameOpt.get();
        // Actualizar los datos del videojuego
        existingGame.setName(videoGameCreateDTO.getName());
        existingGame.setCost(videoGameCreateDTO.getCost());
        existingGame.setImg(videoGameCreateDTO.getImg());

        // Obtener las categorías por sus IDs desde el DTO
        List<Categorys> categoriesList = categoryRepository.findAllById(videoGameCreateDTO.getCategoryIds());
        Set<Categorys> categoriesSet = new HashSet<>(categoriesList);
        existingGame.setCategories(categoriesSet);

        // Guardar el videojuego actualizado
        VideoGames updatedGame = gamesRepository.save(existingGame);
        return convertToDTO(updatedGame);

    }

    public void deleteVideoGameById(long id) {
        Optional<VideoGames> optionalGame = gamesRepository.findById(id);
        if (!optionalGame.isPresent()) {
            throw new IllegalArgumentException("El videojuego con el ID proporcionado no existe");
        }
        gamesRepository.deleteById(id);
    }








/*convertDTO*/
    private VideoGameDTO convertToDTO(VideoGames game) {
        VideoGameDTO dto = new VideoGameDTO();
        dto.setId(game.getId());
        dto.setName(game.getName());
        dto.setCost(game.getCost());
        dto.setImg(game.getImg());

        // Convertir la lista de categorías a DTOs
        List<CategoryDTO> categoryDTOs = game.getCategories().stream()
                .map(this::convertCategoryToDTO)
                .collect(Collectors.toList());
        dto.setCategories(categoryDTOs);

        return dto;
    }

    private CategoryDTO convertCategoryToDTO(Categorys category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }



}
