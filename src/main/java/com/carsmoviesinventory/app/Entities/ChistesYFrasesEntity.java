package com.carsmoviesinventory.app.Entities;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;



import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ChistesYFrasesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("ChisteOFrase")
    @NotBlank(message = "Chiste es Requerido")
    @Size(min = 50, max = 400, message = "El chiste debe tener entre 50 y 400 caracteres")
    private String ChisteOFrase;


    @PrePersist
    public void generateUUID() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    @Override
    public String toString() {
        return "ChistesYFrasesEntity{" +
                "id=" + id +
                "chisteOFrase" + ChisteOFrase +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public String getChisteOFrase() {
        return ChisteOFrase;
    }

    public void setChisteOFrase(String chisteOFrase) {
        this.ChisteOFrase = chisteOFrase;
    }

}
