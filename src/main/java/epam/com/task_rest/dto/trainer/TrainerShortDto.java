package epam.com.task_rest.dto.trainer;


public record TrainerShortDto(String firstName,
                              String lastName,
                              String username,
                              boolean isActive,
                              Integer specializationId) {
}
