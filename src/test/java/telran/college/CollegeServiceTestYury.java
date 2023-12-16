package telran.college;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import telran.college.dto.*;
import telran.college.service.CollegeService;
import telran.exceptions.NotFoundException;
@SpringBootTest
@Sql(scripts = {"db_test.sql"})
class CollegeServiceTestYury { 
@Autowired
CollegeService collegeService;

 PersonDto newStudent = new PersonDto(129, "Shelly", "Rehovot", LocalDate.of(2005, 10, 16), "054-7894451");
 PersonDto studentForUpdate = new PersonDto(123, "Vasya", "Tel-Aviv", LocalDate.of(2005, 10, 16), "054-7894451");
 PersonDto studentWrongId = new PersonDto(111, "Vasya", "Tel-Aviv", LocalDate.of(2005, 10, 16), "054-7894451");
 PersonDto newLecturer = new PersonDto(1233, "Shedy", "Tel-Aviv", LocalDate.of(1994, 05, 18), "054-7894452");
 SubjectDto newSubject = new SubjectDto(326, "Spring Technology", 60, 1232l, SubjectType.BACK_END );
 SubjectDto newSubjectWrogLecturerId = new SubjectDto(327, "Spring Technology", 60, 1234l, SubjectType.BACK_END );
 SubjectDto newSubjectNoLecturer = new SubjectDto(328, "Spring Technology", 60, null, SubjectType.BACK_END );
	MarkDto newMark = new MarkDto(128, 321, 85);
	MarkDto newMarkWrongStudentId = new MarkDto(111, 321, 85);
	MarkDto newMarkWrongSubjectId = new MarkDto(128, 421, 85);

PersonDto lecturerForUpdate = new PersonDto(1231, "Mozes", "Rehovot", LocalDate.of(1963, 10, 20), "054-3334567");
PersonDto lecturerWrongId = new PersonDto(1111, "Mozes", "Rehovot", LocalDate.of(1963, 10, 20), "054-3334567");
 @Test
	void bestStudentsTypeTest() {
		List<String> students = collegeService.bestStudentsSubjectType("BACK_END", 2);
		String[] expected = {
				"David", "Yosef"
		};
		assertArrayEquals(expected, students.toArray(String[]::new));
	}
	@Test
	void studentsAvgScoreTest() {
		List<NameScore> studentMarks = collegeService.studentsMarksAvg();
		String [] students = {
			"David", "Rivka", "Vasya", "Sara", "Yosef"	
		};
		int [] scores = {
				96, 95, 83, 80, 78
		};
		NameScore[] studentMarksArr = studentMarks.toArray(NameScore[]::new);
		IntStream.range(0, students.length)
		.forEach(i -> {
			assertEquals(students[i], studentMarksArr[i].getName());
			assertEquals(scores[i], studentMarksArr[i].getScore());
		});
	}
	@Test
	void lecturersMostHoursTest() {
		List<LecturerHours> lecturersHours = collegeService.lecturersMostHours(2);
		LecturerHours[] lecturersHoursArr = lecturersHours.toArray(LecturerHours[]::new);
		String[] lecturers = {
				"Abraham", "Mozes"
		};
		int [] hours = {
			225, 130	
		};
		IntStream.range(0, hours.length).forEach(i -> {
			assertEquals(lecturers[i], lecturersHoursArr[i].getName());
			assertEquals(hours[i], lecturersHoursArr[i].getHours());
		});
	}
	@Test
	void studentsScoresLessTest() {
		List<StudentCity> studentCityList = collegeService.studentsScoresLess(1);
		assertEquals(1, studentCityList.size());
		StudentCity studentCity = studentCityList.get(0);
		assertEquals("Rehovot",studentCity.getCity());
		assertEquals("Yakob",studentCity.getName());
	}
	@Test
	void studentsBurnMonthTest() {
		String [] namesExpected = {
				"Vasya", "Yakob"
		};
		String [] phonesExpected = {
			"054-1234567", "051-6677889"	
		};
		NamePhone[] studentPhonesArr = collegeService.studentsBornMonth(10)
				.toArray(NamePhone[]::new);
		assertEquals(phonesExpected.length, studentPhonesArr.length);
		IntStream.range(0,  phonesExpected.length).forEach(i -> {
			assertEquals(namesExpected[i], studentPhonesArr[i].getName());
			assertEquals(phonesExpected[i], studentPhonesArr[i].getPhone());
		});
	}
	@Test
	void lecturesCityTest() {
		String[]expectedNames = {
				"Abraham", "Mozes"
		};
		String[] expectedPhones = {
			"050-1111122", "054-3334567"	
		};
		NamePhone[] namePhones = collegeService.lecturersCity("Jerusalem")
				.toArray(NamePhone[]::new);
		assertEquals(expectedNames.length, namePhones.length);
		IntStream.range(0, namePhones.length).forEach(i -> {
			assertEquals(expectedNames[i], namePhones[i].getName());
			assertEquals(expectedPhones[i], namePhones[i].getPhone());
		});
	}
	@Test
	void subjectsScoresTest() {
		String[] subjects = {
				"Java Core", "Java Technologies", "HTML/CSS", "JavaScript", "React"
		};
		int[] scores = {
				75, 60, 95, 85, 100
		};
		SubjectNameScore[] subjectScores = collegeService.subjectsScores("Vasya")
				.toArray(SubjectNameScore[]::new);
		assertEquals(scores.length, subjectScores.length);
		IntStream.range(0, scores.length).forEach(i -> {
			assertEquals(subjects[i], subjectScores[i].getSubjectName());
			assertEquals(scores[i], subjectScores[i].getScore());
		});		
	}
	@Test
	void addStudentTest () {
		PersonDto student = collegeService.addStudent(newStudent);
		assertEquals(student, newStudent);
		assertThrowsExactly(IllegalStateException.class, () -> collegeService.addStudent(student));
	}
	@Test
	void addLecturerTest () {
		PersonDto lecturer = collegeService.addStudent(newLecturer);
		assertEquals(lecturer, newLecturer);
		assertThrowsExactly(IllegalStateException.class, () -> collegeService.addStudent(lecturer));
	}
	@Test
	void addSubjectTest() {
		SubjectDto subject = collegeService.addSubject(newSubject);
		assertEquals(subject, newSubject);
		assertThrowsExactly(IllegalStateException.class, () -> collegeService.addSubject(newSubject));
		assertThrowsExactly(NotFoundException.class, () -> collegeService.addSubject(newSubjectWrogLecturerId));
		SubjectDto subjectNoLecturer = collegeService.addSubject(newSubjectNoLecturer);
		assertEquals(subjectNoLecturer, newSubjectNoLecturer);
	}
	@Test
	void addMarkTest () {
		MarkDto mark = collegeService.addMark(newMark);
		assertEquals(newMark, mark);
		assertThrowsExactly(NotFoundException.class, () -> collegeService.addMark(newMarkWrongStudentId));
		assertThrowsExactly(NotFoundException.class, () -> collegeService.addMark(newMarkWrongSubjectId));
	}
	@Test
	void updateStudentTest () {
		PersonDto student = collegeService.updateStudent(studentForUpdate);
		assertEquals(student, studentForUpdate);
		assertThrowsExactly(NotFoundException.class, () -> collegeService.updateStudent(studentWrongId));
	}
	@Test
	void updateLecturerTest () {
		PersonDto lecturer = collegeService.updateLecturer(lecturerForUpdate);
		assertEquals(lecturer, lecturerForUpdate);
		assertThrowsExactly(NotFoundException.class, () -> collegeService.updateStudent(lecturerWrongId));
	}
	@Test
	void deleteLecturerTest () {
		String city = "Jerusalem";
		List<NamePhone> lecturersBefore = collegeService.lecturersCity(city);
		PersonDto lecturer = collegeService.deleteLecturer(1230);
		List<NamePhone> lecturersAfter = collegeService.lecturersCity(city);
		assertNotEquals(lecturersBefore.size(), lecturersAfter.size());
		assertThrowsExactly(NotFoundException.class,() -> collegeService.updateLecturer(lecturer));
	}
	@Test
	void deleteSubjectTest () {
		SubjectDto subject = collegeService.deleteSubject(325);
		assertEquals(subject, collegeService.addSubject(subject));
	}
	@Test
	void deletStudentHavingLessScore () {
		List<StudentCity> studentCityList = collegeService.studentsScoresLess(1);
		List<PersonDto> studentsDeleted = collegeService.deleteStudentsHavingScoresLess(1);
		assertEquals(studentCityList.size(), studentsDeleted.size());
		Long[] studentsCityIds = studentCityList.stream().map(st -> st.getId()).toArray(Long[]::new);
		Long[] studentsDeletedIds = studentsDeleted.stream().map(st -> st.id()).toArray(Long[]::new);
		assertArrayEquals(studentsCityIds, studentsDeletedIds);
	}

}