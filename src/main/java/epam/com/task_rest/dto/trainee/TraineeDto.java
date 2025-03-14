package epam.com.task_rest.dto.trainee;



import epam.com.task_rest.dto.trainer.TrainerShortDto;
import epam.com.task_rest.dto.user.UserDto;

import java.util.Date;
import java.util.List;

public record TraineeDto(UserDto userDto,
                         Date dateOfBirth,
                         String address,
                         List<TrainerShortDto> trainerDtos
                         ) {
}
