package lp2.repositories;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

  public void save(Gasto param) {
    try {
      List<Gasto> newList = this.list;
      Gasto itemExists = getById(param.getId());
      if (itemExists == null) {
        newList.add(param);
        mapper.writeValue(new File(FILE_PATH), newList);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void update(Gasto param) {
    try {
      Gasto itemExists = getById(param.getId());
      if (itemExists != null) {
        int index = list.indexOf(itemExists);
        if (index >= 0) {
          list.set(index, param);
          mapper.writeValue(new File(FILE_PATH), list);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void delete(Integer id) {
    list = list.stream()
        .filter(item -> !item.getId().equals(id))
        .collect(Collectors.toList());
    try {
      mapper.writeValue(new File(FILE_PATH), list);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public double getAverage() {
    try {
      Map<LocalDate, Double> gastoPorFecha = new HashMap<>();

      for (Gasto gasto : list) {
        LocalDate fecha = gasto.getFecha();
        double monto = gasto.getMonto();

        gastoPorFecha.put(fecha, gastoPorFecha.getOrDefault(fecha, 0.0) + monto);
      }

      double montoTotal = 0;
      for (double total : gastoPorFecha.values()) {
        montoTotal += total;
      }

      int diasDistintos = gastoPorFecha.size();

      if (diasDistintos == 0) {
        return 0;
      }

      return montoTotal / diasDistintos;
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }
  }

  public List<Gasto> getByDateRange(LocalDate fechaInicio, LocalDate fechaFin) {
    try {
      return list.stream()
          .filter(gasto -> !gasto.getFecha().isBefore(fechaInicio) && !gasto.getFecha().isAfter(fechaFin))
          .collect(Collectors.toList());
    } catch (Exception e) {
      e.printStackTrace();
      return new ArrayList<>();
    }
  }
}
