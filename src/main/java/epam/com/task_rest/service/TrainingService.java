package epam.com.task_rest.service;

import epam.com.task_rest.dto.training.TrainingCreateDto;
import epam.com.task_rest.entity.Trainee;
import epam.com.task_rest.entity.Trainer;
import epam.com.task_rest.entity.Training;
import epam.com.task_rest.entity.TrainingType;
import epam.com.task_rest.exception.ResourceNotFoundException;
import epam.com.task_rest.mapper.TrainingMapper;
import epam.com.task_rest.repository.TraineeRepository;
import epam.com.task_rest.repository.TrainerRepository;
import epam.com.task_rest.repository.TrainingRepository;
import epam.com.task_rest.repository.TrainingTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final TraineeRepository traineeRepository;
    private final TrainerRepository trainerRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final TrainingMapper trainingMapper;

    public void create(TrainingCreateDto dto) {
        Trainee trainee = traineeRepository.getTraineeReferenceByUsername(dto.traineeUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Trainee not found"));
        Trainer trainer = trainerRepository.getTrainerReferenceByUsername(dto.trainerUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found"));
        TrainingType trainingType = trainingTypeRepository.getTrainingTypeById(dto.trainingTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Training Type not found"));
        Training training = trainingMapper.toEntity(dto, trainee, trainer, trainingType);
        trainingRepository.save(training);
    }
}
