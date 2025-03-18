package epam.com.task_rest.dto.trainee;


public record TraineeShortDto(String firstName,
                              String lastName,
                              boolean isActive,
                              String username) {
}
