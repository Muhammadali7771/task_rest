package epam.com.task_rest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "trainers")
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User user;
    @ManyToOne
    private TrainingType specialization;
    @ManyToMany(mappedBy = "trainers")
    private List<Trainee> trainees;
}
