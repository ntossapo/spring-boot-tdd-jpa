import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tossapon.Application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

/**
 * Created by Tossapon Nuanchuay on 9/5/2559.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class TestSimple {
    @Test
    public void testRegexp() throws Exception {
        String name = "][";
        String sp = "\\W";
        Pattern p = Pattern.compile(sp);
        Matcher m = p.matcher(name);
        boolean f = m.find();
        boolean s = m.find();
        assertEquals(true, f);
        assertEquals(true, s);
    }
}
