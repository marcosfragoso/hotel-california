package br.edu.ufcg.p2lp2.hotelcalifornia;

import br.edu.ufcg.p2lp2.hotelcalifornia.exception.HotelCaliforniaException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.fail;

public class US10Test {
    private HotelCaliforniaSistema hotel;
    LocalTime horaInicio;
    LocalTime horaFinal;
    @BeforeEach
     void setup() {
        this.hotel = new HotelCaliforniaSistema();
        this.hotel.cadastrarUsuario("ADM1", "Sabugo", "CLI", 456123);
        this.hotel.cadastrarUsuario("ADM1", "Livio", "CLI", 123456);
        this.hotel.cadastrarUsuario("ADM1", "Janaino", "FUN", 654321);
        this.hotel.cadastrarUsuario("ADM1", "Leito quento", "GER", 333333);
        horaInicio = LocalTime.of(18,0);
        horaFinal = LocalTime.of(21,0);
    }
    @Test
    public void admDisponibilizaAreaComum() {
        String result = hotel.disponibilizarAreaComum("ADM1", "AUDITORIO", "AUDITORIO", horaInicio, horaFinal, 100,true, 5);
        String resultadoEsperado = "[1] AUDITORIO: AUDITORIO (18h00 as 21h00). Valor por pessoa: R$100,00. Capacidade: 5 pessoa(s). VIGENTE.";
        Assertions.assertEquals(resultadoEsperado, result);
    }

    @Test
    public void disponibilizaAreaComumComUsuarioInexistente() {
        try {
            hotel.disponibilizarAreaComum("ADM9", "AUDITORIO", "AUDITORIO", horaInicio, horaFinal, 100, true, 5);
            fail("Usuário não existe");
        } catch (HotelCaliforniaException hce) {

        }
    }

    @Test
    public void disponibilizaAreaComumQueJaExiste() {
        hotel.disponibilizarAreaComum("ADM1", "AUDITORIO", "AUDITORIO", horaInicio, horaFinal, 100,true, 5);
        try {
            hotel.disponibilizarAreaComum("ADM1", "AUDITORIO", "AUDITORIO", horaInicio, horaFinal, 100,true, 5);
            fail("Área comum já existe");
        } catch (HotelCaliforniaException hce) {

        }
    }

    @Test
    public void clienteNaoDisponibilizaAreaComum() {
        try {
            hotel.disponibilizarAreaComum("CLI1", "AUDITORIO", "AUDITORIO", horaInicio, horaFinal, 100, true, 5);
            fail("Cliente não tem permissão.");
        } catch (HotelCaliforniaException hce) {

        }
    }

    @Test
    public void funcionarioNaoDisponibilizaAreaComum() {
        try {
            hotel.disponibilizarAreaComum("FUN1", "AUDITORIO", "AUDITORIO", horaInicio, horaFinal, 100, true, 5);
            fail("Funcionário não tem permissão.");
        } catch (HotelCaliforniaException hce) {

        }
    }

    @Test
    public void gerenteNaoDisponibilizaAreaComum() {
        try {
            hotel.disponibilizarAreaComum("GER1", "AUDITORIO", "AUDITORIO", horaInicio, horaFinal, 100, true, 5);
            fail("Gerente não tem permissão.");
        } catch (HotelCaliforniaException hce) {

        }
    }

    @Test
    public void naoDisponibilizaAreaComumComHorariosInvalidos() {
        try {
            hotel.disponibilizarAreaComum("ADM1", "AUDITORIO", "AUDITORIO", horaFinal, horaInicio, 100, true, 5);
            fail("Horário final tem que ser superior ao de início.");
        } catch (HotelCaliforniaException hce) {

        }
    }

    @Test
    public void tentaDisponibilizarAreaComumIndisponvel() {
        try {
            hotel.disponibilizarAreaComum("ADM1", "Neo Química Arena", "Estádio do maior do mundo", horaInicio, horaFinal, 100, true, 5);
            fail("Área comum indisponível.");
        } catch (HotelCaliforniaException hce) {

        }
    }

