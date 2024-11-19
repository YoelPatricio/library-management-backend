package com.growby.challange.repository;

import com.growby.challange.model.EstadoLibro;
import com.growby.challange.model.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroJpaRepository extends JpaRepository<Libro, Long> {
  boolean existsByIsbnAndEstado(String isbn, EstadoLibro estado);

}
