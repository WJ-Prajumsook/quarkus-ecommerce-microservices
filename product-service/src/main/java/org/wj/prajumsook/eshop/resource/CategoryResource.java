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
import org.wj.prajumsook.eshop.model.Category;
import org.wj.prajumsook.eshop.service.CategoryService;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {

  @Inject
  CategoryService categoryService;

  @GET
  public Multi<Category> findAll() {
    return categoryService.findAll();
  }

  @GET
  @Path("/{id}")
  public Uni<Category> findById(@RestPath Long id) {
    return categoryService.findById(id);
  }

  @POST
  public Uni<Category> create(Category category) {
    return categoryService.crate(category);
  }

  @DELETE
  @Path("/{id}")
  public Uni<Response> delete(@RestPath Long id) {
    return categoryService.delete(id)
        .map(deleted -> deleted ? Response.status(Response.Status.NO_CONTENT).build()
            : Response.status(Response.Status.NOT_FOUND).build());
  }

}
