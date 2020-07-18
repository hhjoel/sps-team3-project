// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import com.google.sps.servlets.JsonUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/group")
public class GroupServlet extends HttpServlet {
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    UserService userService = UserServiceFactory.getUserService();

    // user not logged in, do not have groups
    if (!userService.isUserLoggedIn()){
        response.sendError(403, "Not authorized to comment.");
        return;
    }
    String ownerEmail = userService.getCurrentUser().getEmail();
    
    Query query = new Query("Group")
        .setFilter(new FilterPredicate("teamMembers", FilterOperator.EQUAL, ownerEmail));
    
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    ArrayList<String> groupList = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
        groupList.add((String) entity.getProperty("groupName"));
    }

    response.setContentType("application/json;");
    response.getWriter().println(JsonUtility.convertToJsonUsingGson(groupList));  
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    UserService userService = UserServiceFactory.getUserService();
    String groupName = getParameter(request, "groupName");
    Entity groupEntity = new Entity("Group");
    String ownerId = userService.getCurrentUser().getUserId();
    String ownerEmail = userService.getCurrentUser().getEmail();
    String teamMembers = getParameter(request, "teamMembers");
    ArrayList<String> members = new ArrayList<String>(Arrays.asList(teamMembers.split(";")));
    response.setContentType("text/plain;charset=UTF-8");

    for (String userEmail: members) {
        if (!validateGroupUser(userEmail)) {
            response.getWriter().println("Invalid"); 
            return; 
        }
    }
    response.getWriter().println("valid"); 
    members.add(ownerEmail);

    if (!groupName.isEmpty()) {
        long timestamp = System.currentTimeMillis();
        groupEntity.setProperty("groupName", groupName);
        groupEntity.setProperty("timestamp", timestamp);
        groupEntity.setProperty("ownerId", ownerId);
        groupEntity.setProperty("ownerEmail", ownerEmail);
        groupEntity.setProperty("teamMembers", members);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(groupEntity);
    }
  }

/**
  * Get parameter from user input.
  */
  private String getParameter(HttpServletRequest request, String name) {      
      String parameter = request.getParameter(name);
      return parameter;
  }

/**
  * Check whether a userEmail is valid (the user is in the datastore)
  */
  private boolean validateGroupUser(String userEmail) {

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query = new Query("User")
        .setFilter(new FilterPredicate("userEmail", FilterOperator.EQUAL, userEmail));
    PreparedQuery results = datastore.prepare(query);
    return results.countEntities() != 0;
  }

}
