package epam.com.task_rest.dto.trainer;



import epam.com.task_rest.dto.trainee.TraineeShortDto;
import epam.com.task_rest.dto.user.UserDto;

import java.util.List;

public record TrainerDto(UserDto userDto,
                         Integer specializationId,
                         List<TraineeShortDto> traineeShortDtos) {
}
