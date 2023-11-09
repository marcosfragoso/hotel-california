package br.edu.ufcg.p2lp2.hotelcalifornia;

import br.edu.ufcg.p2lp2.hotelcalifornia.exception.HotelCaliforniaException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.fail;

public class US04Test {

    private HotelCaliforniaSistema hotel;

    @BeforeEach
    void setup() {
    	this.hotel = new HotelCaliforniaSistema();
        hotel.cadastrarUsuario("ADM1", "Arabio Saudito", "CLI", 123456);
        hotel.cadastrarUsuario("ADM1", "Claudio Arraio", "FUN", 55555);
        hotel.cadastrarUsuario("ADM1", "Caldo de Cano", "GER", 44444);
        hotel.disponibilizarRefeicao("GER1","CAFE_DA_MANHA","Cafe completo", LocalTime.of(6,30),LocalTime.of(9,30),500, true);
    }

    @Test
    public void funDisponibilizaRefeicaoComSucesso(){
        LocalTime horaInicio = LocalTime.of(10,30);
        LocalTime horaFim = LocalTime.of(11,30);
        String resultadoEsperado = "[2] Almoco: almocin (10h30 as 11h30). Valor por pessoa: R$300,00. VIGENTE.";
        String result = hotel.disponibilizarRefeicao("FUN1","ALMOCO","almocin", horaInicio, horaFim,300, true);
        Assertions.assertEquals(resultadoEsperado, result);
    }

    @Test
    public void gerDisponibilizaRefeicaoComSucesso(){
        String resultadoEsperado = "[2] Almoco: almocin (07h30 as 09h30). Valor por pessoa: R$300,00. VIGENTE.";
        String result = hotel.disponibilizarRefeicao("GER1","ALMOCO","almocin", LocalTime.of(7,30),LocalTime.of(9,30),300, true);
        Assertions.assertEquals(resultadoEsperado, result);
    }

    @Test
    public void cliNaoDisponibilizaRefeicao(){
        try {
            hotel.disponibilizarRefeicao("CLI1", "Café-da-manhã", "Cafe completo", LocalTime.of(7, 30), LocalTime.of(9, 30), 300, true);
            fail("Permissão negada.");
        } catch (HotelCaliforniaException hce) {

        }
    }

    @Test
    public void admNaoDisponibilizaRefeicao(){
        try {
            hotel.disponibilizarRefeicao("ADM1","Café-da-manhã","Cafe completo", LocalTime.of(7,30),LocalTime.of(9,30),300, true);
            fail("Permissão negada.");
        } catch (HotelCaliforniaException hce) {

        }

    }
    @Test
    public void naoDisponibilizaRefeicaoInvalida(){
        String resultadoEsperado = "Refeição Inválida!";
        String result = hotel.disponibilizarRefeicao("FUN1","Lanche","Cafe completo", LocalTime.of(7,30),LocalTime.of(9,30),300, true);
        Assertions.assertEquals(resultadoEsperado, result);
    }

    @Test
    public void naoDisponibilizaRefeicaoComHorarioInvalido(){
        try {
            hotel.disponibilizarRefeicao("FUN1","Lanche","Cafe completo", LocalTime.of(9,30),LocalTime.of(7,30),300, true);
            fail("Horário final tem que ser após o horário inicial.");
        } catch (HotelCaliforniaException hce) {

        }
    }
    @Test
    public void alteraRefeicao() {
        String resultadoEsperado = "[1] Cafe-da-manha: Cafe completo (10h30 as 12h30). Valor por pessoa: R$700,00. INDISPONIVEL.";
        String result = hotel.alterarRefeicao((long)1,LocalTime.of(10,30), LocalTime.of(12,30), 700.00 ,false);
        Assertions.assertEquals(resultadoEsperado, result);
    }

    @Test
    public void naoAlteraRefeicaoInexistente() {
        try {
            hotel.alterarRefeicao((long)99,LocalTime.of(10,30), LocalTime.of(12,30),700.00,true);
            fail("Refeição não existe.");
        } catch (HotelCaliforniaException hce) {

        }
    }

    @Test
    public void exibirRefeicao(){
        String result = hotel.exibirRefeicao(1);
        String resultadoEsperado = "[1] Cafe-da-manha: Cafe completo (06h30 as 09h30). Valor por pessoa: R$500,00. VIGENTE.";
        Assertions.assertEquals(resultadoEsperado, result);
    }

}