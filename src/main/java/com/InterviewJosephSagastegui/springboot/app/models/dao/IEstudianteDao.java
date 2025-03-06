package com.InterviewJosephSagastegui.springboot.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.InterviewJosephSagastegui.springboot.app.models.entity.Estudiantes;

public interface IEstudianteDao extends PagingAndSortingRepository<Estudiantes, Long> {

}