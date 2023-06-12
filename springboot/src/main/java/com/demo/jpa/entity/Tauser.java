package com.demo.jpa.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author huwj
 * @date 2023/2/2 16:50
 */
@Data
@Entity
@Table(name = "tauser")
public class Tauser {

    @Id
    @Column(name = "USERID")
    private String userId;

    @Column(name = "NAME")
    private String name;
}
