package epam.com.task_rest.controller;

import epam.com.task_rest.dto.RegistrationResponseDto;
import epam.com.task_rest.dto.trainee.TraineeCreateDto;
import epam.com.task_rest.dto.trainee.TraineeDto;
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

    @GetMapping("/login")
    public ResponseEntity<Void> login(@RequestParam("username") String username, @RequestParam("password") String password){
        traineeService.login(username, password);
        return ResponseEntity.ok().build();
    }
}
