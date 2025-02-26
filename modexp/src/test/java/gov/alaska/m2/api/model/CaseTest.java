package gov.alaska.m2.api.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CaseTest {

    Case testCase = new Case();

    @Test

    void setValuesCorrectlyReturned() {
        testCase.setCaseStatusCode("Test");
        testCase.setCaseModeCode("Test");
        testCase.setCaseNum(1L);

        assertEquals("Test", testCase.getCaseModeCode());
        assertEquals("Test", testCase.getCaseStatusCode());
        assertEquals(1L, testCase.getCaseNum());
    }
}
