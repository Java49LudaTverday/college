package telran.college;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import telran.college.dto.*;
import telran.college.service.CollegeService;

@SpringBootTest
@Sql(scripts = { "db_test.sql" })
class CollegeServiceTest {
	@Autowired
	CollegeService collegeService;	

	@Test
	void bestStudentsTypeTest() {
		List<String> students = collegeService.bestStudentsSubjectType("BACK_END", 2);
		String[] expected = { "David", "Yosef" };
		assertArrayEquals(expected, students.toArray());
	}

	@Test
	void studentsAvgScoreTest() {
		List<NameScore> studentsMark = collegeService.studentsMarksAvg();
		Map<String, Integer> expected = Map.of("David", 96, "Vasya", 83, "Yosef", 78, "Sara", 80, "Rivka", 95);
		studentsMark.forEach(sm -> System.out.printf("student: %s, avg score: %d\n", 
				sm.getName(), sm.getScore()));
		assertEquals(studentsMark.size(), expected.size());
		studentsMark.forEach(st -> assertEquals(st.getScore(), expected.get(st.getName())));
	}

	@Test
	void mostLecturersHoursTest() throws Exception {
		List<LecturerHours> lecturersHours = collegeService.lecturersMostHours(3);
		Map<String, Integer> expected = Map.of("Abraham", 225,"Mozes", 130,"Sockratus", 65);		
		lecturersHours.forEach(lh -> System.out.printf("lecture: %s, hours: %d\n", lh.getName(), lh.getHours()));
		assertEquals(expected.size(), lecturersHours.size());
		lecturersHours.forEach(lh -> assertEquals(lh.getHours(), expected.get(lh.getName())));
	}
	
	@Test
	void studentsHavingLessScoreTest() throws Exception {
		List<StudentCity> studentsCity = collegeService.studentsScoresLess(1);
		Map<String, String> expected = Map.of("Yakob", "Rehovot");	
		studentsCity.forEach(sc -> System.out.printf("Student: %s , city: %s\n", sc.getName(), sc.getCity()));
		assertEquals(studentsCity.size(), expected.size());
		studentsCity.forEach(sc -> assertEquals(sc.getCity(), expected.get(sc.getName())));
	}
	
	@Test
	void lecturersFromCityTest() {
		List<NamePhone> lecturers = collegeService.lecturersCity("Rehovot");
		Map<String, String> expected = Map.of("Sockratus","057-7664821");
		lecturers.forEach(l -> System.out.printf("Lecturer: %s, Phone: %s\n", l.getName(), l.getPhone()));
		assertEquals(lecturers.size(), expected.size());
		lecturers.forEach(l -> assertEquals(l.getPhone(), expected.get(l.getName())));
	}
	@Test
	void studentsBornInMonthTest() {
		int october = 10;
		List<NamePhone> students = collegeService.studentsBornMonth(october);
		Map<String, String> expected = Map.of("Vasya","054-1234567", "Yakob","051-6677889");
		students.forEach(st -> System.out.printf("Student: %s, phone: %s\n", st.getName(), st.getPhone()));
		assertEquals(students.size(), expected.size());
		students.forEach(s -> assertEquals(s.getPhone(), expected.get(s.getName())));
	}
	@Test
	void subjectsScoresByStudentTest() throws Exception {
		String student = "Sara";
		List<SubjectNameScore> subjectMark = collegeService.subjectsScores(student);
		Map<String, Integer> expected = Map.of("Java Core", 70, "Java Technologies", 65,"HTML/CSS", 90, "JavaScript",95);	
		assertEquals(subjectMark.size(), expected.size());
		subjectMark.forEach(st -> System.out.printf("subject: %s, score: %d\n",st.getSubjectName(), st.getScore()));
		subjectMark.forEach(sm -> assertEquals(sm.getScore(), expected.get(sm.getSubjectName())));
		
	}
}
