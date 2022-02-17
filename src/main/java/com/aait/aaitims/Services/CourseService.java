package com.aait.aaitims.Services;

import java.io.IOException;

import java.util.List;
import java.util.Optional;

import com.aait.aaitims.Entity.Courses;
import com.aait.aaitims.Repositories.CoursesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Optionals;
import org.springframework.stereotype.Service;


@Service
public class CourseService {

    @Autowired
    private CoursesRepository courseRepo;

    public void saveCourse(Courses course) throws IOException {
                courseRepo.save(course);
        
    }
    
    public void deleteCourseById(long id) {
        this.courseRepo.deleteById(id);

    }

    public List<Courses> getAllCourses() {
        return courseRepo.findAll();
    }

    public Courses getCourseById(Long id) {
        Optional<Courses> optional = courseRepo.findById(id);
        Courses course = null;
        if (optional.isPresent()) {
            course = optional.get();
        } else {
            throw new RuntimeException(" Course not found for id :: " + id);
        }
        return course;
    }

}
