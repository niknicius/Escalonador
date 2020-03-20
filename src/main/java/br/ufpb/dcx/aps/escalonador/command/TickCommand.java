package br.ufpb.dcx.aps.escalonador.command;

import br.ufpb.dcx.aps.escalonador.Escalonador;

public class TickCommand implements Command{

    Escalonador escalonador;

    public void setEscalonador(Escalonador escalonador) {
        this.escalonador = escalonador;
    }

    @Override
    public void execute() {
        this.escalonador.tick();
    }
}
