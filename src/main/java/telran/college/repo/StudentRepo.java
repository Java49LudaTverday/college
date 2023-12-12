package telran.college.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.college.dto.NamePhone;
import telran.college.entities.*;

public interface StudentRepo extends JpaRepository<Student, Long> {	
	/*************************************/
	@Query("SELECT name as name, phone as phone FROM Student s "
			+ "where EXTRACT(MONTH from birthDate) =:month")
	List<NamePhone> findStudentsBornMonth(int month);
	
}