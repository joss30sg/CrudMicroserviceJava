package com.InterviewJosephSagastegui.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.InterviewJosephSagastegui.springboot.app.models.dao.IEstudianteDao;
import com.InterviewJosephSagastegui.springboot.app.models.entity.Estudiantes;


@Service
public class EstudianteServiceImpl  implements IEstudiantesService {
	
	@Autowired
	private IEstudianteDao IEstudianteDao;

	@Override
	@Transactional(readOnly = true) // Anotacion que solo permite leer el metodo.
	public List<Estudiantes> findAll() {
		
		return (List<Estudiantes>) IEstudianteDao.findAll();
	}

	@Override
	@Transactional
	public void save(Estudiantes estudiante) {
		IEstudianteDao.save(estudiante);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Estudiantes findOne(Long id) {
		
		return IEstudianteDao.findById(id).orElse(null);
	}

	@Override
	@Transactional()
	public void delete(Long id) {
		IEstudianteDao.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Estudiantes> findAll(Pageable pageable) {
		
		return IEstudianteDao.findAll(pageable);
	}
	

}