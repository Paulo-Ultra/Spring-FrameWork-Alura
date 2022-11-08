package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.Produto;

import javax.persistence.EntityManager;

public class ClienteDao {

    private EntityManager entityManager;

    public ClienteDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void cadastrar(Cliente cliente){
        this.entityManager.persist(cliente);
    }

    public Cliente buscarPorId(Long id){
        return entityManager.find(Cliente.class, id);
    }
}
