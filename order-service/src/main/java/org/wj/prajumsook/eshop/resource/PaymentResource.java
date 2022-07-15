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
import org.wj.prajumsook.eshop.model.Payment;
import org.wj.prajumsook.eshop.service.PaymentService;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentResource {

  @Inject
  PaymentService paymentService;

  @GET
  public Multi<Payment> findAll() {
    return paymentService.findAll();
  }

  @GET
  @Path("/{id}")
  public Uni<Payment> findById(@RestPath Long id) {
    return paymentService.findById(id);
  }

  @POST
  public Uni<Payment> create(Payment payment) {
    return paymentService.create(payment);
  }

  @DELETE
  @Path("/{id}")
  public Uni<Response> delete(@RestPath Long id) {
    return paymentService.delete(id)
        .map(deleted -> deleted ? Response.status(Response.Status.NO_CONTENT).build()
            : Response.status(Response.Status.NOT_FOUND).build());
  }

}
