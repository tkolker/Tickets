package servlets;

import appManager.UsersManager;
import appManager.db_manager.DBTrans;
import com.google.gson.Gson;
import logic.User;
import utils.ServletUtils;
import utils.SessionUtils;

import javax.mail.internet.InternetAddress;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet{

    EntityManagerFactory emf;
    EntityManager em;

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        em = emf.createEntityManager();
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String actionType = request.getParameter(Constants.ACTION_TYPE);

        switch(actionType) {
            case Constants.LOGIN:
                User user = new User(request.getParameter(Constants.EMAIL), request.getParameter(Constants.USER_PASSWORD));
                UsersManager usersManager = ServletUtils.getUsersManager(getServletContext());
                performLogin(request, response, user, usersManager);
                break;
            case Constants.GET_USER:
                getUserFromSessionData(request, response);
                break;
        }
    }

    private void getUserFromSessionData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User)request.getSession(false).getAttribute(Constants.LOGIN_USER);

        response.getWriter().write(user.getFirstName());
        response.getWriter().flush();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException  {

        emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        em = emf.createEntityManager();
        String actionType = request.getParameter(Constants.ACTION_TYPE);

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        try {
            String email = request.getParameter(Constants.EMAIL);
            javax.mail.internet.InternetAddress ia = new InternetAddress(email);
            ia.validate();
            User user = new User(request.getParameter(Constants.EMAIL), request.getParameter(Constants.USER_FIRST_NAME),  request.getParameter(Constants.USER_LAST_NAME),  request.getParameter(Constants.USER_PASSWORD));
            UsersManager usersManager = ServletUtils.getUsersManager(getServletContext());
            switch (actionType) {
                case Constants.SIGNUP:
                    performSignUp(request, response, user, usersManager);
                    break;
                case Constants.LOGOUT:
                    performLogout(request, response, user, usersManager);
            }
        }
        catch (javax.mail.internet.AddressException ae)
        {

        }

    }

    private void performLogout(HttpServletRequest request, HttpServletResponse response, User user, UsersManager usersManager) throws IOException {
        response.setContentType("application/json");
        User LoggedInUser = (User)request.getSession(false).getAttribute(Constants.LOGIN_USER);



    }

    private void performSignUp(HttpServletRequest request, HttpServletResponse response, User user, UsersManager manager) throws IOException{
        response.setContentType("application/json");

        User signedUser = null;
        User userFromSession = SessionUtils.getParameter(request);
        int validInput = Constants.SIGNUP_SUCCESS;

        if(userFromSession == null) {
            List<User> users = manager.getAllSignedUsers(em);
            signedUser = manager.usernameExist(users, user);

            if(signedUser != null) {
                validInput = Constants.USER_EXIST;
            }
            else if(!user.emailValidation(user.getEmail()))
            {
                validInput = Constants.INVALID_EMAIL;
            }
            else {
                validInput = Constants.SIGNUP_SUCCESS;
                DBTrans.persist(em, user);
                em.close();
                request.getSession(true).setAttribute(Constants.LOGIN_USER, user);
            }

        }
        else {
            //TODO: gets here if session is null (when user already logged in from this IP)
        }

        Gson gson = new Gson();
        String res = gson.toJson(validInput);
        String signedUserStr = gson.toJson(user);
        response.getWriter().write("["+res+","+signedUserStr+"]");
        response.getWriter().flush();
    }

    private void performLogin(HttpServletRequest request, HttpServletResponse response, User user, UsersManager manager) throws IOException{
        response.setContentType("application/json");

        User signedUser = null;
        int isNameExist = Constants.LOGIN_SUCCESS;
        User userFromSession = SessionUtils.getParameter(request);
        if(userFromSession == null) {
            List<User> users = manager.getAllSignedUsers(em);
            signedUser = manager.usernameExist(users, user);
            if(signedUser == null) {
                isNameExist = Constants.USER_NOT_EXIST;
            }
            else {
                int ind = users.indexOf(signedUser);
                if(!users.get(ind).getPassword().equals(user.getPassword())) {
                    isNameExist = Constants.WRONG_PASSWORD;
                }
                else if(!user.emailValidation(user.getEmail()))
                {
                    isNameExist = Constants.INVALID_EMAIL;
                }
                else {
                    isNameExist = Constants.LOGIN_SUCCESS;
                    manager.getUsers().add(signedUser);
                    request.getSession(true).setAttribute(Constants.LOGIN_USER, signedUser);
                }
            }

        }
        else {
            //TODO: gets here if session is null (when does it happen?)
        }

        Gson gson = new Gson();
        String res = gson.toJson(isNameExist);
        String userStr = gson.toJson(signedUser);
        String both = "["+res+","+userStr+"]";
        response.getWriter().write(both);
        response.getWriter().flush();
    }
}
