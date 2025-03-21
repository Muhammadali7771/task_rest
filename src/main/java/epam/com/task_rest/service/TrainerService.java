package epam.com.task_rest.service;


import epam.com.task_rest.dto.ChangeLoginDto;
import epam.com.task_rest.dto.LoginRequestDto;
import epam.com.task_rest.dto.RegistrationResponseDto;
import epam.com.task_rest.dto.trainer.*;
import epam.com.task_rest.dto.training.TrainingDto;
import epam.com.task_rest.entity.Trainer;
import epam.com.task_rest.entity.Training;
import epam.com.task_rest.entity.User;
import epam.com.task_rest.exception.AuthenticationException;
import epam.com.task_rest.exception.ResourceNotFoundException;
import epam.com.task_rest.mapper.TrainerMapper;
import epam.com.task_rest.mapper.TrainingMapper;
import epam.com.task_rest.repository.TrainerRepository;
import epam.com.task_rest.repository.TrainingRepository;
import epam.com.task_rest.util.UsernamePasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerService {
    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;
    private final UsernamePasswordGenerator usernamePasswordGenerator;
    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;

    public RegistrationResponseDto create(TrainerCreateDto dto){
        Trainer trainer = trainerMapper.toEntity(dto);
        String username = usernamePasswordGenerator
                .generateUsername(dto.firstName(), dto.lastName());
        String password = usernamePasswordGenerator.generatePassword();
        User user = trainer.getUser();
        user.setUserName(username);
        user.setPassword(password);
        Trainer savedTrainer = trainerRepository.save(trainer);
        return new RegistrationResponseDto(username, password);
    }

    public boolean checkIfUsernameAndPasswordMatching(String username, String password) {
        boolean isMatch = trainerRepository.checkUsernameAndPasswordMatch(username, password);
        return isMatch;
    }

    public void login(LoginRequestDto dto){
        if (!checkIfUsernameAndPasswordMatching(dto.username(), dto.password())){
            throw new AuthenticationException("username or password is incorrect!");
        }
    }

    public TrainerDto getTrainerByUsername(String username) {
        Trainer trainer = trainerRepository.findTrainerByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found"));
        return trainerMapper.toDto(trainer);
    }

    public void changePassword(ChangeLoginDto dto) {
        if (!checkIfUsernameAndPasswordMatching(dto.username(), dto.oldPassword())) {
            throw new AuthenticationException("username or password is incorrect");
        }
        trainerRepository.changePassword(dto.username(), dto.newPassword());
    }

    public TrainerDto update(TrainerUpdateDto dto, String username){
        Trainer trainer = trainerRepository.findTrainerByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found"));
        Trainer trainer1 = trainerMapper.partialUpdate(dto, trainer);
        Trainer updatedTrainer = trainerRepository.update(trainer1);
        return trainerMapper.toDto(updatedTrainer);
    }

    public void activateOrDeactivateTrainer(TrainerStatusUpdateDto dto){
        if (!trainerRepository.existsByUsername(dto.username())) {
            throw new ResourceNotFoundException("Trainer not found");
        }
        trainerRepository.activateOrDeactivateTrainee(dto.username(), dto.isActive());
    }

    public List<TrainingDto> getTrainerTrainingsList(String trainerUsername, Date fromDate, Date toDate, String traineeName) {
        if (!trainerRepository.existsByUsername(trainerUsername)) {
            throw new ResourceNotFoundException("Trainer not found");
        }
        List<Training> trainings = trainingRepository.getTrainerTrainingsListByTrainerUsernameAndCriteria(trainerUsername, fromDate, toDate, traineeName);
        return trainingMapper.toDtoList(trainings);
    }

}
