package br.ufpb.dcx.aps.escalonador.command;

import br.ufpb.dcx.aps.escalonador.Escalonador;

public class BloquearProcessoCommand implements Command {

    private Escalonador escalonador;
    private String nomeProcesso;

    public BloquearProcessoCommand(String nomeProcesso) {
        this.nomeProcesso = nomeProcesso;
    }

    @Override
    public void setEscalonador(Escalonador escalonador) {
        this.escalonador = escalonador;
    }

    @Override
    public void execute() {
        this.escalonador.bloquearProcesso(this.nomeProcesso);
    }
}
