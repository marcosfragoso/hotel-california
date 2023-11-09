package br.edu.ufcg.p2lp2.hotelcalifornia;

import br.edu.ufcg.p2lp2.hotelcalifornia.exception.HotelCaliforniaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

public class US11Test {

    private HotelCaliforniaSistema hotel;

    @BeforeEach
    void initialize() {
        this.hotel = new HotelCaliforniaSistema();
        this.hotel.cadastrarUsuario("ADM1", "Marcos Assunsão", "CLI", 666666666);
        this.hotel.cadastrarUsuario("ADM1", "Vini Junior", "GER", 666666667);
        this.hotel.cadastrarUsuario("ADM1", "Everton Ribeiro", "FUN", 2222);
        this.hotel.disponibilizarAreaComum("ADM1", "Auditorio", "SHOW", LocalTime.of(6,30),LocalTime.of(9,30), 10, true, 100);
    }

    @Test
    void testReservarAuditorio() {
        assertEquals(this.hotel.reservarAuditorio("GER1", "CLI1", 1, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00), 50), "[1] Reserva de AUDITORIO em favor de:\n" +
                "- [CLI1] Marcos Assunsão (No. Doc. 666666666)\n" +
                "Detalhes da reserva:\n" +
                "- Periodo: 25/12/2023 06:30:00 ate 27/12/2023 09:30:00\n" +
                "- Qtde. de Convidados: 50 pessoa(s)\n" +
                "- Valor por pessoa: R$10,00\n" +
                "VALOR TOTAL DA RESERVA: R$500,00 x3 (diarias) => R$1.500,00\n" +
                "SITUACAO DO PAGAMENTO: PENDENTE.");
    }

    @Test
    void testReservarAuditorioAdministradorSemPermissao() {
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarAuditorio("ADM1", "CLI1", 1, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00), 50));
        assertTrue(hte.getMessage().contains("NAO E POSSIVEL PARA USUARIO ADMINISTRADOR CADASTRAR UMA RESERVA"));
        }

    @Test
    void testReservarAuditorioClienteSemPermissao() {
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarAuditorio("CLI1", "CLI1", 1, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00), 50));
        assertTrue(hte.getMessage().contains("NAO E POSSIVEL PARA USUARIO CLIENTE CADASTRAR UMA RESERVA"));
    }

    @Test
    void testReservarCapacidadeUltrapassada() {
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarAuditorio("GER1", "CLI1", 1, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00), 151));
        assertTrue(hte.getMessage().contains("CAPACIDADE EXCEDIDA"));
    }

    @Test
    void testReservarSemAntecedencia() {
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarAuditorio("GER1", "CLI1", 1, LocalDateTime.of(2023,10,29,14,00), LocalDateTime.of(2023,12,26,12,00), 140));
        assertTrue(hte.getMessage().contains("NECESSARIO ANTECEDENCIA MINIMA DE 01 (UM) DIA"));
    }

    @Test
    void testReservarJaExisteReserva() {
        this.hotel.reservarAuditorio("GER1", "CLI1", 1, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00), 50);
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarAuditorio("GER1", "CLI1", 1, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00), 50));
        assertTrue(hte.getMessage().contains("JA EXISTE RESERVA PARA ESTA DATA"));
    }

    @Test
    void testReservarAuditorioClienteInexistente() {
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarAuditorio("GER1", "CLI10", 1, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00), 50));
        assertTrue(hte.getMessage().contains("USUARIO NAO EXISTE"));
    }

    @Test
    void testReservarAuditorioUsuarioInexistente() {
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarAuditorio("GER10", "CLI1", 1, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00), 50));
        assertTrue(hte.getMessage().contains("USUARIO NAO EXISTE"));
    }
}
