package appManager;


import logic.ShowArchive;
import logic.ShowInterface;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class ShowsArchiveManager implements ShowsManagerInterface{
    ArrayList<ShowArchive> showsArchive =  new ArrayList<>();

    public ArrayList<ShowArchive> getShows(){
        return showsArchive;
    }

    public List<ShowInterface> getAllShows(EntityManager entityManager)
    {
        TypedQuery<ShowInterface> query = entityManager.createQuery("SELECT s FROM ShowArchive s ORDER BY s.m_ShowID", ShowInterface.class);
        return query.getResultList();
    }
}
