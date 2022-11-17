package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import br.com.alura.leilao.util.builder.LanceBuilder;
import br.com.alura.leilao.util.builder.LeilaoBuilder;
import br.com.alura.leilao.util.builder.UsuarioBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;

class LanceDaoTest {

	private LanceDao dao;
	private EntityManager em;

	@BeforeEach
	public void beforeEach(){
		em = JPAUtil.getEntityManager();
		this.dao = new LanceDao(em);
		em.getTransaction().begin();

	}

	@AfterEach
	public void afterEach(){
		em.getTransaction().rollback();
	}

	@Test
	void deveriaSalvarUmLance() {
		Usuario usuario = new UsuarioBuilder()
				.comNome("Fulano")
				.comEmail("fulano@email.com")
				.comSenha("123456")
				.criar();
		em.persist(usuario);

		Leilao leilao = new LeilaoBuilder()
				.comNome("Mochila")
				.comValorInicial("500")
				.comData(LocalDate.now())
				.comUsuario(usuario)
				.criar();
		em.persist(leilao);

		Lance lance = new LanceBuilder()
				.comValor("200")
				.comData(LocalDate.now())
				.comUsuario(usuario)
				.comLeilao(leilao)
				.criar();


		lance = dao.salvar(lance);

		Lance salvo = dao.buscarMaiorLanceDoLeilao(lance.getLeilao());
		Assert.assertNotNull(salvo);
	}



}
