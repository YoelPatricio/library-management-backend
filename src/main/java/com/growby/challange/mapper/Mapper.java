package com.growby.challange.mapper;

import com.growby.challange.model.dto.request.AutorDto;
import com.growby.challange.model.dto.request.LibroDto;
import com.growby.challange.model.dto.request.PrestamoDto;
import com.growby.challange.model.dto.response.LibroResponseDto;
import com.growby.challange.model.dto.response.PrestamoResponseDto;
import com.growby.challange.model.entity.Autor;
import com.growby.challange.model.entity.Libro;
import com.growby.challange.model.entity.Prestamo;

public class Mapper {

  public static Autor mapToAutor(AutorDto dto) {
    return Autor.builder()
            .id(dto.getId())
            .nombre(dto.getNombre())
            .nacionalidad(dto.getNacionalidad())
            .fechaNacimiento(dto.getFechaNacimiento())
            .build();
  }

  public static Libro mapToLibro(LibroDto dto) {
    return Libro.builder()
            .id(dto.getId())
            .titulo(dto.getTitulo())
            .autor(Autor.builder().id(dto.getAutorId()).build())
            .isbn(dto.getIsbn())
            .fechaPublicacion(dto.getFechaPublicacion())
            .estado(dto.getEstado())
            .build();
  }

  public static Prestamo mapToPrestamo(PrestamoDto dto, Libro libro) {
    return Prestamo.builder()
            .id(dto.getId())
            .libro(libro)
            .fechaDevolucion(dto.getFechaDevolucion())
            .build();
  }

  public static LibroResponseDto mapToLibroResponseDto(Libro libro) {
    return LibroResponseDto.builder()
            .id(libro.getId())
            .titulo(libro.getTitulo())
            .autorId(libro.getAutor().getId())
            .autorNombre(libro.getAutor().getNombre())
            .isbn(libro.getIsbn())
            .fechaPublicacion(libro.getFechaPublicacion())
            .estado(libro.getEstado())
            .build();
  }

  public static PrestamoResponseDto mapToPrestamoResponseDto(Prestamo prestamo) {
    return PrestamoResponseDto.builder()
            .id(prestamo.getId())
            .libroId(prestamo.getLibro().getId())
            .libroTitulo(prestamo.getLibro().getTitulo())
            .libroIsbn(prestamo.getLibro().getIsbn())
            .lector(prestamo.getLector())
            .fechaPrestamo(prestamo.getFechaPrestamo())
            .fechaDevolucion(prestamo.getFechaDevolucion())
            .estado(prestamo.getEstado())
            .build();
  }

}
