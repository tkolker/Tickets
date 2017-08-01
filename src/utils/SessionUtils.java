package utils;

import logic.Show;
import servlets.Constants;
import logic.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {

    public static User getParameter(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object sessionAttribute = session != null ? session.getAttribute(Constants.LOGIN_USER) : null;
        return sessionAttribute != null ? (User)sessionAttribute : null;
    }

    public static Show getParameterForShow(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        Object sessionAttribute = session != null ? session.getAttribute(Constants.SHOW) : null;
        return sessionAttribute != null ? (Show) sessionAttribute : null;
    }

    public static void clearSession (HttpServletRequest request) {
        request.getSession().invalidate();
    }
}
