package br.ufpb.dcx.aps.escalonador;

public interface Escalonador {
    String getStatus();
    void tick();
    void incrementaTickProcessoAtual();
    void removeProcessosExpirados();
    void readicionaProcessoBloqueado();
    void removeProcessosBloqueados();
    void adicionarProcesso(String nomeProcesso);
    void finalizarProcesso(String nomeProcesso);
    void bloquearProcesso(String nomeProcesso);
    void retomarProcesso(String nomeProcesso);
}
