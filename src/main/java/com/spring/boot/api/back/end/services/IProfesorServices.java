package com.spring.boot.api.back.end.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.spring.boot.api.back.end.Entity.Cursos;
import com.spring.boot.api.back.end.Entity.Distritos;
import com.spring.boot.api.back.end.Entity.Profesor;

public interface IProfesorServices {

	public List<Profesor> findAll();

	public Page<Profesor> findAll(Pageable pageable);

	public Profesor findById(Long id);

	public Profesor save(Profesor profesor);

	public void delete(Long id);

	/* Distritos */
	public List<Distritos> finAllDistritos();

	/* Cursos */
	public List<Cursos> finAllCursos();

}
