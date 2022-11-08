package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Categoria;

import javax.persistence.EntityManager;

public class CategoriaDao {

    private EntityManager entityManager;

    public CategoriaDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void cadastrar(Categoria categoria){
        this.entityManager.persist(categoria);
    }

    public void atualizar(Categoria categoria){
        this.entityManager.merge(categoria);
    }

    public void deletar(Categoria categoria){
        categoria = entityManager.merge(categoria);
        this.entityManager.remove(categoria);
    }
}
