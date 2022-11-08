package br.com.alura.forum.repository;

import br.com.alura.forum.model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    //Derived Query
    Page<Topico> findByCursoNome(String nomeCurso, Pageable paginacao);

    //JPQL
//    @Query("select t from Topico t where t.curso.nome = :nomeCurso")
//    List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);
}
