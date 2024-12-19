package lp2.resources;

import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lp2.models.Gasto;
import lp2.services.GastoService;

@Path("/gastos")
public class GastoResource {
  private final GastoService service;

  public GastoResource(GastoService service) {
    this.service = service;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Gasto> getAll() {
    return this.service.getAll();
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Gasto getById(@PathParam("id") Integer id) {
    return this.service.getById(id);
  }
}
