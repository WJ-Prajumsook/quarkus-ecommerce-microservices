package org.wj.prajumsook.eshop.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.reactive.RestPath;
import org.wj.prajumsook.eshop.model.Customer;
import org.wj.prajumsook.eshop.service.CustomerService;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

  @Inject
  CustomerService customerService;

  @GET
  public Multi<Customer> findAll() {
    return customerService.findAll();
  }

  @GET
  @Path("/{id}")
  public Uni<Customer> findById(@RestPath Long id) {
    return customerService.findById(id);
  }

  @POST
  public Uni<Customer> create(Customer customer) {
    return customerService.create(customer);
  }

  @PUT
  public Uni<Customer> update(Customer customer) {
    return customerService.update(customer);
  }

  @DELETE
  @Path("/{id}")
  public Uni<Response> delete(@RestPath Long id) {
    try {
      return customerService.delete(id)
          .map(deleted -> deleted
              ? Response.status(Response.Status.NO_CONTENT).build()
              : Response.status(Response.Status.NOT_FOUND).build());
    } catch (Exception ex) {

      ex.printStackTrace();
      return Uni.createFrom().item(Response.status(Response.Status.BAD_REQUEST).build());
    }
  }
}
