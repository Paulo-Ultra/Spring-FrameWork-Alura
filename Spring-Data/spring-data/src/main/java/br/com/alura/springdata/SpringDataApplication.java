package br.com.alura.springdata;

import br.com.alura.springdata.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner {

    private Boolean system = true;
    private final RelatoriosService relatoriosService;
    private final CargoService cargoService;
    private final FuncionarioService funcionarioService;
    private final UnidadeTrabalhoService unidadeTrabalhoService;
    private final RelatorioFuncionarioDinamico relatorioFuncionarioDinamico;

    public SpringDataApplication(CargoService cargoService, FuncionarioService funcionarioService,
                                 UnidadeTrabalhoService unidadeTrabalhoService, RelatoriosService relatoriosService,
                                 RelatorioFuncionarioDinamico relatorioFuncionarioDinamico){
        this.cargoService = cargoService;
        this.funcionarioService = funcionarioService;
        this.unidadeTrabalhoService = unidadeTrabalhoService;
        this.relatoriosService = relatoriosService;
        this.relatorioFuncionarioDinamico = relatorioFuncionarioDinamico;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringDataApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (system) {
            System.out.println("Qual ação você quer executar?");
            System.out.println("0 - Sair");
            System.out.println("1 - Funcionário");
            System.out.println("2 - Cargo");
            System.out.println("3 - Unidade");
            System.out.println("4 - Relatórios");
            System.out.println("5 - Relatório dinâmico");

            int action = scanner.nextInt();
            switch (action){
                case 1:
                    funcionarioService.inicial(scanner);
                    break;
                case 2:
                    cargoService.inicial(scanner);
                    break;
                case 3:
                    unidadeTrabalhoService.inicial(scanner);
                    break;
                case 4:
                    relatoriosService.inicial(scanner);
                    break;
                case 5:
                    relatorioFuncionarioDinamico.inicial(scanner);
                    break;
                default:
                    System.out.println("Finalizando");
                    system = false;
                    break;
            }
        }
    }
}
