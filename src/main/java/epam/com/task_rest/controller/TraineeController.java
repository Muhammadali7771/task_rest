package epam.com.task_rest.controller;

import epam.com.task_rest.dto.ChangeLoginDto;
import epam.com.task_rest.dto.LoginRequestDto;
import epam.com.task_rest.dto.RegistrationResponseDto;
import epam.com.task_rest.dto.trainee.TraineeCreateDto;
import epam.com.task_rest.dto.trainee.TraineeDto;
import epam.com.task_rest.dto.trainee.TraineeStatusUpdateDto;
import epam.com.task_rest.dto.trainee.TraineeUpdateDto;
import epam.com.task_rest.service.TraineeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("gym.com/api/trainee")
public class TraineeController {
    private final TraineeService traineeService;

    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @GetMapping
    public ResponseEntity<TraineeDto> getTraineeByUsername(@RequestParam("username") String username){
        return ResponseEntity.ok(traineeService.getTraineeByUsername(username));
    }

    @PostMapping
    public ResponseEntity<RegistrationResponseDto> register(@RequestBody TraineeCreateDto dto){
       return ResponseEntity.ok(traineeService.create(dto));
    }

    @PutMapping
    public ResponseEntity<TraineeDto> update(@RequestParam("username") String username,@RequestBody TraineeUpdateDto dto){
        TraineeDto updatedTrainee = traineeService.update(username, dto);
        return ResponseEntity.ok(updatedTrainee);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequestDto dto){
        traineeService.login(dto.username(), dto.password());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam("username") String username){
        traineeService.deleteTraineeByUsername(username);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/change-status")
    public ResponseEntity<Void> activateOrDeactivateTrainee(@RequestBody TraineeStatusUpdateDto dto){
        traineeService.activateOrDeactivateTrainee(dto.username(), dto.isActive());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/change-login")
    public ResponseEntity<Void> changeLogin(@RequestBody ChangeLoginDto dto){
        traineeService.changePassword(dto.username(), dto.oldPassword(), dto.newPassword());
        return ResponseEntity.ok().build();
    }


}
