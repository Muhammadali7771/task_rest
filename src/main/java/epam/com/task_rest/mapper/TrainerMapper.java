package epam.com.task_rest.mapper;

import epam.com.task_rest.dto.trainee.TraineeShortDto;
import epam.com.task_rest.dto.trainer.TrainerCreateDto;
import epam.com.task_rest.dto.trainer.TrainerDto;
import epam.com.task_rest.dto.trainer.TrainerShortDto;
import epam.com.task_rest.dto.trainer.TrainerUpdateDto;
import epam.com.task_rest.dto.user.UserCreateDto;
import epam.com.task_rest.dto.user.UserDto;
import epam.com.task_rest.dto.user.UserUpdateDto;
import epam.com.task_rest.entity.Trainee;
import epam.com.task_rest.entity.Trainer;
import epam.com.task_rest.entity.TrainingType;
import epam.com.task_rest.entity.User;
import epam.com.task_rest.service.TrainingTypeService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrainerMapper {
    private final TrainingTypeService trainingTypeService;

    public TrainerMapper(TrainingTypeService trainingTypeService) {
        this.trainingTypeService = trainingTypeService;
    }

    public Trainer toEntity(TrainerCreateDto dto) {
        if (dto == null) {
            return null;
        }
        UserCreateDto userCreateDto = dto.userCreateDto();

        User user = new User();
        user.setLastName(userCreateDto.lastName());
        user.setFirstName(userCreateDto.firstName());

        Trainer trainer = new Trainer();
        Integer trainingTypeId = dto.specializationId();
        TrainingType trainingType = trainingTypeService.getTrainingType(trainingTypeId);
        trainer.setSpecialization(trainingType);
        trainer.setUser(user);

        return trainer;
    }

    public Trainer partialUpdate(TrainerUpdateDto dto, Trainer trainer) {
        UserUpdateDto userUpdateDto = dto.userUpdateDto();
        User user = trainer.getUser();
        if (userUpdateDto != null) {
            if (userUpdateDto.firstName() != null) {
                user.setFirstName(userUpdateDto.firstName());
            }
            if (userUpdateDto.lastName() != null) {
                user.setLastName(userUpdateDto.lastName());
            }
            user.setActive(userUpdateDto.isActive());
        }

        Integer trainingTypeId = dto.specializationId();
        TrainingType trainingType = trainingTypeService.getTrainingType(trainingTypeId);
        trainer.setSpecialization(trainingType);

        return trainer;
    }

    public TrainerDto toDto(Trainer trainer) {
        if (trainer == null) {
            return null;
        }

        User user = trainer.getUser();
        UserDto userDto = new UserDto(user.getFirstName(), user.getLastName(), user.isActive());

        List<Trainee> trainees = trainer.getTrainees();
        List<TraineeShortDto> traineeShortDtos = new ArrayList<>();
        for (Trainee trainee : trainees) {
            User traineeUser = trainee.getUser();
            UserDto userDto1 = new UserDto(traineeUser.getFirstName(), traineeUser.getLastName(), traineeUser.isActive());
            TraineeShortDto traineeShortDto = new TraineeShortDto(userDto1, traineeUser.getUserName());
            traineeShortDtos.add(traineeShortDto);
        }
        TrainerDto trainerDto = new TrainerDto(userDto, trainer.getSpecialization().getId(), traineeShortDtos);
        return trainerDto;
    }

    public TrainerShortDto toShortDto(Trainer trainer) {
        if (trainer == null) {
            return null;
        }
        User user = trainer.getUser();
        UserDto userDto = new UserDto(user.getFirstName(), user.getLastName(), user.isActive());

        TrainerShortDto trainerShortDto = new TrainerShortDto(user.getFirstName(),
                user.getLastName(),
                user.getUserName(),
                user.isActive(),
                trainer.getSpecialization().getId());
        return trainerShortDto;
    }

    public List<TrainerShortDto> toShortDtoList(List<Trainer> trainers) {
        return trainers.stream().map(this::toShortDto).toList();
    }
}
