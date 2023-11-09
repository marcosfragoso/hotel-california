package br.edu.ufcg.p2lp2.hotelcalifornia.entities.areaComum;

import java.time.LocalTime;

public class Auditorio extends AreaComum {
    public Auditorio(long id, String titulo, String tipoAreaComum, LocalTime horaInicio, LocalTime horaFim, double valorPessoa, boolean disponibilidade, int quantidadePessoas) {
        super(id, titulo, tipoAreaComum, horaInicio, horaFim, valorPessoa, disponibilidade, quantidadePessoas);
    }
}
