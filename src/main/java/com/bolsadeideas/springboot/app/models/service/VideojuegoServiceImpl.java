package com.bolsadeideas.springboot.app.models.service;
  
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.bolsadeideas.springboot.app.models.dao.IVideojuegoDao;
import com.bolsadeideas.springboot.app.models.entity.Videojuego;

@Service
public class VideojuegoServiceImpl implements IVideojuegoService {

    @Autowired
    private IVideojuegoDao videojuegoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Videojuego> findAll() {
        return (List<Videojuego>) videojuegoDao.findAll();
    }

    @Override
    @Transactional
    public void save(Videojuego videojuego) {
        videojuegoDao.save(videojuego);
    }

    @Override
    @Transactional(readOnly = true)
    public Videojuego findOne(Long id) {
        return (Videojuego) videojuegoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        videojuegoDao.deleteById(id);
    }

    @Override
    public List<Videojuego> findByTitulo(String term) {
        return videojuegoDao.findByTitulo(term);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Videojuego> findAll(Pageable pageable) {
        return videojuegoDao.findAll(pageable);
    }
}
