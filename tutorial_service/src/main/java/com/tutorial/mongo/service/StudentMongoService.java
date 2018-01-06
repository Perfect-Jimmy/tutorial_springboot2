package com.tutorial.mongo.service;

import com.tutorial.domain.mongo.Student;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

/**
 * Created by Jimmy. 2017/12/15  17:35
 */
public interface StudentMongoService {
    public void saveStudent(Student student);
    public void updateStudent(Student student);
    public void deleteStudent(Student student);
    public List<Student> findByName(String name);
    public Optional<Student> findById(String id);

    public List<Student> findStudent(Query query);
}
