package net.permadevelop.currencyx.integration;

import net.permadevelop.currencyx.config.WebAppConfigurationAware;
import net.permadevelop.currencyx.domain.ConversionQuery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.Instant;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ConversionQueryITest extends WebAppConfigurationAware {

    private TestingAuthenticationToken userAuthentication;

    @Before
    public void setUp() throws Exception {
        User user = new User("user", "", AuthorityUtils.createAuthorityList("ROLE_USER"));
        userAuthentication = new TestingAuthenticationToken(user, null);
    }

    @Test
    @WithMockUser
    public void getEmptyQueries() throws Exception {
        mockMvc.perform(
                get("/query")
                        .principal(userAuthentication))
                .andExpect(status().isOk())
                .andExpect(content().contentType(RestTestUtil.JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @WithMockUser
    public void queryPosted() throws Exception {
        ConversionQuery query = new ConversionQuery();
        long ratesTime = Instant.now().toEpochMilli();
        query.setRatesTimestamp(ratesTime);

        mockMvc.perform(
                post("/query")
                        .principal(userAuthentication)
                        .contentType(RestTestUtil.JSON_UTF8)
                        .content(RestTestUtil.convertToJsonBytes(query)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(RestTestUtil.JSON_UTF8))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.ratesTimestamp", is(ratesTime)));
    }
}
