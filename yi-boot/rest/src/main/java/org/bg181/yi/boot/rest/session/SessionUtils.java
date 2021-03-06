package org.bg181.yi.boot.rest.session;

import org.bg181.yi.boot.common.context.AppContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sam Lu
 * @date 2022/07/07
 */
public class SessionUtils {

    private static class Holder {

        private static SessionUtils instance = new SessionUtils();

    }

    private SessionAdapter sessionAdapter;

    private SessionUtils() {
        sessionAdapter = AppContext.getBean(SessionAdapter.class);
    }

    public static Session getSession(HttpServletRequest request) {
        return Holder.instance.sessionAdapter.getSession(request);
    }

    public static Session getSession(HttpServletRequest request, boolean create) {
        return Holder.instance.sessionAdapter.getSession(request, create);
    }

    public static void setSession(HttpServletResponse response, Session session) {
        Holder.instance.sessionAdapter.setSession(response, session);
    }

    public static void storeSession(Session session) {
        Holder.instance.sessionAdapter.storeSession(session);
    }

    public static void clear() {
        Holder.instance.sessionAdapter.clear();
    }

}
