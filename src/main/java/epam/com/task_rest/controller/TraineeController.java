package epam.com.task_rest.controller;

import epam.com.task_rest.dto.ChangeLoginDto;
import epam.com.task_rest.dto.LoginRequestDto;
import epam.com.task_rest.dto.RegistrationResponseDto;
import epam.com.task_rest.dto.UpdateTraineeTrainersRequestDto;
import epam.com.task_rest.dto.trainee.TraineeCreateDto;
import epam.com.task_rest.dto.trainee.TraineeDto;
import epam.com.task_rest.dto.trainee.TraineeStatusUpdateDto;
import epam.com.task_rest.dto.trainee.TraineeUpdateDto;
import epam.com.task_rest.dto.trainer.TrainerShortDto;
import epam.com.task_rest.dto.training.TrainingDto;
import epam.com.task_rest.service.TraineeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("gym.com/api/trainee")
public class TraineeController {
    private final TraineeService traineeService;

    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @GetMapping
    public ResponseEntity<TraineeDto> getTraineeByUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok(traineeService.getTraineeByUsername(username));
    }

    @PostMapping
    public ResponseEntity<RegistrationResponseDto> register(@RequestBody @Valid TraineeCreateDto dto) {
        return ResponseEntity.ok(traineeService.create(dto));
    }

    @PutMapping
    public ResponseEntity<TraineeDto> update(@RequestParam("username") String username, @RequestBody TraineeUpdateDto dto) {
        TraineeDto updatedTrainee = traineeService.update(username, dto);
        return ResponseEntity.ok(updatedTrainee);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid LoginRequestDto dto) {
        traineeService.login(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam("username") String username) {
        traineeService.deleteTraineeByUsername(username);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/change-status")
    public ResponseEntity<Void> activateOrDeactivateTrainee(@RequestBody TraineeStatusUpdateDto dto) {
        traineeService.activateOrDeactivateTrainee(dto.username(), dto.isActive());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/change-login")
    public ResponseEntity<Void> changeLogin(@RequestBody @Valid ChangeLoginDto dto) {
        traineeService.changePassword(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("training-list")
    public ResponseEntity<List<TrainingDto>> getTrainingList(@RequestParam(name = "username") String traineeUsername,
                                                             @RequestParam(name = "period-from", required = false) Date periodFrom,
                                                             @RequestParam(name = "period-to", required = false) Date periodTo,
                                                             @RequestParam(name = "trainer-name", required = false) String trainerName,
                                                             @RequestParam(name = "training-type", required = false) String trainingTypeName) {
        List<TrainingDto> trainings = traineeService.getTraineeTrainingsList(traineeUsername, periodFrom, periodTo, trainerName, trainingTypeName);
        return ResponseEntity.ok(trainings);
    }

    @PutMapping("/trainer-list")
    public ResponseEntity<List<TrainerShortDto>> updateTraineeTrainerList(@RequestBody UpdateTraineeTrainersRequestDto dto) {
        List<TrainerShortDto> trainers = traineeService.updateTraineeTrainerList(dto);
        return new ResponseEntity<>(trainers, HttpStatus.OK);
    }

    @GetMapping("/unassigned-trainers")
    public ResponseEntity<List<TrainerShortDto>> getUnassignedTrainers(@RequestParam("username") String traineeUsername) {
        List<TrainerShortDto> trainers = traineeService
                .getTrainersListNotAssignedOnTrainee(traineeUsername);
        return new ResponseEntity<>(trainers, HttpStatus.OK);
    }

}
