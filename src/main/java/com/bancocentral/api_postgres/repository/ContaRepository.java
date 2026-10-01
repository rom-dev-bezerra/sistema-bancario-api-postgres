package com.bancocentral.api_postgres.repository;

import com.bancocentral.api_postgres.model.ContaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<ContaBancaria, Integer> {
}
