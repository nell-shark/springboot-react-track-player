package com.nellshark.trackplayer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestcontainersTest extends AbstractTestcontainers {
    @Test
    void canStartMySqlDB() {
        assertThat(mySQLContainer.isRunning()).isTrue();
        assertThat(mySQLContainer.isCreated()).isTrue();
    }
}
