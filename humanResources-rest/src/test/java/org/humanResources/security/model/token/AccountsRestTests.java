package org.humanResources.security.model.token;

import static org.assertj.core.api.Assertions.assertThat;
import com.fasterxml.jackson.databind.JsonNode;
import org.humanResources.dto.AccountDTO;
import org.humanResources.environment.BaseTestEnvironmentImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Run tests with SpringBoot support
 *
 * MOCK (default) - Loads a WebApplicationContext and provides a mock servlet
 * environment. It will not start an embedded servlet container. If servlet APIs are
 * not on your classpath, this mode will fall back to creating a regular non-web
 * ApplicationContext
 */

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {SpringBootSecurityApplication.class}
//        ,webEnvironment= WebEnvironment.MOCK)

//@TestPropertySource(locations = "classpath:humanResources/application-integrationTest.properties")
public class AccountsRestTests extends WebBaseTest{




    @Test
    public void should_get_all_accounts() throws Exception {

        baseTestEnvironment.build(true);

        Class<ArrayList<AccountDTO>> clazz =
                (Class<ArrayList<AccountDTO>>) new ArrayList<AccountDTO>().getClass();

        ArrayList<AccountDTO> accounts = this.getResource("/api/accounts", 0, clazz);

        assertThat(accounts.size() == 1);

    }




/*
        Optional<AccountDTO> result = accounts.stream().findFirst();
        assertThat(result.isPresent());

        AccountDTO accountDTO = result.get();

        assertThat(accountDTO.getName().equals(BaseTestEnvironmentImpl.User_defaultUser));


    }
*/

    @Test
    public void should_login_valid_user2() throws Exception{

        baseTestEnvironment.build(true);

        String userName = "defaultUser";
        String password = "password123";

        LoginRequest loginRequest = new LoginRequest(userName, password);

        /*LoadPropertyRequest loadPropertyRequest = new LoadPropertyRequest();
        loadPropertyRequest.setProperty(property);*/

        JsonNode jsonLoginRequest =  getJSonFromObject(loginRequest);

        String indented = webServicesObjectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(jsonLoginRequest);

        System.out.println("====Login Request:");
        System.out.println(indented);

        //JsonNode jsonResponse = webServiceConnector.executeRequest(jsonFindProgramRequest, URI, this.mockMvc, userName);

        MvcResult result = mockMvc.perform(
                //post(WebSecurityConfig.FORM_BASED_LOGIN_ENTRY_POINT)
                post("/api/auth/signin")
                        .contentType(MediaType.valueOf(contentType))
                        .accept(MediaType.valueOf(contentType))
                        //.header("X-Requested-With", "XMLHttpRequest")
                        .content(jsonLoginRequest.toString()))
                .andExpect(status().isOk())

                .andExpect(content().contentType(resultContentType))
                //.andExpect(content().json(expectedResponseJson.toString()))
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        JsonNode jsonResponse = webServicesObjectMapper.readValue(responseString, JsonNode.class);

        /*
        TokenResponse tokenResponse = webServicesObjectMapper.readValue(responseString, TokenResponse.class);

        String formattedResponseString = webServicesObjectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(jsonResponse);

        System.out.println("====Actual response:");
        System.out.println(formattedResponseString);

*/
        // JsonNode propertyDeepLoadResponseJsonNode = jsonResponse.get("result");


        System.out.println();
        //return propertyDeepLoadResponseJsonNode;

    }

    /*
    @Test
    public void should_refresh_token() throws Exception{

        String userName = "defaultUser";
        String password = "password123";

        LoginRequest loginRequest = new LoginRequest(userName, password);


        JsonNode jsonLoginRequest =  getJSonFromObject(loginRequest);

        String indented = webServicesObjectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(jsonLoginRequest);

        System.out.println("====Login Request:");
        System.out.println(indented);

        //get tokens

        MvcResult result = mockMvc.perform(
                post(WebSecurityConfig.FORM_BASED_LOGIN_ENTRY_POINT)
                        .contentType(MediaType.valueOf(contentType))
                        .accept(MediaType.valueOf(contentType))
                        .header("X-Requested-With", "XMLHttpRequest")
                        .content(jsonLoginRequest.toString()))
                .andExpect(status().isOk())

                .andExpect(content().contentType(resultContentType))
                //.andExpect(content().json(expectedResponseJson.toString()))
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        TokenResponse tokenResponse = webServicesObjectMapper.readValue(responseString, TokenResponse.class);


        //refresh tokens

        //JsonNode jsonResponse = webServiceConnector.executeRequest(jsonFindProgramRequest, URI, this.mockMvc, userName);

        MvcResult resultRefreshToken = mockMvc.perform(
                post(WebSecurityConfig.TOKEN_REFRESH_ENTRY_POINT)
                        .contentType(MediaType.valueOf(contentType))
                        .accept(MediaType.valueOf(contentType))
                        .header(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM, "Bearer "+tokenResponse.getRefreshToken())
        )
                .andExpect(status().isOk())

                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                //.andExpect(content().json(expectedResponseJson.toString()))
                .andReturn();

        String resultRefreshTokenString = resultRefreshToken.getResponse().getContentAsString();
        JsonNode jsonResponse = webServicesObjectMapper.readValue(resultRefreshTokenString, JsonNode.class);

        TokenResponse resultRefreshTokenResponse = webServicesObjectMapper.readValue(resultRefreshTokenString, TokenResponse.class);

        String formattedResponseString = webServicesObjectMapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(jsonResponse);

        System.out.println("====Actual response:");
        System.out.println(formattedResponseString);


        // JsonNode propertyDeepLoadResponseJsonNode = jsonResponse.get("result");


        System.out.println();
        //return propertyDeepLoadResponseJsonNode;

    }*/


    protected JsonNode getJSonFromObject(Object object) {
        JsonNode actualObj;
        try {
            //String jsonString = IOUtils.toString(new ClassPathResource(jsonFile).getInputStream());
            //ObjectMapper mapper = new ObjectMapper();
            actualObj = webServicesObjectMapper.valueToTree(object);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return actualObj;

    }
}