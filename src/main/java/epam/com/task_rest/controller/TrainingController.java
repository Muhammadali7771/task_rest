package epam.com.task_rest.controller;

import epam.com.task_rest.dto.training.TrainingCreateDto;
import epam.com.task_rest.service.TrainingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("gym.com/api/training")
public class TrainingController {
    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @PostMapping
    public ResponseEntity<Void> add(@RequestBody @Valid TrainingCreateDto trainingCreateDto) {
        trainingService.create(trainingCreateDto);
        return ResponseEntity.ok().build();
    }

}
