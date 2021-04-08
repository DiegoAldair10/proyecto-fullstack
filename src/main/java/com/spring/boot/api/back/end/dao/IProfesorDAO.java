package com.spring.boot.api.back.end.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.boot.api.back.end.Entity.Cursos;
import com.spring.boot.api.back.end.Entity.Distritos;
import com.spring.boot.api.back.end.Entity.Profesor;

public interface IProfesorDAO extends JpaRepository<Profesor, Long> {

	@Query("from Distritos")
	public List<Distritos> findAllDistritos();

	@Query("from Cursos")
	public List<Cursos> findAllCursos();

}
