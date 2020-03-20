package br.ufpb.dcx.aps.escalonador;

import br.ufpb.dcx.aps.escalonador.command.*;

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
		} else if(tipoEscalonador.equals(TipoEscalonador.Fifo)){
			this.setTipoEscalonadorFifo();
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
		} else if(tipoEscalonador.equals(TipoEscalonador.MaisCurtoPrimeiro)){
			this.setTipoEscalonadorFifo(quantum);
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

	void setTipoEscalonadorFifo() { this.escalonador = new Fifo(); }

	void setTipoEscalonadorFifo(int quantum) { this.escalonador = new Fifo(quantum); }


	void runCommand(Command command){
		command.setEscalonador(this.escalonador);
		command.execute();
	}

	String runCommandWithStringReturn(Command command){
		command.setEscalonador(this.escalonador);
		return command.execute(true);
	}

	void adicionarProcesso(String nomeProcesso){
		this.runCommand(new AdicionarProcessoCommand(nomeProcesso));
	}

	void adicionarProcesso(String nomeProcesso, int prioridade){
		this.runCommand(new AdicionarProcessoCommand(nomeProcesso, prioridade));
	}

	void finalizarProcesso(String nomeProcesso){
		this.runCommand(new FinalizarProcessoCommand(nomeProcesso));
	}

	void bloquearProcesso(String nomeProcesso){
		this.runCommand(new BloquearProcessoCommand(nomeProcesso));
	}

	void retomarProcesso(String nomeProcesso){
		this.runCommand(new RetomarProcessoCommand(nomeProcesso));
	}

	void adicionarProcessoTempoFixo(String nomeProcesso, int duracao){
		this.runCommand(new AdicionarProcessoTempoFixoCommand(nomeProcesso, duracao));
	}

	void tick(){
		this.runCommand(new TickCommand());
	}

	String getStatus(){
		Command getStatus = new GetStatusCommand();
		return this.runCommandWithStringReturn(getStatus);
	}
	
}
