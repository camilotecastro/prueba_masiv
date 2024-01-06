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
        CompletableFuture<String> apiMessage = elevatorService.moveElevator();
        if (!apiMessage.isCompletedExceptionally()) {
            return ResponseEntity.badRequest().body(apiMessage.join());
        }
        return ResponseEntity.ok(apiMessage.join());
    }

    @PostMapping("/request")
    public ResponseEntity<String> callElevator(@RequestParam int floorNumber, @RequestParam boolean priority) {
        String apiMessage = elevatorService.requestElevator(floorNumber, priority);
        if (!apiMessage.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(apiMessage);
    }

    @GetMapping("/state")
    public ResponseEntity<ElevatorState> getElevatorState() {
        return ResponseEntity.ok(elevatorService.getElevatorState());
    }
}
