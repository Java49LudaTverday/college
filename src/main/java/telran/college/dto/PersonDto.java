package telran.college.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.*;

public record PersonDto(@NotNull long id, String name, String city,
		@Past LocalDate birthDate, String phone) {
	

}
