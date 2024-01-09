package masiv.com.prueba.controller;

import masiv.com.prueba.model.ElevatorState;
import masiv.com.prueba.service.ElevatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/elevator")
public class ElevatorController {

    private final ElevatorService elevatorService;

    @Autowired
    public ElevatorController(ElevatorService elevatorService) {
        this.elevatorService = elevatorService;
    }

    @PostMapping("/move")
    public ResponseEntity<String> moveElevator() {
        CompletableFuture<String> responseMoveElevator = elevatorService.moveElevator();
        if (!responseMoveElevator.isCompletedExceptionally()) {
            return ResponseEntity.ok(responseMoveElevator.join());
        }
        return ResponseEntity.badRequest().body(responseMoveElevator.join());
    }

    @PostMapping("/request")
    public ResponseEntity<String> callElevator(@RequestParam int floorNumber, @RequestParam boolean priority) {
        String responseRequestElevator = elevatorService.requestElevator(floorNumber, priority);
        if (!responseRequestElevator.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(responseRequestElevator);
    }

    @GetMapping(value = "/state", produces = "application/json")
    public ResponseEntity<ElevatorState> getElevatorState() {
        return ResponseEntity.ok(elevatorService.getElevatorState());
    }
}
