package com.growby.challange.model.dto.response;

import com.growby.challange.model.EstadoPrestamo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
@Schema(description = "DTO para la entidad Préstamo en la respuesta")
public class PrestamoResponseDto {

  @Schema(description = "ID del préstamo", example = "1")
  private Long id;

  @Schema(description = "ID del libro prestado", example = "1")
  private Long libroId;

  @Schema(description = "Título del libro prestado", example = "Cien Años de Soledad")
  private String libroTitulo;

  @Schema(description = "ISBN del libro prestado", example = "978-3-16-148410-0")
  private String libroIsbn;

  @Schema(description = "Nombre del lector", example = "Juan Perez")
  private String lector;

  @Schema(description = "Fecha del préstamo", example = "2023-01-01")
  private LocalDate fechaPrestamo;

  @Schema(description = "Fecha de devolución del préstamo", example = "2023-12-31")
  private LocalDate fechaDevolucion;

  @Schema(description = "Estado del préstamo", example = "ACTIVO")
  private EstadoPrestamo estado;

}
