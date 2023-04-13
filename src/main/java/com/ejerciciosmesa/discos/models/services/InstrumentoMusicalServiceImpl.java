package com.ejerciciosmesa.discos.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.ejerciciosmesa.discos.models.dao.InstrumentoMusicalDAO;
import com.ejerciciosmesa.discos.models.entities.InstrumentoMusical;


@Service
public class InstrumentoMusicalServiceImpl implements InstrumentoMusicalService {

	private final InstrumentoMusicalDAO instrumentoMusicalDAO;
	
	
	
	public InstrumentoMusicalServiceImpl(
			
			InstrumentoMusicalDAO instrumentoMusicalDAO
			) {
		
		this.instrumentoMusicalDAO = instrumentoMusicalDAO;
	}

	@Transactional(readOnly=true)
	@Override
	public List<InstrumentoMusical> findAll() {
		return (List<InstrumentoMusical>) instrumentoMusicalDAO.findAll();
	}
	
	@Transactional(readOnly=true)
	@Override
	public Page<InstrumentoMusical> findAll(Pageable pageable) {
		return instrumentoMusicalDAO.findAll(pageable);
	}

	@Transactional(readOnly=true)
	@Override
	public InstrumentoMusical findOne(Long id) {
		return instrumentoMusicalDAO.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void save(InstrumentoMusical instrumentoMusical) {
		instrumentoMusicalDAO.save(instrumentoMusical);
	}

	@Transactional
	@Override
	public void remove(Long id) {
		instrumentoMusicalDAO.deleteById(id);
	}
	
	@Transactional(readOnly=true)
	@Override
	public Long count() {
		return instrumentoMusicalDAO.count();
	}
	
	
	
	
}
