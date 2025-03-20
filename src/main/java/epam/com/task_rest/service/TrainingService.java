package epam.com.task_rest.service;

import epam.com.task_rest.dto.training.TrainingCreateDto;
import epam.com.task_rest.entity.Training;
import epam.com.task_rest.mapper.TrainingMapper;
import epam.com.task_rest.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingService {
    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;

    public void create(TrainingCreateDto dto) {
        Training training = trainingMapper.toEntity(dto);
        trainingRepository.save(training);
    }

}
