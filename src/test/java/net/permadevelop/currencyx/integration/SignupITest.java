package net.permadevelop.currencyx.integration;

import net.permadevelop.currencyx.config.WebAppConfigurationAware;
import org.junit.Test;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SignupITest extends WebAppConfigurationAware {
    @Test
    public void displaysSignupForm() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(model().attributeExists("signupForm"))
                .andExpect(view().name("signup/signup"))
                .andExpect(content().string(
                        allOf(
                                containsString("<title>Registration</title>"),
                                containsString("<legend>Please Register</legend>")
                        ))
                );
    }
}
