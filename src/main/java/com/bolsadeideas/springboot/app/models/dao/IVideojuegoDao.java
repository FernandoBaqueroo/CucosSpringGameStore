package com.bolsadeideas.springboot.app.models.dao;
 
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Videojuego;

public interface IVideojuegoDao extends PagingAndSortingRepository<Videojuego, Long>{

	List<Videojuego> findAll();

	void save(Videojuego videojuego);

	Optional <Object> findById(Long id);

	void deleteById(Long id);

	@Query("select p from Videojuego p where p.titulo like %?1%")
    List<Videojuego> findByTitulo(String term);

}
