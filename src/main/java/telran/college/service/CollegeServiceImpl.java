package telran.college.service;

import java.util.List;
import java.util.stream.LongStream;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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
		if(subjectRepo.existsById(subjectDto.id())) {
			throw new IllegalStateException(String.format("subject with id %d already exists", subjectDto.id()));
		}
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
		Lecturer lecturer = lecturerRepo.findById(id).orElseThrow(() -> 
		new NotFoundException(getNotExistsMessage("lecturer", id)));
		List<Subject> subjects = subjectRepo.findByLecturer(lecturer);
		subjects.stream().forEach(s -> s.setLecturer(null));
		lecturerRepo.delete(lecturer);
		return lecturer.build();
	}

	@Override
	@Transactional(readOnly = false)
	public SubjectDto deleteSubject(long id) {
		//find subject by id (with possible exception NotFoundException)
		//delete all marks with a given subject
		//subjectRepo.delete(lecturer);
		//returns subject.build();
		Subject subject = subjectRepo.findById(id).orElseThrow( () -> 
		new NotFoundException(getNotExistsMessage("subject", id)));
		List<Mark> marks = markRepo.findBySubject(subject);
		marks.stream().forEach(m -> markRepo.delete(m));
		subjectRepo.delete(subject);
		return subject.build();
	}

	@Override
	@Transactional(readOnly = false)
	public List<PersonDto> deleteStudentsHavingScoresLess(int nScores) {
		//finding relevant students with they ids
		List<StudentCity> studentCity = markRepo.findStudentsScoresLess(nScores);
		//finding Student with appropriate ids
		List<Student> students = studentCity.stream().map(sc -> studentRepo.findById(sc.getId()).orElseThrow(() -> 
		new NotFoundException(getNotExistsMessage("student", sc.getId())))).toList();
		// finding all marks by each Student
		List<List<Mark>> marks = students.stream().map(st -> markRepo.findByStudent(st)).toList(); 
		//deleting all marks
		for(List<Mark> m : marks) {
			m.stream().forEach(mr -> markRepo.delete(mr));
		}
		//creating list of PersonDto
		List<PersonDto> studentDto = students.stream().map(st -> st.build()).toList();
		//deleting each student from StudentRepo
		students.stream().forEach(st-> studentRepo.delete(st));
		return studentDto;
	}
	
	private String getNotExistsMessage(String object, long id) {
		return String.format("%s with id %d not exists", object, id );
	}

}
