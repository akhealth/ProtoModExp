package gov.alaska.m2.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import gov.alaska.m2.api.repository.ApplicationRepository;
import gov.alaska.m2.api.repository.PersonRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    RedirectAttributes redirectAttributes;

    @MockitoBean
    PersonRepository personRepository;

    @MockitoBean
    ApplicationRepository applicationRepository;

    @Test
    public void defaultLoginPageIsDisplayed() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/"))
                .andExpect(view().name("login"))
                .andExpect(status().isOk());
    }

    @Test
    public void loginPageIsDisplayed() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/login"))
                .andExpect(view().name("login"))
                .andExpect(status().isOk());
    }

    @Test
    public void postResultsInRedirectToLandingPage() throws Exception {
        String username = "TestUser";
        String password = "TestPass";

        when(redirectAttributes.addFlashAttribute("username", username)).thenReturn(redirectAttributes);

        mvc.perform(MockMvcRequestBuilders
                .post("/login").param("username", username).param("password", password).param("submit", ""))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/landingpage"))
                .andExpect(flash().attribute("username", username));

    }
}
