package com.tutorial.repository.mongo;

import com.tutorial.domain.mongo.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Jimmy. 2017/12/15  17:30
 */
@Repository
public interface TeacherMongoRepository extends MongoRepository<Teacher, Long> {

        @Query("{'studentList.name':?0}")
        List<Teacher> findByStudentName(String name);

        /**
         * 分页--jpa命名规范查询
         * @param name
         * @param pageable
         * @return
         */
        public Page<Teacher> findByNameLike(String name, Pageable pageable);



        /**
         * 查找除id以外的其他所有
         */
        // @Query("{'id':{'$ne':?0}}")
}
