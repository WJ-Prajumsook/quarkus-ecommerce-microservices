package org.wj.prajumsook.eshop.resource;

import javax.inject.Inject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.reactive.RestPath;
import org.wj.prajumsook.eshop.model.Address;
import org.wj.prajumsook.eshop.service.AddressService;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/addresses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddressResource {

  @Inject
  AddressService addressService;

  @GET
  public Multi<Address> findAll() {
    return addressService.findAll();
  }

  @GET
  @Path("/{id}")
  public Uni<Address> findById(@RestPath Long id) {
    return addressService.findById(id);
  }

  @POST
  public Uni<Address> create(Address address) {
    return addressService.create(address);
  }

  @DELETE
  @Path("/{id}")
  public Uni<Response> delete(@RestPath Long id) {
    return addressService.delete(id)
        .map(deleted -> deleted ? Response.status(Response.Status.NO_CONTENT).build()
            : Response.status(Response.Status.NOT_FOUND).build());
  }
}
