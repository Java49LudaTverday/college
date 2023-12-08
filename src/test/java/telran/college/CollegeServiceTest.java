package telran.college;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import telran.college.dto.LecturerMark;
import telran.college.dto.StudentMark;
import telran.college.service.CollegeService;

@SpringBootTest
@Sql(scripts = { "db_test.sql" })
class CollegeServiceTest {
	@Autowired
	CollegeService collegeService;	

	@Test
//	@Disabled
	void bestStudentsTypeTest() {
		List<String> students = collegeService.bestStudentsSubjectType("BACK_END", 2);
		String[] expected = { "David", "Yosef" };
		assertArrayEquals(expected, students.toArray());
	}

	@Test
//	@Disabled
	void studentsAvgScoreTest() {
		List<StudentMark> studentsMark = collegeService.studentsAvgMarks();
		Map<String, Integer> expected = new HashMap<>();
		expected.put("David", 96);
		expected.put("Vasya", 83);
		expected.put("Yosef", 78);
		expected.put("Sara", 80);
		expected.put("Rivka", 95);
		studentsMark.forEach(sm -> System.out.printf("student: %s, avg score: %d\n", 
				sm.getName(), sm.getScore()));
		runTest(studentsMark, expected);
	}

	@Test
//	@Disabled
	void mostLecturersHoursTest() throws Exception {
		List<LecturerMark> lecturersHours = collegeService.mostLecturerHours(3);
		Map<String, Integer> expected = new HashMap<>();
		expected.put("Abraham", 225);
		expected.put("Mozes", 130);
		expected.put("Sockratus", 65);		
		lecturersHours.forEach(lh -> System.out.printf("lecture: %s, hours: %d\n", lh.getName(), lh.getHours()));
		assertEquals(expected.size(), lecturersHours.size());
		lecturersHours.forEach(lh -> assertEquals(lh.getHours(), expected.get(lh.getName())));
	}
	
	@Test
//	@Disabled
	void studentsHavingLessScoreTest() throws Exception {
		List<String> lecturersHours = collegeService.studentsHavingLessScore(80);
		String[] expected = {"Sara,Beersheva","Vasya,Rehovot","Yosef,Rehovot"};		
		lecturersHours.forEach(lh -> System.out.println(lh));
		assertArrayEquals(expected, lecturersHours.toArray());
	}
	
	@Test
//	@Disabled
	void lecturersFromCityTest() {
		List<String> lecturers = collegeService.allLecturersByCity("Rehovot");
		String[] expected = {"Sockratus,057-7664821"};
		lecturers.forEach(l -> System.out.println(l));
		assertArrayEquals(expected, lecturers.toArray());
	}
	@Test
//	@Disabled
	void studentsBornInMonthTest() {
		int october = 10;
		List<String> students = collegeService.studentsBornInMonth(october);
		String[] expected = {"Vasya,054-1234567", "Yakob,051-6677889"};
		students.forEach(st -> System.out.println(st));
		assertArrayEquals(expected, students.toArray());
	}
	@Test
//	@Disabled
	void subjectsScoresByStudentTest() throws Exception {
		String student = "Sara";
		List<StudentMark> subjectMark = collegeService.subjectsAndScoresOfStudent(student);
		Map<String, Integer> expected = new HashMap<>();
		expected.put("Java Core", 70);
		expected.put("Java Technologies", 65);
		expected.put("HTML/CSS", 90);
		expected.put("JavaScript",95);		
//		("Java Core",70, "Java Technologies",65, "HTML/CSS",90, "JavaScript",95);
		subjectMark.forEach(st -> System.out.printf("subject: %s, score: %d\n",st.getName(), st.getScore()));
		runTest(subjectMark, expected);		
		
	}

	private void runTest(List<StudentMark> subjectMark, Map<String, Integer> expected) {
		assertEquals(expected.size(), subjectMark.size());
		subjectMark.forEach(sm -> assertEquals(sm.getScore(), expected.get(sm.getName())));
	}

}
