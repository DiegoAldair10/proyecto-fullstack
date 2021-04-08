package com.spring.boot.api.back.end.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.boot.api.back.end.Entity.Alumnos;
import com.spring.boot.api.back.end.Entity.Distritos;
import com.spring.boot.api.back.end.dao.IAlumnosDAO;
import com.spring.boot.api.back.end.services.IAlumnosServices;

@Service
public class IAlumnosServicesImpl implements IAlumnosServices {

	@Autowired
	private IAlumnosDAO alumnoDao;

	@Override
	@Transactional(readOnly = true)
	public List<Alumnos> findAll() {
		return (List<Alumnos>) alumnoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Alumnos> findAll(Pageable pageable) {
		return alumnoDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Alumnos findById(Long id) {
		return alumnoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Alumnos save(Alumnos alumno) {
		return alumnoDao.save(alumno);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		alumnoDao.deleteById(id);

	}

	@Override
	@Transactional
	public List<Distritos> finAllDistritos() {
		return alumnoDao.findAllDistritos();
	}

}
