package com.tutorial.mongo.service.impl;

import com.tutorial.domain.mongo.Student;
import com.tutorial.domain.mongo.Teacher;
import com.tutorial.mongo.service.TeacherMongoService;
import com.tutorial.repository.mongo.TeacherMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jimmy. 2017/12/15  17:46
 */
@Service
public class TeacherMongoServiceImpl implements TeacherMongoService {
    @Autowired
    private TeacherMongoRepository teacherMongoRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public void saveTeacher(Teacher teacher) {
        teacherMongoRepository.save(teacher);
    }

    @Override
    public List<Teacher> queryTeacher(Query query) {
        return mongoTemplate.find(query,Teacher.class);
    }

    @Override
    public List<Teacher> queryByStudentName(String name) {
        return teacherMongoRepository.findByStudentName(name);
    }

    @Override
    public Long count(Query query) {
        return mongoTemplate.count(query, Student.class);
    }

 /*   @Override
    public List<Teacher> pageTeacher(String name,int pageNum, int pageSize, String desc) {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("name").regex("teacher");
        // criteria.and("id").is(1);
        // criteria.and("createTime").gte(d1).lte(d2);
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "id"));
        Sort sort = new Sort(orders);
        query.with(sort);
        query.addCriteria(criteria);
        Pageable pageable = new PageRequest(pageNum,pageSize);
        return mongoTemplate.find(query.with(pageable),Teacher.class);
    }
*/


    @Override
    public Page<Teacher> findByNameLike(String name, int pageNum, int pageSize) {
        Pageable pageable = new PageRequest(pageNum, pageSize);
        return teacherMongoRepository.findByNameLike(name,pageable);
    }

    @Override
    public Page<Teacher> queryPage() {
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("name").regex("teacher");
        // criteria.and("id").is(1);
        // criteria.and("createTime").gte(d1).lte(d2);
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "id"));
        Sort sort = new Sort(orders);
        query.with(sort);
        query.addCriteria(criteria);

        PageRequest page = new PageRequest(0,4);
        System.out.println(mongoTemplate.find(query.with(page),Teacher.class));
        return null;
    }
}
