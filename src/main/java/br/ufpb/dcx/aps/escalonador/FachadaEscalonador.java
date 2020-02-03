package br.ufpb.dcx.aps.escalonador;

import java.util.ArrayList;

public class FachadaEscalonador {

    private TipoEscalonador tipoEscalonador;
    private int quantum;
	private int tick;
	private int prioridade;
	private Processo rodando;
	private ArrayList<Processo> fila = new ArrayList<>();
	private ArrayList<Processo> bloqueados = new ArrayList<>();
    private ArrayList<Processo> aRetormar = new ArrayList<>();


	public FachadaEscalonador(TipoEscalonador tipoEscalonador) {
		if(tipoEscalonador == null){
			throw new EscalonadorException();
		}else{
			this.tipoEscalonador = tipoEscalonador;
		}
        this.tick = 0;
        this.quantum = 3;
	}

	public FachadaEscalonador(TipoEscalonador roundrobin, int quantum) {
	    this.tipoEscalonador = roundrobin;
	    if(quantum <= 0){
	    	throw  new EscalonadorException();
		}else{
			this.quantum = quantum;
		}
	}
	
	public String getStatus() {
		String result = "Escalonador " + this.tipoEscalonador + ";Processos: {";
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

	public void tick() {
		tick++;

		this.removeProcessosExpirados();
		this.removeProcessosBloqueados();

		if(this.rodando == null && this.fila.size() > 0){
            this.rodando = this.fila.remove(0);
		}else if(this.rodando != null && this.rodando.getTickFinal() != 0 && this.rodando.getTickFinal() < (this.tick) && this.fila.size() > 0){
			this.rodando = this.fila.remove(0);
		}else if(this.rodando != null && this.rodando.getTickFinal() != 0 && this.rodando.getTickFinal() < (this.tick)){
			this.rodando = null;
		}else if(this.rodando != null && this.fila.size() > 0 && this.rodando.getTicks()== this.quantum){
		    this.fila.add(this.rodando);
		    this.rodando.setTicks(0);
		    this.rodando = this.fila.remove(0);
        }

		this.readicionaProcessoBloqueado();
		this.incrementaTickProcessoAtual();

	}

	private void incrementaTickProcessoAtual(){
        if(this.rodando !=null && this.fila.size()>0) {
            this.rodando.setTicks(this.rodando.getTicks() + 1);
        }
    }

	private void removeProcessosExpirados(){
		ArrayList<Integer> keys = new ArrayList<>();

		for(Processo p : this.fila){
			if(p.getTickFinal() > 0){
				keys.add(this.fila.indexOf(p));
			}
		}
		for(Integer i : keys){
			this.fila.remove((int) i);
		}
	}

	private void readicionaProcessoBloqueado(){
		ArrayList<Integer> keysA = new ArrayList<>();
        ArrayList<Integer> keysB = new ArrayList<>();

		for(Processo p : this.aRetormar){
			for(Processo b : this.bloqueados){
			    if(p.getName().equalsIgnoreCase(b.getName())){
			        keysA.add(this.bloqueados.indexOf(b));
			        keysB.add(this.aRetormar.indexOf(p));
                }
            }
		}
		for(Integer i : keysA){
		    if(this.rodando == null){
                this.rodando = this.bloqueados.remove((int) i);
            }else {
                this.fila.add(this.bloqueados.remove((int) i));
            }
		}

        for(Integer i : keysB){
            this.aRetormar.remove(i);
        }
	}

	private void removeProcessosBloqueados(){
		if(this.rodando != null && this.rodando.isBloqueado()){
			this.bloqueados.add(this.rodando);
			if(this.fila.size() > 0){
				this.rodando = this.fila.remove(0);
			}else{
				this.rodando = null;
			}

		}

	}

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

	public void adicionarProcesso(String nomeProcesso, int prioridade) {
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
			Processo p = new Processo(nomeProcesso, this.tick, prioridade);
			this.fila.add(p);
		}
	}

	public void finalizarProcesso(String nomeProcesso) {
		boolean existe = false;
		if(this.rodando != null && this.rodando.getName().equalsIgnoreCase(nomeProcesso)){
			existe = true;
			this.rodando.setTickFinal(this.tick);
		}else {
			for (Processo p : this.fila) {
				if (p.getName().equalsIgnoreCase(nomeProcesso)) {
					existe = true;
					p.setTickFinal(this.tick);
					break;
				}
			}

			for (Processo p : this.bloqueados) {
				if (p.getName().equalsIgnoreCase(nomeProcesso)) {
					existe = true;
					p.setTickFinal(this.tick);
					break;
				}
			}
		}

		if(!existe){
			throw new EscalonadorException();
		}
	}

	public void bloquearProcesso(String nomeProcesso) {
		if(this.rodando == null || !this.rodando.getName().equalsIgnoreCase(nomeProcesso)){
			throw new EscalonadorException();
		}
		else if(this.rodando != null && this.rodando.getName().equalsIgnoreCase(nomeProcesso)){
			this.rodando.setBloqueado(true);
		}else {
			throw new EscalonadorException();
		}
	}

	public void retomarProcesso(String nomeProcesso) {
		boolean existe = false;
		for(Processo p : this.bloqueados){
			if(p.getName().equalsIgnoreCase(nomeProcesso)){
				p.setBloqueado(false);
				this.aRetormar.add(p);
				existe = true;
				break;
			}
		}

		if(!existe){
			throw new EscalonadorException();
		}
	}

	public void adicionarProcessoTempoFixo(String string, int duracao) {

	}
}
