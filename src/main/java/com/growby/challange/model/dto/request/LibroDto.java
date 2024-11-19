package com.growby.challange.model.dto.request;

import com.growby.challange.model.EstadoLibro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "DTO para la entidad Libro")
public class LibroDto {

  @Schema(description = "ID del libro", example = "1")
  private Long id;

  @Schema(description = "Título del libro", example = "Cien Años de Soledad")
  private String titulo;

  @Schema(description = "ID del autor del libro", example = "1")
  private Long autorId;

  @Schema(description = "ISBN del libro", example = "978-3-16-148410-0")
  private String isbn;

  @Schema(description = "Fecha de publicación del libro", example = "1967-05-30")
  private LocalDate fechaPublicacion;

  @Schema(description = "Estado del libro", example = "DISPONIBLE")
  @Enumerated(EnumType.STRING)
  private EstadoLibro estado;

}
