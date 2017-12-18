package com.tutorial.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by jimmy.
 * 2017/12/14  14:06
 */
@Data
@Entity
@Table(name = "user")
public class Book implements Serializable{

    private static final long serialVersionUID = 3498205413471236405L;
    @Id
    @GeneratedValue
    private String id;

    private String name;
}
