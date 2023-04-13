package com.ejerciciosmesa.discos.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



import com.ejerciciosmesa.discos.models.entities.Disco;

public interface DiscoService {
	
	public List<Disco> findAll();
	public Page<Disco> findAll(Pageable pageable);
	public Disco findOne(Long id);
	public void save(Disco disco);
	public void remove(Long id);
	public Long count();
	
	
	
}
