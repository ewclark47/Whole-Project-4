package com.example.project4task2;

import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "TriviaServlet",
        urlPatterns = {"/getQuestions"})
public class TriviaServlet extends HttpServlet {

    TriviaModel triviaModel = null;  // The "business model" for this app
    Gson gson = new Gson();

    // Initiate this servlet by instantiating the model that it will use.
    @Override
    public void init() {
        triviaModel = new TriviaModel();
    }

    // This servlet will reply to HTTP GET requests via this doGet method
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String amount = request.getParameter("amount");
        String category = request.getParameter("category");
        String difficulty = request.getParameter("difficulty");
        String type = request.getParameter("type");

        // determine what type of device our user is
        String ua = request.getHeader("User-Agent");

        boolean mobile;
        // prepare the appropriate DOCTYPE for the view pages
        if (ua != null && ((ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1))) {
            mobile = true;
            /*
             * This is the latest XHTML Mobile doctype. To see the difference it
             * makes, comment it out so that a default desktop doctype is used
             * and view on an Android or iPhone.
             */
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            mobile = false;
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }

        String nextView;
        /*
         * Check if the search parameter is present.
         * If not, then give the user instructions and prompt for a search string.
         * If there is a search parameter, then do the search and return the result.
         */
        if (amount != null && category != null && difficulty != null && type != null) {
            String questions = triviaModel.getQuestions(amount, category, difficulty, type);
            request.setAttribute("questions", questions);
            nextView = "index.jsp";
        } else {
            nextView = "prompt.jsp";
        }

        RequestDispatcher view = request.getRequestDispatcher(nextView);
        view.forward(request, response);
    }
}


