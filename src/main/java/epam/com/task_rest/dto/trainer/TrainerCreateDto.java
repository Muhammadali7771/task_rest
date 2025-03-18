package epam.com.task_rest.dto.trainer;


public record TrainerCreateDto(String firstName,
                               String lastName,
                               Integer specializationId) {
}
