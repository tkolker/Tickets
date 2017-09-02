package servlets;

import appManager.MessageManager;
import appManager.ShowsManager;
import appManager.UserShowsManager;
import appManager.UsersManager;
import appManager.db_manager.DBTrans;
import com.google.gson.Gson;
import logic.Message;
import logic.Show;
import logic.User;
import utils.ServletUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "InboxServlet", urlPatterns = {"/inbox"})
public class InboxServlet extends HttpServlet{

    EntityManagerFactory emf;
    EntityManager em;


    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        em = emf.createEntityManager();
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String actionType = request.getParameter(Constants.ACTION_TYPE);
        MessageManager msgManager = ServletUtils.getMessageManager(request.getServletContext());

        switch(actionType) {
            case Constants.SEND_MSG:
                sendMsg(request, response, msgManager);
                break;
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        em = emf.createEntityManager();
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String actionType = request.getParameter(Constants.ACTION_TYPE);
        MessageManager msgManager = ServletUtils.getMessageManager(request.getServletContext());

        switch(actionType) {
            case Constants.GET_INBOX:
                getInbox(request, response, msgManager);
                break;
        }
    }

    private void getInbox(HttpServletRequest request, HttpServletResponse response, MessageManager msgManager) throws IOException {

        response.setContentType("application/json");
        User user = (User) request.getSession(false).getAttribute(Constants.LOGIN_USER);

        List<Message> userMsgs = msgManager.getMessagesByReceiver(em, user.getEmail());

        Gson gson = new Gson();
        String msgs = gson.toJson(userMsgs);
        String size = gson.toJson(userMsgs.size());
        response.getWriter().write("[" + size + "," + msgs + "]");
        response.getWriter().flush();
    }

    private void sendMsg(HttpServletRequest request, HttpServletResponse response, MessageManager msgManager) {
        response.setContentType("text/html;charset=UTF-8");
        User sender = (User) request.getSession(false).getAttribute(Constants.LOGIN_USER);
        UsersManager usersManager = ServletUtils.getUsersManager(request.getServletContext());
        UserShowsManager userShowsManager = ServletUtils.getUserShowsManager(request.getServletContext());
        ShowsManager showsManager = ServletUtils.getShowsManager(request.getServletContext());

        String msg = request.getParameter(Constants.MSG);
        int showId = Integer.parseInt(request.getParameter(Constants.SHOW_ID));
        Show show =  showsManager.getShowByID(em, showId);

        String receiverId = userShowsManager.getUserByShowId(em, showId);
        User receiver = usersManager.getUserByEmail(receiverId, em).get(0);

        String senderName = sender.getFirstName() + " " + sender.getLastName();
        String receiverName = receiver.getFirstName() + " " + receiver.getLastName();

        int msgId;
        List<Message> msgs = msgManager.getAllMessages(em);
        if (msgs.size() == 0){
            msgId = 1;
        }
        else {
            msgId = msgs.get(msgs.size() - 1).getM_MsgId() + 1;
        }

        Message newMsg = new Message(msgId, msg, sender.getEmail(), senderName, receiver.getEmail(), receiverName, showId, show.getShowName());
        DBTrans.persist(em, newMsg);
        em.close();
    }
}
