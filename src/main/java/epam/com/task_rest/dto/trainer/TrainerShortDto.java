package epam.com.task_rest.dto.trainer;


import epam.com.task_rest.dto.user.UserDto;

public record TrainerShortDto(UserDto userDto,
                              Integer specializationId) {
}
