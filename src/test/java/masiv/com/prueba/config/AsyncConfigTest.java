package masiv.com.prueba.config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AsyncConfig.class})
@ExtendWith(SpringExtension.class)
class AsyncConfigTest {
    @Autowired
    private AsyncConfig asyncConfig;

    /**
     * Method under test: {@link AsyncConfig#taskExecutor()}
     */
    @Test
    void testTaskExecutor() {
        assertTrue(asyncConfig.taskExecutor() instanceof SimpleAsyncTaskExecutor);
    }

    @Test
    void shouldNotReturnConcurrentTaskExecutorInstance() {
        assertFalse(asyncConfig.taskExecutor() instanceof ConcurrentTaskExecutor);
    }

    @Test
    void shouldNotReturnThreadPollTaskExecutorInstance() {
        assertFalse(asyncConfig.taskExecutor() instanceof ThreadPoolTaskExecutor);
    }
}
