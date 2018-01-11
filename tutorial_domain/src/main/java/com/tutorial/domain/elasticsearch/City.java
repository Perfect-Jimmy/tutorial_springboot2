package com.tutorial.domain.elasticsearch;


import lombok.Data;

import java.io.Serializable;

/**
 * Created by Jimmy. 2018/1/8  22:03
 */
//@Document(indexName = "city_es", type = "city")
@Data
public class City implements Serializable {
    private static final long serialVersionUID = -3816669077950136539L;

   // @Id
    private Long id;

    private String name;

}
