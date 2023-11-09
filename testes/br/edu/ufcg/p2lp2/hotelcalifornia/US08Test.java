package br.edu.ufcg.p2lp2.hotelcalifornia;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.edu.ufcg.p2lp2.hotelcalifornia.exception.HotelCaliforniaException;

public class US08Test {

	private HotelCaliforniaSistema hotel;

	@BeforeEach
	void preparaPagamentos(){
		this.hotel = new HotelCaliforniaSistema();
		this.hotel.cadastrarUsuario("ADM1", "Sabugo", "CLI", 456123);
		this.hotel.cadastrarUsuario("ADM1", "Livio", "CLI", 123456);
        this.hotel.cadastrarUsuario("ADM1", "Janaino", "FUN", 654321);
        this.hotel.cadastrarUsuario("ADM1", "Leito quento", "GER", 333333);
        this.hotel.disponibilizarRefeicao("GER1", "ALMOCO", "refeição mais importante do dia", LocalTime.of(12,30), LocalTime.of(15,30), 54, true);
        this.hotel.disponibilizarQuartoSingle("ADM1", 45, 10, 10);
        this.hotel.disponibilizarQuartoSingle("ADM1", 5, 20, 20);
        String[] refeicoes = {"1"};
        this.hotel.reservarQuartoSingle("GER1", "CLI1", 45, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00), refeicoes);
        this.hotel.reservarQuartoSingle("GER1", "CLI2", 5, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00), refeicoes);
        this.hotel.disponibilizarFormaDePagamento("ADM1", "DINHEIRO", 10);
        this.hotel.disponibilizarFormaDePagamento("ADM1", "PIX", 5);
        this.hotel.disponibilizarFormaDePagamento("ADM1", "CARTAO DE CREDITO", 0);
	}

	@Test
	void testGerSemPermissaoPagarReservaComDinheiro() {
		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.pagarReservaComDinheiro("GER1", 1, "Sabugo"));
		assertTrue(hte.getMessage().contains("O USUARIO NAO E CLIENTE"));
	}

	@Test
	void testAdmSemPermissaoPagarReservaComDinheiro() {
		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.pagarReservaComDinheiro("ADM1", 1, "Sabugo"));
		assertTrue(hte.getMessage().contains("O USUARIO NAO E CLIENTE"));
	}

	@Test
	void testFunSemPermissaoPagarReservaComDinheiro() {
		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.pagarReservaComDinheiro("FUN1", 1, "Sabugo"));
		assertTrue(hte.getMessage().contains("O USUARIO NAO E CLIENTE"));
	}

	@Test
	void testOutraPessoaTentaPagarReservaComDinheiro() {
		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.pagarReservaComDinheiro("CLI1", 1, "Casos Bahio"));
		assertTrue(hte.getMessage().contains("SOMENTE O PROPRIO CLIENTE PODERA PAGAR A SUA RESERVA"));
	}
	
	@Test
	void testPagarReservaComDinheiroNovamente() {
		this.hotel.pagarReservaComDinheiro("CLI1", 1, "Sabugo");
		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.pagarReservaComDinheiro("CLI1", 1, "Sabugo"));
		assertTrue(hte.getMessage().contains("RESERVA JA FOI PAGA"));
	}

	@Test
	void testPagarReservaComDinheiro() {
		String teste = this.hotel.pagarReservaComDinheiro("CLI1", 1, "Sabugo");
		assertEquals("[1] Reserva de quarto em favor de:\n"
				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
				+ "Detalhes da instalacao:\n"
				+ "- [45] Quarto Single (custo basico: R$10,00; por pessoa: R$10,00 >>> R$20,00 diária)\n"
				+ "Detalhes da reserva:\n"
				+ "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n"
				+ "- No. Hospedes: 01 pessoa(s)\n"
				+ "- Refeicoes incluidas: [[1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.]\n"
				+ "- Pedidos: null\n"
				+ "VALOR TOTAL DA RESERVA: R$74,00 x2 (diarias) => R$148,00\n"
				+ "SITUACAO DO PAGAMENTO: REALIZADO.\n"
				+ "[1] Forma de pagamento: DINHEIRO (10% de desconto em pagamentos).\n"
				+ "Total Efetivamente Pago: R$133,20 em 1x de R$133,20.", teste);
	}

	@Test
	void testGerSemPermissaoPagarReservaComCartao() {
		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.pagarReservaComCartao("GER1", 1, "Sabugo", "1234567891233456", "06/2023", "234", 10));
		assertTrue(hte.getMessage().contains("O USUARIO NAO E CLIENTE"));
	}
	@Test
	void testAdmSemPermissaoPagarReservaComCartao() {
		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.pagarReservaComCartao("ADM1", 1, "Sabugo", "1234567891233456", "06/2023", "234", 10));
		assertTrue(hte.getMessage().contains("O USUARIO NAO E CLIENTE"));
	}

	@Test
	void testFunSemPermissaoPagarReservaComCartao() {
		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () ->  this.hotel.pagarReservaComCartao("FUN1", 1, "Sabugo", "1234567891233456", "06/2023", "234", 10));
		assertTrue(hte.getMessage().contains("O USUARIO NAO E CLIENTE"));
	}

	@Test
	void testNumeroInvalidoCartao() {
		HotelCaliforniaException e = assertThrows(HotelCaliforniaException.class, () -> this.hotel.pagarReservaComCartao("CLI1", 1, "Sabugo", "1234567891234", "06/2023", "234", 6));
        assertTrue(e.getMessage().contains("NUMERO DE CARTAO INVALIDO"));
	}

	@Test
	void testNumeroInvalidoParcelasCartao() {
		HotelCaliforniaException e = assertThrows(HotelCaliforniaException.class, () -> this.hotel.pagarReservaComCartao("CLI1", 1, "Sabugo", "1234567891234567", "06/2023", "234", 13));
        assertTrue(e.getMessage().contains("QUANTIDADE DE PARCELAS NAO PERMITIDA"));
	}

	@Test
	void testNumeroInvalidoCodigoCartao() {
		HotelCaliforniaException e = assertThrows(HotelCaliforniaException.class, () -> this.hotel.pagarReservaComCartao("CLI1", 1, "Sabugo", "1234567891234567", "06/2023", "2345", 5));
        assertTrue(e.getMessage().contains("CVC INVALIDO"));
	}

	@Test
	void testMesAnoInvalidoCartao() {
		HotelCaliforniaException e = assertThrows(HotelCaliforniaException.class, () -> this.hotel.pagarReservaComCartao("CLI1", 1, "Sabugo", "1234567891234567", "6/23", "234", 5));
		assertTrue(e.getMessage().contains("MES E ANO DE VALIDADE INVALIDOS"));
	}

	@Test
	void testOutraPessoaTentaPagarReservaComCartao() {
		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.pagarReservaComCartao("CLI2", 1, "Sabugo", "1234567891234567", "06/2023", "234", 10));
		assertTrue(hte.getMessage().contains("SOMENTE O PROPRIO CLIENTE PODERA PAGAR A SUA RESERVA"));
		}

	@Test
	void testPagarReservaComCartaoNovamente() {
		this.hotel.pagarReservaComCartao("CLI1", 1, "Sabugo", "1234567891234567", "06/2023", "234", 10);
		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.pagarReservaComCartao("CLI1", 1, "Sabugo", "1234567891234567", "06/2023", "234", 10));
		assertTrue(hte.getMessage().contains("RESERVA JA FOI PAGA"));
	}

	@Test
	void testPagarReservaComCartao() {
		String teste = this.hotel.pagarReservaComCartao("CLI1", 1, "Sabugo", "1234567891234567", "06/2023", "234", 10);
		assertEquals("[1] Reserva de quarto em favor de:\n"
				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
				+ "Detalhes da instalacao:\n"
				+ "- [45] Quarto Single (custo basico: R$10,00; por pessoa: R$10,00 >>> R$20,00 diária)\n"
				+ "Detalhes da reserva:\n"
				+ "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n"
				+ "- No. Hospedes: 01 pessoa(s)\n"
				+ "- Refeicoes incluidas: [[1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.]\n"
				+ "- Pedidos: null\n"
				+ "VALOR TOTAL DA RESERVA: R$74,00 x2 (diarias) => R$148,00\n"
				+ "SITUACAO DO PAGAMENTO: REALIZADO.\n"
				+ "[3] Forma de pagamento: CARTAO DE CREDITO (0% de desconto em pagamentos).\n"
				+ "Total Efetivamente Pago: R$148,00 em 10x de R$14,80.", teste);
	}

	@Test
	void testGerSemPermissaoPagarReservaComPix() {
		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () ->  this.hotel.pagarReservaComPix("GER1", 1, "Sabugo", "12345678910", "Nubanko"));
		assertTrue(hte.getMessage().contains("O USUARIO NAO E CLIENTE"));
	}

	@Test
	void testAdmSemPermissaoPagarReservaComPix() {
		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () ->  this.hotel.pagarReservaComPix("ADM1", 1, "Sabugo", "12345678910", "Nubanko"));
		assertTrue(hte.getMessage().contains("O USUARIO NAO E CLIENTE"));
	}

	@Test
	void testFunSemPermissaoPagarReservaComPix() {
		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.pagarReservaComPix("FUN1", 1, "Sabugo", "12345678910", "Nubanko"));
		assertTrue(hte.getMessage().contains("O USUARIO NAO E CLIENTE"));
	}
	
	@Test
	void testOutraPessoaTentaPagarReservaComPix() {
		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.pagarReservaComPix("CLI1", 1, "Reservo", "12345678910", "Nubanko"));
		assertTrue(hte.getMessage().contains("SOMENTE O PROPRIO CLIENTE PODERA PAGAR A SUA RESERVA"));
	}

	@Test
	void testPagarReservaComPixComCPFInvalido() {
		HotelCaliforniaException e = assertThrows(HotelCaliforniaException.class, () -> this.hotel.pagarReservaComPix("CLI1", 1, "Sabugo", "123456789-101", "Nubanko"));
		assertTrue(e.getMessage().contains("O CPF não é válido"));
	}

	@Test
	void testPagarReservaComPixNovamente() {
		this.hotel.pagarReservaComPix("CLI1", 1, "Sabugo", "12345678910", "Nubanko");
		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.pagarReservaComPix("CLI1", 1, "Sabugo", "12345678910", "Nubanko"));
		assertTrue(hte.getMessage().contains("RESERVA JA FOI PAGA"));
	}

	@Test
	void testPagarReservaComPix() {
		String teste = this.hotel.pagarReservaComPix("CLI1", 1, "Sabugo", "12345678910", "Nubanko");
		assertEquals("[1] Reserva de quarto em favor de:\n"
				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
				+ "Detalhes da instalacao:\n"
				+ "- [45] Quarto Single (custo basico: R$10,00; por pessoa: R$10,00 >>> R$20,00 diária)\n"
				+ "Detalhes da reserva:\n"
				+ "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n"
				+ "- No. Hospedes: 01 pessoa(s)\n"
				+ "- Refeicoes incluidas: [[1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.]\n"
				+ "- Pedidos: null\n"
				+ "VALOR TOTAL DA RESERVA: R$74,00 x2 (diarias) => R$148,00\n"
				+ "SITUACAO DO PAGAMENTO: REALIZADO.\n"
				+ "[2] Forma de pagamento: PIX (5% de desconto em pagamentos).\n"
				+ "Total Efetivamente Pago: R$140,60 em 1x de R$140,60.", teste);
	}

	@Test
	void testExibirPagamentoDinheiro() {
		this.hotel.pagarReservaComDinheiro("CLI1", 1, "Sabugo");
		String teste = this.hotel.exibirReserva("GER1", 1);
		assertEquals("[1] Reserva de quarto em favor de:\n"
				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
				+ "Detalhes da instalacao:\n"
				+ "- [45] Quarto Single (custo basico: R$10,00; por pessoa: R$10,00 >>> R$20,00 diária)\n"
				+ "Detalhes da reserva:\n"
				+ "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n"
				+ "- No. Hospedes: 01 pessoa(s)\n"
				+ "- Refeicoes incluidas: [[1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.]\n"
				+ "- Pedidos: null\n"
				+ "VALOR TOTAL DA RESERVA: R$74,00 x2 (diarias) => R$148,00\n"
				+ "SITUACAO DO PAGAMENTO: REALIZADO.\n"
				+ "[1] Forma de pagamento: DINHEIRO (10% de desconto em pagamentos).\n"
				+ "Total Efetivamente Pago: R$133,20 em 1x de R$133,20.", teste);
	}

	@Test
	void testExibirPagamentoPix() {
		this.hotel.pagarReservaComPix("CLI1", 1, "Sabugo", "12345678910", "Nubanko");
		String teste = this.hotel.exibirReserva("GER1", 1);
		assertEquals("[1] Reserva de quarto em favor de:\n"
				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
				+ "Detalhes da instalacao:\n"
				+ "- [45] Quarto Single (custo basico: R$10,00; por pessoa: R$10,00 >>> R$20,00 diária)\n"
				+ "Detalhes da reserva:\n"
				+ "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n"
				+ "- No. Hospedes: 01 pessoa(s)\n"
				+ "- Refeicoes incluidas: [[1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.]\n"
				+ "- Pedidos: null\n"
				+ "VALOR TOTAL DA RESERVA: R$74,00 x2 (diarias) => R$148,00\n"
				+ "SITUACAO DO PAGAMENTO: REALIZADO.\n"
				+ "[2] Forma de pagamento: PIX (5% de desconto em pagamentos).\n"
				+ "Total Efetivamente Pago: R$140,60 em 1x de R$140,60.", teste);
	}

	@Test
	void testExibirPagamentoCartao() {
		this.hotel.pagarReservaComCartao("CLI1", 1, "Sabugo", "1234567891234567", "06/2023", "234", 10);
		String teste = this.hotel.exibirReserva("GER1", 1);
		assertEquals("[1] Reserva de quarto em favor de:\n"
				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
				+ "Detalhes da instalacao:\n"
				+ "- [45] Quarto Single (custo basico: R$10,00; por pessoa: R$10,00 >>> R$20,00 diária)\n"
				+ "Detalhes da reserva:\n"
				+ "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n"
				+ "- No. Hospedes: 01 pessoa(s)\n"
				+ "- Refeicoes incluidas: [[1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.]\n"
				+ "- Pedidos: null\n"
				+ "VALOR TOTAL DA RESERVA: R$74,00 x2 (diarias) => R$148,00\n"
				+ "SITUACAO DO PAGAMENTO: REALIZADO.\n"
				+ "[3] Forma de pagamento: CARTAO DE CREDITO (0% de desconto em pagamentos).\n"
				+ "Total Efetivamente Pago: R$148,00 em 10x de R$14,80.", teste);
	}

	@Test
	void testExibirPagamentoCartaoDecimalArredondado() {
		this.hotel.pagarReservaComCartao("CLI2", 2, "Livio", "1234567891234567", "06/2023", "234", 6);
		String teste = this.hotel.exibirReserva("GER1", 2);
		assertEquals("[2] Reserva de quarto em favor de:\n"
				+ "- [CLI2] Livio (No. Doc. 123456)\n"
				+ "Detalhes da instalacao:\n"
				+ "- [5] Quarto Single (custo basico: R$20,00; por pessoa: R$20,00 >>> R$40,00 diária)\n"
				+ "Detalhes da reserva:\n"
				+ "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n"
				+ "- No. Hospedes: 01 pessoa(s)\n"
				+ "- Refeicoes incluidas: [[1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.]\n"
				+ "- Pedidos: null\n"
				+ "VALOR TOTAL DA RESERVA: R$94,00 x2 (diarias) => R$188,00\n"
				+ "SITUACAO DO PAGAMENTO: REALIZADO.\n"
				+ "[3] Forma de pagamento: CARTAO DE CREDITO (0% de desconto em pagamentos).\n"
				+ "Total Efetivamente Pago: R$188,00 em 6x de R$31,34.", teste);
	}
}