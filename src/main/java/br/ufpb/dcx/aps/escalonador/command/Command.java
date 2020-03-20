package br.ufpb.dcx.aps.escalonador.command;

import br.ufpb.dcx.aps.escalonador.Escalonador;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public interface Command {

    void setEscalonador(Escalonador escalonador);

    default void execute() {
        throw new NotImplementedException();
    }

    default String execute(boolean returnValue) {
        throw new NotImplementedException();
    }

}
