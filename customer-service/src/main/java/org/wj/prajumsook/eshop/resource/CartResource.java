package org.wj.prajumsook.eshop.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.reactive.RestPath;
import org.wj.prajumsook.eshop.model.Cart;
import org.wj.prajumsook.eshop.model.Customer;
import org.wj.prajumsook.eshop.service.CartService;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/carts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

  @Inject
  CartService cartService;

  @GET
  public Multi<Cart> findAll() {
    return cartService.findAll();
  }

  @GET
  @Path("/{id}")
  public Uni<Cart> findById(@RestPath Long id) {
    return cartService.findById(id);
  }

  @GET
  @Path("/customer/{id}")
  public Uni<Cart> findByCustomerId(@RestPath Long id) {
    return cartService.findByCustomerId(id);
  }

  @POST
  public Response create(Customer customer) {
    cartService.create(customer.getId());
    return Response.status(Response.Status.CREATED).build();
  }

  @DELETE
  @Path("/{id}")
  public Uni<Response> delete(@RestPath Long id) {
    return cartService.delete(id)
        .map(deleted -> deleted
            ? Response.status(Response.Status.NO_CONTENT).build()
            : Response.status(Response.Status.NOT_FOUND).build());
  }

}
