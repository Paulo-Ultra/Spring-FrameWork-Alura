package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Produto;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ProdutoDao {

    private EntityManager entityManager;

    public ProdutoDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void cadastrar(Produto produto) {
        this.entityManager.persist(produto);
    }

    public Produto buscarPorId(Long id) {
        return entityManager.find(Produto.class, id);
    }

    public List<Produto> buscarTodos() {
        String jpql = "select p from Produto p";
        return entityManager.createQuery(jpql).getResultList();
    }

    public List<Produto> buscarPorNome(String nome) {
        String jpql = "select p from Produto p where p.nome = :nome ";
        return entityManager.createQuery(jpql)
                .setParameter("nome", nome)
                .getResultList();

        //Usando ? com número da posição
//        public List<Produto> buscarPorNome(String nome){
//            String jpql = "select p from Produto p where p.nome = ?1 ";
//            return entityManager.createQuery(jpql)
//                    .setParameter(1, nome)
//                    .getResultList();
    }

    public List<Produto> buscarPorNomeDaCategoria(String nome) {
        //Filtra pelo relacionamento
//        String jpql = "select p from Produto p where p.categoria.nome = :nome ";
        //Named Query
        return entityManager.createNamedQuery("produtosPorCategoria", Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    public BigDecimal buscarPrecoDoProdutoComNome(String nome) {
        String jpql = "select p.preco from Produto p where p.nome = :nome ";
        return entityManager.createQuery(jpql, BigDecimal.class)
                .setParameter("nome", nome)
                .getSingleResult();
    }


    public List<Produto> buscarPorParametros(String nome, BigDecimal preco, LocalDate dataCadastro) {
        String jpql = "select p from Produto p where 1=1 ";
        if (nome != null && !nome.trim().isEmpty()) {
            jpql = "and p.nome = :nome ";
        }
        if (preco != null) {
            jpql = "and p.preco = :preco ";
        }
        if (dataCadastro != null) {
            jpql = "and p.dataCadastro = :dataCadastro ";
        }
        TypedQuery<Produto> query = entityManager.createQuery(jpql, Produto.class);
        if (nome != null && !nome.trim().isEmpty()) {
            query.setParameter("nome", nome);
        }
        if (preco != null) {
            query.setParameter("preco", preco);
        }
        if (dataCadastro != null) {
            query.setParameter("dataCadastro", dataCadastro);
        }
        return query.getResultList();
    }

    public List<Produto> buscarPorParametrosComCriteria(String nome, BigDecimal preco, LocalDate dataCadastro) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
        Root<Produto> from = query.from(Produto.class);
        Predicate filtros = builder.and();
        if (nome != null && !nome.trim().isEmpty()) {
            filtros = builder.and(filtros, builder.equal(from.get("nome"), nome));
        }
        if (preco != null) {
            filtros = builder.and(filtros, builder.equal(from.get("preco"), preco));
        }
        if (dataCadastro != null) {
            filtros = builder.and(filtros, builder.equal(from.get("dataCadastro"), dataCadastro));
        }
        query.where(filtros);
        return entityManager.createQuery(query).getResultList();
    }
}
