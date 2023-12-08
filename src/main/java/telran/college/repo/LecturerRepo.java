package telran.college.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.college.dto.LecturerMark;
import telran.college.entities.*;

public interface LecturerRepo extends JpaRepository<Lecturer, Long> {
	String TYPE_LECTURER = "'Lecturer'";
	//retrieving names and total lecture hours of a number 
    //of the lecturers with most lecture hours
	String JOIN_LECTURER_SUBJECT = "FROM students_lecturers st join subjects s on st.id=s.lecturer_id ";
	@Query(value="SELECT st.name as name, sum(hours) as hours " + JOIN_LECTURER_SUBJECT
			+ "GROUP BY st.name  ORDER BY sum(hours) desc limit :nLecturers", nativeQuery = true)
	List<LecturerMark> findMostLecturerHours(int nLecturers);
	
    //retrieving all lecturer names and phone
    //numbers from a given city
	@Query(value="SELECT name, phone "
			+ "FROM students_lecturers where city=:city and dtype="+TYPE_LECTURER, nativeQuery = true)
	List<String> findAllLecturersByCity(String city);

}
