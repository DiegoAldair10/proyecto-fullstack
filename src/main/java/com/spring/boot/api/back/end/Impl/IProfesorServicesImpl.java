package com.spring.boot.api.back.end.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.boot.api.back.end.Entity.Cursos;
import com.spring.boot.api.back.end.Entity.Distritos;
import com.spring.boot.api.back.end.Entity.Profesor;
import com.spring.boot.api.back.end.dao.IProfesorDAO;
import com.spring.boot.api.back.end.services.IProfesorServices;

@Service
public class IProfesorServicesImpl implements IProfesorServices {

	@Autowired
	private IProfesorDAO profesorDao;

	@Override
	@Transactional(readOnly = true)
	public List<Profesor> findAll() {
		return (List<Profesor>) profesorDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Profesor> findAll(Pageable pageable) {
		return profesorDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Profesor findById(Long id) {
		return profesorDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Profesor save(Profesor profesor) {
		return profesorDao.save(profesor);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		profesorDao.deleteById(id);
	}

	/*Distritos*/
	@Override
	@Transactional
	public List<Distritos> finAllDistritos() {
		return profesorDao.findAllDistritos();
	}

	/*Cursos*/
	@Override
	@Transactional
	public List<Cursos> finAllCursos() {
		return profesorDao.findAllCursos();
	}

}
