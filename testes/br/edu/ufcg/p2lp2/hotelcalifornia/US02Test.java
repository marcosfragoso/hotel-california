package br.edu.ufcg.p2lp2.hotelcalifornia;

import static org.junit.jupiter.api.Assertions.*;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.quartos.QuartoDouble;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.quartos.QuartoFamily;
import br.edu.ufcg.p2lp2.hotelcalifornia.entities.quartos.QuartoSingle;
import br.edu.ufcg.p2lp2.hotelcalifornia.exception.HotelCaliforniaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class US02Test {

	private HotelCaliforniaSistema hotel;
	private QuartoSingle quartoS;
	private QuartoDouble quartoD;
	private QuartoFamily quartoF;

	@BeforeEach
	void preparaQuarto(){
		this.hotel = new HotelCaliforniaSistema();
        this.hotel.cadastrarUsuario("ADM1", "Saudito", "GER", 234567);
        String[] pedidosF = {"01 (um) babador infantil"};
        this.hotel.disponibilizarQuartoFamily("ADM1", 43, 51.62, 12.24, pedidosF, 7);
        this.hotel.disponibilizarQuartoSingle("ADM1", 17, 16.17, 13.12);
        String[] pedidosD = {"02 (duas) lanternas"};
        this.hotel.disponibilizarQuartoDouble("ADM1", 16, 23.73, 13.34, pedidosD);
        this.quartoS = new QuartoSingle(54, 54.65154, 23.43112);
        String[] pedidosd = {"01 (uma) cama queen size", "02 (duas) lanternas"};
		this.quartoD = new QuartoDouble(32, 43.451111, 12.541234, pedidosd);
		String[] pedidosf = {"03 (três) camas infantis"};
		this.quartoF = new QuartoFamily(13, 62.651111, 13.161234, pedidosf, 7);
	}

	@Test
	void testToStringS() {
		String teste = this.quartoS.toString();
		assertEquals("[54] Quarto Single (custo basico: R$54,65; por pessoa: R$23,43 >>> R$78,08 diária)", teste);
	}

	@Test
	void testGetValorDiariaS() {
		double teste = this.quartoS.getValorDiaria();
		assertEquals(78.08, teste);
	}
	
	@Test
	void testToStringD() {
		String teste = this.quartoD.toString();
		assertEquals("[32] Quarto Double (custo basico: R$43,45; por pessoa: R$12,54 >>> R$68,53 diária). Pedidos: [01 (uma) cama queen size, 02 (duas) lanternas]", teste);
	}

	@Test
	void testGetValorDiariaD() {
		double teste = this.quartoD.getValorDiaria();
		assertEquals(68,53, teste);
	}
	

	@Test
	void testToStringF() {
		String teste = this.quartoF.toString();
		assertEquals("[13] Quarto Family (custo basico: R$62,65; por pessoa: R$13,16 >>> R$154,78 diária). Capacidade: 07 pessoa(s). Pedidos: [03 (três) camas infantis]", teste);
	}

	@Test
	void testGetValorDiariaF() {
		double teste = this.quartoF.getValorDiaria();
		assertEquals(154,78, teste);
	}
	
	@Test
	void testNDisponibilizaQuartoSingleComNumeroInvalido() {
		this.hotel.disponibilizarQuartoSingle("ADM1", 54, 54.6, 23.4);
		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.disponibilizarQuartoSingle("ADM1", 54, 54.6, 23.4));
		assertTrue(hte.getMessage().contains("QUARTO JA EXISTE"));
	}

	@Test
	void testDisponibilizaQuartoSingle() {
		String teste = this.hotel.disponibilizarQuartoSingle("ADM1", 54, 23.4, 54.6);
		assertEquals("[54] Quarto Single (custo basico: R$23,40; por pessoa: R$54,60 >>> R$78,00 diária)", teste);
	}

	@Test
	void testNDisponibilizaQuartoSingleDeUsuarioSemPermissao() {
		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.disponibilizarQuartoSingle("GER1", 54, 54.6, 23.4));
		assertTrue(hte.getMessage().contains("USUARIO NAO E ADMINISTRADOR"));
	}

	@Test
	void testNDisponibilizaQuartoDoubleComNumeroInvalido() {
		String[] pedidosD = {"01 (uma) cama queen size", "02 (duas) lanternas"};
		this.hotel.disponibilizarQuartoDouble("ADM1", 32, 43.45, 12.54, pedidosD);
		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.disponibilizarQuartoDouble("ADM1", 32, 43.45, 12.54, pedidosD));
		assertTrue(hte.getMessage().contains("QUARTO JA EXISTE"));
	}

	@Test
	void testDisponibilizaQuartoDouble() {
		String[] pedidosD = {"01 (uma) cama queen size", "02 (duas) lanternas"};
		String teste = this.hotel.disponibilizarQuartoDouble("ADM1", 32, 12.54, 43.45, pedidosD);
		assertEquals("[32] Quarto Double (custo basico: R$12,54; por pessoa: R$43,45 >>> R$99,44 diária). Pedidos: [01 (uma) cama queen size, 02 (duas) lanternas]", teste);	
	}

	@Test
	void testNDisponibilizaQuartoDoubleDeUsuarioSemPermissao() {
		String[] pedidosD = {"01 (uma) cama queen size", "02 (duas) lanternas"};
		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.disponibilizarQuartoDouble("GER1", 32, 43.45, 12.54, pedidosD));
		assertTrue(hte.getMessage().contains("USUARIO NAO E ADMINISTRADOR"));
	}

	@Test
	void testNDisponibilizaQuartoFamilyDeUsuarioSemPermissao() {
		String[] pedidosF = {"03 (três) camas infantis"};
		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.disponibilizarQuartoFamily("GER1", 13, 62.65, 13.16, pedidosF, 7));
		assertTrue(hte.getMessage().contains("USUARIO NAO E ADMINISTRADOR"));
	}

	@Test
	void testNDisponibilizaQuartoFamilyComNumeroInvalido() {
		String[] pedidosF = {"03 (três) camas infantis"};
		this.hotel.disponibilizarQuartoFamily("ADM1", 13, 62.65, 13.16, pedidosF, 7);
		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.disponibilizarQuartoFamily("ADM1", 13, 62.65, 13.16, pedidosF, 7));
		assertTrue(hte.getMessage().contains("QUARTO JA EXISTE"));
		}

	@Test
	void testDisponibilizaQuartoFamily() {
		String[] pedidosF = {"03 (três) camas infantis"};
		String teste = this.hotel.disponibilizarQuartoFamily("ADM1", 13, 13.16, 62.65, pedidosF, 7);
		assertEquals("[13] Quarto Family (custo basico: R$13,16; por pessoa: R$62,65 >>> R$451,71 diária). Capacidade: 07 pessoa(s). Pedidos: [03 (três) camas infantis]", teste);
	}

	@Test
	void testNDisponibilizaQuartoFamilyComCapacidadeExcedida() {
		String[] pedidosF = {"03 (três) camas infantis"};
		String teste = this.hotel.disponibilizarQuartoFamily("ADM1", 13, 62.65, 13.16, pedidosF, 11);
		assertEquals("Quarto excede o limite de capacidade", teste);
	}

	@Test
	void testNExibirQuartoInexistente() {
		String teste = this.hotel.exibirQuarto(92);
		assertEquals("Quarto inexistente.", teste);
	}

	@Test
	void testExibirQuartoSingle() {
		String teste = this.hotel.exibirQuarto(17);
		assertEquals("[17] Quarto Single (custo basico: R$16,17; por pessoa: R$13,12 >>> R$29,29 diária)", teste);
	}

	@Test
	void testExibirQuartoDouble() {
		String teste = this.hotel.exibirQuarto(16);
		assertEquals("[16] Quarto Double (custo basico: R$23,73; por pessoa: R$13,34 >>> R$50,41 diária). Pedidos: [02 (duas) lanternas]", teste);
	}

	@Test
	void testExibirQuartoFamily() {
		String teste = this.hotel.exibirQuarto(43);
		assertEquals("[43] Quarto Family (custo basico: R$51,62; por pessoa: R$12,24 >>> R$137,30 diária). Capacidade: 07 pessoa(s). Pedidos: [01 (um) babador infantil]", teste);
	}

	@Test
	void testListaQuartos() {
		String[] t = this.hotel.listarQuartos();
		String teste = "";
		for(String quarto: t) {
			teste += quarto + "\n";
		}
		assertEquals("[16] Quarto Double (custo basico: R$23,73; por pessoa: R$13,34 >>> R$50,41 diária). Pedidos: [02 (duas) lanternas]\n[17] Quarto Single (custo basico: R$16,17; por pessoa: R$13,12 >>> R$29,29 diária)\n[43] Quarto Family (custo basico: R$51,62; por pessoa: R$12,24 >>> R$137,30 diária). Capacidade: 07 pessoa(s). Pedidos: [01 (um) babador infantil]\n", teste);
	}
}