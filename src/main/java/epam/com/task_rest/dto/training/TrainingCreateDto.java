package epam.com.task_rest.dto.training;

import java.util.Date;

public record TrainingCreateDto(String traineeUsername,
                                String trainerUsername,
                                String trainingName,
                                Date trainingDate,
                                Integer trainingTypeId,
                                Number trainingDuration) {
}
