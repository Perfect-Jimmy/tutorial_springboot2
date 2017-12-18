package com.tutorial.mongo.service.impl;

import com.tutorial.domain.mongo.Student;
import com.tutorial.mongo.service.StudentMongoService;
import com.tutorial.repository.mongo.StudentMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Jimmy. 2017/12/15  17:42
 */
@Service
public class StudentMongoServiceImpl implements StudentMongoService {
    @Autowired
    private StudentMongoRepository studentMongoRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveStudent(Student student) {
        studentMongoRepository.save(student);
    }

    @Override
    public void updateStudent(Student student) {
        studentMongoRepository.save(student);
    }

    @Override
    public void deleteStudent(Student student) {
        studentMongoRepository.delete(student);
    }

    @Override
    public List<Student> findByName(String name) {
        return studentMongoRepository.findByName(name);
    }

    @Override
    public Student findById(String id) {
        return studentMongoRepository.findOne(id);
    }

    @Override
    public List<Student> findStudent(Query query) {
        return mongoTemplate.find(query,Student.class);
    }


}
