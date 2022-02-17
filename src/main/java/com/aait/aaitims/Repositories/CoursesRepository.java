package com.aait.aaitims.Repositories;

import com.aait.aaitims.Entity.Courses;

// import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, Long> {

  // @Query(value = "SELECT u FROM User u WHERE u.email= ?1")
  // public User findByEmail(String email);

}
