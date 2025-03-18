package epam.com.task_rest.dto.trainee;


import java.util.Date;

public record TraineeCreateDto(Date dateOfBirth,
                               String address,
                               String firstName,
                               String lastName) {
}
