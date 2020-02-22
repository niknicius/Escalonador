package br.ufpb.dcx.aps.escalonador;

public class Processo implements Comparable<Processo> {

    private String name;
    private int tickInicial;
    private int tickFinal;
    private int ticks;
    private int duracao;
    private int prioridade;
    private boolean bloqueado;



    public Processo(String name, int tickInicial) {
        this.name = name;
        this.tickInicial = tickInicial;
    }

    public Processo(String name, int tickInicial, int prioridade) {
        this.name = name;
        this.tickInicial = tickInicial;
        this.prioridade = prioridade;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTickInicial() {
        return tickInicial;
    }

    public void setTickInicial(int tickInicial) {
        this.tickInicial = tickInicial;
    }

    public int getTickFinal() {
        return tickFinal;
    }

    public void setTickFinal(int tickFinal) {
        this.tickFinal = tickFinal;
    }

    @Override
    public String toString(){
        return this.name;
    }

	public int getTicks() {
		return ticks;
	}

	public void setTicks(int ticks) { this.ticks = ticks; }


    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    @Override
    public int compareTo(Processo o) {
        if(this.getPrioridade() > o.getPrioridade()){
            return 1;
        }else if(this.getPrioridade() < o.getPrioridade()){
            return -1;
        }

        if(this.duracao > o.getDuracao()){
            return 1;
        }else if(this.duracao < o.getDuracao()){
            return -1;
        }

        return 0;
    }
}
