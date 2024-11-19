package com.growby.challange.service;

import com.growby.challange.model.dto.request.AutorDto;
import com.growby.challange.model.entity.Autor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AutorService {

  Autor crearAutor(AutorDto autorDto);

  Optional<Autor> obtenerAutorPorId(Long id);

  List<Autor> obtenerTodosLosAutores();

  Page<Autor> obtenerTodosLosAutoresPaginado(Pageable pageable);

  Autor actualizarAutor(AutorDto autorDto);

  void eliminarAutor(Long id);

}
