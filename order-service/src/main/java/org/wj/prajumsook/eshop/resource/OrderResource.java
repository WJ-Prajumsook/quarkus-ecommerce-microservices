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
import org.wj.prajumsook.eshop.model.Order;
import org.wj.prajumsook.eshop.service.AddressService;
import org.wj.prajumsook.eshop.service.OrderItemService;
import org.wj.prajumsook.eshop.service.OrderService;
import org.wj.prajumsook.eshop.service.PaymentService;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

  @Inject
  OrderService orderService;
  @Inject
  AddressService addressService;
  @Inject
  OrderItemService orderItemService;
  @Inject
  PaymentService paymentService;

  @GET
  public Multi<Order> findAll() {
    return orderService.findAll();
  }

  @GET
  @Path("/{id}")
  public Uni<Order> findById(@RestPath Long id) {
    return orderService.findById(id);
  }

  @POST
  public Response create(Order order) {
    orderService.create(order);
    return Response.status(Response.Status.CREATED).build();
  }

  @DELETE
  @Path("/{id}")
  public Uni<Response> delete(@RestPath Long id) {
    return orderService.delete(id)
        .map(deleted -> deleted ? Response.status(Response.Status.NO_CONTENT).build()
            : Response.status(Response.Status.NOT_FOUND).build());
  }

}
