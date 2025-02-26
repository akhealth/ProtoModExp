package gov.alaska.m2.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationTest {

    Application application;

    @BeforeEach
    void setUpTest() {
        application = new Application();
        application.setAppNum("1");
        application.setAppStatusCode("T");
        application.setReceivedDate(new Date(2323223232L));

    }

    @Test
    void setValuesCorrectlyReturned() {
        assertEquals("1", application.getAppNum());
        assertEquals("T", application.getAppStatusCode());
        assertEquals(new Date(2323223232L), application.getReceivedDate());
        assertEquals(null, application.getHeadOfHousehold());

        assertTrue(application.equals(application));
        assertFalse(application.equals(null));
        assertFalse(application.equals("Test"));
    }
}
