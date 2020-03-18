package br.ufpb.dcx.aps.escalonador;

public class FachadaEscalonador {

    private Escalonador escalonador;


	public FachadaEscalonador(TipoEscalonador tipoEscalonador) {
		if(tipoEscalonador == null){
			throw new EscalonadorException();
		}
		else if(tipoEscalonador.equals(TipoEscalonador.RoundRobin)){
			this.setTipoEscalonadorRoundRobin();
		} else if(tipoEscalonador.equals(TipoEscalonador.Prioridade)){
			this.setTipoEscalonadorPrioridade();
		} else if(tipoEscalonador.equals(TipoEscalonador.MaisCurtoPrimeiro)){
			this.setTipoEscalonadorMaisCurtoPrimeiro();
		}

	}

	public FachadaEscalonador(TipoEscalonador tipoEscalonador, int quantum) {
		if(quantum <= 0){
			throw  new EscalonadorException();
		}else if(tipoEscalonador.equals(TipoEscalonador.RoundRobin)){
			this.setTipoEscalonadorRoundRobin(quantum);
		}else if(tipoEscalonador.equals(TipoEscalonador.Prioridade)){
			this.setTipoEscalonadorPrioridade(quantum);
		} else if(tipoEscalonador.equals(TipoEscalonador.MaisCurtoPrimeiro)){
			this.setTipoEscalonadorMaisCurtoPrimeiro(quantum);
		}
	}


	void setTipoEscalonadorRoundRobin(){ this.escalonador = new RoundRobin(); }


	void setTipoEscalonadorRoundRobin(int quantum){
		this.escalonador = new RoundRobin(quantum);
	}

	void setTipoEscalonadorPrioridade(){
		this.escalonador = new Prioridade();
	}

	void setTipoEscalonadorPrioridade(int quantum){
		this.escalonador = new Prioridade(quantum);
	}

	void setTipoEscalonadorMaisCurtoPrimeiro(){
		this.escalonador = new MaisCurtoPrimeiro();
	}

	void setTipoEscalonadorMaisCurtoPrimeiro(int quantum){
		this.escalonador = new MaisCurtoPrimeiro(quantum);
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

	void adicionarProcessoTempoFixo(String nomeProcesso, int a){
		this.escalonador.adicionarProcessoTempoFixo(nomeProcesso, a);
	}

	void tick(){
		this.escalonador.tick();
	}

	String getStatus(){
		return this.escalonador.getStatus();
	}
	
}
