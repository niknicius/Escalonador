package br.ufpb.dcx.aps.escalonador;

import java.util.ArrayList;

public class MaisCurtoPrimeiro implements Escalonador {

    private int quantum;
    private int tick;
    private Processo rodando;
    private ArrayList<Processo> fila = new ArrayList<>();
    private ArrayList<Processo> bloqueados = new ArrayList<>();
    private ArrayList<Processo> aRetormar = new ArrayList<>();

    public MaisCurtoPrimeiro() {
        this.quantum = 0;
    }

    public MaisCurtoPrimeiro(int quantum) {
        this.quantum = quantum;
    }

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
        tick++;

        if(this.rodando == null && this.fila.size() > 0){
            this.rodando = this.fila.remove(0);
        }else if(this.rodando != null && this.rodando.getDuracao() == this.rodando.getTicks()){
            this.rodando = null;
        }

        if(this.rodando != null){
            this.rodando.setTicks(this.rodando.getTicks() + 1);
        }
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
        if(nomeProcesso == null){
            throw new EscalonadorException();
        }
        boolean existe = false;
        if(this.rodando != null && this.rodando.getName().equalsIgnoreCase(nomeProcesso)){
            existe = false;
        }else {
            for (Processo p : this.fila) {
                if (p.getName().equalsIgnoreCase(nomeProcesso)) {
                    existe = true;
                    break;
                }
            }

            for (Processo p : this.bloqueados) {
                if (p.getName().equalsIgnoreCase(nomeProcesso)) {
                    existe = true;
                    break;
                }
            }
        }

        if(existe){
            throw new EscalonadorException();
        }else {
            Processo p = new Processo(nomeProcesso, this.tick);
            this.fila.add(p);
        }
    }

    @Override
    public void adicionarProcessoTempoFixo(String nomeProcesso, int duracao){
        if(nomeProcesso == null){
            throw new EscalonadorException();
        }
        boolean existe = false;
        if(this.rodando != null && this.rodando.getName().equalsIgnoreCase(nomeProcesso)){
            existe = false;
        }else {
            for (Processo p : this.fila) {
                if (p.getName().equalsIgnoreCase(nomeProcesso)) {
                    existe = true;
                    break;
                }
            }

            for (Processo p : this.bloqueados) {
                if (p.getName().equalsIgnoreCase(nomeProcesso)) {
                    existe = true;
                    break;
                }
            }
        }

        if(existe){
            throw new EscalonadorException();
        }else {
            Processo p = new Processo(nomeProcesso, this.tick);
            p.setDuracao(duracao);
            this.fila.add(p);
        }
    }

    @Override
    public void adicionarProcesso(String nomeProcesso, int quantum) {
        throw new EscalonadorException();
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
