package gov.alaska.m2.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersonTest {

    Person person = new Person("12345", "Generic", "Person", new Date(2323223232L));

    @Test
    void setValuesCorrectlyReturned() {
        assertEquals("12345", person.getSsn());
        assertEquals("Generic", person.getFirst_name());
        assertEquals("Person", person.getLast_name());
        assertEquals(new Date(2323223232L), person.getDob());

        assertTrue(person.equals(person));
        assertFalse(person.equals(null));
        assertFalse(person.equals("Test"));
    }
}
