package com.growby.challange.service;

import com.growby.challange.model.dto.request.PrestamoDto;
import com.growby.challange.model.dto.response.PrestamoResponseDto;
import com.growby.challange.model.entity.Prestamo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PrestamoService {

  Prestamo crearPrestamo(PrestamoDto prestamoDto);

  Prestamo actualizarPrestamo(PrestamoDto prestamoDto);

  List<Prestamo> obtenerTodosLosPrestamos();

  Page<PrestamoResponseDto> obtenerTodosLosPrestamosPaginado(Pageable pageable, String filtro, String estado);

  Optional<PrestamoResponseDto> obtenerPrestamoPorId(Long id);

  List<Prestamo> obtenerPrestamosPorLibroId(Long libroId);

  Prestamo finalizarPrestamo(Long prestamoId);

  void eliminarPrestamo(Long id);

}
