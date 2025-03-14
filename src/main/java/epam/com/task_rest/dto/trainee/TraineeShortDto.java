package epam.com.task_rest.dto.trainee;


import epam.com.task_rest.dto.user.UserDto;

public record TraineeShortDto(UserDto userDto,
                              String username) {
}
