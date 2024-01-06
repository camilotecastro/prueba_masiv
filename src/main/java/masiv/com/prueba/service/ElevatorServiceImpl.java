package masiv.com.prueba.service;

import masiv.com.prueba.model.ElevatorState;
import masiv.com.prueba.model.Floor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class ElevatorServiceImpl implements ElevatorService {

    private static final Logger logger = LoggerFactory.getLogger(ElevatorServiceImpl.class);
    private int currentFloor = 1;
    private String movementDirection = UP_DIRECTION;
    private static final String UP_DIRECTION = "UP";
    private static final String DOWN_DIRECTION = "DOWN";
    PriorityQueue<Floor> pendingFloorsInside = new PriorityQueue<>(Comparator.comparingInt(
            floor -> Math.abs(floor.getNumber() - currentFloor))
    );
    PriorityQueue<Floor> pendingFloorsOutside = new PriorityQueue<>(Comparator.comparingInt(
            floor -> Math.abs(floor.getNumber() - currentFloor))
    );


    @Override
    public String requestElevator(int floorNumber, boolean priority) {
        String apiMessage = "It is not possible to add a floor to the queue that is " +
                "equal to the current floor where the elevator is located.";

        if (floorNumber == currentFloor) {
            logger.info(apiMessage);
            return apiMessage;
        }

        Floor floor = new Floor(floorNumber);
        if (priority) {
            pendingFloorsInside.add(floor);
        } else {
            pendingFloorsOutside.add(floor);
        }

        return "";
    }


    @Override
    @Async("taskExecutor")
    public CompletableFuture<String> moveElevator() {

        String apiMessage = "There are no floors to move the elevator.";
        if (pendingFloorsInside.isEmpty() && pendingFloorsOutside.isEmpty()) {
            logger.info(apiMessage);
            return CompletableFuture.completedFuture(apiMessage);
        }

        while (!pendingFloorsInside.isEmpty() || !pendingFloorsOutside.isEmpty()) {
            Floor floor;

            if (!pendingFloorsInside.isEmpty()) {
                floor = findClosestFloor(pendingFloorsInside, movementDirection);

                if (Objects.nonNull(floor)) {
                    moveTowardsFloor(floor);
                    pendingFloorsInside.remove(floor);
                } else {
                    // If no floor is found in the current direction, change the direction
                    changeDirection();
                }

            } else {
                floor = findClosestFloor(pendingFloorsOutside, movementDirection);

                if (Objects.nonNull(floor)) {
                    moveTowardsFloor(floor);
                    pendingFloorsOutside.remove(floor);
                } else {
                    // If no floor is found in the current direction, change the direction
                    changeDirection();
                }
            }

        }
        return CompletableFuture.completedFuture("The elevator has reached all the floors.");

    }


    @Override
    public ElevatorState getElevatorState() {
        return new ElevatorState(this.currentFloor, this.pendingFloorsOutside, this.pendingFloorsInside);
    }


    /**
     * Moves the elevator towards a specified floor.
     *
     * @param floor The target floor towards which the elevator is moving.
     *              InterruptedException If the thread is interrupted while waiting for the elevator to move.
     */
    private void moveTowardsFloor(Floor floor) {
        logger.info("Elevator in direction {}", movementDirection);
        logger.info("Moving elevator towards floor #{}", floor.getNumber());

        try {
            int destination = floor.getNumber();
            int movementDirectionTemp = Integer.compare(destination, currentFloor);

            for (int currentFloorTemp = currentFloor; currentFloorTemp != destination; currentFloorTemp += movementDirectionTemp) {
                Thread.sleep(1000L); // 1 second per floor
                currentFloor = currentFloorTemp;
            }
            currentFloor = destination;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Error during elevator movement.", e);
        }

    }


    /**
     * Finds the closest floor in the specified direction from the current floor.
     *
     * @param floors            The queue of floors to search for the closest floor.
     * @param movementDirection The movement direction of the elevator ("UP" or "DOWN").
     * @return The closest floor in the specified direction from the current floor.
     * Returns null if no floor in the given direction is found in the queue.
     */
    private Floor findClosestFloor(Queue<Floor> floors, String movementDirection) {
        boolean isUpward = movementDirection.equals(UP_DIRECTION);
        boolean isDownward = movementDirection.equals(DOWN_DIRECTION);

        Floor nextFloor = null;

        for (Floor floor : floors) {
            if ((isUpward && floor.getNumber() > currentFloor) || (isDownward && floor.getNumber() < currentFloor)) {
                nextFloor = floor;
                break;
            }
        }
        return nextFloor;
    }

    /**
     * Changes the movement direction of the elevator.
     * If the current direction is "UP," it changes it to "DOWN," and vice versa.
     */
    private void changeDirection() {
        movementDirection = (movementDirection.equals(UP_DIRECTION))
                ? DOWN_DIRECTION : UP_DIRECTION;
    }

}
