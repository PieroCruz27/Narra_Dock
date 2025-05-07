package com.Narradores.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Narradores.Dao.UsuarioRepository;
import com.Narradores.Entidades.Enlace;
import com.Narradores.Entidades.Usuario;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	   public List<Usuario> listarUsuarios() {
	        return usuarioRepo.findAll();
	    }
	   
		 public Usuario validarSesion(String correo) {
			    return usuarioRepo.iniciarSesionCorreo(correo)
			            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + correo));
			}
		 
			
			public List<Enlace> retornarEnlacesUsuario(int codigo){
				return usuarioRepo.retornarEnlaces(codigo);
			}
			
			public List<Usuario> listaUsuarios() {
				return usuarioRepo.findAll();
			}
			
			
			
			public void registrar(Usuario bean) {
				usuarioRepo.save(bean);
				
			}
				
			//validacion del correo existente
			public boolean buscarPorCorreo(String correo) {
		        return usuarioRepo.buscarPorCorreo(correo).isPresent();
		    }
}

