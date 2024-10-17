package com.example.demo.RepoAdmin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.ModelAdmin.userOfAdmin;
@Repository
public interface repositoryAdmin extends JpaRepository<userOfAdmin,Long>{
public userOfAdmin findByUsername(String username);

}
