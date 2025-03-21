package com.carsmoviesinventory.app.Entities;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CarsMoviesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("carMovieName")
    @NotBlank(message = "Movie nameses is required")
    @Size(min = 3, max = 100, message = "Movie name must be between 3 and 100 characters")
    private String carMovieName;

    @JsonProperty("carMovieYear")
    @NotBlank(message = "Year is required")
    @Pattern(regexp = "^(19|20)\\d{2}$", message = "Year must be a valid 4-digit number (1900-2099)")
    private String carMovieYear;

    @JsonProperty("duration")
    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer duration;

    @PrePersist
    public void generateUUID() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    @Override
    public String toString() {
        return "CarsMoviesEntity{" +
                "id=" + id +
                ", carMovieName='" + carMovieName + '\'' +
                ", carMovieYear='" + carMovieYear + '\'' +
                ", duration=" + duration +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public String getCarMovieName() {
        return carMovieName;
    }

    public void setCarMovieName(String carMovieName) {
        this.carMovieName = carMovieName;
    }

    public String getCarMovieYear() {
        return carMovieYear;
    }

    public void setCarMovieYear(String carMovieYear) {
        this.carMovieYear = carMovieYear;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

}
