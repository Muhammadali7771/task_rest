package epam.com.task_rest.dto.trainer;


import epam.com.task_rest.dto.user.UserDto;

public record TrainerShortDto(String firstName,
                              String lastName,
                              String username,
                              boolean isActive,
                              Integer specializationId) {
}
