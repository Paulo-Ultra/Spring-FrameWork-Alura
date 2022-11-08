package br.com.alura.loja.dao;

import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PedidoDao {

    private EntityManager entityManager;

    public PedidoDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void cadastrar(Pedido pedido){
        this.entityManager.persist(pedido);
    }

    public BigDecimal valorTotalVendido(){
        String jpql = "select sum(p.valorTotal) from Pedido p";
        return entityManager.createQuery(jpql, BigDecimal.class)
                .getSingleResult();
    }

    public List<RelatorioDeVendasVo> relatorioDeVendas(){
        String jpql = "select new br.com.alura.loja.vo.RelatorioDeVendasVo (" +
                "produto.nome, sum(item.quantidade), max(pedido.data)) " +
                "from Pedido pedido " +
                "join pedido.itensPedidos item " +
                "join item.produto produto " +
                "group by produto.nome " +
                "order by item.quantidade desc";
        return entityManager.createQuery(jpql, RelatorioDeVendasVo.class)
                .getResultList();
    }

    public Pedido buscarPedidoComCliente(Long id){
        //fetch ja carrega o relacionamento lazy para uma consulta como eager
        return entityManager.createQuery("select p from Pedido p join fetch p.cliente where p.id = :id",Pedido.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
