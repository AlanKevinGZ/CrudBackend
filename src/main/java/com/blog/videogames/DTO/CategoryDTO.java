package com.blog.videogames.DTO;

public class CategoryDTO {

    private Long id;
    private String name;

     // Constructor vacío
    public CategoryDTO() {
    }


     // Constructor con parámetros (opcional)
     public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }


    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryDTO [id=" + id + ", name=" + name + "]";
    }







}
