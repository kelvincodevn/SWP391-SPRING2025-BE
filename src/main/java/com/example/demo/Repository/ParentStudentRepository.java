package com.example.demo.Repository;

import com.example.demo.entity.ParentStudent;
import com.example.demo.entity.ParentStudentId;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParentStudentRepository extends JpaRepository<ParentStudent, ParentStudentId> {
    List<ParentStudent> findByParent(User parent);
    List<ParentStudent> findByStudent(User student);
}