package com.tutorial.repository.mongo;

import com.tutorial.domain.mongo.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Jimmy. 2017/12/15  17:28
 */
@Repository
public interface  StudentMongoRepository  extends MongoRepository<Student, String> {
    List<Student> findByName(@Param("name") String name);
}
