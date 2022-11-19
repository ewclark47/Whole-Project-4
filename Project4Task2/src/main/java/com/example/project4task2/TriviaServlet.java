/*
Name: Elliott Clark and Eric Ryu
AndrewID: elliottc and ericryu
TriviaServlet.java
 */

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
    DatabaseHandling db = null;
    Gson gson = new Gson();

    // Initiate this servlet by instantiating the model that it will use.
    @Override
    public void init() {
        triviaModel = new TriviaModel();
        db = new DatabaseHandling();
    }

    // This servlet will reply to HTTP GET requests via this doGet method
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        long startTime, endTime, totalTime;
        startTime = System.currentTimeMillis();
        String connectType;
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
            connectType = "Mobile";
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            mobile = false;
            connectType = "Desktop";
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }
        /*
         * Check if the search parameters are present.
         * If not, then give the user instructions and prompt that not all parameters are there.
         * If there is are parameters, then do the search and return the result.
         */
        if (amount != null && category != null && difficulty != null && type != null) {
            String questions = gson.toJson(triviaModel.getQuestions(amount, category, difficulty, type));
            PrintWriter out = response.getWriter();
            response.setContentType( "application/json" );
            response.setCharacterEncoding( "UTF-8" );
            out.print(gson.toJson( questions ));
            out.flush();
            out.close();
            endTime = System.currentTimeMillis();
            totalTime = endTime-startTime;
            db.addDataToDatabase( category, Integer.parseInt( amount ), type, difficulty, totalTime , connectType );
            }else{
            System.out.println("Error with input parameters: not all present");
        }
    }
}


