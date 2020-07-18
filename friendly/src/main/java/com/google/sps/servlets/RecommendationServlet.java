package com.google.sps.servlets;

import com.google.appengine.api.datastore.*;
import com.google.gson.Gson;

import com.google.sps.model.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/recommendation")
public class RecommendationServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String groupId = request.getParameter("groupId");
        String restaurantName = request.getParameter("restaurantName");
        String location = request.getParameter("location");
        String userEmail = request.getParameter("userEmail");

        ArrayList<String> recommendation = new ArrayList<String>();
        recommendation.add(restaurantName);
        recommendation.add(location);

        //Add new restaurant to datastore
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Entity groupEntity = new Entity(userEmail + ":" + groupId);

        groupEntity.setProperty("recommendation", recommendation); 
        datastore.put(groupEntity);

        // Redirect back to the HTML page.
        response.sendRedirect("/index.html");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        String groupId = request.getParameter("groupId");
        String userEmail = request.getParameter("userEmail");
        //Retrieve data from Datastore
        Query query = new Query(userEmail + ":" + groupId);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);

        ArrayList<ArrayList<String>> recommendations = new ArrayList<>();

        for (Entity entity : results.asIterable()) {
            ArrayList recommendation = (ArrayList) entity.getProperty("recommendation");
            recommendations.add(recommendation);
        }

        //Send Response
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(convertToJsonUsingGson(recommendations));
    }

    private String convertToJsonUsingGson(ArrayList<ArrayList<String>> recommendationsList) {
        Gson gson = new Gson();
        String json = gson.toJson(recommendationsList);
        return json;
    }
}