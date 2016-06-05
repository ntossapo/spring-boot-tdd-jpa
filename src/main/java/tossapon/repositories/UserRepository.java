package tossapon.repositories;

import org.springframework.data.repository.CrudRepository;
import tossapon.models.User;

import java.util.List;

/**
 * Created by Tossapon Nuanchuay on 6/5/2559.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    User findByFbid(String fbid);
    List<User> findByName(String name);
}
