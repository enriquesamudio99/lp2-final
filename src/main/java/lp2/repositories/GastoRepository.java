package lp2.repositories;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.enterprise.context.ApplicationScoped;
import lp2.models.Gasto;

@ApplicationScoped
public class GastoRepository {
  private static final String FILE_PATH = "src/main/resources/data.json";
  private ObjectMapper mapper;
  private List<Gasto> list;

  public GastoRepository() {
    mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    list = loadData();
  }

  public List<Gasto> loadData() {
    try {
      File data = new File(FILE_PATH);
      if (data.exists()) {
        return mapper.readValue(data, new TypeReference<List<Gasto>>() {
        });
      } else {
        return new ArrayList<>();
      }

    } catch (IOException e) {
      return new ArrayList<>();
    }
  }

  public List<Gasto> getAll() {
    return new ArrayList<>(list);
  }

  public Gasto getById(Integer id) {
    return list.stream()
        .filter(item -> item.getId().equals(id))
        .findFirst()
        .orElse(null);
  }
}
