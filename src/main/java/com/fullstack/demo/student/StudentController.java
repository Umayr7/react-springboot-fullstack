package com.fullstack.demo.student;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fullstack.demo.exception.ApiRequestException;

@RestController
@RequestMapping("api/students")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping
	public List<Student> getStudents() {
//		throw new ApiRequestException("CUSTOM EXCEPTION::Error fetching students Data.");
//		throw new IllegalStateException("Error fetching students Data.");
		return studentService.getAllStudents();
	}
	
	@PostMapping
	public void addNewStudent(@Valid @RequestBody Student student) {
		studentService.addNewStudent(student);
	}
	
	@GetMapping(path = "{studentId}/courses")
	public List<StudentCourse> getStudentCourses(@PathVariable("studentId") UUID studentId) {
		System.out.println(studentId);
		return studentService.getStudentCourses(studentId);
	}
}
