package br.com.alura.springdata.service;

import br.com.alura.springdata.orm.Cargo;
import br.com.alura.springdata.orm.Funcionario;
import br.com.alura.springdata.orm.UnidadeTrabalho;
import br.com.alura.springdata.repository.CargoRepository;
import br.com.alura.springdata.repository.FuncionarioRepository;
import br.com.alura.springdata.repository.UnidadeTrabalhoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class FuncionarioService {
    private Boolean system = true;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final FuncionarioRepository funcionarioRepository;
    private final CargoRepository cargoRepository;
    private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;


    public FuncionarioService(FuncionarioRepository funcionarioRepository, CargoRepository cargoRepository, UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.cargoRepository = cargoRepository;
        this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
    }

    public void inicial(Scanner scanner){
        while (system){
            System.out.println("Qual ação de funcionário deseja executar");
            System.out.println("0 - Sair");
            System.out.println("1 - Salvar");
            System.out.println("2 - Atualizar");
            System.out.println("3 - Visualizar");
            System.out.println("4 - Deletar");

            int action = scanner.nextInt();
            switch (action)  {
                case 1:
                    salvar(scanner);
                    break;
                case 2:
                    atualizar(scanner);
                    break;
                case 3:
                    visualizar(scanner);
                    break;
                case 4:
                    deletar(scanner);
                    break;
                default:
                    system = false;
                    break;
            }
        }
    }

    private void salvar(Scanner scanner){
        System.out.println("Digite o nome funcionário");
        String nome = scanner.next();
        System.out.println("Digite o cpf");
        String cpf = scanner.next();
        System.out.println("Digite o salario");
        BigDecimal salario = scanner.nextBigDecimal();
        System.out.println("Digite a data de contratação");
        String dataContratacao = scanner.next();
        System.out.println("Digite o cargoId");
        Integer cargoId = scanner.nextInt();
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setNome(cpf);
        funcionario.setSalario(salario);
        funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
        Optional<Cargo> cargo = cargoRepository.findById(cargoId);
        funcionario.setCargo(cargo.get());
        funcionarioRepository.save(funcionario);
        System.out.println("Salvo");
    }

    private void atualizar(Scanner scanner){
        System.out.println("Digite o Id");
        Integer id = scanner.nextInt();
        System.out.println("Digite o nome funcionário");
        String nome = scanner.next();
        System.out.println("Digite o cpf");
        String endereco = scanner.next();
        System.out.println("Digite o salario");
        BigDecimal salario = scanner.nextBigDecimal();
        System.out.println("Digite a data de contratação");
        String dataContratacao = scanner.next();
        System.out.println("Digite o cargoId");
        Integer cargoId = scanner.nextInt();
        Funcionario funcionario = new Funcionario();
        funcionario.setId(id);
        funcionario.setNome(nome);
        funcionario.setSalario(salario);
        funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
        Optional<Cargo> cargo = cargoRepository.findById(cargoId);
        funcionario.setCargo(cargo.get());
        funcionarioRepository.save(funcionario);
        System.out.println("Atualizado");
    }

    private void visualizar(Scanner scanner){
        System.out.println("Qual página você deseja visualizar");
        Integer page = scanner.nextInt();

        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.ASC, "salario"));

        Page<Funcionario> funcionarios = funcionarioRepository.findAll(pageable);
        System.out.println(funcionarios);
        System.out.println("Página atual " + funcionarios.getNumber());
        System.out.println("Total elemento " + funcionarios.getTotalElements());
        funcionarios.forEach(funcionario -> System.out.println(funcionario));
    }
    private void deletar(Scanner scanner){
        System.out.println("Id da unidade de trabalho");
        int id = scanner.nextInt();
        funcionarioRepository.deleteById(id);
        System.out.println("Deletado");
    }
    private List<UnidadeTrabalho> unidade(Scanner scanner) {
        Boolean isTrue = true;
        List<UnidadeTrabalho> unidades = new ArrayList<>();

        while (isTrue) {
            System.out.println("Digite o Id da Unidade (Para sair digite 0)");
            Integer unidadeId = scanner.nextInt();

            if (unidadeId != 0) {
                Optional<UnidadeTrabalho> unidade = unidadeTrabalhoRepository.findById(unidadeId);
                unidades.add(unidade.get());
            } else {
                isTrue = false;
            }
        }
        return unidades;
    }
}
