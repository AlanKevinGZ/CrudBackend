package com.blog.videogames.models;


import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity //entidad asociada a una tabla
public class VideoGames {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
   
    @ManyToMany // Define una relación muchos a muchos
    @JoinTable(//Especifica la tabla intermedia que almacena las claves foráneas para la relación.
            name = "video_game_category",
            joinColumns = @JoinColumn(name = "video_game_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Categorys> categories; //coleccion de categorias 

    private double cost;
    private String img;

    
    public VideoGames(String name, double cost,String img) {
        this.name = name;
        this.cost = cost;
        this.img=img;
    }
    public VideoGames() {
    }

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

    public Set<Categorys> getCategories() {
        return categories;
    }

    public void setCategories(Set<Categorys> categories) {
        this.categories = categories;
    }

    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }


    @Override
    public String toString() {
        return "VideoGame [name=" + name + ", cost=" + cost + ", categories=" + categories + "]";
    }
   
    
}
