
package servlets;

import appManager.ShowsManager;
import appManager.UserShowsManager;
import appManager.db_manager.DBTrans;
import com.google.gson.Gson;
import logic.*;
import utils.ServletUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

        switch (actionType) {
            case Constants.GET_SHOW:
                getShowDetails(request, response, showsManager, em);
                break;
            case Constants.GET_SHOWS:
                getAllShows(response, showsManager, em);
                break;
            case Constants.GET_SELL_SHOWS:
                getMySellShows(request, response, showsManager, em);
                break;
            case Constants.GET_SEARCH_SHOW:
                getSearchShow(request, response, showsManager, em);
                break;
            case Constants.GET_SHOW_EXIST:
                isShowFound(request, response, showsManager, em);
                break;
            case Constants.GET_BOUGHT_TICKETS:
                //TODO: implement get bought tickets
                break;
        }
    }

    private void isShowFound(HttpServletRequest request, HttpServletResponse response, ShowsManager showsManager, EntityManager em) throws IOException {
        response.setContentType("application/json");
        ArrayList<Show> res = new ArrayList<>();
        String showNameToSearch = request.getParameter(Constants.SHOW_NAME);
        List<Show> showsFound = showsManager.getAllShowsWithName(em, showNameToSearch);
        Gson gson = new Gson();
        String result;
        if (!showsFound.isEmpty()) {
            result = gson.toJson(Constants.SHOW_NAME_EXIST);
        }
        else {
            result = gson.toJson(Constants.SHOW_NAME_NOT_EXIST);
        }
        response.getWriter().write("[" + result + "]");
        response.getWriter().flush();
    }

    private void getSearchShow(HttpServletRequest request, HttpServletResponse response, ShowsManager showsManager, EntityManager em) throws IOException {
        response.setContentType("application/json");
        ArrayList<Show> res = new ArrayList<>();
        String showNameToSearch = request.getParameter(Constants.SHOW_NAME);
        List<Show> showsFound = showsManager.getAllShowsWithName(em, showNameToSearch);

        if(!showsFound.isEmpty()) {
            res.addAll(showsFound);

            Gson gson = new Gson();
            String showsStr = gson.toJson(res);
            String showNum = gson.toJson(res.size());
            response.getWriter().write("[" + showNum + "," + showsStr + "]");
            response.getWriter().flush();
        }
        else {
            Gson gson = new Gson();
            String result = gson.toJson(Constants.SHOW_NOT_EXIST);
            response.getWriter().write("[" + result +"]");
            response.getWriter().flush();
        }
    }

    private void getMySellShows(HttpServletRequest request, HttpServletResponse response, ShowsManager showsManager, EntityManager em) throws IOException {
        response.setContentType("application/json");
        ArrayList<Show> res = new ArrayList<>();
        String userId = ((User) request.getSession(false).getAttribute(Constants.LOGIN_USER)).getEmail();
        UserShowsManager userShowsManager = ServletUtils.getUserShowsManager(getServletContext());
        List<Show> shows = showsManager.getAllShows(em);
        List<UserShows> userShows = userShowsManager.getShowByUserID(em, userId);
        int i = 0;

        for (Show s : shows) {
            if (s.getShowID() == userShows.get(i).getShowId()) {
                res.add(s);
                if (i < userShows.size() - 1)
                    i++;
                else
                    break;
            }
        }

        Gson gson = new Gson();
        String showsStr = gson.toJson(res);
        String showNum = gson.toJson(res.size());
        response.getWriter().write("[" + showNum + "," + showsStr + "]");
        response.getWriter().flush();
    }

    private void getAllShows(HttpServletResponse response, ShowsManager showsManager, EntityManager em) throws IOException {
        response.setContentType("application/json");
        List<Show> shows = showsManager.getAllShows(em);

        Gson gson = new Gson();
        String showsStr = gson.toJson(shows);
        String showNum = gson.toJson(shows.size());
        response.getWriter().write("[" + showNum + "," + showsStr + "]");
        response.getWriter().flush();
    }

    private void getShowDetails(HttpServletRequest request, HttpServletResponse response, ShowsManager showsManager, EntityManager em) throws IOException {
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

        switch (actionType) {
            case Constants.ADD_SHOW:
                addShowToDB(request, response, showsManager);
                break;
            case Constants.UPDATE_SHOW:
                //show = Show.createShow(request.getParameter(Constants.SHOW_NAME), request.getParameter(Constants.SHOW_LOCATION), request.getParameter(Constants.PICTURE_URL), Integer.parseInt(request.getParameter(Constants.NUMBER_OF_TICKETS)), Integer.parseInt(request.getParameter(Constants.SHOW_PRICE)), LocalDateTime.parse(request.getParameter(Constants.SHOW_DATE)), request.getParameter(Constants.SHOW_ABOUT));
                int showId = Integer.parseInt(request.getParameter(Constants.SHOW_ID));
                updateShow(request, response, showId, showsManager);
                break;
            case Constants.DELETE_SHOW:
                show = (Show) request.getSession(false).getAttribute(Constants.SHOW);
                removeShowFromDB(request, response, show, showsManager);
                break;
            case Constants.BUY_TICKET:
                //TODO: implement buy
                break;
        }

    }

    //TODO: function needs to be checked after shows aren't fictive
    private void updateShow(HttpServletRequest request, HttpServletResponse response, int showID, ShowsManager showsManager) throws IOException {
        response.setContentType("application/json");

        Show showToDelete = null;
        //Show showToDeleteFromSession = SessionUtils.getParameterForShow(request);
        Show showToUpdate = Show.createShowToUpdate(request.getParameter(Constants.SHOW_ID),request.getParameter(Constants.SHOW_NAME), request.getParameter(Constants.SHOW_LOCATION), request.getParameter(Constants.PICTURE_URL), request.getParameter(Constants.NUMBER_OF_TICKETS), request.getParameter(Constants.SHOW_PRICE), request.getParameter(Constants.SHOW_DATE), request.getParameter(Constants.SHOW_ABOUT));

        int validInput = Constants.SHOW_UPDATE_SUCCESSFULLY;

        //if (showToDeleteFromSession == null) {
        List<Show> shows = showsManager.getAllShows(em);
        showToDelete = showsManager.showIDExist(shows, showToUpdate);

        if (showToDelete != null) {
            Show.updateShowDetails(showToUpdate, showToDelete);

            showsManager.deleteShow(showToDelete);
            showsManager.addShow(showToUpdate);
            DBTrans.remove(em, showToDelete);
            request.getSession(true).setAttribute(Constants.SHOW, showToUpdate);
            DBTrans.persist(em, showToUpdate);
            em.close();
        } else {
            validInput = Constants.SHOW_NOT_EXIST;
        }

        Gson gson = new Gson();
        String res = gson.toJson(validInput);
        String showJson = gson.toJson(showToUpdate);
        response.getWriter().write("["+res+","+showJson+"]");
        response.getWriter().flush();
    }

    private void saveImageToDB(String showUrl, String showName, int showId) throws IOException
    {
        System.setProperty("http.agent", "Chrome");
        //try(InputStream in = new URL(showUrl).openStream()){
            String pathToSave = "/web/images/work/" + showId + ".jpg";
            //FileOutputStream fileToCopy = new FileOutputStream(pathToSave + showId);
            //Files.copy(in, Paths.get(pathToSave));
            URL url = new URL(showUrl);
            InputStream in1 = new BufferedInputStream(url.openStream());
            String imageName = showId + ".jpg";
            OutputStream out = new BufferedOutputStream(new FileOutputStream(imageName));

            for ( int i; (i = in1.read()) != -1; ) {
                out.write(i);
            }
            in1.close();
            out.close();
        }
    //}

    private void addShowToDB(HttpServletRequest request, HttpServletResponse response, ShowsManager showsManager) throws IOException{
        response.setContentType("application/json");

        Show show = null;
        Show showToAdd = null;
        UserShows userShowToUpdate = null;
        int validInput;

        List<Show> shows = showsManager.getAllShows(em);
        ShowNumber.showNumber = shows.get(shows.size()-1).getShowID() + 1;
        show = Show.createShow(request.getParameter(Constants.SHOW_NAME), request.getParameter(Constants.SHOW_LOCATION), request.getParameter(Constants.PICTURE_URL), Integer.parseInt(request.getParameter(Constants.NUMBER_OF_TICKETS)), Integer.parseInt(request.getParameter(Constants.SHOW_PRICE)), LocalDateTime.parse(request.getParameter(Constants.SHOW_DATE)), request.getParameter(Constants.SHOW_ABOUT)/*ticketsList*/);
        showToAdd = showsManager.showLocationAndDateExist(shows, show);
        User userFromSession = (User) request.getSession(false).getAttribute(Constants.LOGIN_USER);

        if(showToAdd == null) {
            validInput = Constants.SHOW_ADDED_SUCCESSFULLY;
            request.getSession(true).setAttribute(Constants.SHOW, show);
            DBTrans.persist(em, show);
            UserShowsNumber.userShowNumber = ServletUtils.getUserShowsManager(getServletContext()).getAllShows(em).size();
            userShowToUpdate = new UserShows(UserShowsNumber.userShowNumber++, userFromSession.getEmail(), show.getShowID(), Constants.SHOW_TO_SELL);
            DBTrans.persist(em, userShowToUpdate);
            em.close();
            saveImageToDB(show.getPictureUrl(), show.getShowName(), show.getShowID());
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
                int numOfShowUser = ServletUtils.getUserShowsManager(getServletContext()).countAll(em) + 1;
                userShowToUpdate = new UserShows(numOfShowUser, userFromSession.getEmail(), show.getShowID(), Constants.SHOW_TO_SELL);
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
        String showName = "";

        List<Show> shows = showsManager.getAllShows(em);
        showToRemove = showsManager.showIDExist(shows, showID);
        User userFromSession = (User) request.getSession(false).getAttribute(Constants.LOGIN_USER);

        if (showToRemove == null) {
            validInput = Constants.SHOW_NOT_EXIST;
        }
        else {
            validInput = Constants.SHOW_DELETE_SUCCESSFULLY;
            showName = showToRemove.getShowName();
            DBTrans.remove(em, showToRemove);
            int numOfShowUser = ServletUtils.getUserShowsManager(getServletContext()).countAll(em) + 1;
            UserShowsManager userShowsManager = ServletUtils.getUserShowsManager(getServletContext());
            UserShows userShowToUpdate = userShowsManager.getUserShow(userFromSession.getEmail(), showID);
            DBTrans.remove(em, userShowToUpdate);
        }


        Gson gson = new Gson();
        String res = gson.toJson(validInput);

        response.getWriter().write("[" + res + "," + showName + "]");
        response.getWriter().flush();

    }
}
