package telran.college.service;

import java.util.List;

import telran.college.dto.LecturerHours;
import telran.college.dto.NamePhone;
import telran.college.dto.NameScore;
import telran.college.dto.StudentCity;



public interface CollegeService {
	// returns names of best students
	List<String> bestStudentsSubjectType(String type, int nStudents);

	// returns average score by each student
	List<NameScore> studentsMarksAvg();
	
	// retrieving names and total lecture hours of a number
	//of the lecturers with most lecture hours
	List<LecturerHours> lecturersMostHours(int nLecturers);
	
    //	 retrieving names and cities of the students having less
    //	than a given number of the scores
	List<StudentCity> studentsScoresLess(int nThreshold);
	
    //	retrieving names and phone numbers of then students 
    //	were born at a given month
	List<NamePhone> studentsBurnMonth(int month);
	
    //	retrieving all subject names and scores of a
    //	student with a given name
	List<NamePhone> lecturersCity(String city);
	
    //	retrieving all lecturer names and phone
    //	numbers from a given city
	List<NameScore> subjectsScores(String studentName);

}
