package epam.com.task_rest.mapper;


import epam.com.task_rest.dto.trainee.TraineeCreateDto;
import epam.com.task_rest.dto.trainee.TraineeDto;
import epam.com.task_rest.dto.trainee.TraineeUpdateDto;
import epam.com.task_rest.dto.trainer.TrainerShortDto;
import epam.com.task_rest.dto.user.UserCreateDto;
import epam.com.task_rest.dto.user.UserDto;
import epam.com.task_rest.dto.user.UserUpdateDto;
import epam.com.task_rest.entity.Trainee;
import epam.com.task_rest.entity.Trainer;
import epam.com.task_rest.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TraineeMapper {

    public Trainee toEntity(TraineeCreateDto dto) {
        if (dto == null) {
            return null;
        }
        UserCreateDto userCreateDto = dto.userCreateDto();
        User user = new User();
        user.setFirstName(userCreateDto.firstName());
        user.setLastName(userCreateDto.lastName());

        Trainee trainee = new Trainee();
        trainee.setAddress(dto.address());
        trainee.setDateOfBirth(dto.dateOfBirth());
        trainee.setUser(user);

        return trainee;
    }

    public Trainee partialUpdate(TraineeUpdateDto dto, Trainee trainee) {
        UserUpdateDto userUpdateDto = dto.userUpdateDto();
        User user = trainee.getUser();
        if (userUpdateDto != null) {
            if (userUpdateDto.firstName() != null) {
                user.setFirstName(userUpdateDto.firstName());
            }
            if (userUpdateDto.lastName() != null) {
                user.setLastName(userUpdateDto.lastName());
            }
            user.setActive(userUpdateDto.isActive());
        }

        if (dto.address() != null) {
            trainee.setAddress(dto.address());
        }
        if (dto.dateOfBirth() != null) {
            trainee.setDateOfBirth(dto.dateOfBirth());
        }
        return trainee;
    }

    public TraineeDto toDto(Trainee trainee) {
        if (trainee == null) {
            return null;
        }

        User user = trainee.getUser();
        UserDto userDto = new UserDto(user.getFirstName(), user.getLastName(), user.isActive());

        List<Trainer> trainers = trainee.getTrainers();
        List<TrainerShortDto> trainerShortDtos = new ArrayList<>();
        for (Trainer trainer : trainers) {
            User trainerUser = trainer.getUser();
            UserDto dto = new UserDto(trainerUser.getFirstName(), trainerUser.getLastName(), trainerUser.isActive());
            TrainerShortDto trainerShortDto = new TrainerShortDto(dto, trainer.getSpecialization().getId());
            trainerShortDtos.add(trainerShortDto);
        }

        TraineeDto traineeDto = new TraineeDto(userDto, trainee.getDateOfBirth(),
                trainee.getAddress(), trainerShortDtos);
        return traineeDto;
    }
}
