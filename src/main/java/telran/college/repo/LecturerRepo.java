package telran.college.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.college.dto.LecturerHours;
import telran.college.dto.NamePhone;
import telran.college.entities.*;

public interface LecturerRepo extends JpaRepository<Lecturer, Long> {
	String TYPE_LECTURER = "'Lecturer'";
	//retrieving names and total lecture hours of a number 
    //of the lecturers with most lecture hours
	String JOIN_LECTURER_SUBJECT = "FROM students_lecturers st join subjects s on st.id=s.lecturer_id ";
	@Query(value="select sl.name as name, sum(hours) as hours from "
			+ "students_lecturers sl join subjects sb on sl.id=sb.lecturer_id "
			+ "group by sl.name order by sum(hours) desc limit :nLecturers", nativeQuery=true)
		List<LecturerHours> findLecturersMostHours(int nLecturers);
	
    //retrieving all lecturer names and phone
    //numbers from a given city
	@Query(value="select name, phone from students_lecturers where city=:city and dtype='Lecturer'", nativeQuery = true)
	List<NamePhone> findLecturersCity(String city);

}
