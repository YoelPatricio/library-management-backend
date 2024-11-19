package com.growby.challange.model.dto.request;

import com.growby.challange.model.EstadoPrestamo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "DTO para la entidad Préstamo")
public class PrestamoDto {

  @Schema(description = "ID del préstamo", example = "1")
  private Long id;

  @Schema(description = "ID del libro prestado", example = "1")
  private Long libroId;

  @Schema(description = "Nombre del lector", example = "Juan Perez")
  private String lector;

  @Schema(description = "Fecha de devolución del préstamo", example = "2023-12-31")
  private LocalDate fechaDevolucion;

  @Schema(description = "Estado del préstamo", example = "ACTIVO")
  private EstadoPrestamo estado;

}
