package com.bolsadeideas.springboot.app.controllers; 
import java.util.List;
import java.util.Map;

import com.bolsadeideas.springboot.app.models.entity.ItemFactura;
import com.bolsadeideas.springboot.app.models.entity.Videojuego;
import com.bolsadeideas.springboot.app.models.service.IVideojuegoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Factura;
import com.bolsadeideas.springboot.app.models.service.IClienteService;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {
	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IVideojuegoService libroService;
	
	@GetMapping("/form/{clienteId}")
	public String crear(@PathVariable(value="clienteId") Long clienteId, Map<String,Object> model,
			RedirectAttributes flash) {
		Cliente cliente =  clienteService.findOne(clienteId);
		if(cliente==null) {
			flash.addFlashAttribute("error","El cliente no existe en la base de datos");
			return "redirect:/listar";
		}
		Factura factura = new Factura();
		factura.setCliente(cliente);
		
		model.put("factura", factura);
		model.put("titulo", "Creando la factura");
		
		
		return "factura/form";
		
	}

	@PostMapping("/form")
	public String guardar(@Valid Factura factura, BindingResult result, Model model, @RequestParam(name="item_id[]",required = false)Long[] itemId,
						  @RequestParam(name="cantidad[]",required = false)Integer[] cantidad, RedirectAttributes flash, SessionStatus status){
		if(result.hasErrors()){
			model.addAttribute("titulo","Crear Factura");
			return "factura/form";
		}
		if(itemId ==null || itemId.length ==0){
			model.addAttribute("titulo","Crear factura");
			model.addAttribute("error","Error: la factura no puede no tener lineas! ");
			return "factura/form";
		}
		for (int i = 0; i < itemId.length; i++) {
			Videojuego videojuego = clienteService.findVideojuegoById(itemId[i]);
			ItemFactura linea = new ItemFactura();
			linea.setCantidad(cantidad[i]);
			linea.setVideojuego(videojuego);
			factura.addItemFactura(linea);
		}

		clienteService.saveFactura(factura);
		status.setComplete();
		flash.addFlashAttribute("success","Factura creada con exito");
		return"redirect:/ver/"+factura.getCliente().getId();
	}
	
	@GetMapping(value="/cargar-videojuego/{term}", produces= {"application/json"})
	public @ResponseBody List<Videojuego> cargarVideojuegos(@PathVariable String term){
		return clienteService.findByTitulo(term);
	}
}
