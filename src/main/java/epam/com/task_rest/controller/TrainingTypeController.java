package epam.com.task_rest.controller;

import epam.com.task_rest.dto.training_type.TrainingTypeDto;
import epam.com.task_rest.service.TrainingTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("gym.com/api/training-type")
public class TrainingTypeController {
    private final TrainingTypeService trainingTypeService;

    public TrainingTypeController(TrainingTypeService trainingTypeService) {
        this.trainingTypeService = trainingTypeService;
    }

    @GetMapping
    public ResponseEntity<List<TrainingTypeDto>> getTrainingTypes() {
        List<TrainingTypeDto> allTrainingTypes = trainingTypeService.getAllTrainingTypes();
        return new ResponseEntity<>(allTrainingTypes, HttpStatus.OK);
    }
}
