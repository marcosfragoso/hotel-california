package br.edu.ufcg.p2lp2.hotelcalifornia;

import br.edu.ufcg.p2lp2.hotelcalifornia.exception.HotelCaliforniaException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class US07Test {
    private HotelCaliforniaSistema hotel;

    @BeforeEach
    void setup() {
    	this.hotel = new HotelCaliforniaSistema();
        hotel.cadastrarUsuario("ADM1", "Arabio Saudito", "CLI", 123456);
        hotel.cadastrarUsuario("ADM1", "Claudio Arraio", "FUN", 55555);
        hotel.cadastrarUsuario("ADM1", "Caldo de Cano", "GER", 44444);
        hotel.disponibilizarFormaDePagamento("ADM1", "DINHEIRO", 10);
        hotel.disponibilizarFormaDePagamento("ADM1", "PIX", 20);
        hotel.disponibilizarRefeicao("GER1","CAFE_DA_MANHA","Cafe completo", LocalTime.of(6,30), LocalTime.of(9,30),500, true);
    }
    @Test
    public void admDisponibilizaFormaPagamento(){
        String result = hotel.disponibilizarFormaDePagamento("ADM1", "CARTAO DE CREDITO", 10);
        String resultadoEsperado = "[3] Forma de pagamento: CARTAO DE CREDITO (10% de desconto em pagamentos)";
        Assertions.assertEquals(resultadoEsperado, result);
    }

    @Test
    public void gerNaoDisponibilizaFormaPagamento(){
        try {
            hotel.disponibilizarFormaDePagamento("GER1", "CARTAO DE CREDITO", 10);
            fail("Permissão negada.");
        } catch (HotelCaliforniaException hce) {

        }
    }

    @Test
    public void cliNaoDisponibilizaFormaPagamento(){
        try {
            hotel.disponibilizarFormaDePagamento("CLI1", "CARTAO DE CREDITO", 10);
            fail("Permissão negada.");
        } catch (HotelCaliforniaException hce) {

        }
    }

    @Test
    public void funNaoDisponibilizaFormaPagamento(){
        try {
            hotel.disponibilizarFormaDePagamento("FUN1", "CARTAO DE CREDITO", 10);
            fail("Permissão negada.");
        } catch (HotelCaliforniaException hce) {

        }

    }
    @Test
    public void admAlteraFormaPagamento(){
        String result = hotel.alterarFormaDePagamento("ADM1", 1, "PIX",50);
        String resultadoEsperado = "[1] Forma de pagamento: PIX (50% de desconto em pagamentos)";
        Assertions.assertEquals(resultadoEsperado, result);
    }

    @Test
    public void cliNaoAlteraFormaPagamento(){
        HotelCaliforniaException e = assertThrows(HotelCaliforniaException.class, () -> hotel.alterarFormaDePagamento("CLI1", 1, "PIX",50));
        assertTrue(e.getMessage().contains("NAO E POSSIVEL PARA USUARIO CLIENTE ALTERAR UMA FORMA DE PAGAMENTO"));
    }

    @Test
    public void funNaoAlteraFormaPagamento(){
        HotelCaliforniaException e = assertThrows(HotelCaliforniaException.class, () -> hotel.alterarFormaDePagamento("FUN1", 1, "PIX",50));
        assertTrue(e.getMessage().contains("NAO E POSSIVEL PARA USUARIO FUNCIONARIO ALTERAR UMA FORMA DE PAGAMENTO"));
    }

    @Test
    public void gerNaoAlteraFormaPagamento(){
        HotelCaliforniaException e = assertThrows(HotelCaliforniaException.class, () -> hotel.alterarFormaDePagamento("GER1", 1, "PIX",50));
        assertTrue(e.getMessage().contains("NAO E POSSIVEL PARA USUARIO GERENTE ALTERAR UMA FORMA DE PAGAMENTO"));
    }

    @Test
    public void exibirFormaPagamento() {
        String resultadoEsperado = "[1] Forma de pagamento: DINHEIRO (10% de desconto em pagamentos)";
        String result = hotel.exibirFormaPagamento(1);
        Assertions.assertEquals(resultadoEsperado, result);
    }

    @Test
    public void listarFormasDePagamento() {
        String[] result = hotel.listarFormasPagamentos();
        String[] resultadoEsperado = {"[1] Forma de pagamento: DINHEIRO (10% de desconto em pagamentos)", "[2] Forma de pagamento: PIX (20% de desconto em pagamentos)"};
        Assertions.assertArrayEquals(resultadoEsperado, result);
    }



}