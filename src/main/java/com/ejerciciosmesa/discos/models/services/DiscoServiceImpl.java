package com.ejerciciosmesa.discos.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.ejerciciosmesa.discos.models.dao.DiscoDAO;
import com.ejerciciosmesa.discos.models.entities.Disco;


@Service
public class DiscoServiceImpl implements DiscoService {

	private final DiscoDAO discoDAO;
	
	
	
	public DiscoServiceImpl(
			
			DiscoDAO discoDAO
			) {
		
		this.discoDAO = discoDAO;
	}

	@Transactional(readOnly=true)
	@Override
	public List<Disco> findAll() {
		return (List<Disco>) discoDAO.findAll();
	}
	
	@Transactional(readOnly=true)
	@Override
	public Page<Disco> findAll(Pageable pageable) {
		return discoDAO.findAll(pageable);
	}

	@Transactional(readOnly=true)
	@Override
	public Disco findOne(Long id) {
		return discoDAO.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void save(Disco disco) {
		discoDAO.save(disco);
	}

	@Transactional
	@Override
	public void remove(Long id) {
		discoDAO.deleteById(id);
	}
	
	@Transactional(readOnly=true)
	@Override
	public Long count() {
		return discoDAO.count();
	}
	
	
	
	
}
