package com.example.demo.Repository;

import com.example.demo.entity.Tests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TestsRepository extends JpaRepository<Tests, Long> {
    List<Tests> findByTestsNameIn(Set<String> testNames);

    List<Tests> findByTestsName(String testsName);

    @Query("SELECT t FROM Tests t WHERE t.testsName = :testsName")
    Tests searchByTestsName(@Param("testNames") String testsName);


    Optional<Tests> findById(Long id);

    @Query("SELECT t.testsName FROM Tests t")
    List<String> findAllTestsNames();

    @Query("SELECT MAX(t.id) FROM Tests t")
    Long findMaxTestsId();

}
