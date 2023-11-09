package br.edu.ufcg.p2lp2.hotelcalifornia;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.edu.ufcg.p2lp2.hotelcalifornia.exception.HotelCaliforniaException;

public class US06Test {

	private HotelCaliforniaSistema hotel;

	@BeforeEach
	void preapraReservas(){
		this.hotel = new HotelCaliforniaSistema();
        this.hotel.cadastrarUsuario("ADM1", "Sabugo", "CLI", 456123);
        this.hotel.cadastrarUsuario("ADM1", "Janaino", "FUN", 654321);
        this.hotel.cadastrarUsuario("ADM1", "Pipoco", "CLI", 341256);
        this.hotel.cadastrarUsuario("ADM1", "Garrafo", "CLI", 162534);
        this.hotel.cadastrarUsuario("ADM1", "Leito quento", "GER", 333333);
        this.hotel.disponibilizarQuartoSingle("ADM1", 45, 10, 10);
        this.hotel.disponibilizarQuartoSingle("ADM1", 67, 10, 20);
        this.hotel.disponibilizarQuartoSingle("ADM1", 4, 18, 10);
        this.hotel.disponibilizarAreaComum("ADM1", "AUDITORIO", "Show do Léo Lins", LocalTime.of(14,00), LocalTime.of(19,00), 0.0, true, 90);
        this.hotel.disponibilizarAreaComum("ADM1", "AUDITORIO", "Teatro", LocalTime.of(15,00), LocalTime.of(17,00), 0.0, true, 60);
        this.hotel.disponibilizarRefeicao("GER1", "ALMOCO", "refeição mais importante do dia", LocalTime.of(12,30), LocalTime.of(15,30), 54, true);
        String[] refeicoes = {"1"};
        this.hotel.reservarQuartoSingle("GER1", "CLI1", 45, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00), refeicoes);
        this.hotel.reservarQuartoSingle("GER1", "CLI1", 4, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00), refeicoes);
        this.hotel.reservarRestaurante("GER1", "CLI1", LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00), 10, "1");
        this.hotel.reservarAuditorio("GER1", "CLI1", 1, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00), 80);
        this.hotel.reservarQuartoSingle("GER1", "CLI2", 67, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00), refeicoes);
        this.hotel.reservarRestaurante("GER1", "CLI2", LocalDateTime.of(2023,12,28,14,00), LocalDateTime.of(2023,12,30,12,00), 10, "1");
        //this.hotel.reservarAuditorio("GER1", "CLI2", 2, LocalDateTime.of(2022,12,25,14,00), LocalDateTime.of(2022,12,27,12,00), 30);
		//reserva utilizada para verificação do teste listarTodas, porém como não podemos reservar ela sem 1 dia de antecedência, foi testado e comentado para não atrapalhar o funcionamento do código
	}

        @Test
    	void testExibeReserva() {
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
    				+ "SITUACAO DO PAGAMENTO: PENDENTE.", teste);
    	}

    	@Test
    	void testNExibeReserva() {
    		HotelCaliforniaException e = assertThrows(HotelCaliforniaException.class, () -> this.hotel.exibirReserva("ADM1", 5));
            assertTrue(e.getMessage().contains("NAO E POSSIVEL PARA USUARIO ADMINISTRADOR EXIBIR/LISTAR RESERVA(S) DO CLIENTE"));
    	}

    	@Test
    	void testNListarReserva() {
    		HotelCaliforniaException e = assertThrows(HotelCaliforniaException.class, () -> this.hotel.listarReservasAtivasDoCliente("ADM1", "CLI2"));
    		assertTrue(e.getMessage().contains("NAO E POSSIVEL PARA USUARIO ADMINISTRADOR EXIBIR/LISTAR RESERVA(S) DO CLIENTE"));
    	}

    	@Test
    	void testListarReservasAtivasCliente() {
    		String[] t = this.hotel.listarReservasAtivasDoCliente("GER1", "CLI1");
    		String teste = "";
    		for(String s: t) {
    			teste += s + "\n";
    		}
    		assertEquals("Vizualizar Reservas de: [CLI1] Sabugo (No. Doc. 456123)\n"
    				+ "==========\n"
    				+ "[1] Reserva de quarto em favor de:\n"
    				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
    				+ "Detalhes da instalacao:\n"
    				+ "- [45] Quarto Single (custo basico: R$10,00; por pessoa: R$10,00 >>> R$20,00 diária)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n"
    				+ "- No. Hospedes: 01 pessoa(s)\n"
    				+ "- Refeicoes incluidas: [[1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.]\n"
    				+ "- Pedidos: null\n"
    				+ "VALOR TOTAL DA RESERVA: R$74,00 x2 (diarias) => R$148,00\n"
    				+ "SITUACAO DO PAGAMENTO: PENDENTE.\n"
    				+ "------------------\n"
    				+ "[2] Reserva de quarto em favor de:\n"
    				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
    				+ "Detalhes da instalacao:\n"
    				+ "- [4] Quarto Single (custo basico: R$18,00; por pessoa: R$10,00 >>> R$28,00 diária)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n"
    				+ "- No. Hospedes: 01 pessoa(s)\n"
    				+ "- Refeicoes incluidas: [[1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.]\n"
    				+ "- Pedidos: null\n"
    				+ "VALOR TOTAL DA RESERVA: R$82,00 x2 (diarias) => R$164,00\n"
    				+ "SITUACAO DO PAGAMENTO: PENDENTE.\n"
    				+ "------------------\n"
    				+ "[3] Reserva de RESTAURANTE em favor de:\n"
    				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 25/12/2023 12:30:00 ate 27/12/2023 15:30:00\n"
    				+ "- Qtde. de Convidados: 10 pessoa(s)\n"
    				+ "- Refeicao incluida: [1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.\n"
    				+ "VALOR TOTAL DA RESERVA: R$540,00 x3 (diarias) => R$1.620,00\n"
    				+ "SITUACAO DO PAGAMENTO: PENDENTE.\n"
    				+ "------------------\n"
    				+ "[4] Reserva de AUDITORIO em favor de:\n"
    				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 19:00:00\n"
    				+ "- Qtde. de Convidados: 80 pessoa(s)\n"
    				+ "- Valor por pessoa: Grátis\n"
    				+ "VALOR TOTAL DA RESERVA: Grátis x3 (diarias) => Grátis\n"
    				+ "SITUACAO DO PAGAMENTO: REALIZADO.\n"
    				+ "------------------\n"
    				+ "==========\n", teste);
    	}

    	@Test
    	void testListarReservasAtivasClienteTipoQ() {
    		String[] t = this.hotel.listarReservasAtivasDoClientePorTipo("GER1", "CLI1", "Quarto");
    		String teste = "";
    		for(String s: t) {
    			teste += s + "\n";
    		}
    		assertEquals("Vizualizar Reservas de: [CLI1] Sabugo (No. Doc. 456123)\n"
    				+ "==========\n"
    				+ "[1] Reserva de quarto em favor de:\n"
    				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
    				+ "Detalhes da instalacao:\n"
    				+ "- [45] Quarto Single (custo basico: R$10,00; por pessoa: R$10,00 >>> R$20,00 diária)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n"
    				+ "- No. Hospedes: 01 pessoa(s)\n"
    				+ "- Refeicoes incluidas: [[1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.]\n"
    				+ "- Pedidos: null\n"
    				+ "VALOR TOTAL DA RESERVA: R$74,00 x2 (diarias) => R$148,00\n"
    				+ "SITUACAO DO PAGAMENTO: PENDENTE.\n"
    				+ "------------------\n"
    				+ "[2] Reserva de quarto em favor de:\n"
    				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
    				+ "Detalhes da instalacao:\n"
    				+ "- [4] Quarto Single (custo basico: R$18,00; por pessoa: R$10,00 >>> R$28,00 diária)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n"
    				+ "- No. Hospedes: 01 pessoa(s)\n"
    				+ "- Refeicoes incluidas: [[1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.]\n"
    				+ "- Pedidos: null\n"
    				+ "VALOR TOTAL DA RESERVA: R$82,00 x2 (diarias) => R$164,00\n"
    				+ "SITUACAO DO PAGAMENTO: PENDENTE.\n"
    				+ "------------------\n"
    				+ "==========\n", teste);
    	}
    	
    	@Test
    	void testListarReservasAtivasClienteTipoA() {
    		String[] t = this.hotel.listarReservasAtivasDoClientePorTipo("GER1", "CLI1", "Auditorio");
    		String teste = "";
    		for(String s: t) {
    			teste += s + "\n";
    		}
    		assertEquals("Vizualizar Reservas de: [CLI1] Sabugo (No. Doc. 456123)\n"
    				+ "==========\n"
    				+ "[4] Reserva de AUDITORIO em favor de:\n"
    				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 19:00:00\n"
    				+ "- Qtde. de Convidados: 80 pessoa(s)\n"
    				+ "- Valor por pessoa: Grátis\n"
    				+ "VALOR TOTAL DA RESERVA: Grátis x3 (diarias) => Grátis\n"
    				+ "SITUACAO DO PAGAMENTO: REALIZADO.\n"
    				+ "------------------\n"
    				+ "==========\n", teste);
    	}

    	@Test
    	void testListarReservasAtivasClienteTipoR() {
    		String[] t = this.hotel.listarReservasAtivasDoClientePorTipo("GER1", "CLI1", "Restaurante");
    		String teste = "";
    		for(String s: t) {
    			teste += s + "\n";
    		}
    		assertEquals("Vizualizar Reservas de: [CLI1] Sabugo (No. Doc. 456123)\n"
    				+ "==========\n"
    				+ "[3] Reserva de RESTAURANTE em favor de:\n"
    				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 25/12/2023 12:30:00 ate 27/12/2023 15:30:00\n"
    				+ "- Qtde. de Convidados: 10 pessoa(s)\n"
    				+ "- Refeicao incluida: [1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.\n"
    				+ "VALOR TOTAL DA RESERVA: R$540,00 x3 (diarias) => R$1.620,00\n"
    				+ "SITUACAO DO PAGAMENTO: PENDENTE.\n"
    				+ "------------------\n"
    				+ "==========\n", teste);
    	}

    	@Test
    	void testListarReservasTodasAtivas() {
    		String[] t = this.hotel.listarReservasAtivas("GER1");
    		String teste = "";
    		for(String s: t) {
    			teste += s + "\n";
    		}
    		assertEquals("==========\n"
    				+ "[1] Reserva de quarto em favor de:\n"
    				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
    				+ "Detalhes da instalacao:\n"
    				+ "- [45] Quarto Single (custo basico: R$10,00; por pessoa: R$10,00 >>> R$20,00 diária)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n"
    				+ "- No. Hospedes: 01 pessoa(s)\n"
    				+ "- Refeicoes incluidas: [[1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.]\n"
    				+ "- Pedidos: null\n"
    				+ "VALOR TOTAL DA RESERVA: R$74,00 x2 (diarias) => R$148,00\n"
    				+ "SITUACAO DO PAGAMENTO: PENDENTE.\n"
    				+ "------------------\n"
    				+ "[2] Reserva de quarto em favor de:\n"
    				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
    				+ "Detalhes da instalacao:\n"
    				+ "- [4] Quarto Single (custo basico: R$18,00; por pessoa: R$10,00 >>> R$28,00 diária)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n"
    				+ "- No. Hospedes: 01 pessoa(s)\n"
    				+ "- Refeicoes incluidas: [[1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.]\n"
    				+ "- Pedidos: null\n"
    				+ "VALOR TOTAL DA RESERVA: R$82,00 x2 (diarias) => R$164,00\n"
    				+ "SITUACAO DO PAGAMENTO: PENDENTE.\n"
    				+ "------------------\n"
    				+ "[3] Reserva de RESTAURANTE em favor de:\n"
    				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 25/12/2023 12:30:00 ate 27/12/2023 15:30:00\n"
    				+ "- Qtde. de Convidados: 10 pessoa(s)\n"
    				+ "- Refeicao incluida: [1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.\n"
    				+ "VALOR TOTAL DA RESERVA: R$540,00 x3 (diarias) => R$1.620,00\n"
    				+ "SITUACAO DO PAGAMENTO: PENDENTE.\n"
    				+ "------------------\n"
    				+ "[4] Reserva de AUDITORIO em favor de:\n"
    				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 19:00:00\n"
    				+ "- Qtde. de Convidados: 80 pessoa(s)\n"
    				+ "- Valor por pessoa: Grátis\n"
    				+ "VALOR TOTAL DA RESERVA: Grátis x3 (diarias) => Grátis\n"
    				+ "SITUACAO DO PAGAMENTO: REALIZADO.\n"
    				+ "------------------\n"
    				+ "[5] Reserva de quarto em favor de:\n"
    				+ "- [CLI2] Pipoco (No. Doc. 341256)\n"
    				+ "Detalhes da instalacao:\n"
    				+ "- [67] Quarto Single (custo basico: R$10,00; por pessoa: R$20,00 >>> R$30,00 diária)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n"
    				+ "- No. Hospedes: 01 pessoa(s)\n"
    				+ "- Refeicoes incluidas: [[1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.]\n"
    				+ "- Pedidos: null\n"
    				+ "VALOR TOTAL DA RESERVA: R$84,00 x2 (diarias) => R$168,00\n"
    				+ "SITUACAO DO PAGAMENTO: PENDENTE.\n"
    				+ "------------------\n"
    				+ "[6] Reserva de RESTAURANTE em favor de:\n"
    				+ "- [CLI2] Pipoco (No. Doc. 341256)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 28/12/2023 12:30:00 ate 30/12/2023 15:30:00\n"
    				+ "- Qtde. de Convidados: 10 pessoa(s)\n"
    				+ "- Refeicao incluida: [1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.\n"
    				+ "VALOR TOTAL DA RESERVA: R$540,00 x3 (diarias) => R$1.620,00\n"
    				+ "SITUACAO DO PAGAMENTO: PENDENTE.\n"
    				+ "------------------\n"
    				+ "==========\n", teste);
    	}

    	@Test
    	void testListarReservasTodasAtivasTipoQ() {
    		String[] t = this.hotel.listarReservasAtivasPorTipo("GER1", "Quarto");
    		String teste = "";
    		for(String s: t) {
    			teste += s + "\n";
    		}
    		assertEquals("Vizualizar Reservas de: Quarto\n"
    				+ "==========\n"
    				+ "[1] Reserva de quarto em favor de:\n"
    				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
    				+ "Detalhes da instalacao:\n"
    				+ "- [45] Quarto Single (custo basico: R$10,00; por pessoa: R$10,00 >>> R$20,00 diária)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n"
    				+ "- No. Hospedes: 01 pessoa(s)\n"
    				+ "- Refeicoes incluidas: [[1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.]\n"
    				+ "- Pedidos: null\n"
    				+ "VALOR TOTAL DA RESERVA: R$74,00 x2 (diarias) => R$148,00\n"
    				+ "SITUACAO DO PAGAMENTO: PENDENTE.\n"
    				+ "------------------\n"
    				+ "[2] Reserva de quarto em favor de:\n"
    				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
    				+ "Detalhes da instalacao:\n"
    				+ "- [4] Quarto Single (custo basico: R$18,00; por pessoa: R$10,00 >>> R$28,00 diária)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n"
    				+ "- No. Hospedes: 01 pessoa(s)\n"
    				+ "- Refeicoes incluidas: [[1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.]\n"
    				+ "- Pedidos: null\n"
    				+ "VALOR TOTAL DA RESERVA: R$82,00 x2 (diarias) => R$164,00\n"
    				+ "SITUACAO DO PAGAMENTO: PENDENTE.\n"
    				+ "------------------\n"
    				+ "[5] Reserva de quarto em favor de:\n"
    				+ "- [CLI2] Pipoco (No. Doc. 341256)\n"
    				+ "Detalhes da instalacao:\n"
    				+ "- [67] Quarto Single (custo basico: R$10,00; por pessoa: R$20,00 >>> R$30,00 diária)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n"
    				+ "- No. Hospedes: 01 pessoa(s)\n"
    				+ "- Refeicoes incluidas: [[1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.]\n"
    				+ "- Pedidos: null\n"
    				+ "VALOR TOTAL DA RESERVA: R$84,00 x2 (diarias) => R$168,00\n"
    				+ "SITUACAO DO PAGAMENTO: PENDENTE.\n"
    				+ "------------------\n"
    				+ "==========\n", teste);
    	}

    	@Test
    	void testListarReservasTodasAtivasTipoR() {
    		String[] t = this.hotel.listarReservasAtivasPorTipo("GER1", "Restaurante");
    		String teste = "";
    		for(String s: t) {
    			teste += s + "\n";
    		}
    		assertEquals("Vizualizar Reservas de: Restaurante\n"
    				+ "==========\n"
    				+ "[3] Reserva de RESTAURANTE em favor de:\n"
    				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 25/12/2023 12:30:00 ate 27/12/2023 15:30:00\n"
    				+ "- Qtde. de Convidados: 10 pessoa(s)\n"
    				+ "- Refeicao incluida: [1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.\n"
    				+ "VALOR TOTAL DA RESERVA: R$540,00 x3 (diarias) => R$1.620,00\n"
    				+ "SITUACAO DO PAGAMENTO: PENDENTE.\n"
    				+ "------------------\n"
    				+ "[6] Reserva de RESTAURANTE em favor de:\n"
    				+ "- [CLI2] Pipoco (No. Doc. 341256)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 28/12/2023 12:30:00 ate 30/12/2023 15:30:00\n"
    				+ "- Qtde. de Convidados: 10 pessoa(s)\n"
    				+ "- Refeicao incluida: [1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.\n"
    				+ "VALOR TOTAL DA RESERVA: R$540,00 x3 (diarias) => R$1.620,00\n"
    				+ "SITUACAO DO PAGAMENTO: PENDENTE.\n"
    				+ "------------------\n"
    				+ "==========\n", teste);
    	}
    	
    	@Test
    	void testListarReservasTodasAtivasTipoA() {
    		String[] t = this.hotel.listarReservasAtivasPorTipo("GER1", "Auditorio");
    		String teste = "";
    		for(String s: t) {
    			teste += s + "\n";
    		}
    		assertEquals("Vizualizar Reservas de: Auditorio\n"
    				+ "==========\n"
    				+ "[4] Reserva de AUDITORIO em favor de:\n"
    				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 19:00:00\n"
    				+ "- Qtde. de Convidados: 80 pessoa(s)\n"
    				+ "- Valor por pessoa: Grátis\n"
    				+ "VALOR TOTAL DA RESERVA: Grátis x3 (diarias) => Grátis\n"
    				+ "SITUACAO DO PAGAMENTO: REALIZADO.\n"
    				+ "------------------\n"
    				+ "==========\n", teste);
    	}
    	
    	@Test
    	void testListarReservasTodas() {
    		String[] t = this.hotel.listarReservasTodas("GER1");
    		String teste = "";
    		for(String s: t) {
    			teste += s + "\n";
    		}
    		assertEquals("==========\n"
    				+ "[1] Reserva de quarto em favor de:\n"
    				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
    				+ "Detalhes da instalacao:\n"
    				+ "- [45] Quarto Single (custo basico: R$10,00; por pessoa: R$10,00 >>> R$20,00 diária)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n"
    				+ "- No. Hospedes: 01 pessoa(s)\n"
    				+ "- Refeicoes incluidas: [[1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.]\n"
    				+ "- Pedidos: null\n"
    				+ "VALOR TOTAL DA RESERVA: R$74,00 x2 (diarias) => R$148,00\n"
    				+ "SITUACAO DO PAGAMENTO: PENDENTE.\n"
    				+ "------------------\n"
    				+ "[2] Reserva de quarto em favor de:\n"
    				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
    				+ "Detalhes da instalacao:\n"
    				+ "- [4] Quarto Single (custo basico: R$18,00; por pessoa: R$10,00 >>> R$28,00 diária)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n"
    				+ "- No. Hospedes: 01 pessoa(s)\n"
    				+ "- Refeicoes incluidas: [[1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.]\n"
    				+ "- Pedidos: null\n"
    				+ "VALOR TOTAL DA RESERVA: R$82,00 x2 (diarias) => R$164,00\n"
    				+ "SITUACAO DO PAGAMENTO: PENDENTE.\n"
    				+ "------------------\n"
    				+ "[3] Reserva de RESTAURANTE em favor de:\n"
    				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 25/12/2023 12:30:00 ate 27/12/2023 15:30:00\n"
    				+ "- Qtde. de Convidados: 10 pessoa(s)\n"
    				+ "- Refeicao incluida: [1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.\n"
    				+ "VALOR TOTAL DA RESERVA: R$540,00 x3 (diarias) => R$1.620,00\n"
    				+ "SITUACAO DO PAGAMENTO: PENDENTE.\n"
    				+ "------------------\n"
    				+ "[4] Reserva de AUDITORIO em favor de:\n"
    				+ "- [CLI1] Sabugo (No. Doc. 456123)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 19:00:00\n"
    				+ "- Qtde. de Convidados: 80 pessoa(s)\n"
    				+ "- Valor por pessoa: Grátis\n"
    				+ "VALOR TOTAL DA RESERVA: Grátis x3 (diarias) => Grátis\n"
    				+ "SITUACAO DO PAGAMENTO: REALIZADO.\n"
    				+ "------------------\n"
    				+ "[5] Reserva de quarto em favor de:\n"
    				+ "- [CLI2] Pipoco (No. Doc. 341256)\n"
    				+ "Detalhes da instalacao:\n"
    				+ "- [67] Quarto Single (custo basico: R$10,00; por pessoa: R$20,00 >>> R$30,00 diária)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n"
    				+ "- No. Hospedes: 01 pessoa(s)\n"
    				+ "- Refeicoes incluidas: [[1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.]\n"
    				+ "- Pedidos: null\n"
    				+ "VALOR TOTAL DA RESERVA: R$84,00 x2 (diarias) => R$168,00\n"
    				+ "SITUACAO DO PAGAMENTO: PENDENTE.\n"
    				+ "------------------\n"
    				+ "[6] Reserva de RESTAURANTE em favor de:\n"
    				+ "- [CLI2] Pipoco (No. Doc. 341256)\n"
    				+ "Detalhes da reserva:\n"
    				+ "- Periodo: 28/12/2023 12:30:00 ate 30/12/2023 15:30:00\n"
    				+ "- Qtde. de Convidados: 10 pessoa(s)\n"
    				+ "- Refeicao incluida: [1] Almoco: refeição mais importante do dia (12h30 as 15h30). Valor por pessoa: R$54,00. VIGENTE.\n"
    				+ "VALOR TOTAL DA RESERVA: R$540,00 x3 (diarias) => R$1.620,00\n"
    				+ "SITUACAO DO PAGAMENTO: PENDENTE.\n"
    				+ "------------------\n"
//    				+ "[7] Reserva de AUDITORIO em favor de:\n"
//    				+ "- [CLI2] Pipoco (No. Doc. 341256)\n"
//    				+ "Detalhes da reserva:\n"
//    				+ "- Periodo: 25/12/2022 15:00:00 ate 27/12/2022 17:00:00\n"
//    				+ "- Qtde. de Convidados: 30 pessoa(s)\n"
//    				+ "- Valor por pessoa: Grátis\n"
//    				+ "VALOR TOTAL DA RESERVA: Grátis x3 (diarias) => Grátis\n"
//    				+ "SITUACAO DO PAGAMENTO: REALIZADO.\n"
//    				+ "------------------\n"
    				+ "==========\n", teste);
    	}

    	@Test
    	void testNListaReservas() {
    		HotelCaliforniaException e = assertThrows(HotelCaliforniaException.class, () -> this.hotel.listarReservasAtivasDoCliente("GER1", "CLI3"));
            assertTrue(e.getMessage().contains("RESERVA NAO ENCONTRADA"));
    	}

    }