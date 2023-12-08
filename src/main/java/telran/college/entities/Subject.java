package telran.college.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import telran.college.dto.SubjectType;

@Entity
@Table(name = "subjects")
@Getter
@NoArgsConstructor
public class Subject {
	@Id
	long id;
	@Column(nullable=false)
	String name;
	int hours;
	@ManyToOne
	@JoinColumn(name="lecturer_id")
	Lecturer lecturer;
	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	SubjectType type;

}
