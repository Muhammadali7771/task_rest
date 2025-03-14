package epam.com.task_rest.service;


import epam.com.task_rest.entity.TrainingType;
import epam.com.task_rest.exception.ResourceNotFoundException;
import epam.com.task_rest.repository.TrainingTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class TrainingTypeService {
    private final TrainingTypeRepository trainingTypeRepository;

    public TrainingTypeService(TrainingTypeRepository trainingTypeRepository) {
        this.trainingTypeRepository = trainingTypeRepository;
    }

    public TrainingType getTrainingType(Integer id) {
        return trainingTypeRepository.getTrainingTypeById(id).orElseThrow(
                () -> new ResourceNotFoundException("Training type not found with id: " + id));
    }
}
