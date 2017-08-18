package appManager;

import logic.ShowInterface;

import javax.lang.model.element.Element;
import javax.persistence.EntityManager;
import java.util.List;

public interface ShowsManagerInterface {
    List<ShowInterface> getAllShows(EntityManager entityManager);
}
