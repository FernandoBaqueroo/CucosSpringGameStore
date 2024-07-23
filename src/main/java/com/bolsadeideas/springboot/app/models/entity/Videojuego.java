package com.bolsadeideas.springboot.app.models.entity;
 
import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="videojuego")
public class Videojuego implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String desarrollador;

    private String plataforma;

    private String categoria;

    @NotNull
    @Column(name = "lanzamiento")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date lanzamiento;

    private Double precio;

    private String imagen;

    // Constructores
    public Videojuego() {}
    
    public Videojuego(Long id, String titulo, String desarrollador, String plataforma, String categoria, Date lanzamiento, Double precio, String imagen) {
    	this.id = id;
        this.titulo = titulo;
        this.desarrollador = desarrollador;
        this.plataforma = plataforma;
        this.categoria = categoria;
        this.lanzamiento = lanzamiento;
        this.precio = precio;
        this.imagen = imagen;
    }

    public Videojuego(String titulo, String desarrollador, String plataforma, String categoria, Date lanzamiento, Double precio, String imagen) {
        this.titulo = titulo;
        this.desarrollador = desarrollador;
        this.plataforma = plataforma;
        this.categoria = categoria;
        this.lanzamiento = lanzamiento;
        this.precio = precio;
        this.imagen = imagen;
    }

    // Métodos getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDesarrollador() {
        return desarrollador;
    }

    public void setDesarrollador(String desarrollador) {
        this.desarrollador = desarrollador;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Date getLanzamiento() {
        return lanzamiento;
    }

    public void setLanzamiento(Date lanzamiento) {
        this.lanzamiento = lanzamiento;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @PrePersist
    public void prePersist() {
        lanzamiento = new Date();
    }
}