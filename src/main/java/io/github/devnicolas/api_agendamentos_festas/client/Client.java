package io.github.devnicolas.api_agendamentos_festas.client;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "client")
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String phoneNumber;

  @Column(nullable = false)
  private LocalDate dateOfBirth;

  protected Client() {
  }

  public Client(String name, String phoneNumber, LocalDate dateOfBirth) {
    setName(name);
    setPhoneNumber(phoneNumber);
    setDateOfBirth(dateOfBirth);
  }

  public void setName(String name) {
    if (name == null || name.isBlank())
      throw new IllegalArgumentException("Nome não pode ser vazio");
    this.name = name;
  }

  public void setPhoneNumber(String phoneNumber) {
    if (phoneNumber == null || phoneNumber.isBlank())
      throw new IllegalArgumentException("Telefone é obrigatório");
    this.phoneNumber = phoneNumber;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {

    if (dateOfBirth == null)
      throw new IllegalArgumentException("Data de nascimento é obrigatória");

    this.dateOfBirth = dateOfBirth;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }
}


