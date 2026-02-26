package io.github.devnicolas.api_agendamentos_festas.place;

import io.github.devnicolas.api_agendamentos_festas.exception.ValidationException;
import jakarta.persistence.*;

import java.util.List;

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
    private String address;

    protected Place() {
        // Para JPA
    }

    public Place(String name, int capacity, String address) {
        changeName(name);
        changeCapacity(capacity);
        changeAddress(address);
    }



    public void changeName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException(List.of("Nome é obrigatório."));
        }
        this.name = name;
    }

    public void changeCapacity(int capacity) {
        if (capacity <= 0) {
            throw new ValidationException(List.of("Capacidade deve ser maior que zero."));
        }
        this.capacity = capacity;
    }

    public void changeAddress(String address) {
        if (address == null || address.isBlank()) {
            throw new ValidationException(List.of("Endereço é obrigatório."));
        }
        this.address = address;
    }



    public Long getId() { return id; }
    public String getName() { return name; }
    public int getCapacity() { return capacity; }
    public String getAddress() { return address; }



    protected void setName(String name) { this.name = name; }
    protected void setCapacity(int capacity) { this.capacity = capacity; }
    protected void setAddress(String address) { this.address = address; }
}