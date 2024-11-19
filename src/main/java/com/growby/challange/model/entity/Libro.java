package com.growby.challange.model.entity;

import com.growby.challange.model.EstadoLibro;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Libro {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable=false)
  private String titulo;

  @ManyToOne
  @JoinColumn(name = "autor_id", nullable = false)
  private Autor autor;

  @Column(unique = true, nullable = false)
  private String isbn;

  private LocalDate fechaPublicacion;

  @Enumerated(EnumType.STRING)
  private EstadoLibro estado;

  @OneToMany(mappedBy = "libro")
  private Set<Prestamo> prestamos;

}
