package io.github.devnicolas.api_agendamentos_festas.client;

import io.github.devnicolas.api_agendamentos_festas.client.exceptions.EmailInvalidExeception;
import jakarta.persistence.*;

@Entity
@Table(name = "client")
public class Client {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  public Client() {
  }

  public Client(String name, String email, String password) {
    setEmail(email);
    setName(name);
    setPassword(password);
  }


  public String getName() {
    return this.name;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    if (email == null || !email.contains("@"))
      throw new EmailInvalidExeception("Formato Emáil inválido");
    this.email = email;
  }

  public void setName(String name) {
    if (name == null || name.isBlank())
      throw new IllegalArgumentException("Nome não pode ser vazio");
    this.name = name;
  }

  public Long getId() {
    return this.id;
  }

  public void setPassword(String password) {
    if (password == null || password.length() < 6)
      throw new IllegalArgumentException("Senha deve ter no mínimo 6 caracteres");

    this.password = password;
  }
}
