package br.com.alura.leilao.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import br.com.alura.leilao.util.builder.UsuarioBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;

class UsuarioDaoTest {

	private UsuarioDao dao;
	private EntityManager em;

	@BeforeEach
	public void beforeEach(){
		em = JPAUtil.getEntityManager();
		this.dao = new UsuarioDao(em);
		em.getTransaction().begin();

	}

	@AfterEach
	public void afterEach(){
		em.getTransaction().rollback();
	}

	@Test
	void deveriaEncontrarUsuarioCadastrado() {

		Usuario usuario = new UsuarioBuilder()
				.comNome("Fulano")
				.comEmail("fulano@email.com")
				.comSenha("123456")
				.criar();
		em.persist(usuario);
		Usuario encontrado = this.dao.buscarPorUsername(usuario.getNome());
		Assert.assertNotNull(encontrado);
	}

	@Test
	void naoDeveriaEncontrarUsuarioCadastrado() {

		Usuario usuario = new UsuarioBuilder()
				.comNome("Fulano")
				.comEmail("fulano@email.com")
				.comSenha("123456")
				.criar();
		em.persist(usuario);
		Assert.assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername("beltrano"));
	}

	@Test
	void DeveriaRemoverUmUsuario() {

		Usuario usuario = new UsuarioBuilder()
				.comNome("Fulano")
				.comEmail("fulano@email.com")
				.comSenha("123456")
				.criar();
		em.persist(usuario);
		dao.deletar(usuario);
		Assert.assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername(usuario.getNome()));
	}

}
