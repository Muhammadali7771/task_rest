package epam.com.task_rest.dto;

import java.util.List;

public record UpdateTraineeTrainersRequestDto(String traineeUsername,
                                              List<String> trainers) {
}
