package masiv.com.prueba.model;

import java.util.PriorityQueue;

public class ElevatorState {
    private int currentFloor;
    private PriorityQueue<Floor> pendingFloorsOutsideElevator;
    private PriorityQueue<Floor> pendingFloorsInsideElevator;

    public ElevatorState() {
    }

    public ElevatorState(int currentFloor,
                         PriorityQueue<Floor> pendingFloorsOutsideElevator,
                         PriorityQueue<Floor> pendingFloorsInsideElevator) {
        this.currentFloor = currentFloor;
        this.pendingFloorsOutsideElevator = pendingFloorsOutsideElevator;
        this.pendingFloorsInsideElevator = pendingFloorsInsideElevator;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public PriorityQueue<Floor> getPendingFloorsOutsideElevator() {
        return pendingFloorsOutsideElevator;
    }

    public void setPendingFloorsOutsideElevator(PriorityQueue<Floor> pendingFloorsOutsideElevator) {
        this.pendingFloorsOutsideElevator = pendingFloorsOutsideElevator;
    }

    public PriorityQueue<Floor> getPendingFloorsInsideElevator() {
        return pendingFloorsInsideElevator;
    }

    public void setPendingFloorsInsideElevator(PriorityQueue<Floor> pendingFloorsInsideElevator) {
        this.pendingFloorsInsideElevator = pendingFloorsInsideElevator;
    }
}
