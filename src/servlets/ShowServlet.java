
package servlets;

import appManager.*;
import appManager.db_manager.DBTrans;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.Gson;
import logic.*;
import utils.ServletUtils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@MultipartConfig
@WebServlet(name = "ShowServlet", urlPatterns = {"/SellTicket"})
public class ShowServlet extends HttpServlet {

    EntityManagerFactory emf;
    EntityManager em;
    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "tickets",
            "api_key", "363777688854323",
            "api_secret", "Ug-k08JZjiPTcwcXEShfkLO1Eeo"));

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
                //TODO: debug
                getBoughtTickets(request, response, showsManager, em);
                break;
        }
    }

    private void createShowArrayResult(List<ShowInterface> shows, List<UserShowsInterface> userShowInList, ArrayList<ShowInterface> res)
    {
        int i = 0;
        for (ShowInterface s : shows) {
            if (s.getShowID() == userShowInList.get(i).getShowId()) {
                res.add(s);
                if (i < userShowInList.size() - 1)
                    i++;
                else
                    break;
            }
        }
    }

    private void createShowArchiveArrayResult(List<ShowInterface> shows, List<UserShowsInterface> userShowInList, ArrayList<ShowInterface> res)
    {
        for (UserShowsInterface u : userShowInList) {
            res.addAll(shows.stream().filter(s -> s.getShowID() == u.getShowId()).collect(Collectors.toList()));
        }
    }
    //TODO : need to change to showArchive !!!! sivan's work :)
    private void getBoughtTickets(HttpServletRequest request, HttpServletResponse response, ShowsManager showsManager, EntityManager em) throws IOException {
        response.setContentType("application/json");
        ArrayList<ShowInterface> res = new ArrayList<>();
        String userId = ((User) request.getSession(false).getAttribute(Constants.LOGIN_USER)).getEmail();
        UserShowsManagerInterface userShowBoughtManager = ServletUtils.getUserShowBoughtManager(getServletContext());
        ShowsArchiveManager showsArchiveManager = ServletUtils.getShowsArchiveManager(getServletContext());
        List<ShowInterface> shows = showsArchiveManager.getAllShows(em);
        List<UserShowsInterface> userShowBought = userShowBoughtManager.getShowByUserID(em, userId);

        createShowArchiveArrayResult(shows, userShowBought, res);

        Gson gson = new Gson();
        if(!res.isEmpty()) {
            String showsStr = gson.toJson(res);
            String showNum = gson.toJson(res.size());
            response.getWriter().write("[" + showNum + "," + showsStr + "]");
        }
        else{
            String showResultString = gson.toJson(Constants.SHOW_NOT_EXIST);
            response.getWriter().write("[" + showResultString + "]");
        }
        response.getWriter().flush();
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
        ArrayList<ShowInterface> res = new ArrayList<>();
        String userId = ((User) request.getSession(false).getAttribute(Constants.LOGIN_USER)).getEmail();
        UserShowsManagerInterface userShowsManager = ServletUtils.getUserShowsManager(getServletContext());
        List<ShowInterface> shows = showsManager.getAllShows(em);
        List<UserShowsInterface> userShows = userShowsManager.getShowByUserID(em, userId);

        createShowArrayResult(shows, userShows, res);

        Gson gson = new Gson();
        String showsStr = gson.toJson(res);
        String showNum = gson.toJson(res.size());
        response.getWriter().write("[" + showNum + "," + showsStr + "]");
        response.getWriter().flush();
    }

    private void getAllShows(HttpServletResponse response, ShowsManager showsManager, EntityManager em) throws IOException {
        response.setContentType("application/json");
        List<ShowInterface> allShows = showsManager.getAllShows(em);
        ArrayList<ShowInterface> filteredShows = new ArrayList<>();

        for(ShowInterface s:allShows){
            if(s.getShowDate().after(new Date())){
                filteredShows.add(s);
            }
        }

        Gson gson = new Gson();
        String showsStr = gson.toJson(filteredShows);
        String showNum = gson.toJson(filteredShows.size());
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
        //response.setContentType("text/html;charset=UTF-8");
        String actionType = request.getParameter(Constants.ACTION_TYPE);
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
                try {
                    buyTicket(request, response, showsManager);
                } catch (MessagingException e) {
                    //e.printStackTrace();
                }
                break;
            case Constants.CRAWLER_UPDATE:
                try {
                    writeCrawlResults(request, response, showsManager);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    private void writeCrawlResults(HttpServletRequest request, HttpServletResponse response, ShowsManager showsManager) throws Exception {
        ArrayList<Show> shows = new ArrayList<>();
        String sStr = request.getParameter(Constants.CRAWLER_SHOWS);

        String[] parsedShows = ServletUtils.parseRequestParams(sStr);

        for(int i = 0; i< parsedShows.length - 1; i++){
            shows.add(Show.parseShow(em, parsedShows[i], showsManager));
        }

        for(Show s: shows){
            if(showsManager.showLocationAndDateExist(showsManager.getAllShows(em),s) == null){
                String picture = ServletUtils.uploadImageToCloud(s.getPictureUrl());
                s.setPictureUrl(picture);
                DBTrans.persist(em, s);
            }
        }
        em.close();
    }


    private void buyTicket(HttpServletRequest request, HttpServletResponse response, /*Show show*/ ShowsManager showsManager) throws IOException, MessagingException {
        response.setContentType("application/json");

        User userFromSession = (User) request.getSession(false).getAttribute(Constants.LOGIN_USER);
        //TODO: need to update UserShow DB, Show DB,
        int numOfTicketsToBuy = Integer.parseInt(request.getParameter(Constants.NUMBERS_OF_TICKETS_TO_BUY));
        int showID = Integer.parseInt(request.getParameter(Constants.SHOW_ID));
        Show showToBuy = showsManager.getShowByID(em, showID);
        UserShowsManager userShowsManager = ServletUtils.getUserShowsManager(getServletContext());
        UsersManager usersManager = ServletUtils.getUsersManager(getServletContext());

        if(showToBuy != null)
        {
            // Add the new show to Show Archive DB
            ShowArchive showToAddToUserShowBought = new ShowArchive(showToBuy.getShowID(), showToBuy.getShowName(), showToBuy.getLocation(), showToBuy.getPictureUrl(), numOfTicketsToBuy, showToBuy.getShowPrice(), showToBuy.getShowDate(), showToBuy.getAbout());
            DBTrans.persist(em, showToAddToUserShowBought);
            // Add to user show bought DB
            UserShowBoughtManager userShowBoughtManager = ServletUtils.getUserShowBoughtManager(getServletContext());
            UserShowBoughtNumber.userShowNumber = userShowBoughtManager.getAllShows(em).size();
            UserShowBought userShowBought = new UserShowBought(UserShowsNumber.userShowNumber++, userFromSession.getEmail(), showToAddToUserShowBought.getShowID());
            DBTrans.persist(em, userShowBought);
            String sellerMail =  userShowsManager.getUserByShowId(em, showID);
            String sellerName = usersManager.getUserNameByEmail(em, sellerMail);
            //emailToSeller(sellerMail,sellerName, showToAddToUserShowBought);
            if (showToBuy.getNumOfTickets() > numOfTicketsToBuy)
            {
                // Update show DB
                DBTrans.updateShow(em, showID, showToBuy.getNumOfTickets() - numOfTicketsToBuy);
                // Update user show DB
            }
            else if (showToBuy.getNumOfTickets() == numOfTicketsToBuy)
            {
                DBTrans.remove(em, showToBuy);
            }
        }

    }

    private void emailToSeller(String sellerMail, String sellerName, ShowArchive show) throws MessagingException {
        Properties properties=new Properties();
        Session session=Session.getDefaultInstance(properties,null);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("sivan.izhar93@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(sellerMail));
            message.setHeader("מכרת כרטיס במכרטסים!", "מכרת כרטיס במכרטסים!");
            int totalAmount = show.getShowPrice() * show.getShowTickets();
            StringBuilder text = new StringBuilder("היי ").append(sellerName).
                    append("\n").append("נמכרו ").append(show.getShowTickets()).append("כרטיסים להופעה ").append(show.getShowName()).append("שמכרת.")
                    .append("\n").append("סך הכל הועבר לחשבונך").append(totalAmount).append("שקלים חדשים.").append("\n\n")
                    .append("צוות מכרטסים");
            message.setText(text.toString());
            Transport.send(message);
        }
        catch (MessagingException mex) {mex.printStackTrace();}
    }

    //TODO: function needs to be checked after shows aren't fictive
    private void updateShow(HttpServletRequest request, HttpServletResponse response, int showID, ShowsManager showsManager) throws IOException {
        response.setContentType("application/json");

        String picture = "";
        Show showToDelete;
        if(Integer.parseInt(request.getParameter(Constants.PIC_TYPE)) != Constants.EMPTY_IMG)
            picture = ServletUtils.uploadImageToCloud(request, Integer.parseInt(request.getParameter(Constants.PIC_TYPE)));

        Show showToUpdate = Show.createShowToUpdate(request.getParameter(Constants.SHOW_ID),request.getParameter(Constants.SHOW_NAME), request.getParameter(Constants.SHOW_LOCATION), picture, request.getParameter(Constants.NUMBER_OF_TICKETS), request.getParameter(Constants.SHOW_PRICE), request.getParameter(Constants.SHOW_DATE), request.getParameter(Constants.SHOW_ABOUT));

        int validInput = Constants.SHOW_UPDATE_SUCCESSFULLY;

        List<ShowInterface> shows = showsManager.getAllShows(em);
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


    private void addShowToDB(HttpServletRequest request, HttpServletResponse response, ShowsManager showsManager) throws IOException, ServletException {
        response.setContentType("application/json");

        Show show = null;
        Show showToAdd = null;
        UserShows userShowToUpdate = null;
        int validInput;

        List<ShowInterface> shows = showsManager.getAllShows(em);
        if (shows.size() > 0) {
            ShowNumber.showNumber = shows.get(shows.size() - 1).getShowID() + 1;
        }
        else
        {
            ShowNumber.showNumber = 0;
        }
        show = Show.createShow(request.getParameter(Constants.SHOW_NAME), request.getParameter(Constants.SHOW_LOCATION), request.getParameter(Constants.PICTURE_URL), Integer.parseInt(request.getParameter(Constants.NUMBER_OF_TICKETS)), Integer.parseInt(request.getParameter(Constants.SHOW_PRICE)), LocalDateTime.parse(request.getParameter(Constants.SHOW_DATE)), request.getParameter(Constants.SHOW_ABOUT)/*ticketsList*/);
        showToAdd = showsManager.showLocationAndDateExist(shows, show);
        User userFromSession = (User) request.getSession(false).getAttribute(Constants.LOGIN_USER);
        List<UserShows> userShows= ServletUtils.getUserShowsManager(getServletContext()).getAllShows(em);
        int userShowNum = userShows.get(userShows.size() - 1).getShowId() + 1;

        if(showToAdd == null) {
            validInput = Constants.SHOW_ADDED_SUCCESSFULLY;
            request.getSession(true).setAttribute(Constants.SHOW, show);
            String picture = ServletUtils.uploadImageToCloud(request, Integer.parseInt(request.getParameter(Constants.PIC_TYPE)));
            show.setPictureUrl(picture);
            DBTrans.persist(em, show);
            userShowToUpdate = new UserShows(userShowNum, userFromSession.getEmail(), show.getShowID());
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
                String picture = ServletUtils.uploadImageToCloud(request, Integer.parseInt(request.getParameter(Constants.PIC_TYPE)));
                show.setPictureUrl(picture);
                DBTrans.persist(em, show);
                em.close();
                userShowToUpdate = new UserShows(userShowNum, userFromSession.getEmail(), show.getShowID());
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

        List<ShowInterface> shows = showsManager.getAllShows(em);
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
