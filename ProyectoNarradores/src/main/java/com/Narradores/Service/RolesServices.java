package com.Narradores.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Narradores.Dao.RolesRepository;
import com.Narradores.Entidades.Roles;

@Service
public class RolesServices {
	
	@Autowired
	private RolesRepository repo;
	
	 public List<Roles> listaRoles() {
	        return repo.findAll();
	    }
	

}
