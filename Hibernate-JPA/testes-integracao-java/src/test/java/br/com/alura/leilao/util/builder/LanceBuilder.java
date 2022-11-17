package br.com.alura.leilao.util.builder;

import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LanceBuilder {

    private BigDecimal valor;
    private LocalDate data;
    private Usuario usuario;
    private Leilao leilao;


    public LanceBuilder comValor(String valor) {
        this.valor = new BigDecimal(valor);
        return this;
    }
    public LanceBuilder comData(LocalDate data) {
        this.data = data;
        return this;
    }

    public LanceBuilder comUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }
    public LanceBuilder comLeilao(Leilao Leilao) {
        this.leilao = leilao;
        return this;
    }

    public Lance criar() {
        Lance lance = new Lance(valor, data, usuario, leilao);
        lance.setLeilao(lance);
        return lance;
    }
}
