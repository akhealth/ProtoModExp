package gov.alaska.m2.web;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import gov.alaska.m2.api.repository.ApplicationRepository;
import gov.alaska.m2.api.repository.PersonRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class LandingControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    PersonRepository personRepository;

    @MockitoBean
    ApplicationRepository applicationRepository;

    @Test
    public void defaultLandingPageIsDisplayed() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/landingpage"))
                .andExpect(view().name("landingpage"))
                .andExpect(status().isOk());
    }

    @Test
    public void modulePageIsDisplayed() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/module"))
                .andExpect(view().name("module"))
                .andExpect(status().isOk());
    }

}
