package gov.alaska.m2.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationIndividualKeyTest {

    ApplicationIndividualKey applicationIndividualKey = new ApplicationIndividualKey();

    @Test
    void setValuesCorrectlyReturned() {

        applicationIndividualKey.setApp_num("Test");
        applicationIndividualKey.setIndv_id(1L);

        assertEquals("Test", applicationIndividualKey.getApp_num());
        assertEquals(1L, applicationIndividualKey.getIndv_id());

        assertTrue(applicationIndividualKey.equals(applicationIndividualKey));
        assertFalse(applicationIndividualKey.equals(null));
        assertFalse(applicationIndividualKey.equals("Test"));
    }
}
