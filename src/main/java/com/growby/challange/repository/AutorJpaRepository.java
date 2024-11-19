package com.growby.challange.repository;

import com.growby.challange.model.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorJpaRepository extends JpaRepository<Autor, Long> {

}
