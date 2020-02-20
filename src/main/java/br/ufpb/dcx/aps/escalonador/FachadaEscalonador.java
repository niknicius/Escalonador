package br.ufpb.dcx.aps.escalonador;

public class FachadaEscalonador {

    private Escalonador escalonador;


	public FachadaEscalonador(TipoEscalonador tipoEscalonador) {
		if(tipoEscalonador == null){
			throw new EscalonadorException();
		}
		else if(tipoEscalonador.equals(TipoEscalonador.RoundRobin)){
			this.setTipoEscalonadorRoundRobin(3);
		}

	}

	public FachadaEscalonador(TipoEscalonador tipoEscalonador, int quantum) {
		if(quantum <= 0){
			throw  new EscalonadorException();
		}else{
			this.setTipoEscalonadorRoundRobin(quantum);
		}
	}

	void setTipoEscalonadorRoundRobin(){
		this.escalonador = new RoundRobin();
	}

	void setTipoEscalonadorRoundRobin(int quantum){
		this.escalonador = new RoundRobin(quantum);
	}

	void adicionarProcesso(String nomeProcesso){
		this.escalonador.adicionarProcesso(nomeProcesso);
	}

	void adicionarProcesso(String nomeProcesso, int prioridade){
		this.escalonador.adicionarProcesso(nomeProcesso);
	}

	void finalizarProcesso(String nomeProcesso){
		this.escalonador.finalizarProcesso(nomeProcesso);
	}

	void bloquearProcesso(String nomeProcesso){
		this.escalonador.bloquearProcesso(nomeProcesso);
	}

	void retomarProcesso(String nomeProcesso){
		this.escalonador.retomarProcesso(nomeProcesso);
	}

	void adicionarProcessoTempoFixo(String nomeProcesso, int a){}

	void tick(){
		this.escalonador.tick();
	}

	String getStatus(){
		return this.escalonador.getStatus();
	}

	

}
