package epam.com.task_rest.service;


import epam.com.task_rest.dto.training.TrainingCreateDto;
import epam.com.task_rest.dto.training.TrainingDto;
import epam.com.task_rest.entity.Trainee;
import epam.com.task_rest.entity.Trainer;
import epam.com.task_rest.entity.Training;
import epam.com.task_rest.exception.ResourceNotFoundException;
import epam.com.task_rest.mapper.TrainingMapper;
import epam.com.task_rest.repository.TraineeRepository;
import epam.com.task_rest.repository.TrainerRepository;
import epam.com.task_rest.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;

    public void create(TrainingCreateDto dto) {
        Training training = trainingMapper.toEntity(dto);
        trainingRepository.save(training);
    }

    public List<TrainingDto> getTraineeTrainingsList(String traineeUsername, Date fromDate, Date toDate, String trainerName, Integer trainingTypeId) {
        Trainee trainee = traineeRepository.findTraineeByUsername(traineeUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Trainee not found"));
        List<Training> trainings = trainingRepository.getTraineeTrainingsListByTraineeUsernameAndCriteria(traineeUsername, fromDate, toDate, trainerName, trainingTypeId);
        return trainingMapper.toDtoList(trainings);
    }

    public List<TrainingDto> getTrainerTrainingsList(String trainerUsername, Date fromDate, Date toDate, String traineeName) {
        Trainer trainer = trainerRepository.findTrainerByUsername(trainerUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found"));
        List<Training> trainings = trainingRepository.getTrainerTrainingsListByTrainerUsernameAndCriteria(trainerUsername, fromDate, toDate, traineeName);
        return trainingMapper.toDtoList(trainings);
    }
}
