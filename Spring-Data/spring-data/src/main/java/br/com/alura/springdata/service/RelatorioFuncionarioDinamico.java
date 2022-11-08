package br.com.alura.springdata.service;

import br.com.alura.springdata.orm.Funcionario;
import br.com.alura.springdata.repository.FuncionarioRepository;
import br.com.alura.springdata.specification.SpecificationFuncionario;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Service
public class RelatorioFuncionarioDinamico {

    private final FuncionarioRepository funcionarioRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public RelatorioFuncionarioDinamico(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public void inicial(Scanner scanner){
        System.out.println("Digite o nome");
        String nome = scanner.next();
        if(nome.equalsIgnoreCase("NULL")){
            nome = null;
        }

        System.out.println("Digite o cpf");
        String cpf = scanner.next();
        if(cpf.equalsIgnoreCase("NULL")){
            cpf = null;
        }

        System.out.println("Digite o salário");
        BigDecimal salario = scanner.nextBigDecimal();
        if(salario.equals(0)){
            salario = null;
        }

        System.out.println("Digite data de contratação");
        String data = scanner.next();
        LocalDate dataContratacao;
        if(data.equalsIgnoreCase("NULL")){
            dataContratacao = null;
        } else {
            dataContratacao = LocalDate.parse(data, formatter);
        }

        List<Funcionario> funcionarios = funcionarioRepository
                .findAll(Specification.where(SpecificationFuncionario.nome(nome))
                        .or(SpecificationFuncionario.cpf(cpf))
                        .or(SpecificationFuncionario.salario(salario))
                        .or(SpecificationFuncionario.dataContratacao(dataContratacao))
                );
        funcionarios.forEach(System.out::println);
    }
}
