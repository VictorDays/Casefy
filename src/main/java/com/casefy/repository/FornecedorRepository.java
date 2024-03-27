package com.casefy.repository;

import java.util.List;

import com.casefy.model.Fornecedor;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FornecedorRepository implements PanacheRepository<Fornecedor> {
    public List<Fornecedor> findByNome(String nome) {
        return find("UPPER(nome) LIKE UPPER(?1) ", "%" + nome + "%").list();
    }
}
