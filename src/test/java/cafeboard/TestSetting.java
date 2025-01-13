package cafeboard;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestSetting {

    @LocalServerPort
    int port;

    @Autowired
    DatabaseCleanupPostgres databaseCleanupPostgres;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        databaseCleanupPostgres.execute();
    }
}