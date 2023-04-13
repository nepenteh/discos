package com.ejerciciosmesa.discos.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



import com.ejerciciosmesa.discos.models.entities.Banda;

public interface BandaService {
	
	public List<Banda> findAll();
	public Page<Banda> findAll(Pageable pageable);
	public Banda findOne(Long id);
	public void save(Banda banda);
	public void remove(Long id);
	public Long count();
	
	
	
}
