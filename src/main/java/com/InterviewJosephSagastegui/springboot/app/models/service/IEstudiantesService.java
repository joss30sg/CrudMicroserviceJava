package com.InterviewJosephSagastegui.springboot.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.InterviewJosephSagastegui.springboot.app.models.entity.Estudiantes;



public interface IEstudiantesService {
	
    public List<Estudiantes> findAll();
    
    public Page<Estudiantes> findAll(Pageable pageable);
	
	public void save (Estudiantes cliente);
	
	public Estudiantes findOne(Long id);

	public void delete(Long id);

	

}
