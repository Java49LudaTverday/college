package telran.college;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.jdbc.Sql;

import lombok.AllArgsConstructor;
import lombok.Getter;

import telran.college.dto.LecturerMark;
import telran.college.dto.StudentMark;
import telran.college.service.CollegeService;

@SpringBootTest
@Sql(scripts = { "db_test.sql" })
class CollegeServiceTest2 {
	@Autowired
	CollegeService collegeService;
	
	@AllArgsConstructor
	@Getter
	public class LecturerMarkDto implements LecturerMark {
		String name;
		int hours;	
	}
	
	@AllArgsConstructor
	@Getter
	public class StudentMarkDto implements StudentMark{
		String name;
		int score;		
	}
	@Test
	void bestStudentsTypeTest() {
		List<String> students = collegeService.bestStudentsSubjectType("BACK_END", 2);
		String[] expected = { "David", "Yosef" };
		assertArrayEquals(expected, students.toArray());
	}

	@Test
	void studentsAvgScoreTest() {
		List<StudentMark> studentsMark = collegeService.studentsAvgMarks();
		StudentMark david = new StudentMarkDto("David", 96);
		StudentMark vasya = new StudentMarkDto("Vasya", 83);
		StudentMark yosef = new StudentMarkDto("Yosef", 78);
		StudentMark sara = new StudentMarkDto("Sara", 80);
		StudentMark rivka = new StudentMarkDto("Rivka", 95);
		StudentMark[] expected = { david,rivka, vasya,sara, yosef};		
		runTest(expected,   studentsMark.toArray());
		studentsMark.forEach(sm -> System.out.printf("student: %s, avg score: %d\n", 
				sm.getName(), sm.getScore()));

	}

	private void runTest(StudentMark[] expected, Object[] actual) {
		assertEquals(expected.length, actual.length);
		for(int i = 0; i< expected.length; i++) {
			StudentMark smEx = expected[i];
			StudentMark smAc = (StudentMark) actual[i];
			assertEquals(smEx.getName(), smAc.getName());
			assertEquals(smEx.getScore(), smAc.getScore());			
		}		
	}

	@Test
	void mostLecturersHoursTest() throws Exception {
		List<LecturerMark> lecturersHours = collegeService.mostLecturerHours(3);
		LecturerMark[]  expected = {new LecturerMarkDto("Abraham", 225),
				                    new LecturerMarkDto("Mozes", 130),
				                    new LecturerMarkDto("Sockratus", 65)}; 
		lecturersHours.forEach(lh -> System.out.printf("lecture: %s, hours: %d\n",
				lh.getName(), lh.getHours()));
		assertEquals(lecturersHours.size(), expected.length);
		for(int i = 0; i< expected.length; i++) {
		LecturerMark lmEx = expected[i];
		LecturerMark lmAc = lecturersHours.get(i);
		assertEquals(lmEx.getName(), lmAc.getName());
		assertEquals(lmEx.getHours(), lmAc.getHours());	
		}
	}
	
	@Test
	void studentsHavingLessScoreTest() throws Exception {
		List<String> lecturersHours = collegeService.studentsHavingLessScore(80);
		String[] expected = {"Sara,Beersheva","Vasya,Rehovot","Yosef,Rehovot"};		
		lecturersHours.forEach(lh -> System.out.println(lh));
		assertArrayEquals(expected, lecturersHours.toArray());
	}
	
	@Test
	void lecturersFromCityTest() {
		List<String> lecturers = collegeService.allLecturersByCity("Rehovot");
		String[] expected = {"Sockratus,057-7664821"};
		lecturers.forEach(l -> System.out.println(l));
		assertArrayEquals(expected, lecturers.toArray());
	}
	@Test
	void studentsBornInMonthTest() {
		int october = 10;
		List<String> students = collegeService.studentsBornInMonth(october);
		String[] expected = {"Vasya,054-1234567", "Yakob,051-6677889"};
		students.forEach(st -> System.out.println(st));
		assertArrayEquals(expected, students.toArray());
	}
	@Test
	void subjectsScoresByStudentTest() throws Exception {
		String student = "Sara";
		List<StudentMark> subjectMark = collegeService.subjectsAndScoresOfStudent(student);
		subjectMark.forEach(st -> System.out.printf("subject: %s, score: %d\n",st.getName(), st.getScore()));
		StudentMark[] expected = {new StudentMarkDto("HTML/CSS", 90),
				new StudentMarkDto("Java Core", 70),
				new StudentMarkDto("Java Technologies", 65), new StudentMarkDto("JavaScript",95)};
		runTest(expected, subjectMark.toArray());
		subjectMark.forEach(st -> System.out.printf("subject: %s, score: %d\n",st.getName(), st.getScore()));		
	}
}
