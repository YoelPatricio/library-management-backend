package com.growby.challange.model.entity;

import com.growby.challange.model.EstadoPrestamo;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Prestamo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional=false)
  @JoinColumn(name = "libro_id")
  private Libro libro;

  private String lector;

  private LocalDate fechaPrestamo;

  private LocalDate fechaDevolucion;

  @Enumerated(EnumType.STRING)
  private EstadoPrestamo estado;

}
