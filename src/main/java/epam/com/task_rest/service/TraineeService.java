package epam.com.task_rest.service;


import epam.com.task_rest.dto.RegistrationResponseDto;
import epam.com.task_rest.dto.trainee.TraineeCreateDto;
import epam.com.task_rest.dto.trainee.TraineeDto;
import epam.com.task_rest.dto.trainee.TraineeUpdateDto;
import epam.com.task_rest.dto.trainer.TrainerShortDto;
import epam.com.task_rest.entity.Trainee;
import epam.com.task_rest.entity.Trainer;
import epam.com.task_rest.entity.User;
import epam.com.task_rest.exception.AuthenticationException;
import epam.com.task_rest.exception.ResourceNotFoundException;
import epam.com.task_rest.mapper.TraineeMapper;
import epam.com.task_rest.mapper.TrainerMapper;
import epam.com.task_rest.repository.TraineeRepository;
import epam.com.task_rest.util.UsernamePasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TraineeService {
    private final TraineeRepository traineeRepository;
    private final TraineeMapper traineeMapper;
    private final TrainerMapper trainerMapper;
    private final UsernamePasswordGenerator usernamePasswordGenerator;

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

    public void login(String username, String password) {
        if (!traineeRepository.checkUsernameAndPasswordMatch(username, password)) {
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

    public void changePassword(String username, String oldPassword, String newPassword) {
        if (!checkIfUsernameAndPasswordMatching(username, oldPassword)) {
            throw new AuthenticationException("username or password is incorrect");
        }
        traineeRepository.changePassword(username, newPassword);
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

    public List<TrainerShortDto> updateTraineeTrainerList(String username, List<String> trainers){
        Trainee trainee = traineeRepository.findTraineeByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee with username " + username + " not found"));
        List<Trainer> trainerList = traineeRepository.updateTraineeTrainerList(username, trainers);
        return trainerMapper.toShortDtoList(trainerList);
    }

}
