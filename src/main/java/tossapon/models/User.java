package tossapon.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Tossapon Nuanchuay on 6/5/2559.
 */

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String name;
    @NotNull
    private String fbid;
    @NotNull
    private String profile;

    public User() {
    }

    public User(String name, String fbid, String profile) {
        this.name = name;
        this.fbid = fbid;
        this.profile = profile;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFbid(String fbid) {
        this.fbid = fbid;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFbid() {
        return fbid;
    }

    public static String GenerateProfileUrl(String fbid){
        return "http://graph.facebook.com/" + fbid + "/picture?type=large&redirect=true&width=400&height=400";
    }
}
