package com.bolsadeideas.springboot.app.controllers;
 
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.bolsadeideas.springboot.app.models.entity.Videojuego;
import com.bolsadeideas.springboot.app.models.service.IVideojuegoService;

@Controller
@SessionAttributes("index")
public class IndexController {
	
	@Autowired
    private IVideojuegoService videojuegoService;
	
	@GetMapping(value = "/index")
    public String index(Model model) {
		List<Videojuego> videojuegos = videojuegoService.findAll();
	    model.addAttribute("videojuegos", videojuegos);
	    return "index";
    }
}
