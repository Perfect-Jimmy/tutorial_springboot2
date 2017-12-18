package com.tutorial.domain.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by Jimmy. 2017/12/15  17:26
 */
@Document
@Data
public class Teacher {
    @Id
    private Long id;

    private String name;

    private Integer age;

    List<Student> studentsList;

}