package com.aait.aaitims.Repositories;

import java.util.List;

import com.aait.aaitims.Entity.User;
import com.aait.aaitims.Entity.UserCourses;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCoursesRepository extends JpaRepository<UserCourses, Long> {

  List<UserCourses> findUserCoursesByUserOrderById(User user);

  List<UserCourses> findAllByOrderByIdDesc();

  @Query(value = "FROM UserCourses u WHERE u.user.id= :id")
  public List<UserCourses> findUserById(Long id);

}
