package br.ufpb.dcx.aps.escalonador;

import java.util.ArrayList;

public class FachadaEscalonador {

	private TipoEscalonador tipoEscalonador;
	private int quantum;
	private int quantumAtual;
	private int tick;
	private Processo rodando;
	private ArrayList<Processo> fila = new ArrayList<>();
	private ArrayList<Processo> bloqueados = new ArrayList<>();

	public FachadaEscalonador(TipoEscalonador tipoEscalonador) {
		this.tipoEscalonador = tipoEscalonador;
		this.quantumAtual = 0;
		this.tick = 0;
		this.quantum = 3;
	}

	public FachadaEscalonador(TipoEscalonador roundrobin, int quantum) {
		this.tipoEscalonador = roundrobin;
		this.quantumAtual = 0;
		this.quantum = quantum;
		this.tick = 0;
	}

	public String getStatus() {

		String result = "Escalonador " + this.tipoEscalonador + ";Processos: {";

		if(naoNull()){
			result += "Rodando: " + this.rodando.toString();
		}

		if(fila.size() > 0 && this.rodando == null){
			result += "Fila: " + this.fila.toString();
		}else if(fila.size() > 0){
			result += ", Fila: " + this.fila.toString();
		}
		result += "};Quantum: " + this.quantum + ";Tick: " + this.tick;

		return result;
	}

	public void tick() {
		tick++;
		if(naoNull() && this.rodando.getTickFinal() != 0 && this.rodando.getTickFinal() < (this.tick)){
			this.rodando = null;
		}else if(naoNull() && filaNaoVazia() && this.quantumAtual >= this.quantum){
			this.fila.add(this.rodando);
			this.rodando = this.fila.remove(0);
			this.quantumAtual = 1;
		}else if(this.rodando == null && filaNaoVazia()){
			this.rodando = this.fila.remove(0);
			if(this.fila.size() > 1) {
				this.quantumAtual++;
			}else{
				this.quantumAtual = 1;
			}
		} else if(naoNull() && filaNaoVazia()){
			quantumAtual++;
		}
	}

	private boolean filaNaoVazia() {
		return this.fila.size() > 0;
	}

	private boolean naoNull() {
		return this.rodando != null;
	}

	public void adicionarProcesso(String nomeProcesso) {
		Processo p = new Processo(nomeProcesso, this.tick);
		this.fila.add(p);
	}

	public void adicionarProcesso(String nomeProcesso, int prioridade) {
	}

	public void finalizarProcesso(String nomeProcesso) {
		boolean find = false;
		for(Processo p : this.fila){
			if(temNaFila(nomeProcesso, p)){
				p.setTickFinal(this.tick);
				find = true;
				break;
			}
		}

		if(!find){
			this.rodando.setTickFinal(this.tick);
		}
	}

	private boolean temNaFila(String nomeProcesso, Processo p) {
		return p.getName().equalsIgnoreCase(nomeProcesso);
	}

	public void bloquearProcesso(String nomeProcesso) {
		for (Processo p : this.fila) {
			if(temNaFila(nomeProcesso, p)) {
				this.bloqueados.add(p);
				this.fila.remove(this.tick);
			}
		}
	}

	public void retomarProcesso(String nomeProcesso) {

	}

	public void adicionarProcessoTempoFixo(String string, int duracao) {

	}
}
