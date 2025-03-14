package epam.com.task_rest.dto.trainer;


import epam.com.task_rest.dto.user.UserUpdateDto;

public record TrainerUpdateDto(UserUpdateDto userUpdateDto,
                               Integer specializationId) {
}
