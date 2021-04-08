package com.spring.boot.api.back.end.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.spring.boot.api.back.end.Entity.Alumnos;
import com.spring.boot.api.back.end.Entity.Distritos;

public interface IAlumnosServices {

	
	public List<Alumnos> findAll();

	public Page<Alumnos> findAll(Pageable pageable);

	public Alumnos findById(Long id);

	public Alumnos save(Alumnos alumno);

	public void delete(Long id);
	
	/* Distritos */
	public List<Distritos> finAllDistritos();
}
