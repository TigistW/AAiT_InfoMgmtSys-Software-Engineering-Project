package com.aait.aaitims.Controllers;
import java.util.List;
// import java.util.Optional;

import javax.validation.Valid;

import com.aait.aaitims.Entity.Courses;
import com.aait.aaitims.Entity.User;
import com.aait.aaitims.Entity.UserCourses;
import com.aait.aaitims.Repositories.CoursesRepository;
import com.aait.aaitims.Repositories.UserCoursesRepository;
import com.aait.aaitims.Repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserCoursesController {

  @Autowired
  private UserCoursesRepository ucRepo;

  @Autowired
  private CoursesRepository courseRepo;

  @Autowired
  private UserRepository Repo;


  @GetMapping("/registeredCourses")
  public String addFormCourse(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentPrincipalName = authentication.getName();
    User auth = Repo.findByEmail(currentPrincipalName);
    if (auth == null) {
      return "userCourseView";
    }
    List<UserCourses> courses = ucRepo.findUserById(auth.getId());
    System.out.println(courses);
    model.addAttribute("courses", courses);
    return "registeredCourses";
  }


  @GetMapping("/registeredCourses/{id}")
  public String registerCourse(@PathVariable("id") long id, @ModelAttribute("course") @Valid UserCourses userCourses,
      BindingResult result, Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentPrincipalName = authentication.getName();

    User eUser = Repo.findByEmail(currentPrincipalName);

    // eUser= Repo.findUserById(eUser.getId());

    Courses course = courseRepo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + id));

    if (result.hasErrors()) {
      course.setId(id);
      return "userCourseView";
    }
    userCourses.setUser(eUser);
    userCourses.setCourses(course);

    ucRepo.save(userCourses);

    return "redirect:/registeredCourses";
  }

}
