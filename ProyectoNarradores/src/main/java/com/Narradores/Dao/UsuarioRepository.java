package com.Narradores.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Narradores.Entidades.Enlace;
import com.Narradores.Entidades.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	@Query("SELECT u FROM Usuario u WHERE u.correo = ?1")
	public Optional<Usuario> iniciarSesionCorreo(String correo);
    
    //pponer los mismsoa tributos que la clase dicha enlace role rol enlace
	@Query("select e from RolEnlace re join re.enlace e where re.roles.id=?1")
	public List<Enlace> retornarEnlaces(int codigo);
	
	
	   // Método para buscar un usuario por correo
    @Query("SELECT u FROM Usuario u WHERE u.correo = ?1")
    public Optional<Usuario> buscarPorCorreo(String correo);
    


}
