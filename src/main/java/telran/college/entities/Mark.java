package telran.college.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Entity
@NoArgsConstructor
@Table(name="marks")
@Getter
public class Mark {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	@ManyToOne
	@JoinColumn(name="stid", nullable = false)
	Student student;
	@ManyToOne
	@JoinColumn(name="suid", nullable = false)
	Subject subject;
	@Column(nullable = false)
	int score;
	public void setFields (int score, Student student, Subject subject) {
		this.score = score;
		this.student= student;
		this.subject =  subject;
	}

}