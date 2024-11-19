package com.growby.challange.repository;

import com.growby.challange.model.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PrestamoJpaRepository extends JpaRepository<Prestamo, Long> {
  List<Prestamo> findByLibroId(Long libroId);

}
