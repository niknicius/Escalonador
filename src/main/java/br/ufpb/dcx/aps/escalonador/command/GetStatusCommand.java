package br.ufpb.dcx.aps.escalonador.command;

import br.ufpb.dcx.aps.escalonador.Escalonador;

public class GetStatusCommand implements Command {

    private Escalonador escalonador;

    @Override
    public void setEscalonador(Escalonador escalonador) {
        this.escalonador = escalonador;
    }

    @Override
    public String execute(boolean returnValue) {
        return this.escalonador.getStatus();
    }
}
