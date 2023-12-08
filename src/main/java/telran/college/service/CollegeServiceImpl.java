package telran.college.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.college.dto.LecturerHours;
import telran.college.dto.StudentMark;
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
	
		return studentRepo.findBestStudentsSubjectType(type, nStudents);
	}

	@Override
	public List<StudentMark> studentsAvgMarks() {
		
		return studentRepo.studentsAvgMarks();
	}

	@Override
	public List<LecturerHours> mostLecturerHours(int nLecturers) {
		
		return lecturerRepo.findMostLecturerHours(nLecturers);
	}

	@Override
	public List<String> studentsHavingLessScore(int score) {
		
		return studentRepo.findStudentsHavingLessScore(score);
	}

	@Override
	public List<String> studentsBornInMonth(String mounth) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudentMark> subjectsAndScoresOfStudent(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> allLecturersFromCity(String city) {
		
		return lecturerRepo.findAllLecturersFromCity(city);
	}
	
	

}
