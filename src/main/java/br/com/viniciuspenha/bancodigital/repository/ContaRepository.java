package br.com.viniciuspenha.bancodigital.repository;

import br.com.viniciuspenha.bancodigital.model.db.AgenciaContaId;
import br.com.viniciuspenha.bancodigital.model.db.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta, AgenciaContaId> {

}