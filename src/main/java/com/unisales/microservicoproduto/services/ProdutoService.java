package com.unisales.microservicoproduto.services;

import com.unisales.microservicoproduto.domain.Produto;
import com.unisales.microservicoproduto.domain.ProdutoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class ProdutoService {
    @Autowired
    private ProdutoRepository repository;

    public List<Produto> findAll() {
        return repository.findAll();
    }

    public Produto findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new Error("Produto não encontrado"));
    }

    public List<Produto> findByNome(String nome) {
        return repository.findByNomeContaining(nome);
    }

    public Produto save(Produto produto) {
        if (produto.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Error("Preço deve ser maior que zero");
        }
        return repository.save(produto);
    }

    public Produto update(Long id, Produto produto) {
        Produto existingProduto = findById(id);
        BeanUtils.copyProperties(produto, existingProduto, "id");
        return repository.save(existingProduto);
    }

    public void delete(Long id) {
        Produto produto = findById(id);
        repository.delete(produto);
    }
}