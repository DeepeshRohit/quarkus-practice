package org.deep;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @RestClient
    ContactProxy contactProxy;



    @POST
    public List<User> add(User user) {
        userService.add(user);
        return userService.list();
    }
    @GET
    @Path("/contact/{userId}")
    public List<Contact> getDetail(@PathParam("userId") Long userId) {
        return contactProxy.getById(userId);
   }
    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("userId") Long userId) {
        List<Contact> listCon= contactProxy.getById(userId);
        List<User> listuser = userService.list();
        User users= listuser.stream().filter(user -> user.getUserId().equals(userId)).findAny().orElse(null);
        users.setContacts(listCon);
        return users;
    }
}