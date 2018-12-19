package com.cbsexam;

import cache.ProductCache;
import cache.UserCache;
import com.google.gson.Gson;
import controllers.UserController;
import java.util.ArrayList;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.User;
import utils.Encryption;
import utils.Log;



@Path("user")
public class UserEndpoints {


  /**
   * @param idUser
   * @return Responses
   */
  @GET
  @Path("/{idUser}")
  public Response getUser(@PathParam("idUser") int idUser) {

    // Use the ID to get the user from the controller.
    User user = UserController.getUser(idUser);


    // TODO: Add Encryption to JSON : FIXED
    // Convert the user object to json in order to return the object
    String json = new Gson().toJson(user);
    json = Encryption.encryptDecryptXOR(json);

    // Return the user with the status code 200
    // TODO: What should happen if something breaks down? :FIXED
    if (user != null) {
      // returner en 200 hvis det er en sy
      return Response.status(200).type(MediaType.APPLICATION_JSON_TYPE).entity(json).build();
    } else {
      return Response.status(400).entity("Could not create user").build();
    }
  }

  // kalder metoden userCache
  private static UserCache userCache = new UserCache();

  /**
   * @return Responses
   */
  @GET
  @Path("/")
  public Response getUsers() {

    // Write to log that we are here
    Log.writeLog(this.getClass().getName(), this, "Get all users", 0);

    // Get a list of users

    // redigeret her
    ArrayList<User> users = userCache.getUsers(false);

    // TODO: Add Encryption to JSON : FIXED
    // Transfer users to json in order to return it to the user
    String json = new Gson().toJson(users);
    json = Encryption.encryptDecryptXOR(json);

    // Return the users with the status code 200
    return Response.status(200).type(MediaType.APPLICATION_JSON).entity(json).build();
  }

  @POST
  @Path("/create")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createUser(String body) {

    // Read the json from body and transfer it to a user class
    User newUser = new Gson().fromJson(body, User.class);

    // Use the controller to add the user
    User createUser = UserController.createUser(newUser);

    // Get the user back with the added ID and return it to the user
    String json = new Gson().toJson(createUser);

    // Return the data to the user
    if (createUser != null) {
      // Return a response with status 200 and JSON as type
      return Response.status(200).type(MediaType.APPLICATION_JSON_TYPE).entity(json).build();
    } else {
      return Response.status(400).entity("Could not create user").build();
    }
  }

  // TODO: Make the system able to login users and assign them a token to use throughout the system. : FIXED
  @POST
  @Path("/login")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response loginUser(String body) {


    // Read the json from body and transfer it to a user class
    User user = new Gson().fromJson(body, User.class);

    // Get the user back with the added ID and return it to the user
    String token = UserController.loginUser(user);

    /// Return the data to the user
    if (token != "") {
      // Return a response with status 200 and JSON as type
      return Response.status(200).type(MediaType.APPLICATION_JSON_TYPE).entity(token).build();
    } else {
      return Response.status(400).entity("Could not create user").build();
    }
  }

  // TODO: Make the system able to delete users (FIXED)

  @DELETE
  @Path("/delete")
  public Response deleteUser(String body) {

    User user = new Gson().fromJson(body, User.class);

    // Return the data to the user
    // Return a response with status 200 and JSON as type
    if (UserController.deleteUser(user.getToken()))

      return Response.status(200).entity("Bruger er slettet fra systemet").build();
    else {
      // Return a response with status 200 and JSON as type
      return Response.status(400).entity("Brugeren kan ikke findes i systemet").build();
    }

  }

  // TODO: Make the system able to update users : FIXED :-D
  @POST
  @Path("/updateUser")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateUser(String body) {

    User user = new Gson().fromJson(body, User.class);

    // Return the data to the user
    if (UserController.updateUser(user, user.getToken())) {

      //Opdaterer Cache efter at user i databasen er blevet opdateres
      userCache.getUsers(true);

      // Return a response with status 200 and JSON as type
      return Response.status(200).entity("Bruger er updateret i systemet").build();
    } else {
      // Return a response with status 200 and JSON as type
      return Response.status(400).entity("Brugeren kan ikke findes i systemet").build();
    }
  }
}




