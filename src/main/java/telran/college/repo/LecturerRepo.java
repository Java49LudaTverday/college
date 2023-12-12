package telran.college.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.college.dto.LecturerHours;
import telran.college.dto.NamePhone;
import telran.college.entities.*;

public interface LecturerRepo extends JpaRepository<Lecturer, Long> {
    //retrieving all lecturer names and phone numbers
    //from a given city
	List<NamePhone> findByCity(String city);

}
