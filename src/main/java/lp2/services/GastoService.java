package lp2.services;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import lp2.models.Gasto;
import lp2.repositories.GastoRepository;

@ApplicationScoped
public class GastoService {
  private final GastoRepository repository;

  public GastoService(GastoRepository repository) {
    this.repository = repository;
  }

  public List<Gasto> getAll(){
    return repository.getAll();
  }

  public Gasto getById(Integer id){
    Gasto data = repository.getById(id);
    return data;
  }
}
