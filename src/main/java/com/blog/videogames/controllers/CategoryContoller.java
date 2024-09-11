package com.blog.videogames.controllers;


import com.blog.videogames.DTO.CategoryDTO;
import com.blog.videogames.models.Categorys;
import com.blog.videogames.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/videogames/category")
@CrossOrigin
public class CategoryContoller {

    @Autowired
    CategoryServices categoryServices;

    @GetMapping("get")
    public List<CategoryDTO> getCategory() {
         return categoryServices.getCategory();
    }

    @GetMapping("saludo")
    public String getSaludo() {
        return "hola";
    }
}
