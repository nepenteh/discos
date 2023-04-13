package com.ejerciciosmesa.discos.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.ejerciciosmesa.discos.models.dao.BandaDAO;
import com.ejerciciosmesa.discos.models.entities.Banda;


@Service
public class BandaServiceImpl implements BandaService {

	private final BandaDAO bandaDAO;
	
	
	
	public BandaServiceImpl(
			
			BandaDAO bandaDAO
			) {
		
		this.bandaDAO = bandaDAO;
	}

	@Transactional(readOnly=true)
	@Override
	public List<Banda> findAll() {
		return (List<Banda>) bandaDAO.findAll();
	}
	
	@Transactional(readOnly=true)
	@Override
	public Page<Banda> findAll(Pageable pageable) {
		return bandaDAO.findAll(pageable);
	}

	@Transactional(readOnly=true)
	@Override
	public Banda findOne(Long id) {
		return bandaDAO.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void save(Banda banda) {
		bandaDAO.save(banda);
	}

	@Transactional
	@Override
	public void remove(Long id) {
		bandaDAO.deleteById(id);
	}
	
	@Transactional(readOnly=true)
	@Override
	public Long count() {
		return bandaDAO.count();
	}
	
	
	
	
}
