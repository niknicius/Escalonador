package br.ufpb.dcx.aps.escalonador;

public class MenorPrimeiro implements Escalonador {

    @Override
    public String getStatus() {
        String result = "Escalonador " + TipoEscalonador.MaisCurtoPrimeiro + ";Processos: {";
        if(this.rodando != null){
            result += "Rodando: " + this.rodando.toString();
        }
        if(fila.size() > 0 && this.rodando == null){
            result += "Fila: " + this.fila.toString();
        }else if(fila.size() > 0){
            result += ", Fila: " + this.fila.toString();
        }
        if(bloqueados.size() > 0 && this.rodando == null){
            result += "Bloqueados: " + this.bloqueados.toString();
        }else if(bloqueados.size() > 0){
            result += ", Bloqueados: " + this.bloqueados.toString();
        }
        result += "};Quantum: " + this.quantum + ";Tick: " + this.tick;

        return result;
    }

    @Override
    public void tick() {

    }

    @Override
    public void incrementaTickProcessoAtual() {

    }

    @Override
    public void removeProcessosExpirados() {

    }

    @Override
    public void readicionaProcessoBloqueado() {

    }

    @Override
    public void removeProcessosBloqueados() {

    }

    @Override
    public void adicionarProcesso(String nomeProcesso) {

    }

    @Override
    public void adicionarProcesso(String nomeProcesso, int prioridade) {

    }

    @Override
    public void finalizarProcesso(String nomeProcesso) {

    }

    @Override
    public void bloquearProcesso(String nomeProcesso) {

    }

    @Override
    public void retomarProcesso(String nomeProcesso) {

    }
}
