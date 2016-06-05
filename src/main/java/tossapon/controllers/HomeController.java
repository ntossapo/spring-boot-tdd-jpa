package tossapon.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Tossapon Nuanchuay on 8/5/2559.
 */
@RestController
public class HomeController {

    @RequestMapping("/hello")
    public String Hello(){
        return "hello";
    }
}
