package org.base.client;

import jakarta.ws.rs.*;
import org.base.client.dto.ClientResDto;
import org.base.filter.RestClientFilter;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/")
@RegisterRestClient(configKey = "auth-api")
@RegisterProvider(RestClientFilter.class)
public interface AuthClient {

    @GET
    @Path("validate-token")
    @Produces("application/json")
    ClientResDto validateToken(@HeaderParam("Authorization") String token);

}

