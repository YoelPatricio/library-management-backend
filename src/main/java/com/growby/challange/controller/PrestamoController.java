package com.growby.challange.controller;

import com.growby.challange.model.dto.request.PrestamoDto;
import com.growby.challange.model.dto.response.PrestamoResponseDto;
import com.growby.challange.model.entity.Prestamo;
import com.growby.challange.service.PrestamoService;
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
@RequestMapping("/api/prestamos")
@Tag(name = "Prestamos", description = "Operaciones relacionadas a los prestamos de libros")
public class PrestamoController {

  private final PrestamoService prestamoService;

  @Autowired
  public PrestamoController(PrestamoService prestamoService) {
    this.prestamoService = prestamoService;
  }

  @PostMapping("/libro")
  @Operation(summary = "Crear un nuevo préstamo", description = "Crea un nuevo préstamo para un libro")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Préstamo creado exitosamente",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = Prestamo.class)) }),
          @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                  content = @Content),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                  content = @Content)
  })
  public ResponseEntity<Prestamo> crearPrestamo(@RequestBody PrestamoDto prestamoDto) {
    Prestamo nuevoPrestamo = prestamoService.crearPrestamo(prestamoDto);
    return ResponseEntity.ok(nuevoPrestamo);
  }

  @PutMapping("/{id}")
  @Operation(summary = "Actualizar un préstamo", description = "Actualiza los detalles de un préstamo existente por su ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Préstamo actualizado exitosamente",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = Prestamo.class)) }),
          @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                  content = @Content),
          @ApiResponse(responseCode = "404", description = "Préstamo no encontrado",
                  content = @Content),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                  content = @Content)
  })
  public ResponseEntity<Prestamo> actualizarPrestamo(@PathVariable Long id, @RequestBody PrestamoDto prestamoDto) {
    prestamoDto.setId(id);
    Prestamo prestamoActualizado = prestamoService.actualizarPrestamo(prestamoDto);
    return ResponseEntity.ok(prestamoActualizado);
  }

  @GetMapping
  @Operation(summary = "Obtener todos los préstamos", description = "Obtiene una lista de todos los préstamos")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Préstamos encontrados",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = Prestamo.class)) }),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                  content = @Content)
  })
  public ResponseEntity<List<Prestamo>> obtenerTodosLosPrestamos(
          @RequestParam Optional<Integer> page,
          @RequestParam Optional<Integer> size) {
    List<Prestamo> libros = prestamoService.obtenerTodosLosPrestamos();
    return ResponseEntity.ok(libros);
  }

  @GetMapping("/paginado")
  @Operation(summary = "Obtener préstamos paginados", description = "Obtiene una lista paginada de préstamos")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Préstamos encontrados",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = PrestamoResponseDto.class)) }),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                  content = @Content)
  })
  public ResponseEntity<Page<PrestamoResponseDto>> obtenerTodosLosPrestamosPaginado(
          @RequestParam Optional<Integer> page,
          @RequestParam Optional<Integer> size,
          @RequestParam String filtro,
          @RequestParam String estado) {
    Page<PrestamoResponseDto> libros = prestamoService.obtenerTodosLosPrestamosPaginado(
            PageRequest.of(page.orElse(0), size.orElse(10)), filtro, estado);
    return ResponseEntity.ok(libros);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Obtener un préstamo por ID", description = "Obtiene los detalles de un préstamo específico por su ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Préstamo encontrado",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = PrestamoResponseDto.class)) }),
          @ApiResponse(responseCode = "404", description = "Préstamo no encontrado",
                  content = @Content)
  })
  public ResponseEntity<PrestamoResponseDto> obtenerPrestamo(@PathVariable Long id) {
    return prestamoService.obtenerPrestamoPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/libro/{libroId}")
  @Operation(summary = "Obtener préstamos por libro", description = "Obtiene una lista de préstamos por el ID del libro")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Préstamos encontrados",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = Prestamo.class)) }),
          @ApiResponse(responseCode = "404", description = "Libro no encontrado",
                  content = @Content),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                  content = @Content)
  })
  public ResponseEntity<List<Prestamo>> obtenerPrestamosPorLibro(@PathVariable Long libroId) {
    List<Prestamo> prestamos = prestamoService.obtenerPrestamosPorLibroId(libroId);
    return ResponseEntity.ok(prestamos);
  }

  @PutMapping("/finalizar/{prestamoId}")
  @Operation(summary = "Finalizar un préstamo", description = "Finaliza un préstamo existente por su ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Préstamo finalizado exitosamente",
                  content = { @Content(mediaType = "application/json",
                          schema = @Schema(implementation = Prestamo.class)) }),
          @ApiResponse(responseCode = "404", description = "Préstamo no encontrado",
                  content = @Content),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                  content = @Content)
  })
  public ResponseEntity<Prestamo> finalizarPrestamo(@PathVariable Long prestamoId) {
    Prestamo prestamoFinalizado = prestamoService.finalizarPrestamo(prestamoId);
    return ResponseEntity.ok(prestamoFinalizado);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Eliminar un préstamo", description = "Elimina un préstamo existente por su ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "204", description = "Préstamo eliminado exitosamente"),
          @ApiResponse(responseCode = "404", description = "Préstamo no encontrado",
                  content = @Content),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                  content = @Content)
  })
  public ResponseEntity<Void> eliminarPrestamo(@PathVariable Long id) {
    prestamoService.eliminarPrestamo(id);
    return ResponseEntity.noContent().build();
  }
}
