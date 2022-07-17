package com.fullstack.demo.student;

import java.time.LocalDate;
import java.util.UUID;

public class StudentCourse {
	private final UUID studentId;
	private final UUID courseId;
	private final LocalDate startDate;
	private final LocalDate endDate;
	private final Integer grade;
	private final String name;
	private final String description;
	private final String department;
	private final String teachersName;
	
	public StudentCourse(UUID studentId, UUID courseId, LocalDate startDate, LocalDate endDate, Integer grade,
			String name, String description, String department, String teachersName) {
		super();
		this.studentId = studentId;
		this.courseId = courseId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.grade = grade;
		this.name = name;
		this.description = description;
		this.department = department;
		this.teachersName = teachersName;
	}

	public UUID getStudentId() {
		return studentId;
	}

	public UUID getCourseId() {
		return courseId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public Integer getGrade() {
		return grade;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getDepartment() {
		return department;
	}

	public String getTeachersName() {
		return teachersName;
	}
	
	
	
}
