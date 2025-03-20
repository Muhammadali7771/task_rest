package epam.com.task_rest.controller;

import epam.com.task_rest.dto.ChangeLoginDto;
import epam.com.task_rest.dto.LoginRequestDto;
import epam.com.task_rest.dto.RegistrationResponseDto;
import epam.com.task_rest.dto.trainer.*;
import epam.com.task_rest.dto.training.TrainingDto;
import epam.com.task_rest.service.TrainerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("gym.com/api/trainer")
public class TrainerController {
    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping
    public ResponseEntity<RegistrationResponseDto> register(@RequestBody @Valid TrainerCreateDto dto) {
        RegistrationResponseDto responseDto = trainerService.create(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<TrainerDto> get(@RequestParam("username") String username) {
        TrainerDto trainerDto = trainerService.getTrainerByUsername(username);
        return new ResponseEntity<>(trainerDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid LoginRequestDto dto) {
        trainerService.login(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/change-login")
    public ResponseEntity<Void> changeLogin(@RequestBody @Valid ChangeLoginDto dto) {
        trainerService.changePassword(dto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<TrainerDto> update(@RequestParam("username") String username, @RequestBody TrainerUpdateDto dto) {
        TrainerDto updatedTrainer = trainerService.update(dto, username);
        return new ResponseEntity<>(updatedTrainer, HttpStatus.OK);
    }


    @PatchMapping("/change-status")
    public ResponseEntity<Void> activateOrDeactivateTrainer(@RequestBody TrainerStatusUpdateDto dto) {
        trainerService.activateOrDeactivateTrainer(dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/trainings-list")
    public ResponseEntity<List<TrainingDto>> getTrainingsList(@RequestParam("username") String trainerUsername,
                                                              @RequestParam(name = "period-from", required = false) Date periodFrom,
                                                              @RequestParam(name = "period-to", required = false) Date periodTo,
                                                              @RequestParam(name = "trainee-name", required = false) String traineeName) {
        List<TrainingDto> trainings = trainerService.getTrainerTrainingsList(trainerUsername, periodFrom, periodTo, traineeName);
        return ResponseEntity.ok(trainings);
    }

}
