package br.ufpb.dcx.aps.escalonador;

import java.util.ArrayList;

public class Fifo implements Escalonador {

    private int quantum;
    private int tick;
    private Processo rodando;
    private ArrayList<Processo> fila = new ArrayList<>();

    public int getQuantum() { return quantum; }

    public void setQuantum(int quantum) { this.quantum = quantum; }

    public int getTick() {return tick;}

    public void setTick(int tick) {this.tick = tick;}

    public Processo getRodando() {return rodando;}

    public void setRodando(Processo rodando) {this.rodando = rodando;}

    public ArrayList<Processo> getFila() {return fila;}

    public Fifo(int quantum) {setQuantum(quantum);}
    public Fifo() { setQuantum(0); }

    @Override
    public String getStatus() {
        String result = "Escalonador " + TipoEscalonador.Fifo + ";Processos: {";
        if(getRodando() != null){
            result += "Rodando: " + getRodando().toString();
        }
        if(getFila().size() > 0 && getRodando() == null){
            result += "Fila: " + getFila().toString();
        }else if(getFila().size() > 0){
            result += ", Fila: " + getFila().toString();
        }
        result += "};Quantum: " + getQuantum() + ";Tick: " + getTick();

        return result;
    }

    @Override
    public void tick() {
        tick++;
        if(getRodando() == null && getFila().size() > 0){
            setRodando(getFila().remove(0));
        }
        if(getRodando() != null && getRodando().getDuracao() == getRodando().getTicks()){
            setRodando(null);
        }
        if(getRodando() != null){
            getRodando().setTicks(getRodando().getTicks() + 1);
        }
    }

    @Override
    public void incrementaTickProcessoAtual() {}

    @Override
    public void removeProcessosExpirados() {}

    @Override
    public void readicionaProcessoBloqueado() {}

    @Override
    public void removeProcessosBloqueados() {}

    @Override
    public void adicionarProcesso(String nomeProcesso) {
        throw new EscalonadorException();
    }

    @Override
    public void adicionarProcesso(String nomeProcesso, int prioridade) {
        throw new EscalonadorException();
    }

    @Override
    public void finalizarProcesso(String nomeProcesso) {}

    @Override
    public void bloquearProcesso(String nomeProcesso) {}

    @Override
    public void retomarProcesso(String nomeProcesso) {}

    @Override
    public void adicionarProcessoTempoFixo(String nomeProcesso, int duracao) {
        Processo p = new Processo(nomeProcesso, getTick());
        p.setDuracao(duracao);
        getFila().add(p);
    }
}
