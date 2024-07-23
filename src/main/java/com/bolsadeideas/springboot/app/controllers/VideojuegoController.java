package com.bolsadeideas.springboot.app.controllers;
 
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Videojuego;
import com.bolsadeideas.springboot.app.models.service.IUploadFileService;
import com.bolsadeideas.springboot.app.models.service.IVideojuegoService;
import com.bolsadeideas.springboot.app.util.paginator.PageRender;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("videojuego")
public class VideojuegoController {

    @Autowired
    private IVideojuegoService videojuegoService;

    @Autowired
    private IUploadFileService uploadFileService;

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final static String UPLOADS_FOLDER = "uploads";

    @GetMapping(value="/uploads/{filename:.+}")
    public ResponseEntity<Resource> verImagen(@PathVariable String filename) {
        Resource recurso = null;
        try {
            recurso = uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"").body(recurso);
    }

    @GetMapping(value="/verVideojuego/{id}")
    public String ver(@PathVariable(value="id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Videojuego videojuego = videojuegoService.findOne(id);
        if (videojuego == null) {
            flash.addFlashAttribute("error", "El videojuego no existe en nuestra BBDD");
            return "redirect:/listarVideojuegos";
        }
        model.put("videojuego", videojuego);
        model.put("titulo", "Detalles del Videojuego: " + videojuego.getTitulo() + ", " + videojuego.getDesarrollador());
        return "verVideojuego";
    }

    @RequestMapping(value = "/listarVideojuegos", method = RequestMethod.GET)
    public String listar(@RequestParam(name="page", defaultValue="0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page, 6);
        Page<Videojuego> videojuegos = videojuegoService.findAll(pageRequest);

        PageRender<Videojuego> pageRender = new PageRender<>("/listarVideojuegos", videojuegos);

        model.addAttribute("titulo", "Listado de videojuegos por página");
        model.addAttribute("videojuegos", videojuegos);
        model.addAttribute("page", pageRender);
        return "listarVideojuegos";
    }

    @RequestMapping(value = "/formVideojuegos")
    public String crear(Map<String, Object> model) {
        Videojuego videojuego = new Videojuego();
        model.put("videojuego", videojuego);
        model.put("titulo", "Formulario de Videojuegos");
        return "formVideojuegos";
    }

    @RequestMapping(value = "/formVideojuegos/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Videojuego videojuego = null;

        if (id > 0) {
            videojuego = videojuegoService.findOne(id);
            if (videojuego == null) {
                flash.addFlashAttribute("error", "El ID del videojuego no existe en la BBDD!");
                return "redirect:/listarVideojuegos";
            }
        } else {
            flash.addFlashAttribute("error", "El ID del videojuego no puede ser cero!");
            return "redirect:/listarVideojuegos";
        }
        model.put("videojuego", videojuego);
        model.put("titulo", "Editar Videojuego");
        return "formVideojuegos";
    }

    @RequestMapping(value = "/formVideojuegos", method = RequestMethod.POST)
    public String guardar(@Valid Videojuego videojuego, BindingResult result, Model model, SessionStatus status, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Videojuego");
            return "formVideojuegos";
        }

        String mensajeFlash = (videojuego.getId() != null) ? "Videojuego editado con éxito!" : "Videojuego creado con éxito!";
        videojuegoService.save(videojuego);
        status.setComplete();
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:listarVideojuegos";
    }

    @RequestMapping(value = "/eliminarVideojuego/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        if (id > 0) {
            Videojuego videojuego = videojuegoService.findOne(id);
            videojuegoService.delete(id);

            flash.addFlashAttribute("info", "El videojuego " + videojuego.getTitulo() + " ha sido eliminada con éxito");

        }

        return "redirect:/listarVideojuegos";
    }
}
