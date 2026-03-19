package com.petshop.repository;

import com.petshop.entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<Agendamento, Long> {
}
