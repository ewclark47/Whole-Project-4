/*
Name: Elliott Clark and Eric Ryu
AndrewID: elliottc and ericryu
CategoryServlet.java
 */

package com.example.project4task2;


import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "CategoryServlet",
        urlPatterns = {"/getCategory"})
public class CategoryServlet extends HttpServlet {

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

        String ua = request.getHeader("User-Agent");

        boolean mobile;
        if (ua != null && ((ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1))) {
            mobile = true;
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            mobile = false;
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }

        // Create a JSON object of categories
        // Learned from https://www.baeldung.com/servlet-json-response
        String categories = gson.toJson(triviaModel.getAPICategories());
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(gson.toJson(categories));
        out.flush();
        out.close();
    }
}


