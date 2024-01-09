package masiv.com.prueba.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import masiv.com.prueba.model.ElevatorState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.CompletableFuture;

@ContextConfiguration(classes = {ElevatorServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ElevatorServiceImplTest {
    @Autowired
    private ElevatorServiceImpl elevatorServiceImpl;

    /**
     * Method under test: {@link ElevatorServiceImpl#requestElevator(int, boolean)}
     */
    @Test
    void testRequestElevator() {
        // Arrange, Act and Assert
        assertEquals("", elevatorServiceImpl.requestElevator(10, true));
        assertEquals("It is not possible to add a floor to the queue that is equal to the current floor where the elevator"
                + " is located.", elevatorServiceImpl.requestElevator(1, true));
        assertEquals("", elevatorServiceImpl.requestElevator(0, true));
        assertEquals("", elevatorServiceImpl.requestElevator(10, false));
        assertEquals("", elevatorServiceImpl.requestElevator(0, false));
    }


    /**
     * Method under test: {@link ElevatorServiceImpl#moveElevator()}
     * This test case covers the default statement of the elevator movement.
     */
    @Test
    void testGetElevatorStateStart() {
        // Arrange and Act
        ElevatorState actualElevatorState = elevatorServiceImpl.getElevatorState();

        // Assert
        assertEquals(10, actualElevatorState.getCurrentFloor());
        assertTrue(actualElevatorState.getPendingFloorsInsideElevator().isEmpty());
        assertTrue(actualElevatorState.getPendingFloorsOutsideElevator().isEmpty());
    }


    @Test
    void testToVerifyTheResponseWhenThereAreNoFloorsToVisit() {
        elevatorServiceImpl.moveElevator();
        assertTrue(CompletableFuture.completedFuture("There are no floors to move the elevator.").isDone());
    }

    /**
     * Method under test: {@link ElevatorServiceImpl#moveElevator()}
     */
    @Test
    void testRequestMoveAndGetStateElevator() {

        assertEquals("", elevatorServiceImpl.requestElevator(10, true));
        assertEquals("It is not possible to add a floor to the queue that is equal to the current floor where the elevator"
                + " is located.", elevatorServiceImpl.requestElevator(1, true));
        assertEquals("", elevatorServiceImpl.requestElevator(0, true));
        assertEquals("", elevatorServiceImpl.requestElevator(10, false));
        assertEquals("", elevatorServiceImpl.requestElevator(0, false));


        elevatorServiceImpl.moveElevator();
        ElevatorState actualElevatorState = elevatorServiceImpl.getElevatorState();
        assertEquals(10, actualElevatorState.getCurrentFloor());
        assertTrue(actualElevatorState.getPendingFloorsInsideElevator().isEmpty());
        assertTrue(actualElevatorState.getPendingFloorsOutsideElevator().isEmpty());

    }


}
