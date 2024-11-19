package com.growby.challange.service.impl;

import com.growby.challange.mapper.Mapper;
import com.growby.challange.model.dto.request.AutorDto;
import com.growby.challange.model.entity.Autor;
import com.growby.challange.repository.AutorJpaRepository;
import com.growby.challange.service.AutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AutorServiceImpl implements AutorService {

  private final AutorJpaRepository autorJpaRepository;

  public Autor crearAutor(AutorDto autorDto) {
    return autorJpaRepository.save(Mapper.mapToAutor(autorDto));
  }

  public Optional<Autor> obtenerAutorPorId(Long id) {
    return autorJpaRepository.findById(id);
  }

  public List<Autor> obtenerTodosLosAutores() {
    return autorJpaRepository.findAll();
  }

  public Page<Autor> obtenerTodosLosAutoresPaginado(Pageable pageable) {
    return autorJpaRepository.findAll(pageable);
  }


  public Autor actualizarAutor(AutorDto autorDto) {
    return autorJpaRepository.save(Mapper.mapToAutor(autorDto));
  }

  public void eliminarAutor(Long id) {
    autorJpaRepository.deleteById(id);
  }

}
