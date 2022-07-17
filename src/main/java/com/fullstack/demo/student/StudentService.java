package com.fullstack.demo.student;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fullstack.demo.EmailValidator;
import com.fullstack.demo.exception.ApiRequestException;


@Service
public class StudentService {
	
	@Autowired
	private StudentDAO studentDAO;
	
	@Autowired
	private EmailValidator emailValidator;
	
	List<Student> getAllStudents() {
		return studentDAO.selectAllStudents();
	}
	
	void addNewStudent(Student student) {
		addNewStudent(null, student);
	}

	void addNewStudent(UUID studentId, Student student) {
		UUID newStudentId = Optional.ofNullable(studentId).orElse(UUID.randomUUID());
		
		// TODO: Validate Email
		if ( !emailValidator.test(student.getEmail()) ) {
			throw new ApiRequestException(student.getEmail() + " is not valid");
		}
		
		// TODO: Verify that Email is Unique
		if (studentDAO.isEmailTaken(student.getEmail())) {
			throw new ApiRequestException(student.getEmail() + " is taken");
		}
		
		studentDAO.insertStudent(newStudentId, student);
	}

	public List<StudentCourse> getStudentCourses(UUID studentId) {
		return studentDAO.selectStudentCourses(studentId);
	}
}
