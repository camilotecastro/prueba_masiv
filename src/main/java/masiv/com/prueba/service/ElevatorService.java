package masiv.com.prueba.service;

import masiv.com.prueba.model.ElevatorState;

import java.util.concurrent.CompletableFuture;

public interface ElevatorService {

    /**
     * Asynchronously moves the elevator based on the pending floors.
     * If there are no pending floors, a message indicating that there are no floors is returned.
     * The method continues to move towards the closest floors until all pending floors are reached.
     *
     * @return A CompletableFuture representing the result of the elevator movement.
     * The completed future contains a message indicating the completion status.
     */
    CompletableFuture<String> moveElevator();

    /**
     * Requests the elevator to move to a specified floor.
     *
     * @param numberFloor The floor number to which the elevator is requested to move.
     * @param priority    Indicates whether the floor request has priority over others.
     *                    If set to true, the floor is added to the inside queue; otherwise, to the outside queue.
     * @return A message indicating the result of the elevator request.
     * If the specified floor is equal to the current floor, a message is logged and returned indicating
     * that it is not possible to add a floor to the queue at the current location.
     * Otherwise, an empty string is returned to indicate a successful floor request.
     */
    String requestElevator(int numberFloor, boolean priority);

    /**
     * Retrieves the current state of the elevator.
     *
     * @return An ElevatorState object representing the current state of the elevator,
     * including the current floor and the lists of pending floors outside and inside the elevator.
     */
    ElevatorState getElevatorState();
}
