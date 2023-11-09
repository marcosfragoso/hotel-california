package br.edu.ufcg.p2lp2.hotelcalifornia;

import br.edu.ufcg.p2lp2.hotelcalifornia.exception.HotelCaliforniaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class US03E05Test {

    private HotelCaliforniaSistema hotel;
    private String[] refeicoes;
    private String[] pedidos;

    @BeforeEach
    void initialize() {
    	this.hotel = new HotelCaliforniaSistema();
        hotel.cadastrarUsuario("ADM1", "Marcos Assunsão", "CLI", 666666666);
        hotel.cadastrarUsuario("ADM1", "Vini Junior", "GER", 666666667);
        hotel.cadastrarUsuario("ADM1", "Everton Ribeiro", "FUN", 2222);
        hotel.disponibilizarQuartoSingle("ADM1", 1, 100, 100);
        refeicoes = new String[2];
        pedidos = new String[2];
        pedidos[0] = "1";
        pedidos[1] = "2";
        refeicoes[0] = "1";
        refeicoes[1] = "2";
        hotel.disponibilizarRefeicao("GER1","CAFE_DA_MANHA","Cafe completo", LocalTime.of(6,30),LocalTime.of(9,30),500, true);
        hotel.disponibilizarRefeicao("GER1","ALMOCO","Filezinho", LocalTime.of(6,30),LocalTime.of(9,30),500, true);
        hotel.disponibilizarQuartoDouble("ADM1", 2, 100, 100, this.pedidos);
        hotel.disponibilizarQuartoFamily("ADM1", 3, 100, 100, this.pedidos, 5);
    }

    @Test
    void testReservaQuartoSingleGerente(){
        assertEquals(this.hotel.reservarQuartoSingle("GER1", "CLI1", 1, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00), this.refeicoes), "[1] Reserva de quarto em favor de:\n" +
                "- [CLI1] Marcos Assunsão (No. Doc. 666666666)\n" +
                "Detalhes da instalacao:\n" +
                "- [1] Quarto Single (custo basico: R$100,00; por pessoa: R$100,00 >>> R$200,00 diária)\n" +
                "Detalhes da reserva:\n" +
                "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n" +
                "- No. Hospedes: 01 pessoa(s)\n" +
                "- Refeicoes incluidas: [[1] Cafe-da-manha: Cafe completo (06h30 as 09h30). Valor por pessoa: R$500,00. VIGENTE., [2] Almoco: Filezinho (06h30 as 09h30). Valor por pessoa: R$500,00. VIGENTE.]\n" +
                "- Pedidos: null\n" +
                "VALOR TOTAL DA RESERVA: R$1.200,00 x2 (diarias) => R$2.400,00\n" +
                "SITUACAO DO PAGAMENTO: PENDENTE.");
    }

    @Test
    void testReservaQuartoSingleFuncionario(){
        assertEquals(this.hotel.reservarQuartoSingle("FUN1", "CLI1", 1, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00), this.refeicoes), "[1] Reserva de quarto em favor de:\n" +
                "- [CLI1] Marcos Assunsão (No. Doc. 666666666)\n" +
                "Detalhes da instalacao:\n" +
                "- [1] Quarto Single (custo basico: R$100,00; por pessoa: R$100,00 >>> R$200,00 diária)\n" +
                "Detalhes da reserva:\n" +
                "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n" +
                "- No. Hospedes: 01 pessoa(s)\n" +
                "- Refeicoes incluidas: [[1] Cafe-da-manha: Cafe completo (06h30 as 09h30). Valor por pessoa: R$500,00. VIGENTE., [2] Almoco: Filezinho (06h30 as 09h30). Valor por pessoa: R$500,00. VIGENTE.]\n" +
                "- Pedidos: null\n" +
                "VALOR TOTAL DA RESERVA: R$1.200,00 x2 (diarias) => R$2.400,00\n" +
                "SITUACAO DO PAGAMENTO: PENDENTE.");
    }

    @Test
    void testReservaQuartoSingleFailDataMininima(){
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarQuartoSingle("GER1", "CLI1", 1, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,26,12,00), this.refeicoes));
        assertTrue(hte.getMessage().contains("TEMPO DE RESERVA MINIMA DE 01 (UM) DIA"));
    }
    @Test
    void testReservaQuartoSingleDataInicioMaior(){
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarQuartoSingle("GER1", "CLI1", 1, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,26,12,00), this.refeicoes));
        assertTrue(hte.getMessage().contains("TEMPO DE RESERVA MINIMA DE 01 (UM) DIA"));
    }

    @Test
    void testReservaQuartoSingleClienteInexistente(){
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarQuartoSingle("GER1", "CLI5", 1, LocalDateTime.of(2023,12,22,20,00), LocalDateTime.of(2023,12,23,20,00), this.refeicoes));
        assertTrue(hte.getMessage().contains("USUARIO NAO EXISTE"));
    }

    @Test
    void testReservaQuartoSingleQuartoNaoDisponivel(){
        this.hotel.reservarQuartoSingle("GER1", "CLI1", 1, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00), this.refeicoes);
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarQuartoSingle("GER1", "CLI1", 1, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00), this.refeicoes));
        assertTrue(hte.getMessage().contains("JA EXISTE RESERVA PARA ESTA DATA"));
    }
    @Test
    void testReservaQuartoSingleSemPermissao(){
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarQuartoSingle("CLI1", "CLI1", 1, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00), this.refeicoes));
        assertTrue(hte.getMessage().contains("NAO E POSSIVEL PARA USUARIO CLIENTE CADASTRAR UMA RESERVA"));
    }

    @Test
    void testReservaQuartoSingleInexistente(){
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarQuartoSingle("GER1", "CLI1", 666, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00), this.refeicoes));
        assertTrue(hte.getMessage().contains("Quarto inexistente"));
    }

    @Test
    void testReservaQuartoDoubleGerente(){
        assertEquals(this.hotel.reservarQuartoDouble("GER1", "CLI1", 2, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00),this.pedidos, this.refeicoes), "[1] Reserva de quarto em favor de:\n" +
                "- [CLI1] Marcos Assunsão (No. Doc. 666666666)\n" +
                "Detalhes da instalacao:\n" +
                "- [2] Quarto Double (custo basico: R$100,00; por pessoa: R$100,00 >>> R$300,00 diária). Pedidos: [1, 2]\n" +
                "Detalhes da reserva:\n" +
                "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n" +
                "- No. Hospedes: 02 pessoa(s)\n" +
                "- Refeicoes incluidas: [[1] Cafe-da-manha: Cafe completo (06h30 as 09h30). Valor por pessoa: R$500,00. VIGENTE., [2] Almoco: Filezinho (06h30 as 09h30). Valor por pessoa: R$500,00. VIGENTE.]\n" +
                "- Pedidos: [1, 2]\n" +
                "VALOR TOTAL DA RESERVA: R$2.300,00 x2 (diarias) => R$4.600,00\n" +
                "SITUACAO DO PAGAMENTO: PENDENTE.");
    }

    @Test
    void testReservaQuartoDoubleFuncionario(){
        assertEquals(this.hotel.reservarQuartoDouble("FUN1", "CLI1", 2, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00),this.pedidos, this.refeicoes), "[1] Reserva de quarto em favor de:\n" +
                "- [CLI1] Marcos Assunsão (No. Doc. 666666666)\n" +
                "Detalhes da instalacao:\n" +
                "- [2] Quarto Double (custo basico: R$100,00; por pessoa: R$100,00 >>> R$300,00 diária). Pedidos: [1, 2]\n" +
                "Detalhes da reserva:\n" +
                "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n" +
                "- No. Hospedes: 02 pessoa(s)\n" +
                "- Refeicoes incluidas: [[1] Cafe-da-manha: Cafe completo (06h30 as 09h30). Valor por pessoa: R$500,00. VIGENTE., [2] Almoco: Filezinho (06h30 as 09h30). Valor por pessoa: R$500,00. VIGENTE.]\n" +
                "- Pedidos: [1, 2]\n" +
                "VALOR TOTAL DA RESERVA: R$2.300,00 x2 (diarias) => R$4.600,00\n" +
                "SITUACAO DO PAGAMENTO: PENDENTE.");
    }

    @Test
    void testReservaQuartoDoubleFailDataMininima(){
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarQuartoDouble("GER1", "CLI1", 2, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,26,12,00),this.pedidos, this.refeicoes));
        assertTrue(hte.getMessage().contains("TEMPO DE RESERVA MINIMA DE 01 (UM) DIA"));
    }

    @Test
    void testReservaQuartoDoubleDataInicioMaior(){
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarQuartoDouble("GER1", "CLI1", 2, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,26,12,00), this.pedidos, this.refeicoes));
        assertTrue(hte.getMessage().contains("TEMPO DE RESERVA MINIMA DE 01 (UM) DIA"));
    }

    @Test
    void testReservaQuartoDoubleClienteInexistente(){
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarQuartoDouble("GER1", "CLI5", 2, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,28,12,00), this.pedidos, this.refeicoes));
        assertTrue(hte.getMessage().contains("USUARIO NAO EXISTE"));
    }

    @Test
    void testReservaQuartoDoubleQuartoNaoDisponivel(){
        this.hotel.reservarQuartoDouble("GER1", "CLI1", 2, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00),this.pedidos, this.refeicoes);
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarQuartoDouble("GER1", "CLI1", 2, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00),this.pedidos, this.refeicoes));
        assertTrue(hte.getMessage().contains("JA EXISTE RESERVA PARA ESTA DATA"));
    }

    @Test
    void testReservaQuartoDoubleSemPermissao(){
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarQuartoDouble("CLI1", "CLI1", 2, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00),this.pedidos, this.refeicoes));
        assertTrue(hte.getMessage().contains("NAO E POSSIVEL PARA USUARIO CLIENTE CADASTRAR UMA RESERVA"));
    }

    @Test
    void testReservaQuartoDoubleInexistente(){
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarQuartoDouble("GER1", "CLI1", 666, LocalDateTime.of(2023,12,25,14,00), LocalDateTime.of(2023,12,27,12,00),this.pedidos, this.refeicoes));
        assertTrue(hte.getMessage().contains("Quarto inexistente"));
    }

    @Test
    void testReservaQuartoFamilyGerente() {
        assertEquals(this.hotel.reservarQuartoFamily("GER1", "CLI1", 3, LocalDateTime.of(2023, 12, 25, 14, 00), LocalDateTime.of(2023, 12, 27, 12, 00),this.pedidos, this.refeicoes, 4), "[1] Reserva de quarto em favor de:\n" +
                "- [CLI1] Marcos Assunsão (No. Doc. 666666666)\n" +
                "Detalhes da instalacao:\n" +
                "- [3] Quarto Family (custo basico: R$100,00; por pessoa: R$100,00 >>> R$600,00 diária). Capacidade: 05 pessoa(s). Pedidos: [1, 2]\n" +
                "Detalhes da reserva:\n" +
                "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n" +
                "- No. Hospedes: 04 pessoa(s)\n" +
                "- Refeicoes incluidas: [[1] Cafe-da-manha: Cafe completo (06h30 as 09h30). Valor por pessoa: R$500,00. VIGENTE., [2] Almoco: Filezinho (06h30 as 09h30). Valor por pessoa: R$500,00. VIGENTE.]\n" +
                "- Pedidos: [1, 2]\n" +
                "VALOR TOTAL DA RESERVA: R$4.600,00 x2 (diarias) => R$9.200,00\n" +
                "SITUACAO DO PAGAMENTO: PENDENTE.");
    }

    @Test
    void testReservaQuartoFamilyFuncionario() {
        assertEquals(this.hotel.reservarQuartoFamily("FUN1", "CLI1", 3, LocalDateTime.of(2023, 12, 25, 14, 00), LocalDateTime.of(2023, 12, 27, 12, 00),this.pedidos, this.refeicoes, 4), "[1] Reserva de quarto em favor de:\n" +
                "- [CLI1] Marcos Assunsão (No. Doc. 666666666)\n" +
                "Detalhes da instalacao:\n" +
                "- [3] Quarto Family (custo basico: R$100,00; por pessoa: R$100,00 >>> R$600,00 diária). Capacidade: 05 pessoa(s). Pedidos: [1, 2]\n" +
                "Detalhes da reserva:\n" +
                "- Periodo: 25/12/2023 14:00:00 ate 27/12/2023 12:00:00\n" +
                "- No. Hospedes: 04 pessoa(s)\n" +
                "- Refeicoes incluidas: [[1] Cafe-da-manha: Cafe completo (06h30 as 09h30). Valor por pessoa: R$500,00. VIGENTE., [2] Almoco: Filezinho (06h30 as 09h30). Valor por pessoa: R$500,00. VIGENTE.]\n" +
                "- Pedidos: [1, 2]\n" +
                "VALOR TOTAL DA RESERVA: R$4.600,00 x2 (diarias) => R$9.200,00\n" +
                "SITUACAO DO PAGAMENTO: PENDENTE.");
    }

    @Test
    void testReservaQuartoFamilyFailDataMinima() {
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarQuartoFamily("GER1", "CLI1", 3, LocalDateTime.of(2023, 12, 25, 14, 00), LocalDateTime.of(2023, 12, 26, 12, 00),this.pedidos, this.refeicoes, 4));
        assertTrue(hte.getMessage().contains("TEMPO DE RESERVA MINIMA DE 01 (UM) DIA"));
    }

    @Test
    void testReservaQuartoFamilyDataInicioMaior() {
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarQuartoFamily("GER1", "CLI1", 3, LocalDateTime.of(2023, 12, 25, 14, 00), LocalDateTime.of(2023, 12, 23, 12, 00),this.pedidos, this.refeicoes, 4));
        assertTrue(hte.getMessage().contains("DATA DE INICIO PRECISA SER POSTERIOR A DATA FINAL"));
    }

    @Test
    void testReservaQuartoFamilyClienteInexistente() {
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarQuartoFamily("GER1", "CLI5", 3, LocalDateTime.of(2023, 12, 23, 20, 00), LocalDateTime.of(2023, 12, 26, 12, 00),this.pedidos, this.refeicoes, 4));
        assertTrue(hte.getMessage().contains("USUARIO NAO EXISTE"));
    }

    @Test
    void testReservaQuartoFamilyQuartoNaoDisponivel() {
        this.hotel.reservarQuartoFamily("GER1", "CLI1", 3, LocalDateTime.of(2023, 12, 25, 14, 00), LocalDateTime.of(2023, 12, 27, 12, 00),this.pedidos, this.refeicoes, 4);
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarQuartoFamily("GER1", "CLI1", 3, LocalDateTime.of(2023, 12, 25, 14, 00), LocalDateTime.of(2023, 12, 27, 12, 00),this.pedidos, this.refeicoes, 4));
        assertTrue(hte.getMessage().contains("JA EXISTE RESERVA PARA ESTA DATA"));
    }

    @Test
    void testReservaQuartoFamilySemPermissao() {
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarQuartoFamily("CLI1", "CLI1", 3, LocalDateTime.of(2023, 12, 25, 14, 00), LocalDateTime.of(2023, 12, 27, 12, 00),this.pedidos, this.refeicoes, 4));
        assertTrue(hte.getMessage().contains("NAO E POSSIVEL PARA USUARIO CLIENTE CADASTRAR UMA RESERVA"));
    }

    @Test
    void testReservaQuartoFamilyQuantidadePessoasSuperior() {
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarQuartoFamily("GER1", "CLI1", 3, LocalDateTime.of(2023, 12, 25, 14, 00), LocalDateTime.of(2023, 12, 27, 12, 00),this.pedidos, this.refeicoes, 6));
        assertTrue(hte.getMessage().contains("CAPACIDADE EXCEDIDA"));
    }

    @Test
    void testReservaQuartoFamilyQuartoInexistente() {
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarQuartoFamily("GER1", "CLI1", 666, LocalDateTime.of(2023, 12, 25, 14, 00), LocalDateTime.of(2023, 12, 27, 12, 00),this.pedidos, this.refeicoes, 4));
        assertTrue(hte.getMessage().contains("Quarto inexistente"));
    }

    @Test
    void testExibicaoSemPermissao() {
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.exibirReserva("ADM1", 1));
        assertTrue(hte.getMessage().contains("NAO E POSSIVEL PARA USUARIO ADMINISTRADOR EXIBIR/LISTAR RESERVA(S) DO CLIENTE"));
    }

    @Test
    void testAdmNaoPodeExibirLista() {
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.exibirReserva("ADM1", 1));
        assertTrue(hte.getMessage().contains("NAO E POSSIVEL PARA USUARIO ADMINISTRADOR EXIBIR/LISTAR RESERVA(S) DO CLIENTE"));
    }

    @Test
    void testDisponibilizarRefeicaoGerente() {
        assertEquals(hotel.reservarRestaurante("GER1", "CLI1", LocalDateTime.of(2023, 12, 25, 14, 00), LocalDateTime.of(2023, 12, 26, 12, 00), 5, "1"), "[1] Reserva de RESTAURANTE em favor de:\n" +
                "- [CLI1] Marcos Assunsão (No. Doc. 666666666)\n" +
                "Detalhes da reserva:\n" +
                "- Periodo: 25/12/2023 06:30:00 ate 26/12/2023 09:30:00\n" +
                "- Qtde. de Convidados: 5 pessoa(s)\n" +
                "- Refeicao incluida: [1] Cafe-da-manha: Cafe completo (06h30 as 09h30). Valor por pessoa: R$500,00. VIGENTE.\n" +
                "VALOR TOTAL DA RESERVA: R$2.500,00 x2 (diarias) => R$5.000,00\n" +
                "SITUACAO DO PAGAMENTO: PENDENTE.");
    }

    @Test
    void testDisponibilizarRefeicaoSemPermissao() {
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarRestaurante("ADM1", "CLI1", LocalDateTime.of(2023, 12, 25, 14, 00), LocalDateTime.of(2023, 12, 26, 12, 00), 5, "1"));
        assertTrue(hte.getMessage().contains("NAO E POSSIVEL PARA USUARIO ADMINISTRADOR CADASTRAR UMA RESERVA"));
    }

    @Test
    void testDisponibilizarRefeicaoUsuarioInexistente() {
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarRestaurante("GER1", "CLI21", LocalDateTime.of(2023, 12, 25, 14, 00), LocalDateTime.of(2023, 12, 26, 12, 00), 5, "1"));
        assertTrue(hte.getMessage().contains("USUARIO NAO EXISTE"));
    }

    @Test
    void testDisponibilizarRefeicaoNaoExiste() {
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarRestaurante("GER1", "CLI1", LocalDateTime.of(2023, 12, 25, 14, 00), LocalDateTime.of(2023, 12, 26, 12, 00), 5, "12"));
        assertTrue(hte.getMessage().contains("REFEICAO NAO EXISTE"));
    }

    @Test
    void testDisponibilizarRefeicaoNaCapacidadeExcedida() {
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarRestaurante("GER1", "CLI1", LocalDateTime.of(2023, 12, 25, 14, 00), LocalDateTime.of(2023, 12, 26, 12, 00), 55, "1"));
        assertTrue(hte.getMessage().contains("CAPACIDADE EXCEDIDA"));
    }

    @Test
    void testDisponibilizaRefeicaoCapacidadeMaxima() {
        assertEquals(hotel.reservarRestaurante("GER1", "CLI1", LocalDateTime.of(2023, 12, 25, 14, 00), LocalDateTime.of(2023, 12, 26, 12, 00), 50, "1"), "[1] Reserva de RESTAURANTE em favor de:\n" +
                "- [CLI1] Marcos Assunsão (No. Doc. 666666666)\n" +
                "Detalhes da reserva:\n" +
                "- Periodo: 25/12/2023 06:30:00 ate 26/12/2023 09:30:00\n" +
                "- Qtde. de Convidados: 50 pessoa(s)\n" +
                "- Refeicao incluida: [1] Cafe-da-manha: Cafe completo (06h30 as 09h30). Valor por pessoa: R$500,00. VIGENTE.\n" +
                "VALOR TOTAL DA RESERVA: R$25.000,00 x2 (diarias) => R$50.000,00\n" +
                "SITUACAO DO PAGAMENTO: PENDENTE.");
    }

    @Test
    void testDisponibilizarRefeicaoSemAntecedenciaMinima() {
        HotelCaliforniaException hte = assertThrows(HotelCaliforniaException.class, () -> this.hotel.reservarRestaurante("GER1", "CLI1", LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2), 5, "1"));
        assertTrue(hte.getMessage().contains("NECESSARIO ANTECEDENCIA MINIMA DE 01 (UM) DIA"));
    }

    @Test
    void testListarRefeicoes(){
        String[] t = new String[2];
        t[0] = hotel.exibirRefeicao(1);
        t[1] = hotel.exibirRefeicao(2);
        assertArrayEquals(hotel.listarRefeicoes(), t);
    }

}