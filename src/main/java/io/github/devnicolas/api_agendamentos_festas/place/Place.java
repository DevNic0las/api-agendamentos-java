package io.github.devnicolas.api_agendamentos_festas.place;

import jakarta.persistence.*;

@Entity
@Table(name = "place")
public class Place {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private int capacity;

  @Column(nullable = false)
  private String addrees;

  public Place() {
  }

  public Place(String name, int capacity, String addrees) {
    setName(name);
    setCapacity(capacity);
    setAddress(addrees);
    isNegativeCapacity();
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public void setAddress(String address) {
    this.addrees = address;
  }

  public void isNegativeCapacity() {
    if (this.capacity <= 0) throw new RuntimeException("Capacidade não pode ser negativa");
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getCapacity() {
    return capacity;
  }

  public String getAddress() {
    return addrees;
  }
}
