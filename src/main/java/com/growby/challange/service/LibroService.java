package com.growby.challange.service;

import com.growby.challange.model.dto.request.LibroDto;
import com.growby.challange.model.dto.response.LibroResponseDto;
import com.growby.challange.model.entity.Libro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LibroService {

  Libro crearLibro(LibroDto libroDto);

  Optional<LibroResponseDto> obtenerLibroPorId(Long id);

  List<Libro> obtenerTodosLosLibros();

  Page<LibroResponseDto> obtenerTodosLosLibrosPaginado(Pageable pageable);

  Libro actualizarLibro(LibroDto libroDto);

  void eliminarLibro(Long id);

  boolean verificarDisponibilidad(String isbn);

}
