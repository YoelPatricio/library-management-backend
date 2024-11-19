package com.growby.challange.model.dto.response;

import com.growby.challange.model.EstadoLibro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
@Schema(description = "DTO para la entidad Libro en la respuesta")
public class LibroResponseDto {

  @Schema(description = "ID del libro", example = "1")
  private Long id;

  @Schema(description = "Título del libro", example = "Cien Años de Soledad")
  private String titulo;

  @Schema(description = "ID del autor del libro", example = "1")
  private Long autorId;

  @Schema(description = "Nombre del autor del libro", example = "Gabriel Garcia Marquez")
  private String autorNombre;

  @Schema(description = "ISBN del libro", example = "978-3-16-148410-0")
  private String isbn;

  @Schema(description = "Fecha de publicación del libro", example = "1967-05-30")
  private LocalDate fechaPublicacion;

  @Schema(description = "Estado del libro", example = "DISPONIBLE")
  @Enumerated(EnumType.STRING)
  private EstadoLibro estado;

}
