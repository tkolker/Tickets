package appManager;

import logic.UserShows;
import logic.UserShowsInterface;

import javax.persistence.EntityManager;
import java.util.List;

public interface UserShowsManagerInterface {

    List<UserShowsInterface> getShowByUserID(EntityManager em, String userId);
}
