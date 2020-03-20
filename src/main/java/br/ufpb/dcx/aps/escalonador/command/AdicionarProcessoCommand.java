package br.ufpb.dcx.aps.escalonador.command;

import br.ufpb.dcx.aps.escalonador.Escalonador;
import br.ufpb.dcx.aps.escalonador.Processo;

public class AdicionarProcessoCommand implements Command {

    private Escalonador escalonador;
    private String nome;
    private Integer prioridade;

    public AdicionarProcessoCommand(String nome) {
        this.nome = nome;
    }

    public AdicionarProcessoCommand(String nome, int prioridade) {
        this.nome = nome;
        this.prioridade = prioridade;
    }

    @Override
    public void setEscalonador(Escalonador escalonador) {
        this.escalonador = escalonador;
    }

    @Override
    public void execute() {
        if(this.prioridade == null) {
            this.escalonador.adicionarProcesso(this.nome);
        } else{
            this.escalonador.adicionarProcesso(this.nome, this.prioridade);
        }
    }
}
