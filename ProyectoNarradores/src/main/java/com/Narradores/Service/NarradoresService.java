package com.Narradores.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Narradores.Dao.NarradoresRepository;
import com.Narradores.Entidades.Narradores;

@Service
public class NarradoresService {
	
    @Autowired
    private NarradoresRepository narradorRepository;

    public List<Narradores> listarNarradores() {
        return narradorRepository.findAll();
    }

    public Narradores guardarNarrador(Narradores narrador) {
        return narradorRepository.save(narrador);
    }

    public Narradores obtenerNarradorPorId(Integer id) {
        return narradorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Narrador no encontrado"));
    }
    
    public void eliminarNarrador(Integer id) {
        narradorRepository.deleteById(id);
    }

    
}
