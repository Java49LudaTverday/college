package telran.college.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.college.dto.NameScore;
import telran.college.dto.StudentCity;
import telran.college.dto.SubjectNameScore;
import telran.college.dto.SubjectType;
import telran.college.entities.*;

public interface MarkRepo extends JpaRepository<Mark, Long> {
/************************************************/
	
	List<SubjectNameScore> findByStudentName(String studentName);
	/**********************/
	@Query("SELECT student.name as name FROM Mark where subject.type=:type "
			+ "group by student.name order by avg(score) desc limit :nStudents")
List<String> findBestStudentsSubjectType(SubjectType type, int nStudents);
	/*********************************/
	@Query("SELECT st.id as id, st.name as name, st.city as city "
			+ "from Mark m "
			+ "right join m.student st "
			+ "group by st.id, st.city having count(m.score) < :scoresThreshold")
	List<StudentCity> findStudentsScoresLess(int scoresThreshold);
	/*************************/
	@Query("SELECT student.name as name, round(avg(score)) as score FROM " 
              + "Mark GROUP BY student.name ORDER BY avg(score) desc")
	List<NameScore> studentsMarksAvg();
	/************************/
	List<Mark> findBySubject(Subject subject);
	List<Mark> findByStudent(Student student);
}
