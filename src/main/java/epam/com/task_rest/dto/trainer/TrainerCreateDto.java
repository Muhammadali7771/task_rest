package epam.com.task_rest.dto.trainer;


import epam.com.task_rest.dto.user.UserCreateDto;

public record TrainerCreateDto(UserCreateDto userCreateDto,
                               Integer specializationId) {
}
