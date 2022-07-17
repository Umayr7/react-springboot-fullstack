package com.fullstack.demo.student;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	List<Student> selectAllStudents() {
		String sql = "" +
				"SELECT student_id, " +
				"first_name, " +
				"last_name, " +
				"email, " +
				"student_id, " +
				"gender " +
				"FROM student";
		
		return jdbcTemplate.query(sql, mapStudentFromDb());
	}
	
	int insertStudent(UUID studentId, Student student) {
		String sql = "" +
					"INSERT INTO student ("
					+ "student_id, "
					+ "first_name, "
					+ "last_name, "
					+ "email, "
					+ "gender) "
					+ "VALUES (?, ?, ?, ?, ?::gender)";
		return jdbcTemplate.update(
				sql,
				studentId,
				student.getFirstName(),
				student.getLastName(),
				student.getEmail(),
				student.getGender().name().toUpperCase()
		);
	}
	
	private RowMapper<Student> mapStudentFromDb() {
		return (resultSet, i) -> {
			String studentIdStr = resultSet.getString("student_id");
			UUID studentId = UUID.fromString(studentIdStr);

			String first_name = resultSet.getString("first_name");
			String last_name = resultSet.getString("last_name");
			String email = resultSet.getString("email");
			String genderStr = resultSet.getString("gender").toUpperCase();
			
			Student.Gender gender = Student.Gender.valueOf(genderStr);
			
			return new Student(
					studentId, first_name, last_name, email, gender
					);
		};
	}

	@SuppressWarnings("ConstantConditions")
	boolean isEmailTaken(String email) {
		String sql = "SELECT EXISTS ("
				+ "SELECT 1 "
				+ "FROM student "
				+ "WHERE email = ?"
				+ ");";
		
		return jdbcTemplate.queryForObject(
				sql,
				(resultSet, i) -> resultSet.getBoolean(1),
				new Object[] {email}
		);
	}

	public List<StudentCourse> selectStudentCourses(UUID studentId) {
		String sql = ""
				+ "SELECT "
				+ "student.student_id, "
				+ "course.course_id, "
				+ "course.name, "
				+ "course.description, "
				+ "course.department, "
				+ "course.teacher_name, "
				+ "student_course.start_date, "
				+ "student_course.end_date, "
				+ "student_course.grade "
				+ "FROM student "
				+ "JOIN student_course USING (student_id) "
				+ "JOIN course USING (course_id) "
				+ "WHERE student.student_id = ?;";
		
		
		return jdbcTemplate.query(
				sql,
				mapStudentCourseFromDb(),
				new Object[]{studentId}
		);
	}
	
	private RowMapper<StudentCourse> mapStudentCourseFromDb() {
		return (resultSet, i) -> {
			String studentIdStr = resultSet.getString("student_id");
			String courseIdStr = resultSet.getString("course_id");
			String name = resultSet.getString("name");
			String description = resultSet.getString("description");
			String department = resultSet.getString("department");
			String teachersName = resultSet.getString("teacher_name");
			LocalDate start_date = resultSet.getDate("start_date").toLocalDate();
			LocalDate end_date = resultSet.getDate("end_date").toLocalDate();
			String grade = resultSet.getString("grade");
			
			return new StudentCourse(
					UUID.fromString(studentIdStr),
					UUID.fromString(courseIdStr),
					start_date,
					end_date,
					Optional.ofNullable(grade)
					.map(Integer::parseInt)
					.orElse(null),
					name,
					description,
					department,
					teachersName
			);
		};
	}
}
