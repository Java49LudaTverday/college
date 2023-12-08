package telran.college.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.college.dto.LecturerMark;
import telran.college.dto.StudentMark;
import telran.college.entities.*;

public interface StudentRepo extends JpaRepository<Student, Long> {
	String JOIN_STUDENTS_MARKS = "FROM students_lecturers st join marks m on stid=st.id ";
	String JOIN_ALL = JOIN_STUDENTS_MARKS + "join subjects sb on sb.id=suid ";
	String ALL_STUDENTS = "FROM students_lecturers where dtype='Student' ";

	@Query(value = "SELECT st.name as name " + JOIN_ALL + "where type=:type "
			+ "group by st.name order by avg(score) desc limit :nStudents", nativeQuery = true)
	List<String> findBestStudentsSubjectType(String type, int nStudents);

	@Query(value = "SELECT st.name as name, round(avg(score)) as score " + JOIN_STUDENTS_MARKS
			+ "group by st.name order by avg(score) desc", nativeQuery = true)
	List<StudentMark> studentsAvgMarks();

	@Query(value = "SELECT name, city " + JOIN_STUDENTS_MARKS
			+ "GROUP BY name having min(m.score)< :score", nativeQuery = true)
	List<String> findStudentsHavingLessScore(int score);
	
	@Query(value="SELECT name,  phone " + ALL_STUDENTS
			+ "AND EXTRACT(month from birth_date)=:month", nativeQuery = true)
	List<String> findStudentsBornInMonth (int month);
	
	@Query(value="SELECT sb.name as name, m.score " + JOIN_ALL
			+ "group by st.id, sb.name, m.score having st.id="
			+ "(select id from students_lecturers where name=:name)", nativeQuery = true)
	List<StudentMark> findSubjectsScoreByStudent (String name);
}