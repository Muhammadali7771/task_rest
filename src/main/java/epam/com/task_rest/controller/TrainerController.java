package epam.com.task_rest.controller;

import epam.com.task_rest.dto.ChangeLoginDto;
import epam.com.task_rest.dto.LoginRequestDto;
import epam.com.task_rest.dto.RegistrationResponseDto;
import epam.com.task_rest.dto.trainer.*;
import epam.com.task_rest.service.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("gym.com/api/trainer")
public class TrainerController {
    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping
    public ResponseEntity<RegistrationResponseDto> register(@RequestBody TrainerCreateDto dto){
        RegistrationResponseDto responseDto = trainerService.create(dto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<TrainerDto> get(@RequestParam("username") String username){
        TrainerDto trainerDto = trainerService.getTrainerByUsername(username);
        return new ResponseEntity<>(trainerDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequestDto dto){
        trainerService.login(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/change-login")
    public ResponseEntity<Void> changeLogin(@RequestBody ChangeLoginDto dto){
        trainerService.changePassword(dto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<TrainerDto> update(@RequestParam("username") String username,@RequestBody TrainerUpdateDto dto){
        TrainerDto updatedTrainer = trainerService.update(dto, username);
        return new ResponseEntity<>(updatedTrainer, HttpStatus.OK);
    }


    @PatchMapping("/change-status")
    public ResponseEntity<Void> activateOrDeactivateTrainer(@RequestBody TrainerStatusUpdateDto dto){
        trainerService.activateOrDeactivateTrainer(dto);
        return ResponseEntity.noContent().build();
    }

}
