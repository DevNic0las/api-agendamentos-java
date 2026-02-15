package io.github.devnicolas.api_agendamentos_festas.client;

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
    this.name = name;
    this.email = email;
    this.password = password;
  }


  public String getName() {
    return this.name;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId() {
    return this.id;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
