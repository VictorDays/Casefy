package com.casefy.dto.Capa;

import java.math.BigDecimal;

import com.casefy.model.Capinha;
import com.casefy.model.Cor;

import com.casefy.dto.Modelo.*;

public record CapinhaResponseDTO(
        Long id,
        ModeloResponseDTO modelo,
        Cor cor,
        String nome,
        BigDecimal valor,
        Integer quantEstoque,
        String nomeImagem,
        String descricao) {
    public static CapinhaResponseDTO valueOf(Capinha capinha) {
        return new CapinhaResponseDTO(
                capinha.getId(),
                ModeloResponseDTO.valueOf(capinha.getModelo()),
                capinha.getCor(),
                capinha.getNome(),
                capinha.getValor(),
                capinha.getQuantEstoque(),
                capinha.getNomeImagem(),
                capinha.getDescricao());
    }
}
