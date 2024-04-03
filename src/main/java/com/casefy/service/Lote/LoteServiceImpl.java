package com.casefy.service.Lote;

import java.util.List;

import com.casefy.dto.Lote.*;
import com.casefy.model.Fornecedor;
import com.casefy.model.Lote;
import com.casefy.repository.FornecedorRepository;
import com.casefy.repository.LoteRepository;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LoteServiceImpl implements LoteService {
    @Inject
    LoteRepository loteRepository;

    @Inject
    FornecedorRepository fornecedorRepository;

    @Override
    @Transactional
    public LoteResponseDTO insert(LoteDTO dto) {
        // Criação do novo lote
        Lote lote = new Lote();
        lote.setQuantidadeItens(dto.quantidadeItens());
        lote.setValorUnitario(dto.valorUnitario());
        lote.setValorTotal(dto.valorTotal());
        lote.setEstoque(dto.estoque());
        lote.setDataCompra(dto.dataCompra());
        lote.setCodigo(dto.codigo());

        // Busca do fornecedor pelo ID
        Fornecedor fornecedor = fornecedorRepository.findById(dto.fornecedor().id());
        if (fornecedor == null) {
            throw new EntityNotFoundException("Fornecedor não cadastrado! ID: " + dto.fornecedor().id());
        }
        // Adiciona o lote à lista de lotes do fornecedor
        fornecedor.getLotes().add(lote);
        lote.setFornecedor(fornecedor);

        // Persiste o lote. Como o fornecedor já está sendo gerenciado pela JPA, não é
        // necessário persisti-lo separadamente.
        loteRepository.persist(lote);

        return LoteResponseDTO.valueOf(lote);
    }

    @Override
    @Transactional
    public LoteResponseDTO update(LoteDTO dto, Long id) {
        Lote loteExistente = loteRepository.findById(id);
        if (loteExistente != null) {
            loteExistente.setQuantidadeItens(dto.quantidadeItens());
            loteExistente.setValorUnitario(dto.valorUnitario());
            loteExistente.setValorTotal(dto.valorTotal());
            loteExistente.setEstoque(dto.estoque());
            loteExistente.setDataCompra(dto.dataCompra());
            loteExistente.setCodigo(dto.codigo());

            // Busca do fornecedor pelo ID
            Fornecedor fornecedor = fornecedorRepository.findById(dto.fornecedor().id());
            if (fornecedor == null) {
                throw new EntityNotFoundException("Fornecedor não cadastrado! ID: " + dto.fornecedor().id());
            }
            // Adiciona o lote à lista de lotes do fornecedor
            fornecedor.getLotes().add(loteExistente);
            loteExistente.setFornecedor(fornecedor);

            // Persiste o lote. Como o fornecedor já está sendo gerenciado pela JPA, não é
            // necessário persisti-lo separadamente.
            loteRepository.persist(loteExistente);

            return LoteResponseDTO.valueOf(loteExistente);
        } else {
            throw new EntityNotFoundException(
                    "Lote não cadastrado!");
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!loteRepository.deleteById(id))
            throw new NotFoundException("A exclusão falhou falhou!");
    }

    @Override
    public LoteResponseDTO findById(Long id) {
        Lote lote = loteRepository.findById(id);
        if (lote != null) {
            return LoteResponseDTO.valueOf(lote);
        } else {
            throw new EntityNotFoundException("Lote não encontrado com o ID: " + id);
        }
    }

    @Override
    public List<LoteResponseDTO> findByCodigo(String codigo) {
        List<Lote> lotes = loteRepository.findByCodigo(codigo);
        if (lotes == null) {
            throw new EntityNotFoundException("Lote não encontrado com a codigo: " + codigo);
        }
        return lotes.stream().map(e -> LoteResponseDTO.valueOf(e)).toList();
    }

    @Override
    public List<LoteResponseDTO> findByAll() {
        return loteRepository.findAll().stream().map(f -> LoteResponseDTO.valueOf(f)).toList();
    }

}
