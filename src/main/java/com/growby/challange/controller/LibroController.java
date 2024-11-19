package com.growby.challange.controller;

import com.growby.challange.model.dto.request.LibroDto;
import com.growby.challange.model.dto.response.LibroResponseDto;
import com.growby.challange.model.entity.Libro;
import com.growby.challange.service.LibroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/libros")
@Tag(name = "Libros", description = "Operaciones relacionadas a los libros")
public class LibroController {

  private final LibroService libroService;

  @Autowired
  public LibroController(LibroService libroService) {
    this.libroService = libroService;
  }

  @PostMapping
  @Operation(summary = "Crear un nuevo libro", description = "Crea un nuevo libro en el sistema")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Libro creado exitosamente",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = Libro.class)) }),
          @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                  content = @Content),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                  content = @Content)
  })
  public ResponseEntity<Libro> crearLibro(@RequestBody LibroDto libro) {
    Libro nuevoLibro = libroService.crearLibro(libro);
    return ResponseEntity.ok(nuevoLibro);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Obtener un libro por ID", description = "Obtiene los detalles de un libro específico por su ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Libro encontrado",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = LibroResponseDto.class)) }),
          @ApiResponse(responseCode = "404", description = "Libro no encontrado",
                  content = @Content)
  })
  public ResponseEntity<LibroResponseDto> obtenerLibro(@PathVariable Long id) {
    return libroService.obtenerLibroPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  @Operation(summary = "Obtener todos los libros", description = "Obtiene una lista de todos los libros")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Libros encontrados",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = Libro.class)) }),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                  content = @Content)
  })
  public ResponseEntity<List<Libro>> obtenerTodosLosLibros(
          @RequestParam Optional<Integer> page,
          @RequestParam Optional<Integer> size) {
    List<Libro> libros = libroService.obtenerTodosLosLibros();
    return ResponseEntity.ok(libros);
  }

  @GetMapping("/paginado")
  @Operation(summary = "Obtener libros paginados", description = "Obtiene una lista paginada de libros")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Libros encontrados",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = LibroResponseDto.class)) }),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                  content = @Content)
  })
  public ResponseEntity<Page<LibroResponseDto>> obtenerTodosLosLibrosPaginado(
          @RequestParam Optional<Integer> page,
          @RequestParam Optional<Integer> size) {
    Page<LibroResponseDto> libros = libroService.obtenerTodosLosLibrosPaginado(
            PageRequest.of(page.orElse(0), size.orElse(10)));
    return ResponseEntity.ok(libros);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Actualizar un libro", description = "Actualiza los detalles de un libro existente por su ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Libro actualizado exitosamente",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = Libro.class)) }),
          @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                  content = @Content),
          @ApiResponse(responseCode = "404", description = "Libro no encontrado",
                  content = @Content),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                  content = @Content)
  })
  public ResponseEntity<Libro> actualizarLibro(@PathVariable Long id, @RequestBody LibroDto libroDto) {
    libroDto.setId(id);
    Libro libroActualizado = libroService.actualizarLibro(libroDto);
    return ResponseEntity.ok(libroActualizado);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar un libro", description = "Elimina un libro existente por su ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "Libro eliminado exitosamente"),
          @ApiResponse(responseCode = "404", description = "Libro no encontrado",
                  content = @Content),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                  content = @Content)
  })
  public ResponseEntity<Void> eliminarLibro(@PathVariable Long id) {
    libroService.eliminarLibro(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/disponibilidad/{isbn}")
  @Operation(summary = "Verificar disponibilidad de un libro", description = "Verifica la disponibilidad de un libro por su ISBN")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Disponibilidad verificada",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = Boolean.class)) }),
          @ApiResponse(responseCode = "404", description = "Libro no encontrado",
                  content = @Content),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                  content = @Content)
  })
  public ResponseEntity<Boolean> verificarDisponibilidad(@PathVariable String isbn) {
    boolean disponible = libroService.verificarDisponibilidad(isbn);
    return ResponseEntity.ok(disponible);
  }
}
