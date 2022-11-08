package br.com.alura.springdata.repository;

import br.com.alura.springdata.orm.Funcionario;
import br.com.alura.springdata.orm.FuncionarioProjecao;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Integer>,
        JpaSpecificationExecutor<Funcionario> {
        //Especification

    List<Funcionario> findByNome(String nome);

    //Derived Query -> Cmo seria a JPQL abaixo
//    List<Funcionario> findByNomeAndSalarioGreaterThanAndDataContratacao(String nome, BigDecimal salario, LocalDate data);

    //JPQL
    @Query("select f from Funcionario f where f.nome = :nome" +
            " AND f.salario >= :salario and f.dataContratacao = :data")
    List<Funcionario> findNomeSalarioMaiorDataContratacao(String nome, BigDecimal salario, LocalDate data);

    //Native Query
    @Query(value = "SELECT * FROM funcionarios f WHERE f.data_contratacao >= :data", nativeQuery = true)
    List<Funcionario> findDataContratacaoMaior(LocalDate data);


    @Query(value = "select f.id, f.nome, f.salario from funcionarios f",
            nativeQuery = true)
    List<FuncionarioProjecao> findFuncionarioSalario();
}
