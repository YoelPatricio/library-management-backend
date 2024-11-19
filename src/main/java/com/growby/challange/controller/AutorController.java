package com.growby.challange.controller;

import com.growby.challange.model.dto.request.AutorDto;
import com.growby.challange.model.entity.Autor;
import com.growby.challange.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
@Tag(name = "Autores", description = "Operaciones relacionadas con autores")
public class AutorController {

  private final AutorService autorService;

  @Autowired
  public AutorController(AutorService autorService) {
    this.autorService = autorService;
  }

  @PostMapping
  @Operation(summary = "Crear un nuevo autor", description = "Crea un nuevo autor en el sistema")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Autor creado exitosamente",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = Autor.class)) }),
          @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                  content = @Content),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                  content = @Content)
  })
  public ResponseEntity<Autor> crearAutor(@RequestBody AutorDto autorDto) {
    Autor nuevoAutor = autorService.crearAutor(autorDto);
    return ResponseEntity.ok(nuevoAutor);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Obtener un autor por ID", description = "Obtiene los detalles de un autor específico por su ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Autor encontrado",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = Autor.class)) }),
          @ApiResponse(responseCode = "404", description = "Autor no encontrado",
                  content = @Content)
  })
  public ResponseEntity<Autor> obtenerAutor(@PathVariable Long id) {
    return autorService.obtenerAutorPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  @Operation(summary = "Obtener todos los autores", description = "Obtiene una lista de todos los autores")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Autores encontrados",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = Autor.class)) }),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                  content = @Content)
  })
  public ResponseEntity<List<Autor>> obtenerTodosLosAutores() {
    List<Autor> autores = autorService.obtenerTodosLosAutores();
    return ResponseEntity.ok(autores);
  }

  @GetMapping("/paginado")
  @Operation(summary = "Obtener autores paginados", description = "Obtiene una lista paginada de autores")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Autores encontrados",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = Autor.class)) }),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                  content = @Content)
  })
  public Page<Autor> getAutores(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int size
  ) {
    Pageable pageable = PageRequest.of(page, size);
    return autorService.obtenerTodosLosAutoresPaginado(pageable);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Actualizar un autor", description = "Actualiza los detalles de un autor existente por su ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Autor actualizado exitosamente",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = Autor.class)) }),
          @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                  content = @Content),
          @ApiResponse(responseCode = "404", description = "Autor no encontrado",
                  content = @Content),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                  content = @Content)
  })
  public ResponseEntity<Autor> actualizarAutor(@PathVariable Long id, @RequestBody AutorDto autorDto) {
    autorDto.setId(id);
    Autor autorActualizado = autorService.actualizarAutor(autorDto);
    return ResponseEntity.ok(autorActualizado);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar un autor", description = "Elimina un autor existente por su ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "Autor eliminado exitosamente"),
          @ApiResponse(responseCode = "404", description = "Autor no encontrado",
                  content = @Content),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                  content = @Content)
  })
  public ResponseEntity<Void> eliminarAutor(@PathVariable Long id) {
    autorService.eliminarAutor(id);
    return ResponseEntity.noContent().build();
  }
}
