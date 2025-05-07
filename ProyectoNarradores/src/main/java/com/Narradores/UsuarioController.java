package com.Narradores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Narradores.Entidades.Enlace;
import com.Narradores.Entidades.Roles;
import com.Narradores.Entidades.Usuario;
import com.Narradores.Service.RolesServices;
import com.Narradores.Service.UsuarioService;

@SessionAttributes({"datosUsuario","ENLACES","esAdmin"})
@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	
	@Autowired
	private UsuarioService servicio;
	@Autowired
	private RolesServices rolService;
	//
	@Autowired
    private ResourceLoader resourceLoader;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	
	   // Muestra el formulario de registro
    @GetMapping("/login")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("rolesxd", rolService.listaRoles());
        return "login";  // registro.html
    }
	@RequestMapping("/principal")
	public String index(Authentication auth, Model x) {

		String vLogin = auth.getName();
		//Invocar al método validar sesión
		Usuario bean = servicio.validarSesion(vLogin);
		//Invocar al método enlaces del usuario
		List<Enlace> lista = servicio.retornarEnlacesUsuario(bean.getRoles().getCodigo());
		//Asignar valor a los atributos de tipo sesión
		x.addAttribute("datosUsuario",bean.getApellido()+" "+bean.getNombre());
		x.addAttribute("ENLACES", lista);
		 x.addAttribute("esAdmin", bean.getRoles().getNombre().equalsIgnoreCase("ADMIN"));
		
		return "principal";
	}
	
	
    @GetMapping("/resources/**")
    public ResponseEntity<Resource> getResource() {
        String resourcePath = "classpath:/static" + "/resources/"; // Obtén la ruta del recurso específico que necesites

        Resource resource = resourceLoader.getResource(resourcePath);

        // Lógica adicional según tus necesidades, como leer o manipular el recurso

        return ResponseEntity.ok().body(resource);
    }
	

    @PostMapping("/registroUsu")
	public String registroUsu( @RequestParam("codigo")Integer cod,
			  @RequestParam("nombre")String nom,
			  @RequestParam("apellido")String ape,
						  @RequestParam("correo")String log,
						  @RequestParam("contrasena")String cla,
						  @RequestParam("role")Integer codRol,
						  RedirectAttributes redirect){
		
			try {
				Usuario u=new Usuario();
				u.setNombre(nom);
				u.setApellido(ape);
				u.setCorreo(log);
				String claveCifrada = passwordEncoder.encode(cla);
				u.setContrasena(claveCifrada);
				
				Roles r=new Roles();
				r.setCodigo(codRol);
				
				u.setRoles(r);
				
				if(cod==0) {
					servicio.registrar(u);
					redirect.addFlashAttribute("MENSAJE","Usuario registrado");
				}//ACTUALIZAR ALUMNO
		
				
			} catch (Exception e) {
				redirect.addFlashAttribute("MENSAJE","Error en el registro");
				e.printStackTrace();
			}
			return "redirect:/usuario/login";
	}
    
    //VALIDAR POR CORREO
    @GetMapping("/buscaUsuarioPorCorreo")
    @ResponseBody
    public String validaCorreo(@RequestParam("correo") String correo) {
        boolean existe = servicio.buscarPorCorreo(correo);
        if (!existe) {
            return "{\"valid\" : true }"; // El correo no está en uso
        } else {
            return "{\"valid\" : false }"; // El correo ya está en uso
        }
    }

}
