package com.aait.aaitims.Controllers;
import javax.validation.Valid;

import com.aait.aaitims.Entity.User;
import com.aait.aaitims.Repositories.CoursesRepository;
import com.aait.aaitims.Repositories.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {

  @Autowired
  private UserRepository repo;

  @Autowired
  private CoursesRepository courseRepo;

  // @GetMapping("")
  // public String viewHomePage() {
  //   return "";
  // }
  
  // @PostMapping("/login")
  // public String viewHomeLoginPage() {
  //   return "noticeViewUser";
  // }

  // @GetMapping("/dashboard")
  // public String enrolledform(@PathVariable("id") long id, Model model) {
  //   Courses courses = courseRepo.findById(id)
  //       .orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + id));

  //   model.addAttribute("courses", courses);

  //   return "userdashboard";
  // }

  @GetMapping("/apply")
  public String showapplypage(Model model) {
    model.addAttribute("user", new User());

    return "apply";

  }

  @PostMapping("/register")
  public String userRegistration(final @Valid User user, final BindingResult bindingResult, final Model model) {
    boolean eUser = repo.findAll().isEmpty();
    System.out.println(eUser);
    if (bindingResult.hasErrors()) {
      model.addAttribute("apply", user);
      return "apply";
    }
    try {
      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
      String encodedPassword = encoder.encode(user.getPassword());
      user.setPassword(encodedPassword);
      // List<Courses> courses = courseRepo.findAll();
      // model.addAttribute("courses", courses);

      if (eUser) {
        user.setRole("Admin");
      } else {
        user.setRole("user");
      }

      repo.save(user);

    } catch (Exception e) {
      bindingResult.rejectValue("email", "user.email", "An account already exists for this email.");
      model.addAttribute("apply", user);
      return "apply";
    }
    return "noticeViewUser";
  }

  @GetMapping("/Home")
  public String courseList(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentPrincipalName = authentication.getName();

    User auth = repo.findByEmail(currentPrincipalName);
    String role = (String) auth.getRole();

    if (!role.equals("Admin")) {
      return "redirect:/noticeViewUser";
      
    } else {
      model.addAttribute("courses", courseRepo.findAll());
      return "adminPage";
    }
  }

}
// @GetMapping("/noticeViewUser")
// 	String showNotice(Model map) {
// 		List<NoticeBoard> notice = noticeboardService.getAllActiveNotices();
// 		map.addAttribute("notice", notice);
// 		return "noticeViewUser";
// 	}