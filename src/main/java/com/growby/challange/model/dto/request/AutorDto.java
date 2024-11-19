package com.growby.challange.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDate;

@Data
@Schema(description = "DTO para la entidad Autor")
public class AutorDto {

  @Schema(description = "ID del autor", example = "1")
  private Long id;

  @Schema(description = "Nombre del autor", example = "Gabriel Garcia Marquez")
  private String nombre;

  @Schema(description = "Nacionalidad del autor", example = "Colombiana")
  private String nacionalidad;

  @Schema(description = "Fecha de nacimiento del autor", example = "1927-03-06")
  private LocalDate fechaNacimiento;

}
