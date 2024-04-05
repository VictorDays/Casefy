package com.casefy.repository;

import com.casefy.model.Marca;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MarcaRepository implements PanacheRepository<Marca>{

    @SuppressWarnings("unchecked")
    public PanacheQuery<Marca> findByNome(String nome){
        if(nome ==null){
            return null;
        }
        return (PanacheQuery<Marca>) find("UPPER(nome) LIKE ?1 ", "%"+nome.toUpperCase() + "%").list();
    }
}