package br.com.alura.springdata.service;

import br.com.alura.springdata.orm.UnidadeTrabalho;
import br.com.alura.springdata.repository.UnidadeTrabalhoRepository;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class UnidadeTrabalhoService {

    private Boolean system = true;
    private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;

    public UnidadeTrabalhoService(UnidadeTrabalhoRepository unidadeTrabalhoRepository) {
        this.unidadeTrabalhoRepository = unidadeTrabalhoRepository;
    }

    public void inicial(Scanner scanner){
        while (system){
            System.out.println("Qual ação de unidade deseja executar");
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
                    visualizar();
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
        System.out.println("Digite o nome da unidade");
        String descricao = scanner.next();
        System.out.println("Digite o endereço");
        String endereco = scanner.next();
        UnidadeTrabalho unidadeTrabalho = new UnidadeTrabalho();
        unidadeTrabalho.setDescricao(descricao);
        unidadeTrabalho.setEndereco(endereco);
        unidadeTrabalhoRepository.save(unidadeTrabalho);
        System.out.println("Salvo");
    }

    private void atualizar(Scanner scanner){
        System.out.println("Digite o Id");
        int id = scanner.nextInt();
        System.out.println("Digite o nome da unidade");
        String descricao = scanner.next();
        System.out.println("Digite o endereço da unidade");
        String endereco = scanner.next();
        UnidadeTrabalho unidadeTrabalho = new UnidadeTrabalho();
        unidadeTrabalho.setId(id);
        unidadeTrabalho.setDescricao(descricao);
        unidadeTrabalho.setEndereco(descricao);
        unidadeTrabalhoRepository.save(unidadeTrabalho);
        System.out.println("Atualizado");
    }

    private void visualizar(){
        Iterable<UnidadeTrabalho> unidades = unidadeTrabalhoRepository.findAll();
        unidades.forEach(unidadeTrabalho -> System.out.println(unidadeTrabalho));
    }
    private void deletar(Scanner scanner){
        System.out.println("Id da unidade de trabalho");
        int id = scanner.nextInt();
        unidadeTrabalhoRepository.deleteById(id);
        System.out.println("Deletado");
    }
}
