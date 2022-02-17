package com.aait.aaitims.Controllers;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;

// import javax.sound.sampled.Line;
import javax.validation.Valid;

import com.aait.aaitims.Entity.Courses;
import com.aait.aaitims.Entity.User;
import com.aait.aaitims.Entity.UserCourses;
import com.aait.aaitims.Repositories.CoursesRepository;
import com.aait.aaitims.Repositories.UserCoursesRepository;
import com.aait.aaitims.Repositories.UserRepository;
import com.aait.aaitims.Services.CourseService;

import com.aait.aaitims.Services.CourseService;
import com.aait.aaitims.Services.CourseService;
import com.aait.aaitims.Services.CourseService;
import com.aait.aaitims.Services.CourseService;
import com.aait.aaitims.Services.CourseService;
import com.aait.aaitims.Services.CourseService;
// import org.hibernate.query.criteria.internal.expression.function.AggregationFunction.LEAST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
// @CrossOrigin("http://localhost:8080")

public class Coursescontroller {

  public static String uploadDirectory = System.getProperty("user.dir") + "/uploads";

  @Autowired
  private CoursesRepository courseRepo;

  @Autowired
  private UserCoursesRepository ucRepo;

  @Autowired
  private CourseService courseServ;

  @Autowired
  private UserRepository Repo;

  // @RequestMapping("/userCourseView")
  // public String viewcoursePage(Model model) {
  //   List<Courses> course= courseRepo.findAll();
  //   model.addAttribute("course", course);
  //   return "userCourseView";
  // }
  
  @GetMapping("/userCourseView")
  public String viewCourseUserListPage(Model model) {
    model.addAttribute("courseList", courseServ.getAllCourses());
    return "userCourseView";
  }

  @GetMapping("/addCoursePage")
  public String showNewCourseFormPage(Model model) {
    Courses course = new Courses();
    model.addAttribute("course", course);
    return "addCoursePage";
  }


  @PostMapping("/saveCourse")
  public String addCourse(@ModelAttribute("course") Courses course) {
    try {
      courseServ.saveCourse(course);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "redirect:/adminCourseView";
  }

  @GetMapping("/adminCourseView")
  public String viewCourseListPage(Model model) {
    model.addAttribute("courseList", courseServ.getAllCourses());
    return "adminCourseView";
  }
 

  @GetMapping("/deleteCourse/{id}")
  public String addCourse(@PathVariable("id") long id, @ModelAttribute("course") Courses course){
  courseServ.deleteCourseById(id);
  return "redirect:/adminCourseView";
  }

  @GetMapping("/updateCourse/{id}")
  public String updateCourse(@PathVariable("id") long id, Model model) {
      Courses courses = courseServ.getCourseById(id);
      model.addAttribute("courses", courses);
    return "updateCourse";
  }
  
  // @PostMapping("/updateCourse/{id}")
  // public  updateCourse(@PathVariable("id") long id, @Valid Courses courses,
  //     BindingResult result, Model model) {
  //   Courses existCourse = courseRepo.findById(id)
  //       .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
  //   // if (result.hasErrors()) {
  //   //   courses.setId(id);
  //   //   return "adminCourseView";
  //   // }
  //   courses.setId(existCourse.getId());
  //   try {
  //     courseServ.saveCourse(courses);
  //   } catch (IOException e) {
  //     // TODO Auto-generated catch block
  //     e.printStackTrace();
  //   }
  //   return "redirect:/adminCourseView";
  // }

  @PostMapping("/updaCourse/{id}")
  public String updateCourse(@PathVariable("id") long id, @ModelAttribute("courses") Courses courses) {
    try {
      courseServ.saveCourse(courses);
      // courseServ.deleteCourseById(id);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "redirect:/adminCourseView";
  }

  // @GetMapping("/deleteAdvisor/{id}")
  // public String deleteAdvisor(@PathVariable("id") Long id) {
  //   advisorService.deleteAdvisorById(id);
  //   return "redirect:/advisoryLIst";
  // }

  // @GetMapping("/list")
  // public String listpage(Model model) {
  //   model.addAttribute("courses", new Courses());
  //   {
  //     return "list";
  //   }
  // }

  // @GetMapping("/admin/edit/{id}")
  // public String updatepage(@PathVariable long id, Model model) {
  //   Courses courses = courseRepo.findById(id)
  //       .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

  //   model.addAttribute("courses", courses);
  //   return "edit";
  // }

  // @PostMapping("/admin/{id}")
  // public String updateUser(@PathVariable("id") long id, @Valid Courses courses,
  //     BindingResult result, Model model) {
  //   Courses existCourse = courseRepo.findById(id)
  //       .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
  //   if (result.hasErrors()) {
  //     courses.setId(id);
  //     return "edit";
  //   }
  //   courses.setId(existCourse.getId());
  //   courseRepo.save(courses);

  //   return "redirect:/admin";
  // }

  // @GetMapping("/delete/{id}")
  // public String deleteUser(@PathVariable("id") long id, Model model) {
  //   Courses courses = courseRepo.findById(id)
  //       .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
  //   courseRepo.delete(courses);
  //   return "redirect:/admin";
  // }

  // @GetMapping("/course/{id}")
  // public String showUpdateForm(@PathVariable("id") long id, Model model) {
  //   Courses courses = courseRepo.findById(id)
  //       .orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + id));

  //   model.addAttribute("courses", courses);
  //   // ucRepo.save(courses);

  //   return "detail";
  // }

  // @PostMapping("/admin")
  // public String addNewCourse(@Valid Courses courses,
  //     @RequestParam("title") String title,
  //     @RequestParam("description") String description,
  //     @RequestParam("file") MultipartFile file) {
  //   try {
  //     String fileTempo = file.getOriginalFilename();
  //     String fileName = fileTempo.replaceAll(" ", "_");
  //     String filePath = Paths.get(uploadDirectory, fileName).toString();
  //     String fileType = file.getContentType();
  //     long size = file.getSize();
  //     String fileSize = String.valueOf(size);
  //     Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
  //     fileName = "/uploads/" + fileName;

  //     // Save the file locally
  //     BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
  //     stream.write(file.getBytes());
  //     stream.close();
  //     courses.setTitle(title);
  //     courses.setDescription(description);
     
  //     courseRepo.save(courses);

  //   } catch (IOException e) {
  //     e.printStackTrace();

  //   }
  //   return "redirect:/admin";
  // }

  // @GetMapping("/learnCourse/{id}")
  // public String showFileView(@PathVariable("id") long id, Model model) {
  //   Courses courses = courseRepo.findById(id)
  //       .orElseThrow(() -> new IllegalArgumentException("Invalid course Id:" + id));

  //   model.addAttribute("courses", courses);
  //   // ucRepo.save(courses);

  //   return "watchVideo";
  // }
}
