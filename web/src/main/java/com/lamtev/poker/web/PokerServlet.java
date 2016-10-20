package com.lamtev.poker.web;

import com.lamtev.poker.core.Poker;

import java.io.IOException;
import javax.servlet.http.*;

public class PokerServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Poker poker = new Poker(5, 2, 100);

        resp.setContentType("text/plain");
        resp.getWriter().println("Number of players " + poker.getNumberOfPlayers());
        resp.getWriter().println("Small blind " + poker.getSmallBlindSize());
    }
}