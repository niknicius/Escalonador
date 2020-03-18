package br.ufpb.dcx.aps.escalonador;

import java.util.ArrayList;
import java.util.Collections;


public class Prioridade implements Escalonador {

    private int quantum;
    private int tick;
    private Processo rodando;
    private ArrayList<Processo> fila = new ArrayList<Processo>();
    private ArrayList<Processo> bloqueados = new ArrayList<Processo>();
    private ArrayList<Processo> aRetormar = new ArrayList<Processo>();

    public Prioridade(){
        this.quantum = 3;
    }

    public Prioridade(int quantum){
        this.quantum = quantum;
    }

    @Override
    public String getStatus() {
        String result = "Escalonador " + TipoEscalonador.Prioridade + ";Processos: {";
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

        this.removeProcessosExpirados();
        this.removeProcessosBloqueados();

        if(this.rodando == null && this.fila.size() > 0){
            this.rodando = this.fila.remove(0);
        }else if(this.rodando != null && this.rodando.getTickFinal() != 0 && this.rodando.getTickFinal() < (this.tick) && this.fila.size() > 0){
            this.rodando = this.fila.remove(0);
        }else if(this.rodando != null && this.rodando.getTickFinal() != 0 && this.rodando.getTickFinal() < (this.tick)){
            this.rodando = null;
        }else if(this.rodando != null && this.fila.size() > 0 && this.rodando.getPrioridade() < this.fila.get(0).getPrioridade()){
            this.fila.add(this.rodando);
            this.rodando = this.fila.remove(0);
            System.out.println("a");
        }
        else if(this.rodando != null && this.fila.size() > 0 && this.rodando.getTicks()== this.quantum){
            this.fila.add(this.rodando);
            this.rodando.setTicks(0);
            this.rodando = this.fila.remove(0);
        }

        this.readicionaProcessoBloqueado();
        this.incrementaTickProcessoAtual();

    }

    @Override
    public void incrementaTickProcessoAtual(){
        if(this.rodando !=null && this.fila.size()>0) {
            this.rodando.setTicks(this.rodando.getTicks() + 1);
        }
    }

    @Override
    public void removeProcessosExpirados(){
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

    @Override
    public void readicionaProcessoBloqueado(){
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

    @Override
    public void removeProcessosBloqueados(){
        if(this.rodando != null && this.rodando.isBloqueado()){
            this.bloqueados.add(this.rodando);
            if(this.fila.size() > 0){
                this.rodando = this.fila.remove(0);
            }else{
                this.rodando = null;
            }

        }

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
            if(checarMaiorPriordadeFila(p.getPrioridade())){
                this.fila.add(p);
            }else{
                this.fila.add(0, p);
            }

        }
    }

    private boolean checarMaiorPriordadeFila(int prioridade){
        boolean existe = false;

        for(Processo p : this.fila){
            if(p.getPrioridade() < prioridade){
                existe = true;
                break;
            }
        }
        return existe;
    }


    @Override
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

    @Override
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

    @Override
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
