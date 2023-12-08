package telran.college.service;

import java.util.List;

import telran.college.dto.StudentMark;

public interface CollegeService {
	//returns names of best students
	List<String> bestStudentsSubjectType(String type, int nStudents);
	//returns average score by each student 
	List<StudentMark> studentsAvgMarks();

}
