package com.growby.challange.service.impl;

import com.growby.challange.mapper.Mapper;
import com.growby.challange.model.EstadoLibro;
import com.growby.challange.model.dto.request.LibroDto;
import com.growby.challange.model.dto.response.LibroResponseDto;
import com.growby.challange.model.entity.Libro;
import com.growby.challange.repository.LibroJpaRepository;
import com.growby.challange.service.LibroService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibroServiceImpl implements LibroService {

  private final LibroJpaRepository libroJpaRepository;

  public Libro crearLibro(LibroDto libroDto) {
    libroDto.setEstado(EstadoLibro.DISPONIBLE);
    return libroJpaRepository.save(Mapper.mapToLibro(libroDto));
  }

  public Optional<LibroResponseDto> obtenerLibroPorId(Long id) {
    return libroJpaRepository.findById(id).map(Mapper::mapToLibroResponseDto);
  }

  public List<Libro> obtenerTodosLosLibros() {
    return libroJpaRepository.findAll();
  }

  public Page<LibroResponseDto> obtenerTodosLosLibrosPaginado(Pageable pageable) {
    return libroJpaRepository.findAll(pageable).map(Mapper::mapToLibroResponseDto);
  }

  public Libro actualizarLibro(LibroDto libroDto) {
    return libroJpaRepository.save(Mapper.mapToLibro(libroDto));
  }

  public void eliminarLibro(Long id) {
    libroJpaRepository.deleteById(id);
  }

  public boolean verificarDisponibilidad(String isbn) {
    return libroJpaRepository.existsByIsbnAndEstado(isbn, EstadoLibro.DISPONIBLE);
  }
}
