
package servlets;

import appManager.ShowsManager;
import appManager.UserShowsManager;
import appManager.db_manager.DBTrans;
import com.google.gson.Gson;
import logic.Show;
import logic.ShowNumber;
import logic.User;
import logic.UserShows;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "ShowServlet", urlPatterns = {"/SellTicket"})
public class ShowServlet extends HttpServlet {

    EntityManagerFactory emf;
    EntityManager em;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        em = emf.createEntityManager();

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String actionType = request.getParameter(Constants.ACTION_TYPE);
        ShowsManager showsManager = ServletUtils.getShowsManager(getServletContext());

        switch(actionType) {
            case Constants.GET_SHOW:
                getShowDetails(request, response, showsManager, em);
                break;
            case Constants.GET_SHOWS:
                getAllShows(response, showsManager, em);
                break;
        }
    }

    private void getAllShows(HttpServletResponse response, ShowsManager showsManager, EntityManager em) throws IOException {
        response.setContentType("application/json");
        List<Show> shows = showsManager.getAllShowsByDates(em);

        Gson gson = new Gson();
        String showsStr = gson.toJson(shows);
        String showNum = gson.toJson(shows.size());
        response.getWriter().write("["+showNum+","+showsStr+"]");
        response.getWriter().flush();
    }

    private void getShowDetails(HttpServletRequest request, HttpServletResponse response, ShowsManager showsManager, EntityManager em) throws IOException{
        response.setContentType("application/json");
        int showID = Integer.parseInt(request.getParameter(Constants.SHOW_ID));
        Show show = showsManager.getShowByID(em, showID);

        Gson gson = new Gson();
        String showStr = gson.toJson(show);
        response.getWriter().write(showStr);
        response.getWriter().flush();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        em = emf.createEntityManager();

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String actionType = request.getParameter(Constants.ACTION_TYPE);
        /*ArrayList<Ticket> ticketsList = new ArrayList<>();
        while (request.getAttribute(Constants.TICKET_LIST) != null)
        {
            ticketsList.add((Ticket) request.getAttribute(Constants.TICKET_LIST));
        }*/
        //TODO: if action is update the parameters will be different (need to pull parameters inside cases
        Show show;
        ShowsManager showsManager = ServletUtils.getShowsManager(getServletContext());

        switch (actionType)
        {
            case Constants.ADD_SHOW:
                addShowToDB(request, response, showsManager);
                break;
            case Constants.UPDATE_SHOW:
                show = (Show) request.getSession(false).getAttribute(Constants.SHOW);
                updateShow(request, response, show, showsManager);
                break;
            case Constants.DELETE_SHOW:
                show = (Show) request.getSession(false).getAttribute(Constants.SHOW);
                removeShowFromDB(request, response, show, showsManager);
                break;
        }

    }

    //TODO: function needs to be checked after shows aren't fictive
    private void updateShow(HttpServletRequest request, HttpServletResponse response, Show showToUpdate, ShowsManager showsManager) throws IOException {
        response.setContentType("application/json");

        Show showToDelete = null;
        Show showToDeleteFromSession = SessionUtils.getParameterForShow(request);
        int validInput = Constants.SHOW_UPDATE_SUCCESSFULLY;

        if (showToDeleteFromSession == null) {
            List<Show> shows = showsManager.getAllShows(em);
            showToDelete = showsManager.showIDExist(shows, showToUpdate);

            if(showToDelete != null)
            {
                Show.updateShowDetails(showToUpdate, showToDelete);

                showsManager.deleteShow(showToDelete);
                showsManager.addShow(showToUpdate);
                DBTrans.remove(em, showToDelete);
                em.close();
                request.getSession(true).setAttribute(Constants.SHOW, showToUpdate);
                DBTrans.persist(em, showToUpdate);
                em.close();
            }
            else
            {
                validInput = Constants.SHOW_NOT_EXIST;
            }
        }
        else
        {
            //TODO: what happen when showToDeleteFromSession return not null????
        }

        Gson gson = new Gson();
        String res = gson.toJson(validInput);
        String showJson = gson.toJson(showToUpdate);
        response.getWriter().write("["+res+","+showJson+"]");
        response.getWriter().flush();
    }

    private void addShowToDB(HttpServletRequest request, HttpServletResponse response, ShowsManager showsManager) throws IOException{
        response.setContentType("application/json");

        Show show = null;
        Show showToAdd = null;
        UserShows userShowToUpdate = null;
        int validInput = Constants.SHOW_ADDED_SUCCESSFULLY;

        List<Show> shows = showsManager.getAllShows(em);
        ShowNumber.showNumber = shows.size() + 1;
        show = new Show(request.getParameter(Constants.SHOW_NAME), request.getParameter(Constants.SHOW_LOCATION), request.getParameter(Constants.PICTURE_URL), Integer.parseInt(request.getParameter(Constants.NUMBER_OF_TICKETS)), Integer.parseInt(request.getParameter(Constants.SHOW_PRICE)), LocalDateTime.parse(request.getParameter(Constants.SHOW_DATE)), request.getParameter(Constants.SHOW_ABOUT)/*ticketsList*/);
        showToAdd = showsManager.showLocationAndDateExist(shows, show);
        User userFromSession = (User) request.getSession(false).getAttribute(Constants.LOGIN_USER);

        if(showToAdd == null) {
            validInput = Constants.SHOW_ADDED_SUCCESSFULLY;
            request.getSession(true).setAttribute(Constants.SHOW, show);
            DBTrans.persist(em, show);
            em.close();
            userShowToUpdate = new UserShows(userFromSession.getEmail(), show.getShowID(), Constants.SHOW_TO_SELL);
            em = emf.createEntityManager();
            DBTrans.persist(em, userShowToUpdate);
            em.close();

        }
        else {
            if (UserShowsManager.showIDExistInUser(em, userFromSession.getEmail(), showToAdd.getShowID())) {
                validInput = Constants.SHOW_EXIST;
            } else //if user is logged in, and want to add show, need to update userShowsManager, and do persist
            {
                request.getSession(true).setAttribute(Constants.SHOW, show);
                validInput = Constants.SHOW_ADDED_SUCCESSFULLY;
                DBTrans.persist(em, show);
                em.close();
                userShowToUpdate = new UserShows(userFromSession.getEmail(), show.getShowID(), Constants.SHOW_TO_SELL);
                em = emf.createEntityManager();
                DBTrans.persist(em, userShowToUpdate);
                em.close();
            }
        }


        Gson gson = new Gson();
        String res = gson.toJson(validInput);
        String showJson = gson.toJson(show);
        response.getWriter().write("["+res+","+showJson+"]");
        response.getWriter().flush();
    }

    private void removeShowFromDB(HttpServletRequest request, HttpServletResponse response, Show show, ShowsManager showsManager) throws IOException {
        response.setContentType("application/json");

        Show showToRemove;
        int validInput;
        int showID = Integer.parseInt(request.getParameter(Constants.SHOW_ID));

        List<Show> shows = showsManager.getAllShows(em);
        showToRemove = showsManager.showIDExist(shows, showID);
        User userFromSession = (User) request.getSession(false).getAttribute(Constants.LOGIN_USER);

        if (showToRemove == null) {
            validInput = Constants.SHOW_NOT_EXIST;
        }
        else {
            validInput = Constants.SHOW_DELETE_SUCCESSFULLY;
            DBTrans.remove(em, show);
            UserShows userShowToUpdate = new UserShows(userFromSession.getEmail(), show.getShowID(), Constants.SHOW_TO_SELL);
            DBTrans.remove(em, userShowToUpdate);
        }


        Gson gson = new Gson();
        String res = gson.toJson(validInput);

        response.getWriter().write("[" + res + "," + showToRemove + "]");
        response.getWriter().flush();

    }
}
