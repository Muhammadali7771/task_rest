package epam.com.task_rest.service;


import epam.com.task_rest.dto.ChangeLoginDto;
import epam.com.task_rest.dto.LoginRequestDto;
import epam.com.task_rest.dto.RegistrationResponseDto;
import epam.com.task_rest.dto.UpdateTraineeTrainersRequestDto;
import epam.com.task_rest.dto.trainee.TraineeCreateDto;
import epam.com.task_rest.dto.trainee.TraineeDto;
import epam.com.task_rest.dto.trainee.TraineeUpdateDto;
import epam.com.task_rest.dto.trainer.TrainerShortDto;
import epam.com.task_rest.dto.training.TrainingDto;
import epam.com.task_rest.entity.Trainee;
import epam.com.task_rest.entity.Trainer;
import epam.com.task_rest.entity.Training;
import epam.com.task_rest.entity.User;
import epam.com.task_rest.exception.AuthenticationException;
import epam.com.task_rest.exception.ResourceNotFoundException;
import epam.com.task_rest.mapper.TraineeMapper;
import epam.com.task_rest.mapper.TrainerMapper;
import epam.com.task_rest.mapper.TrainingMapper;
import epam.com.task_rest.repository.TraineeRepository;
import epam.com.task_rest.repository.TrainingRepository;
import epam.com.task_rest.util.UsernamePasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TraineeService {
    private final TraineeRepository traineeRepository;
    private final TraineeMapper traineeMapper;
    private final TrainerMapper trainerMapper;
    private final UsernamePasswordGenerator usernamePasswordGenerator;
    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;

    public RegistrationResponseDto create(TraineeCreateDto traineeCreateDto) {
        Trainee trainee = traineeMapper.toEntity(traineeCreateDto);
        User user = trainee.getUser();
        String username = usernamePasswordGenerator
                .generateUsername(traineeCreateDto.firstName(), traineeCreateDto.lastName());
        String password = usernamePasswordGenerator.generatePassword();
        user.setPassword(password);
        user.setUserName(username);
        Trainee savedTrainee = traineeRepository.save(trainee);
        RegistrationResponseDto registrationResponseDto = new RegistrationResponseDto(username, password);
        return registrationResponseDto;
    }

    public void login(LoginRequestDto dto) {
        if (!traineeRepository.checkUsernameAndPasswordMatch(dto.username(), dto.password())) {
            throw new AuthenticationException("Username or password is incorrect");
        }
    }

    public boolean checkIfUsernameAndPasswordMatching(String username, String password) {
        boolean isMatch = traineeRepository.checkUsernameAndPasswordMatch(username, password);
        return isMatch;
    }

    public TraineeDto getTraineeByUsername(String username) {
        Trainee trainee = traineeRepository.findTraineeByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee with username " + username + " not found"));
        return traineeMapper.toDto(trainee);
    }

    public void changePassword(ChangeLoginDto dto) {
        if (!checkIfUsernameAndPasswordMatching(dto.username(), dto.oldPassword())) {
            throw new AuthenticationException("username or password is incorrect");
        }
        traineeRepository.changePassword(dto.username(), dto.newPassword());
    }

    public TraineeDto update(String username, TraineeUpdateDto traineeUpdateDto) {
        Trainee trainee = traineeRepository.findTraineeByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee with username " + username + " not found"));
        Trainee trainee1 = traineeMapper.partialUpdate(traineeUpdateDto, trainee);
        Trainee updatedTrainee = traineeRepository.update(trainee1);
        return traineeMapper.toDto(updatedTrainee);
    }

    public void activateOrDeactivateTrainee(String username, boolean isActive) {
        Trainee trainee = traineeRepository.findTraineeByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee with username " + username + " not found"));
        traineeRepository.activateOrDeactivateTrainee(username, isActive);
    }

    public void deleteTraineeByUsername(String username) {
        Trainee trainee = traineeRepository.findTraineeByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee with username " + username + " not found"));
        traineeRepository.deleteByUsername(username);
    }

    public List<TrainingDto> getTraineeTrainingsList(String traineeUsername, Date fromDate, Date toDate, String trainerName, String trainingTypeName) {
        Trainee trainee = traineeRepository.findTraineeByUsername(traineeUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee not found"));
        List<Training> trainings = trainingRepository.getTraineeTrainingsListByTraineeUsernameAndCriteria(traineeUsername, fromDate, toDate, trainerName, trainingTypeName);
        return trainingMapper.toDtoList(trainings);
    }


    public List<TrainerShortDto> getTrainersListNotAssignedOnTrainee(String traineeUsername){
        Trainee trainee = traineeRepository.findTraineeByUsername(traineeUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found"));
        List<Trainer> trainers = traineeRepository.findTrainersListThatNotAssignedOnTraineeByTraineeUsername(traineeUsername);
        return trainerMapper.toShortDtoList(trainers);
    }

    public List<TrainerShortDto> updateTraineeTrainerList(UpdateTraineeTrainersRequestDto dto){
        Trainee trainee = traineeRepository.findTraineeByUsername(dto.traineeUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Trainee with username " + dto.traineeUsername() + " not found"));
        List<Trainer> trainerList = traineeRepository.updateTraineeTrainerList(dto.traineeUsername(), dto.trainers());
        return trainerMapper.toShortDtoList(trainerList);
    }

}
