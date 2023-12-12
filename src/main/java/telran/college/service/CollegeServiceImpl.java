package telran.college.service;

import java.util.List;

import org.springframework.stereotype.Service;
import java.lang.Override;
import lombok.RequiredArgsConstructor;
import telran.college.dto.LecturerHours;
import telran.college.dto.NamePhone;
import telran.college.dto.NameScore;
import telran.college.dto.StudentCity;
import telran.college.dto.SubjectNameScore;
import telran.college.dto.SubjectType;
import telran.college.entities.Person;
import telran.college.entities.Student;
import telran.college.repo.*;

@Service
@RequiredArgsConstructor
public class CollegeServiceImpl implements CollegeService {
	final StudentRepo studentRepo;
	final LecturerRepo lecturerRepo;
	final SubjectRepo subjectRepo;
	final MarkRepo markRepo;

	@Override
	public List<String> bestStudentsSubjectType(String type, int nStudents) {
	
		return markRepo.findBestStudentsSubjectType(SubjectType.valueOf(type), nStudents);
	}

	@Override
	public List<NameScore> studentsMarksAvg() {
		
		return markRepo.studentsMarksAvg();
	}

	@Override
	public List<LecturerHours> lecturersMostHours(int nLecturers) {
		
		return subjectRepo.findLecturersMostHours(nLecturers);
	}
	@Override
	public List<StudentCity> studentsScoresLess(int nThreshold) {
		
		return markRepo.findStudentsScoresLess(nThreshold);
	}
	@Override
	public List<NamePhone> studentsBornMonth(int month) {
		
		return studentRepo.findStudentsBornMonth(month);
	}
	@Override
	public List<NamePhone> lecturersCity(String city) {
		//TODO method with named query
		return lecturerRepo.findByCity(city);
	}
	@Override
	public List<SubjectNameScore> subjectsScores(String studentName) {
		
		return markRepo.findByStudentName(studentName);
	}
	

}
