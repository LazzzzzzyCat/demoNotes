package com.demo.jpa.repository;

import com.demo.jpa.entity.Tauser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huwj
 * @date 2023/2/2 16:51
 */
@Repository
public interface RepositoryTauser extends JpaRepository<Tauser, String> {

    List<Tauser> findByName(String name);

    @Query(value = "select name from tauser", nativeQuery = true)
    List<String> queryNames();
}
