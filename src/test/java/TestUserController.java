import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tossapon.Application;
import tossapon.models.User;
import tossapon.repositories.UserRepository;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by benvo_000 on 22/3/2559.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class TestUserController {
    private MockMvc mvc;
    private final String nf = "name";
    private final String ff = "fbid";
    private final String nv = "John Doe";
    private final String fv = "1234567890";


    @Autowired
    private UserRepository userRepository;
    @Resource
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp(){
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void RequestPostUsersJohnResponseOk() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .param("name", nv)
                        .param("fbid", fv)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void RequestPostUsersJohnResponseStatusOk() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .param(nf, nv)
                        .param(ff, fv)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    public void RequestPostUserJohnAssertDataInDatabase() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .param(nf, nv)
                        .param(ff, fv)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true));

        assertEquals(1, userRepository.count());
    }

    @Test
    public void RequestPostUserJohn2TimesAssert2ResponseFalse() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .param(nf, nv)
                        .param(ff, fv)
        )
                .andExpect(status().isOk());

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .param(nf, nv)
                        .param(ff, fv)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(false));
    }

    @Test
    public void RequestPostUserJohnAndJohnWithNotSameFBIDAssertResponseTrue() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .param(nf, nv)
                        .param(ff, fv)
        );

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .param(nf, nv)
                        .param(ff, "0987654321")
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true));
    }

    @Test
    public void RequestPostUserJohnAndJohnWithNotSameFBIDAssertDataCountEqual2() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .param(nf, nv)
                        .param(ff, fv)
        );

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .param(nf, nv)
                        .param(ff, "0987654321")
        );

        assertEquals("expect 2 user in database", 2, userRepository.count());
    }

    @Test
    public void RequestPostUserJohnAndMartinWithSameFBIDAssertResponseFalse() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .param(nf, nv)
                        .param(ff, fv)
        );

        mvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .param(nf, "Martin Skrtel")
                        .param(ff, fv)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(false));
    }

    @Test
    public void RequestPostUserJohnAssertDataInDatabaseNameJohn() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .param(nf, nv)
                        .param(ff, fv)
        );

        User result = userRepository.findByFbid(fv);
        assertEquals(nv, result.getName());
    }

    @Test
    public void RequestPostUserJohnAssertDataInDatabaseFbidEqualize() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .param(nf, nv)
                        .param(ff, fv)
        );

        List<User> result = userRepository.findByName(nv);
        assertEquals(result.size(), 1);
        if(result.size() > 0) {
            assertEquals(fv, result.get(result.size()-1).getFbid());
        }
    }

    @Test
    public void RequestPostUserJohnAssertProfileEqualProfileTemplate() throws Exception {
        String profileResult = "http://graph.facebook.com/" + fv + "/picture?type=large&redirect=true&width=400&height=400";
        mvc.perform(
                MockMvcRequestBuilders
                .post("/users")
                .param(nf, nv)
                .param(ff, fv)
        );

        User result = userRepository.findByFbid(fv);
        assertEquals(profileResult, result.getProfile());
    }

    @Test
    public void RequestInvalidNameAssertResponseFalse() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .param(nf, "{}[];;{}()")
                        .param(ff, "{}[];;{}()")
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(false));
    }

    @Test
    public void RequestInvalidName2AssertResponseFalse() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .param(nf, "John }Doe")
                        .param(ff, fv)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(false));
    }
    @Test
    public void RequestInvalidFBIDAssertResponseFalse() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders
                        .post("/users")
                        .param(nf, nv)
                        .param(ff, fv+"{")
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(false));
    }

}
