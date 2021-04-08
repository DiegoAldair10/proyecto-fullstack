package com.spring.boot.api.back.end.Entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@JsonPropertyOrder({ "id", "nombre" })
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@Table(name = "CURSOS")
@Entity(name = "Cursos")
@NoArgsConstructor
@AllArgsConstructor
public class Cursos implements Serializable {

	private static final long serialVersionUID = -8469137530102720616L;

	@Id
	@Column(name = "ID_CURSOS")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqCursos")
	@SequenceGenerator(name = "seqCursos", allocationSize = 1, sequenceName = "SEQ_CURSOS")

	@Positive(message = "El id debe ser positivo")
	private Long id;

	@Column(name = "NOMBRE")
	private String nombre;
}
