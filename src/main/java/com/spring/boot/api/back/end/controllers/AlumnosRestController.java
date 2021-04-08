package com.spring.boot.api.back.end.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.boot.api.back.end.Entity.Alumnos;
import com.spring.boot.api.back.end.Entity.Distritos;
import com.spring.boot.api.back.end.services.IAlumnosServices;
import com.spring.boot.api.back.end.services.IUploadFileAlumnosServices;

@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/registro")
public class AlumnosRestController {

	@Autowired
	private IAlumnosServices alumnoService;

	@Autowired
	private IUploadFileAlumnosServices uploadsService;

	@GetMapping("/alumnos")
	public List<Alumnos> index() {
		return alumnoService.findAll();
	}

	@GetMapping("/alumnos/page/{page}")
	public Page<Alumnos> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 6);
		return alumnoService.findAll(pageable);
	}

	@GetMapping("/alumnos/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {
		Alumnos alumno = null;
		Map<String, Object> response = new HashMap<>();
		try {
			alumno = alumnoService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (alumno == null) {
			response.put("mensaje", "El alumno ID".concat(id.toString().concat("no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Alumnos>(alumno, HttpStatus.OK);
	}

	@PostMapping("/alumnos")
	public ResponseEntity<?> create(@Valid @RequestBody Alumnos alumno, BindingResult result) {
		Alumnos alumnoNew = null;
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> erros = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", erros);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

		}
		try {
			alumnoNew = alumnoService.save(alumno);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos ");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		response.put("mensaje", "El auto ha sido creado con éxito!");
		response.put("alumno", alumnoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	@PutMapping("/alumnos/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Alumnos alumno, BindingResult result, @PathVariable Long id) {
		Alumnos alumnoActual = alumnoService.findById(id);
		Alumnos alumnoUpdate = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> erros = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", erros);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

		}

		if (alumnoActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el alumno ID:"
					.concat(id.toString().concat("no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			alumnoActual.setNombre(alumno.getNombre());
			alumnoActual.setApellido_paterno(alumno.getApellido_paterno());
			alumnoActual.setApellido_materno(alumno.getApellido_materno());
			alumnoActual.setFecha_Nacimiento(alumno.getFecha_Nacimiento());
			alumnoActual.setDni(alumno.getDni());
			alumnoActual.setEdad(alumno.getEdad());
			alumnoActual.setDireccion(alumno.getDireccion());
			alumnoActual.setTelefono(alumno.getTelefono());
			alumnoActual.setCorreo(alumno.getCorreo());
			alumnoActual.setFoto(alumno.getFoto());
			alumnoActual.setDistrito(alumno.getDistrito());

			alumnoUpdate = alumnoService.save(alumnoActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el alumno en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El alumno ha sido actualizado con éxito!");
		response.put("alumno", alumnoUpdate);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	@DeleteMapping("/alumnos/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			Alumnos auto = alumnoService.findById(id);
			String nombreFotoAnterior = auto.getFoto();

			uploadsService.eliminar(nombreFotoAnterior);
			alumnoService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el alumno de la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		response.put("mensaje", "El alumno eliminado con éxito!");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

	@GetMapping("/alumnos/distritos")
	public List<Distritos> listarDistritos() {
		return alumnoService.finAllDistritos();
	}

	@PostMapping("/alumnos/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {

		Map<String, Object> response = new HashMap<>();

		Alumnos alumno = alumnoService.findById(id);

		if (!archivo.isEmpty()) {

			String nombreArchivo = null;
			try {

				nombreArchivo = uploadsService.copiar(archivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir imagen del auto");
				response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			String nombreFotoAnterior = alumno.getFoto();
			uploadsService.eliminar(nombreFotoAnterior);
			alumno.setFoto(nombreArchivo);
			alumnoService.save(alumno);

			response.put("alumno", alumno);
			response.put("mensaje", "Has subido correctamente la imagen:" + nombreArchivo);

		}

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	@GetMapping("/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {
		Resource recurso = null;

		try {
			recurso = uploadsService.cargar(nombreFoto);
		} catch (MalformedURLException e) {

			e.printStackTrace();
		}
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");

		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);

	}

}
