package epam.com.task_rest.service;


import epam.com.task_rest.dto.trainer.TrainerCreateDto;
import epam.com.task_rest.dto.trainer.TrainerDto;
import epam.com.task_rest.dto.trainer.TrainerShortDto;
import epam.com.task_rest.dto.trainer.TrainerUpdateDto;
import epam.com.task_rest.entity.Trainer;
import epam.com.task_rest.entity.User;
import epam.com.task_rest.exception.ResourceNotFoundException;
import epam.com.task_rest.mapper.TrainerMapper;
import epam.com.task_rest.repository.TrainerRepository;
import epam.com.task_rest.util.UsernamePasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainerService {
    private final TrainerRepository trainerRepository;
    private final TrainerMapper trainerMapper;
    private final UsernamePasswordGenerator usernamePasswordGenerator;

    public TrainerDto create(TrainerCreateDto dto){
        Trainer trainer = trainerMapper.toEntity(dto);
        String username = usernamePasswordGenerator
                .generateUsername(dto.userCreateDto().firstName(), dto.userCreateDto().lastName());
        String password = usernamePasswordGenerator.generatePassword();
        User user = trainer.getUser();
        user.setUserName(username);
        user.setPassword(password);
        Trainer savedTrainer = trainerRepository.save(trainer);
        return trainerMapper.toDto(savedTrainer);
    }

    public boolean checkIfUsernameAndPasswordMatching(String username, String password) {
        boolean isMatch = trainerRepository.checkUsernameAndPasswordMatch(username, password);
        return isMatch;
    }

    public TrainerDto getTrainerByUsername(String username) {
        Trainer trainer = trainerRepository.findTrainerByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found"));
        return trainerMapper.toDto(trainer);
    }

    public TrainerDto update(TrainerUpdateDto dto, String username){
        Trainer trainer = trainerRepository.findTrainerByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found"));
        Trainer trainer1 = trainerMapper.partialUpdate(dto, trainer);
        Trainer updatedTrainer = trainerRepository.update(trainer1);
        return trainerMapper.toDto(updatedTrainer);
    }

    public void activateOrDeactivateTrainer(String username, boolean isActive){
        Trainer trainer = trainerRepository.findTrainerByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found"));
        trainerRepository.activateOrDeactivateTrainee(username, isActive);
    }

    public List<TrainerShortDto> getTrainersListNotAssignedOnTrainee(String traineeUsername){
        Trainer trainer = trainerRepository.findTrainerByUsername(traineeUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found"));
        List<Trainer> trainers = trainerRepository.findTrainersListThatNotAssignedOnTraineeByTraineeUsername(traineeUsername);
        return trainerMapper.toShortDtoList(trainers);
    }

}
