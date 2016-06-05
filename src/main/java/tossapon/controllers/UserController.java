package tossapon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tossapon.models.User;
import tossapon.repositories.UserRepository;
import tossapon.responses.Response;
import tossapon.responses.ResponseWithClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tossapon Nuanchuay on 6/5/2559.
 */
@RestController
public class UserController {

    @Autowired
    private UserRepository userDao;
//
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody
    public Response Create(
            @RequestParam(required = true) String name,
            @RequestParam(required = true) String fbid
    ){
        User guestSameNameUser = userDao.findByFbid(fbid);
        Response response;

        boolean m = isInvalidName(name);
        boolean b = isInvalidFBID(fbid);

        if(canAddNewUser(guestSameNameUser, m, b)) {
            User u = new User(name, fbid, User.GenerateProfileUrl(fbid));
            userDao.save(u);
            userDao.
            response = new ResponseWithClass<>(true, u);
        }else{
            response = new Response(false);
        }
        return response;
    }

    private boolean canAddNewUser(User guestSameNameUser, boolean m, boolean b) {
        return guestSameNameUser == null && m && b;
    }

    private boolean isInvalidFBID(String fbid) {
        String sp = "[^\\d]";
        Pattern p = Pattern.compile(sp);
        Matcher ma = p.matcher(fbid);
        return !ma.find();
    }

    private boolean isInvalidName(String name) {
        String sp = "[^\\w\\s]";
        Pattern p = Pattern.compile(sp);
        Matcher m = p.matcher(name);
        return !m.find();
    }
}
