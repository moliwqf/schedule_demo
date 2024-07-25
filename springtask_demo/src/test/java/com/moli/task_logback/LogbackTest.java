package com.moli.task_logback;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author moli
 * @time 2024-07-16 14:32:04
 */
@Slf4j
@SpringBootTest
public class LogbackTest {

    @Test
    public void testLogback() {
        log.info("test log back");
    }
}
