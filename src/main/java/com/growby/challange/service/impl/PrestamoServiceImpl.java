package com.growby.challange.service.impl;

import com.growby.challange.mapper.Mapper;
import com.growby.challange.model.EstadoLibro;
import com.growby.challange.model.EstadoPrestamo;
import com.growby.challange.model.dto.request.PrestamoDto;
import com.growby.challange.model.dto.response.PrestamoResponseDto;
import com.growby.challange.model.entity.Libro;
import com.growby.challange.model.entity.Prestamo;
import com.growby.challange.repository.LibroJpaRepository;
import com.growby.challange.repository.PrestamoJpaRepository;
import com.growby.challange.service.PrestamoService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrestamoServiceImpl implements PrestamoService {

  private final PrestamoJpaRepository prestamoRepository;
  private final LibroJpaRepository libroRepository;

  @PersistenceContext
  private EntityManager entityManager;

  public Prestamo crearPrestamo(PrestamoDto prestamoDto) {
    Libro libro = libroRepository.findById(prestamoDto.getLibroId())
            .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

    if (libro.getEstado() != EstadoLibro.DISPONIBLE) {
      throw new RuntimeException("El libro no está disponible");
    }

    // Actualizar estado del libro
    libro.setEstado(EstadoLibro.NO_DISPONIBLE);
    libroRepository.save(libro);

    Prestamo prestamo = Prestamo.builder()
            .libro(libro)
            .lector(prestamoDto.getLector())
            .fechaPrestamo(LocalDate.now())
            .fechaDevolucion(prestamoDto.getFechaDevolucion())
            .estado(EstadoPrestamo.ACTIVO)
            .build();

    return prestamoRepository.save(prestamo);

  }

  public Prestamo actualizarPrestamo(PrestamoDto prestamoDto) {
      Prestamo prestamo = prestamoRepository.findById(prestamoDto.getId())
              .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

      Libro libro = libroRepository.findById(prestamoDto.getLibroId())
              .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

      if (libro.getEstado() != EstadoLibro.DISPONIBLE
              && libro.getId() != prestamo.getLibro().getId()) {
      throw new RuntimeException("El libro no está disponible");
      }

      // Actualizar estado del libro
      Libro libroAnterior = prestamo.getLibro();
      libroAnterior.setEstado(EstadoLibro.DISPONIBLE);
      libroRepository.save(libroAnterior);

      libro.setEstado(EstadoLibro.NO_DISPONIBLE);
      libroRepository.save(libro);

      prestamo.setLibro(libro);
      prestamo.setLector(prestamoDto.getLector());
      prestamo.setFechaDevolucion(prestamoDto.getFechaDevolucion());

      return prestamoRepository.save(prestamo);
  }

  public List<Prestamo> obtenerTodosLosPrestamos() {
    return prestamoRepository.findAll();
  }

  /*public Page<PrestamoResponseDto> obtenerTodosLosPrestamosPaginado(Pageable pageable, String filtro, String estado) {
    return prestamoRepository.findAll(pageable).map(Mapper::mapToPrestamoResponseDto);
  }*/

  public Page<PrestamoResponseDto> obtenerTodosLosPrestamosPaginado(Pageable pageable, String filtro, String estado) {

    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Prestamo> query = cb.createQuery(Prestamo.class);
    Root<Prestamo> prestamo = query.from(Prestamo.class);

    // Construcción de predicados dinámicos
    List<Predicate> predicates = new ArrayList<>();

    // Filtro por título del libro o isbn
    if (filtro != null && !filtro.isEmpty()) {
      Predicate tituloPredicate = cb.like(cb.lower(prestamo.join("libro").get("titulo")), "%" + filtro.toLowerCase() + "%");
      Predicate isbnPredicate = cb.like(cb.lower(prestamo.join("libro").get("isbn")), "%" + filtro.toLowerCase() + "%");
      predicates.add(cb.or(tituloPredicate, isbnPredicate));
    }

    // Filtro por estado
    if (estado != null && !estado.equalsIgnoreCase("Todos")) {
      predicates.add(cb.equal(prestamo.get("estado"), estado));
    }

    query.where(cb.and(predicates.toArray(new Predicate[0])));

    query.orderBy(cb.desc(prestamo.get("fechaPrestamo")));

    // Obtener la lista de resultados paginados
    List<PrestamoResponseDto> prestamos = entityManager.createQuery(query)
            .setFirstResult((int) pageable.getOffset())
            .setMaxResults(pageable.getPageSize())
            .getResultList().stream().map(Mapper::mapToPrestamoResponseDto).toList();

    // Contar el total de registros
    CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
    Root<Prestamo> countRoot = countQuery.from(Prestamo.class);
    countQuery.select(cb.count(countRoot)).where(cb.and(predicates.toArray(new Predicate[0])));
    Integer total = prestamos.size();

    return new PageImpl<>(prestamos, pageable, total);

  }

  public Optional<PrestamoResponseDto> obtenerPrestamoPorId(Long id) {
    return prestamoRepository.findById(id).map(Mapper::mapToPrestamoResponseDto);
  }

  public List<Prestamo> obtenerPrestamosPorLibroId(Long libroId) {
    return prestamoRepository.findByLibroId(libroId);
  }

  public Prestamo finalizarPrestamo(Long prestamoId) {
    Prestamo prestamo = prestamoRepository.findById(prestamoId)
            .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

    if (prestamo.getEstado() == EstadoPrestamo.FINALIZADO) {
      throw new RuntimeException("El préstamo ya ha sido finalizado");
    }

    prestamo.setFechaDevolucion(LocalDate.now());
    prestamo.setEstado(EstadoPrestamo.FINALIZADO);

    // Actualizar estado del libro
    Libro libro = prestamo.getLibro();
    libro.setEstado(EstadoLibro.DISPONIBLE);
    libroRepository.save(libro);

    return prestamoRepository.save(prestamo);
  }

  public void eliminarPrestamo(Long id) {
    Prestamo prestamo = prestamoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

    // Actualizar estado del libro
    Libro libro = prestamo.getLibro();
    libro.setEstado(EstadoLibro.DISPONIBLE);
    libroRepository.save(libro);

    prestamoRepository.deleteById(id);

  }

}
