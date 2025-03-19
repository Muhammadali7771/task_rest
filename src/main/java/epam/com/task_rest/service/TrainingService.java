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

}
