package com.demo.jpa.controller;

import com.demo.jpa.repository.RepositoryTauser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huwj
 * @date 2023/2/2 16:55
 */
@RestController
public class TauerController {

    @Autowired
    private RepositoryTauser repositoryTauser;

    @GetMapping("findUser")
    public Object findUser(){
        return repositoryTauser.findAll();
    }

    @GetMapping("findById")
    public Object findById(String id){
        return repositoryTauser.findById(id);
    }

    @GetMapping("findByName")
    public Object findByName(String name){
        return repositoryTauser.findByName(name);
    }

    @GetMapping("queryNames")
    public Object queryNames(){
        return repositoryTauser.queryNames();
    }
}
