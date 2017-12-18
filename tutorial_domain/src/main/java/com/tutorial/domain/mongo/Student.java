package com.tutorial.domain.mongo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Jimmy. 2017/12/15  17:12
 */
@Document
@Data
public class Student {
    @Id
    public String id;

    public String name;

    public Integer age;

    public String[] hobbyArray;

}