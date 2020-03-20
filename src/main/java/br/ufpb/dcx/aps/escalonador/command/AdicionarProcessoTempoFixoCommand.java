package br.ufpb.dcx.aps.escalonador.command;

import br.ufpb.dcx.aps.escalonador.Escalonador;

public class AdicionarProcessoTempoFixoCommand implements Command {

    private Escalonador escalonador;
    private String nomeProcesso;
    private int duracao;

    public AdicionarProcessoTempoFixoCommand(String nomeProcesso, int duracao) {
        this.nomeProcesso = nomeProcesso;
        this.duracao = duracao;
    }

    @Override
    public void setEscalonador(Escalonador escalonador) {
        this.escalonador = escalonador;
    }

    @Override
    public void execute() {
        this.escalonador.adicionarProcessoTempoFixo(this.nomeProcesso, this.duracao);
    }
}
