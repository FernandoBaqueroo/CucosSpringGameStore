package com.bolsadeideas.springboot.app.models.service;
 
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Videojuego;

public interface IVideojuegoService {
	
	public List<Videojuego> findAll();
	
	public Page<Videojuego> findAll(Pageable pageable);

	public void save(Videojuego videojuego);
	
	public Videojuego findOne(Long id);
	
	public void delete(Long id);
	
	public List<Videojuego> findByTitulo(String term);

}
