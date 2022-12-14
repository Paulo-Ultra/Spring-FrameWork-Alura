package br.com.alura.springdata.service;

import br.com.alura.springdata.orm.Cargo;
import br.com.alura.springdata.repository.CargoRepository;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class CargoService {

    private Boolean system = true;
    private final CargoRepository cargorepository;

    public CargoService(CargoRepository cargorepository) {
        this.cargorepository = cargorepository;
    }

    public void inicial(Scanner scanner){
        while (system){
            System.out.println("Qual ação de cargo deseja executar");
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
        System.out.println("Descrição do cargo");
        String descricao = scanner.next();
        Cargo cargo = new Cargo();
        cargo.setDescricao(descricao);
        cargorepository.save(cargo);
        System.out.println("Salvo");
    }

    private void atualizar(Scanner scanner){
        System.out.println("Id do cargo");
        int id = scanner.nextInt();
        System.out.println("Nova descrição do cargo");
        String descricao = scanner.next();
        Cargo cargo = new Cargo();
        cargo.setId(id);
        cargo.setDescricao(descricao);
        cargorepository.save(cargo);
        System.out.println("Atualizado");
    }

    private void visualizar(){
        Iterable<Cargo> cargos = cargorepository.findAll();
        cargos.forEach(cargo -> System.out.println(cargo));
    }
    private void deletar(Scanner scanner){
        System.out.println("Id do cargo");
        int id = scanner.nextInt();
        cargorepository.deleteById(id);
        System.out.println("Deletado");
    }
}
