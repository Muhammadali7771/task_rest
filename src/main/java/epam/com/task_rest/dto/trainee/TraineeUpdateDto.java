package epam.com.task_rest.dto.trainee;


import epam.com.task_rest.dto.user.UserUpdateDto;

import java.util.Date;


public record TraineeUpdateDto(UserUpdateDto userUpdateDto,
                               Date dateOfBirth,
                               String address) {
}

