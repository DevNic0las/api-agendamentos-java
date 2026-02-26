package io.github.devnicolas.api_agendamentos_festas.client;

import io.github.devnicolas.api_agendamentos_festas.exception.ValidationException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(name = "phone_number", nullable = false)
  private String phoneNumber;

  @Column(name = "date_of_birth", nullable = false)
  private LocalDate dateOfBirth;

  protected Client() {

  }

  public Client(String name, String phoneNumber, LocalDate dateOfBirth) {
    List<String> errors = new ArrayList<>();
    changeName(name, errors);
    changePhoneNumber(phoneNumber, errors);
    changeDateOfBirth(dateOfBirth, errors);

    if (!errors.isEmpty()) {
      throw new ValidationException(errors);
    }
  }

  public void changeName(String name, List<String> errors) {
    if (name == null || name.isBlank()) errors.add("Nome é obrigatório.");
    this.name = name;
  }

  public void changePhoneNumber(String phoneNumber, List<String> errors) {
    if (phoneNumber == null || phoneNumber.isBlank()) errors.add("Telefone é obrigatório.");
    this.phoneNumber = phoneNumber;
  }

  public void changeDateOfBirth(LocalDate dateOfBirth, List<String> errors) {
    if (dateOfBirth == null) errors.add("Data de nascimento é obrigatória.");
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

  protected void setName(String name) {
    this.name = name;
  }

  protected void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  protected void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }
}
