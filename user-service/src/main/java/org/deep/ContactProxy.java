package org.deep;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;



@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "extensions-api")
public interface ContactProxy {
    @GET
    @Path("/user/{userId}")
    List<Contact> getById(@PathParam("userId") Long userId);
}

