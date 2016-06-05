import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tossapon.Application;
import tossapon.models.User;
import tossapon.repositories.UserRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Tossapon Nuanchuay on 8/5/2559.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class TestUserRepository {

    private String name = "John Doe";
    private String fbid = "1234567890";

    @Autowired
    UserRepository userRepository;

    @After
    public void tearDown() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void testSaveUserAndFindFBIDAssertEqualNameAndFBID() throws Exception {
        User a = new User(name, fbid, User.GenerateProfileUrl(fbid));
        userRepository.save(a);

        User b = userRepository.findByFbid(a.getFbid());
        assertEquals(a.getFbid(), b.getFbid());
    }

    @Test
    public void testSaveUserAndFindByNameAssertEqualNameAndFBID(){
        User a = new User(name, fbid, User.GenerateProfileUrl(fbid));
        userRepository.save(a);

        List<User> b = userRepository.findByName(a.getName());
        assertEquals(a.getName(), b.get(0).getName());
    }

    @Test
    public void testSaveUserAssertUserCount1() throws Exception {
        User a = new User(name, fbid, User.GenerateProfileUrl(fbid));
        userRepository.save(a);
        assertEquals(1, userRepository.count());
    }
}
