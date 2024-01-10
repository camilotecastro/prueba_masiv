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

        // 10 -> 1 -> 0 -> 10 -> 0 -> 10  UP
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

    /**
     * Method under test: {@link ElevatorServiceImpl#moveElevator()}
     */

    @Test
    void testRequestMoveAndGetStateElevator2() {

        assertEquals("", elevatorServiceImpl.requestElevator(3, true));
        elevatorServiceImpl.moveElevator();

        // 3 -> 8 -> 10 -> 6 -> 4 -> 2 DOWN
        assertEquals("", elevatorServiceImpl.requestElevator(8, true));
        assertEquals("", elevatorServiceImpl.requestElevator(10, false));
        assertEquals("", elevatorServiceImpl.requestElevator(6, false));
        assertEquals("", elevatorServiceImpl.requestElevator(4, false));
        assertEquals("", elevatorServiceImpl.requestElevator(2, false));

        elevatorServiceImpl.moveElevator();
        ElevatorState actualElevatorState = elevatorServiceImpl.getElevatorState();
        assertEquals(2, actualElevatorState.getCurrentFloor());
        assertTrue(actualElevatorState.getPendingFloorsInsideElevator().isEmpty());
        assertTrue(actualElevatorState.getPendingFloorsOutsideElevator().isEmpty());

    }

    /**
     * Method under test: {@link ElevatorServiceImpl#moveElevator()}
     */
    @Test
    void testRequestMoveAndGetStateElevator3() {

        assertEquals("", elevatorServiceImpl.requestElevator(10, true));
        elevatorServiceImpl.moveElevator();

        // 10  -> 9 -> 6 -> 2 -> 4 -> 7 -> 11 UP
        assertEquals("", elevatorServiceImpl.requestElevator(9, true));
        assertEquals("", elevatorServiceImpl.requestElevator(6, true));
        assertEquals("", elevatorServiceImpl.requestElevator(2, true));
        assertEquals("", elevatorServiceImpl.requestElevator(11, false));
        assertEquals("", elevatorServiceImpl.requestElevator(4, false));
        assertEquals("", elevatorServiceImpl.requestElevator(7, false));

        elevatorServiceImpl.moveElevator();
        ElevatorState actualElevatorState = elevatorServiceImpl.getElevatorState();
        assertEquals(11, actualElevatorState.getCurrentFloor());
        assertTrue(actualElevatorState.getPendingFloorsInsideElevator().isEmpty());
        assertTrue(actualElevatorState.getPendingFloorsOutsideElevator().isEmpty());

    }

    /**
     * Method under test: {@link ElevatorServiceImpl#moveElevator()}
     */
    @Test
    void testRequestMoveAndGetStateElevator4() {

        assertEquals("", elevatorServiceImpl.requestElevator(4, true));
        elevatorServiceImpl.moveElevator();

        // 4 -> 5 -> 9 -> 12 -> 3 -> 2
        assertEquals("", elevatorServiceImpl.requestElevator(9, true));
        assertEquals("", elevatorServiceImpl.requestElevator(5, true));
        assertEquals("", elevatorServiceImpl.requestElevator(12, true));
        assertEquals("", elevatorServiceImpl.requestElevator(3, false));
        assertEquals("", elevatorServiceImpl.requestElevator(2, false));
        assertEquals("It is not possible to add a floor to the queue that is equal to the current floor where the elevator is located.",
                elevatorServiceImpl.requestElevator(4, false));
        elevatorServiceImpl.moveElevator();
        ElevatorState actualElevatorState = elevatorServiceImpl.getElevatorState();
        assertEquals(2, actualElevatorState.getCurrentFloor());
        assertTrue(actualElevatorState.getPendingFloorsInsideElevator().isEmpty());
        assertTrue(actualElevatorState.getPendingFloorsOutsideElevator().isEmpty());

    }



}
