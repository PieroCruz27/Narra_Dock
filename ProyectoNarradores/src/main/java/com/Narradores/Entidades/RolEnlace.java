package com.Narradores.Entidades;

import java.util.List;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@Table(name="tb_rol_enlace")
public class RolEnlace {
	
	@EmbeddedId
	private RolEnlacePK id;
	
	@ManyToOne
	@JoinColumn(name = "codigo",insertable = false,updatable = false,referencedColumnName ="codigo")
	private Roles roles;

	
	@ManyToOne
	@JoinColumn(name="idenlace",insertable = false,updatable = false,referencedColumnName = "idenlace")
	private Enlace enlace;


	





	public Enlace getEnlace() {
		return enlace;
	}


	public void setEnlace(Enlace enlace) {
		this.enlace = enlace;
	}


	public RolEnlacePK getId() {
		return id;
	}


	public void setId(RolEnlacePK id) {
		this.id = id;
	}
	
	
	
}