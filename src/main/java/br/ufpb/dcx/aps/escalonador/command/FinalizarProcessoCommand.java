package br.ufpb.dcx.aps.escalonador.command;

import br.ufpb.dcx.aps.escalonador.Escalonador;

public class FinalizarProcessoCommand implements Command {

    private Escalonador escalonador;
    String nomeProcesso;

    public FinalizarProcessoCommand(String nomeProcesso) {
        this.nomeProcesso = nomeProcesso;
    }

    @Override
    public void setEscalonador(Escalonador escalonador) {
        this.escalonador = escalonador;
    }

    @Override
    public void execute() {
        this.escalonador.finalizarProcesso(this.nomeProcesso);
    }
}
