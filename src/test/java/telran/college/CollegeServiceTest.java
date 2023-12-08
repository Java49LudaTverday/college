package telran.college;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.minidev.json.JSONObject;
import telran.college.dto.LecturerHours;
import telran.college.dto.StudentMark;
import telran.college.service.CollegeService;

@SpringBootTest
@Sql(scripts = { "db_test.sql" })
class CollegeServiceTest {
	@Autowired
	CollegeService collegeService;
	@Autowired
	ObjectMapper objectMapper;

	@Test
	@Disabled
	void bestStudentsTypeTest() {
		List<String> students = collegeService.bestStudentsSubjectType("BACK_END", 2);
		String[] expected = { "David", "Yosef" };
		assertArrayEquals(expected, students.toArray());
	}

	@Test
	@Disabled
	void studentsAvgScoreTest() {
		List<StudentMark> studentsMark = collegeService.studentsAvgMarks();
		StudentMark[] expected = {};
		studentsMark.forEach(sm -> System.out.printf("student: %s, avg score: %d\n", sm.getName(), sm.getScore()));
	}

	@Test
	@Disabled
	void mostLecturersHoursTest() throws Exception {
		List<LecturerHours> lecturersHours = collegeService.mostLecturerHours(3);
		List<LecturerHours> expected = new ArrayList<>();
		lecturersHours.forEach(lh -> System.out.printf("lecture: %s, hours: %d\n", lh.getName(), lh.getHours()));
		assertArrayEquals(expected.toArray(), lecturersHours.toArray());
	}
	
	@Test
	@Disabled
	void studentsHavingLessScoreTest() throws Exception {
		List<String> lecturersHours = collegeService.studentsHavingLessScore(80);
		String[] expected = {"Sara,Beersheva","Vasya,Rehovot","Yosef,Rehovot"};		
		lecturersHours.forEach(lh -> System.out.println(lh));
		assertArrayEquals(expected, lecturersHours.toArray());
	}
	
	@Test
//	@Disabled
	void lecturersFromCityTest() {
		List<String> lecturers = collegeService.allLecturersFromCity("Rehovot");
		String[] expected = {"Sockratus,057-7664821"};
		lecturers.forEach(l -> System.out.println(l));
		assertArrayEquals(expected, lecturers.toArray());
	}

	private JSONObject getJSONObject(String name, String hours) {
		JSONObject lecturer = new JSONObject();
		lecturer.put("name", name);
		lecturer.put("hours", hours);
		return lecturer;
	}

}
