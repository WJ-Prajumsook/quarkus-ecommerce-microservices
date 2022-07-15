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
import org.wj.prajumsook.eshop.model.OrderItem;
import org.wj.prajumsook.eshop.service.OrderItemService;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/order_items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderItemResource {

  @Inject
  OrderItemService orderItemService;

  @GET
  public Multi<OrderItem> findAll() {
    return orderItemService.findAll();
  }

  @GET
  @Path("/{id}")
  public Uni<OrderItem> findById(@RestPath Long id) {
    return orderItemService.findById(id);
  }

  @POST
  public Uni<OrderItem> create(OrderItem orderItem) {
    return orderItemService.create(orderItem);
  }

  @DELETE
  @Path("/{id}")
  public Uni<Response> delete(@RestPath Long id) {
    return orderItemService.delete(id)
        .map(deleted -> deleted ? Response.status(Response.Status.NO_CONTENT).build()
            : Response.status(Response.Status.NOT_FOUND).build());
  }
}
