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
import org.wj.prajumsook.eshop.model.Product;
import org.wj.prajumsook.eshop.service.ProductService;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

  @Inject
  ProductService productService;

  @GET
  public Multi<Product> findAll() {
    return productService.findAll();
  }

  @GET
  @Path("/{id}")
  public Uni<Product> findById(@RestPath Long id) {
    return productService.findById(id);
  }

  @POST
  public Response create(Product product) {
    productService.create(product);
    return Response.status(Response.Status.CREATED).build();
  }

  @DELETE
  @Path("/{id}")
  public Uni<Response> delete(@RestPath Long id) {
    return productService.delete(id)
        .map(deleted -> deleted ? Response.status(Response.Status.NO_CONTENT).build()
            : Response.status(Response.Status.NOT_FOUND).build());
  }

}
