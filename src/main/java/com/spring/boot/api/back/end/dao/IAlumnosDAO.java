package com.spring.boot.api.back.end.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spring.boot.api.back.end.Entity.Alumnos;
import com.spring.boot.api.back.end.Entity.Distritos;

public interface IAlumnosDAO extends JpaRepository<Alumnos, Long> {

	@Query("from Distritos")
	public List<Distritos> findAllDistritos();
}
