package br.ufpb.dcx.aps.escalonador;

public interface Escalonador {
    String getStatus();
    void tick();
    void incrementaTickProcessoAtual();
    void removeProcessosExpirados();
    void readicionaProcessoBloqueado();
    void removeProcessosBloqueados();
    void adicionarProcesso();
    void finalizarProcesso();
    void bloquearProcesso();
    void retomarProcesso();
    void adicionarProcessoTempoFixo();
}
