package telran.college.service;

import java.util.List;

import telran.college.dto.*;

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
	List<NamePhone> studentsBornMonth(int month);
	
    //	retrieving all subject names and scores of a
    //	student with a given name
	List<NamePhone> lecturersCity(String city);
	
    //	retrieving all lecturer names and phone
    //	numbers from a given city
	List<SubjectNameScore> subjectsScores(String studentName);
	// adding all objects
	PersonDto addStudent(PersonDto personDto);
	PersonDto addLecturer(PersonDto personDto);
	SubjectDto addSubject(SubjectDto subjectDto);
	MarkDto addMark(MarkDto markDto);
	//updating information
	PersonDto updateStudent (PersonDto personDto);
	PersonDto updateLecturer (PersonDto personDto);
	// deleting
	PersonDto deleteLecturer(long id);
	SubjectDto deleteSubject(long id);
	List<PersonDto> deleteStudentsHavingScoresLess(int nScores);
	// retrieving any jpql query
	

}
