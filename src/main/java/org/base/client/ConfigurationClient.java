package org.base.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.base.client.dto.ClientResDto;
import org.base.filter.RestClientFilter;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/")
@RegisterRestClient(configKey = "configuration-api")
@RegisterProvider(RestClientFilter.class)
public interface ConfigurationClient {

    @GET
    @Path("{key}")
    @Produces(MediaType.APPLICATION_JSON)
    ClientResDto getConfigurationByKey(@PathParam("key") String key);

}
