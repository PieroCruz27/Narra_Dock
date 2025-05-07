package com.Narradores.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Narradores.Entidades.Narradores;
import com.Narradores.Entidades.Usuario;
import com.Narradores.Service.NarradoresService;
import com.Narradores.Service.UsuarioService;

@Controller
@RequestMapping("/narradores")
public class NarradorController {
	 @Autowired
	    private NarradoresService narradorService;
	 
	    @Autowired
	    private UsuarioService usuarioService;

	
	    @GetMapping
	    public String listarNarradores(Model model) {
	        List<Narradores> narradores = narradorService.listarNarradores();
	        model.addAttribute("narradores", narradores);
	        model.addAttribute("narrador", new Narradores()); // Para el formulario
	        model.addAttribute("usuarios", usuarioService.listarUsuarios());
	        return "narradores";
	    }

	    @PostMapping("/guardar")
	    public String guardarNarrador(@ModelAttribute Narradores narrador, RedirectAttributes redirectAttributes) {
	        try {
	            narradorService.guardarNarrador(narrador);
	            redirectAttributes.addFlashAttribute("mensaje", "Narrador guardado exitosamente");
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("error", "Error al guardar el narrador: " + e.getMessage());
	        }
	        return "redirect:/narradores";
	    }

		@RequestMapping("/eliminar")
	    public String eliminarNarrador(@RequestParam("codigoEliminar") Integer id, RedirectAttributes redirectAttributes) {
	        try {
	            narradorService.eliminarNarrador(id);
	            redirectAttributes.addFlashAttribute("mensaje", "Narrador eliminado exitosamente");
	        } catch (Exception e) {
	            redirectAttributes.addFlashAttribute("error", "Error al eliminar el narrador: " + e.getMessage());
	        }
	        return "redirect:/narradores";
	    }
	    
	    
	    

	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	}