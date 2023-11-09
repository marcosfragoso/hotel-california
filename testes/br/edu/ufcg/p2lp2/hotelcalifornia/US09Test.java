package br.edu.ufcg.p2lp2.hotelcalifornia;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.edu.ufcg.p2lp2.hotelcalifornia.exception.HotelCaliforniaException;

class US09Test {
	
	private HotelCaliforniaSistema hotel;
	
	@BeforeEach
	void preparaTestes(){
		this.hotel = new HotelCaliforniaSistema();
		this.hotel.cadastrarUsuario("ADM1", "Sabugo", "CLI", 456123);
		this.hotel.cadastrarUsuario("ADM1", "Livio", "CLI", 123456);
        this.hotel.cadastrarUsuario("ADM1", "Janaino", "FUN", 654321);
        this.hotel.cadastrarUsuario("ADM1", "Leito quento", "GER", 333333);
        this.hotel.disponibilizarRefeicao("GER1", "ALMOCO", "refeição mais importante do dia", LocalTime.of(12,30), LocalTime.of(15,30), 54, true);
        this.hotel.disponibilizarQuartoSingle("ADM1", 45, 10, 10);
        String[] refeicoes = {"1"};
        this.hotel.reservarQuartoSingle("GER1", "CLI1", 45, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00), refeicoes);
	}

	@Test
	void testGerNCancelaReserva() {
		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.cancelarReserva("GER1", "1"));
		assertTrue(hte.getMessage().contains("USUARIO NAO E CLIENTE"));
	}
	
	@Test
	void testAdmNCancelaReserva() {
		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.cancelarReserva("ADM1", "1"));
		assertTrue(hte.getMessage().contains("USUARIO NAO E CLIENTE"));
	}
	@Test
	void testFunNCancelaReserva() {
		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.cancelarReserva("FUN1", "1"));
		assertTrue(hte.getMessage().contains("USUARIO NAO E CLIENTE"));
	}
	
	@Test
	void testCliNCancelaReservaDeOutro() {
		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.cancelarReserva("CLI2", "1"));
		assertTrue(hte.getMessage().contains("SOMENTE O PROPRIO CLIENTE PODERA CANCELAR A SUA RESERVA"));
	}
	
	//Não tem como manter este teste ativo, porque para criar a reserva precisa ter um dia de antecedencia de HOJE, porém, ignorando esse fato, o teste passa com sucesso.
//	@Test
//	void testNCancelarReservaComMenosDeUmDia() {
//		this.hotel.disponibilizarQuartoSingle("ADM1", 5, 20, 20);
//		this.hotel.reservarQuartoSingle("GER1", "CLI2", 5, LocalDateTime.of(2023,10,28,21,40), LocalDateTime.of(2023,10,30,12,00), new String[] {"1"});
//		HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.cancelarReserva("CLI2", "2"));
//		assertTrue(hte.getMessage().contains("CLIENTE NAO PODE CANCELAR A RESERVA"));
//	}
	
	
	@Test
	void testCancelarReserva() {
		String teste = this.hotel.cancelarReserva("CLI1", "1");
		assertTrue(teste.contains("[CANCELADA]"));
	}

}