    @Test
    public void admAlteraAreaComum(){
        hotel.disponibilizarAreaComum("ADM1", "AUDITORIO", "AUDITORIO", horaInicio, horaFinal, 100,true, 5);
        String result = hotel.alterarAreaComum("ADM1", 1L, horaInicio.plusHours(1), horaFinal.plusHours(1), 500, 4, true);
        String resultadoEsperado = "[1] AUDITORIO: AUDITORIO (19h00 as 22h00). Valor por pessoa: R$500,00. Capacidade: 4 pessoa(s). VIGENTE.";
        Assertions.assertEquals(resultadoEsperado, result);
    }

    @Test
    public void admAlteraAreaComumParaAreaIndisponivel(){
        hotel.disponibilizarAreaComum("ADM1", "AUDITORIO", "AUDITORIO", horaInicio, horaFinal, 100,true, 5);
        try {
            hotel.alterarAreaComum("ADM1", 5L, horaInicio.plusHours(1), horaFinal.plusHours(1), 500, 4, true);
            fail("Área comum indisponível.");
        } catch (HotelCaliforniaException hce) {

        }
    }

    @Test
    public void admAlteraAreaComumParaDataErrada(){
        hotel.disponibilizarAreaComum("ADM1", "AUDITORIO", "AUDITORIO", horaInicio, horaFinal, 100,true, 5);
        try {
            hotel.alterarAreaComum("ADM1", 1L, horaFinal, horaInicio, 500, 4, true);
            fail("Horário final tem que ser posterior ao de início.");
        } catch (HotelCaliforniaException hce) {

        }
    }

    @Test
    public void funNaoAlteraAreaComum(){
        hotel.disponibilizarAreaComum("ADM1", "AUDITORIO", "AUDITORIO", horaInicio, horaFinal, 100,true, 5);
        try {
            hotel.alterarAreaComum("FUN1", 1L, horaInicio.plusHours(1), horaFinal.plusHours(1), 500, 4, true);
            fail("Usuário sem permissão.");
        } catch (HotelCaliforniaException hce) {

        }
    }

    @Test
    public void cliNaoAlteraAreaComum(){
        hotel.disponibilizarAreaComum("ADM1", "AUDITORIO", "AUDITORIO", horaInicio, horaFinal, 100,true, 5);
        try {
            hotel.alterarAreaComum("CLI1", 1L, horaInicio.plusHours(1), horaFinal.plusHours(1), 500, 4, true);
            fail("Usuário sem permissão.");
        } catch (HotelCaliforniaException hce) {

        }
    }

    @Test
    public void gerNaoAlteraAreaComum(){
        hotel.disponibilizarAreaComum("ADM1", "AUDITORIO", "AUDITORIO", horaInicio, horaFinal, 100,true, 5);
        try {
            hotel.alterarAreaComum("GER1", 1L, horaInicio.plusHours(1), horaFinal.plusHours(1), 500, 4, true);
            fail("Usuário sem permissão.");
        } catch (HotelCaliforniaException hce) {

        }
    }

    @Test
    public void exibeToStringAreaComum(){
        hotel.disponibilizarAreaComum("ADM1", "AUDITORIO", "AUDITORIO", horaInicio, horaFinal, 100,true, 5);
        String result = hotel.exibirAreaComum(1);
        String resultadoEsperado = "[1] AUDITORIO: AUDITORIO (18h00 as 21h00). Valor por pessoa: R$100,00. Capacidade: 5 pessoa(s). VIGENTE.";
        Assertions.assertEquals(resultadoEsperado, result);
    }

    @Test
    public void exibeToStringAreaComumInexistente(){
        hotel.disponibilizarAreaComum("ADM1", "AUDITORIO", "AUDITORIO", horaInicio, horaFinal, 100,true, 5);
        try {
            hotel.exibirAreaComum(2);
            fail("Área comum inexistente.");
        } catch (HotelCaliforniaException hce) {

        }
    }

    @Test
    public void listaAreasComuns() {
        hotel.disponibilizarAreaComum("ADM1", "AUDITORIO", "AUDITORIO", horaInicio, horaFinal, 100,true, 5);
        String[] result = hotel.listarAreasComuns();
        String[] resultadoEsperado = {hotel.exibirAreaComum(1)};
        Assertions.assertArrayEquals(resultadoEsperado, result);

    }
}
