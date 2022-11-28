package org.deep;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/contact")
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {
    @Inject
    ContactService contactService;

    @GET
    public List<Contact> list() {
        return contactService.list();
    }

    @POST
    public List<Contact> add(Contact contact) {
        contactService.add(contact);
        return list();
    }
    @GET
    @Path("/user/{userId}")
    public List<Contact> getContacts(@PathParam("userId") Long userId){
        List<Contact> listCon= contactService.list();
        return listCon.stream().filter(contact -> contact.getUserId().equals(userId)).collect(Collectors.toList());
    }
}
