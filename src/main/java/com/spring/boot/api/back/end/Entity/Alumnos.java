package com.spring.boot.api.back.end.Entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@JsonPropertyOrder({ "id", "nombre", "direccion" })
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@Table(name = "ALUMNOS")
@Entity(name = "Alumnos")
@NoArgsConstructor
@AllArgsConstructor
public class Alumnos implements Serializable {

	private static final long serialVersionUID = -280865320432528653L;

	@Id
	@Column(name = "ID_ALUMNOS")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqAlumnos")
	@SequenceGenerator(name = "seqAlumnos", allocationSize = 1, sequenceName = "SEQ_ALUMNOS")
	@Positive(message = "El id debe ser positivo")
	private Long id;

	@NotEmpty(message = "no puede estar vacio")
	@Size(min = 2, max = 20, message = "el tamaño tiene que estar entre 4 y 20")
	@Column(name = "NOMBRE")
	private String nombre;

	@NotEmpty(message = "no puede estar vacio")
	@Column(name = "APELLIDO_PATERNO ")
	private String apellido_paterno;

	@NotEmpty(message = "no puede estar vacio")
	@Column(name = "  APELLIDO_MATERNO  ")
	private String apellido_materno;

	@NotNull(message = "no puede estar vacio")
	@Column(name = "FECHA_NACIMIENTO")
	@Temporal(TemporalType.DATE)
	private Date fecha_Nacimiento;

	@NotEmpty(message = "no puede estar vacio")
	@Size(min = 1, max = 8, message = "el tamaño tiene ser 8 digitos")
	@Column(name = "DNI")
	private String dni;

	@NotEmpty(message = "no puede estar vacio")
	@Column(name = "EDAD")
	private String edad;

	@NotEmpty(message = "no puede estar vacio")
	@Column(name = "DIRECCION")
	private String direccion;

	@NotEmpty(message = "no puede estar vacio")
	@Size(min = 1, max = 9, message = "el tamaño tiene ser 9 digitos")
	@Column(name = "TELEFONO")
	private String telefono;

	@NotEmpty(message = "no puede estar vacio")
	@Column(name = "CORREO")
	private String correo;

	@NotNull(message = "el distrito no puede ser vacia")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_DISTRITOS")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Distritos distrito;

	@Column(name = "FOTO")
	private String foto;
}
