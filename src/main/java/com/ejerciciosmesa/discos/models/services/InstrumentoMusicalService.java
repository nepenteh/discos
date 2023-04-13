package com.ejerciciosmesa.discos.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



import com.ejerciciosmesa.discos.models.entities.InstrumentoMusical;

public interface InstrumentoMusicalService {
	
	public List<InstrumentoMusical> findAll();
	public Page<InstrumentoMusical> findAll(Pageable pageable);
	public InstrumentoMusical findOne(Long id);
	public void save(InstrumentoMusical instrumentoMusical);
	public void remove(Long id);
	public Long count();
	
	
	
}
