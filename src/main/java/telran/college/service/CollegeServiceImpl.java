package telran.college.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.Override;
import lombok.RequiredArgsConstructor;
import telran.college.dto.*;
import telran.college.entities.*;
import telran.college.repo.*;
import telran.exceptions.NotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
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
		return lecturerRepo.findByCity(city);
	}
	@Override
	public List<SubjectNameScore> subjectsScores(String studentName) {
		
		return markRepo.findByStudentName(studentName);
	}

	@Override
	@Transactional(readOnly = false)
	public PersonDto addStudent(PersonDto personDto) {
		if(studentRepo.existsById(personDto.id())) {
			throw new IllegalStateException(String.format("Student with id %d already exists", personDto.id()));
		}
		studentRepo.save(new Student(personDto));
		return personDto;
	}

	@Override
	@Transactional(readOnly = false)
	public PersonDto addLecturer(PersonDto personDto) {
		if(lecturerRepo.existsById(personDto.id())) {
			throw new IllegalStateException(String.format("Lecturer with id %d already exists", personDto.id()));
		}
		lecturerRepo.save(new Lecturer(personDto));
		return personDto;
	}

	@Override
	@Transactional(readOnly = false)
	public SubjectDto addSubject(SubjectDto subjectDto) {
		Lecturer lecturer = null;
		if(subjectDto.lecturerId() != null) {
			lecturer = lecturerRepo.findById(subjectDto.lecturerId()).orElseThrow(
				() -> new NotFoundException(getNotExistsMessage("lecturer", subjectDto.lecturerId())));
		}
		Subject subject = new Subject(subjectDto);
		subject.setLecturer(lecturer);
		subjectRepo.save(subject);
		return subjectDto;
	}

	@Override
	@Transactional(readOnly = false)
	public MarkDto addMark(MarkDto markDto) {
		Student student = studentRepo.findById(markDto.studentId()).orElseThrow(() -> 
		new NotFoundException(getNotExistsMessage("student", markDto.studentId())));
		Subject subject = subjectRepo.findById(markDto.subjectId()).orElseThrow(()-> 
		new NotFoundException(getNotExistsMessage("subject", markDto.subjectId())));
		Mark mark = new Mark();
		mark.setFields(markDto.score(), student, subject);
		markRepo.save(mark);			
		return markDto;	
	}

	@Override
	@Transactional(readOnly = false)
	public PersonDto updateStudent(PersonDto personDto) {
		Student student = studentRepo.findById(personDto.id()).orElseThrow(
				() -> new NotFoundException(getNotExistsMessage("student", personDto.id())));
		student.setCity(personDto.city());
		student.setPhone(personDto.phone());
		return personDto;
	}

	@Override
	@Transactional(readOnly = false)
	public PersonDto updateLecturer(PersonDto personDto) {
		Lecturer lecturer = lecturerRepo.findById(personDto.id()).orElseThrow(() -> 
		new NotFoundException(getNotExistsMessage("lecturer", personDto.id())));
		lecturer.setCity(personDto.city());
		lecturer.setPhone(personDto.phone());
		return personDto;
	}

	@Override
	@Transactional(readOnly = false)
	public PersonDto deleteLecturer(long id) {
		//find Lecturer by id (with possible exception NotFoundException)
		//find all subjects with a given lecturer id 
		// update all subjects with being deleted lecturer by setting null in field Lecturer
		//lecturerRepo.delete(lecturer);
		//returns lecturer.build();
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public SubjectDto deleteSubject(long id) {
		//find subject by id (with possible exception NotFoundException)
		//delete all marks with a given subject
		//subjectRepo.delete(lecturer);
		//returns subject.build();
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public List<PersonDto> deleteStudentsHavingScoresLess(int nScores) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private String getNotExistsMessage(String object, long id) {
		return String.format("%s with id %d not exists", object, id );
	}

}
