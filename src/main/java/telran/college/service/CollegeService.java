package telran.college.service;

import java.util.List;

import telran.college.dto.LecturerMark;
import telran.college.dto.StudentMark;


public interface CollegeService {
	// returns names of best students
	List<String> bestStudentsSubjectType(String type, int nStudents);

	// returns average score by each student
	List<StudentMark> studentsAvgMarks();
	
	// retrieving names and total lecture hours of a number
	//of the lecturers with most lecture hours
	List<LecturerMark> mostLecturerHours(int nLecturers);
	
    //	 retrieving names and cities of the students having less
    //	than a given number of the scores
	List<String> studentsHavingLessScore(int score);
	
    //	retrieving names and phone numbers of then students 
    //	were born at a given month
	List<String> studentsBornInMonth (int mounth);
	
    //	retrieving all subject names and scores of a
    //	student with a given name
	List<StudentMark> subjectsAndScoresOfStudent (String name);
	
    //	retrieving all lecturer names and phone
    //	numbers from a given city
	List<String> allLecturersByCity (String city);

}
