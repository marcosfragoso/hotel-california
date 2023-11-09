package br.edu.ufcg.p2lp2.hotelcalifornia;

import br.edu.ufcg.p2lp2.hotelcalifornia.entities.usuarios.*;
import br.edu.ufcg.p2lp2.hotelcalifornia.exception.HotelCaliforniaException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.fail;

public class US01Test {
    private HotelCaliforniaSistema hotel;
    private Gerente gerPatente;
    private Funcionario funPatente;
    private Cliente cliPatente;
    @BeforeEach
    void setup() {
    	this.hotel = new HotelCaliforniaSistema();
        hotel.cadastrarUsuario("ADM1", "Arabio Saudito", "CLI", 123456);
        hotel.cadastrarUsuario("ADM1", "Claudio Arraio", "FUN", 55555);
        hotel.cadastrarUsuario("ADM1", "Caldo de Cano", "GER", 44444);
    }
    @Test
    public void admCadastraFuncionarioComSucesso() {
        String resultadoEsperado = "[FUN2]";
        Assertions.assertEquals(resultadoEsperado, hotel.cadastrarUsuario("ADM1", "Ludmilo", "FUN", 123456));
    }

    @Test
    public void gerenteCadastraFuncionarioComSucesso() {
        String resultadoEsperado = "[FUN2]";
        Assertions.assertEquals(resultadoEsperado, hotel.cadastrarUsuario("GER1", "Ludmilo", "FUN", 123456));
    }

    @Test
    public void clienteNaoCadastraFuncionario() {
        try {
            hotel.cadastrarUsuario("CLI1", "Ludmilo", "FUN", 123456);
            fail("Permissão negada");
        } catch (HotelCaliforniaException hce) {

        }
    }

    @Test
    public void funcionarioNaoCadastraFuncionario() {
        try {
            hotel.cadastrarUsuario("FUN1", "Ludmilo", "GUN", 123456);
            fail("Permissão negada");
        } catch (HotelCaliforniaException hce) {

        }
    }

    @Test
    public void admCadastraClienteComSucesso() {
        String resultadoEsperado = "[CLI2]";
        Assertions.assertEquals(resultadoEsperado, hotel.cadastrarUsuario("ADM1", "Ludmilo", "CLI", 123456));
    }

    @Test
    public void funcionarioCadastraClienteComSucesso() {
        String resultadoEsperado = "[CLI2]";
        Assertions.assertEquals(resultadoEsperado, hotel.cadastrarUsuario("FUN1", "Ludmilo", "CLI", 123456));
    }

    @Test
    public void gerenteCadastraClienteComSucesso() {
        String resultadoEsperado = "[CLI2]";
        Assertions.assertEquals(resultadoEsperado, hotel.cadastrarUsuario("GER1", "Ludmilo", "CLI", 123456));
    }

    @Test
    public void clienteNaoCadastraCliente() {
        try {
            hotel.cadastrarUsuario("CLI1", "Ludmilo", "CLI", 123456);
            fail("Permissão negada");
        } catch (HotelCaliforniaException hce) {

        }
    }

    @Test
    public void admTentaCadastrarGerenteNovamente() {
        try {
            hotel.cadastrarUsuario("ADM1", "Ludmilo", "GER", 123456);
            fail("Gerente já existe");
        } catch (HotelCaliforniaException hce) {

        }

    }

    @Test
    public void funcionarioNaoCadastraGerente() {
        try {
            hotel.cadastrarUsuario("FUN1", "Ludmilo", "GER", 123456);
            fail("Permissão negada");
        } catch (HotelCaliforniaException hce) {

        }

    }

    @Test
    public void clienteNaoCadastraGerente() {
        try {
            hotel.cadastrarUsuario("CLI1", "Ludmilo", "GER", 123456);
            fail("Permissão negada");
        } catch (HotelCaliforniaException hce) {

        }
    }

    @Test
    public void gerenteNaoCadastraGerente() {
        try {
            hotel.cadastrarUsuario("GER1", "Ludmilo", "GER", 123456);
            fail("Permissão negada");
        } catch (HotelCaliforniaException hce) {

        }
    }
    @Test
    public void admCadastraAdmComSucesso() {
        String resultadoEsperado = "[ADM2]";
        Assertions.assertEquals(resultadoEsperado, hotel.cadastrarUsuario("ADM1", "Ludmilo", "ADM", 123456));
    }

    @Test
    public void funcionarioNaoCadastraAdm() {
        try {
            hotel.cadastrarUsuario("FUN1", "Ludmilo", "ADM", 123456);
            fail("Permissão negada");
        } catch (HotelCaliforniaException hce) {

        }
    }

    @Test
    public void clienteNaoCadastraAdm() {
        try {
            hotel.cadastrarUsuario("CLI1", "Ludmilo", "ADM", 123456);
            fail("Permissão negada");
        } catch (HotelCaliforniaException hce) {

        }
    }

    @Test
    public void gerenteNaoCadastraAdm() {
        try {
            hotel.cadastrarUsuario("GER1", "Ludmilo", "ADM", 123456);
            fail("Permissão negada");
        } catch (HotelCaliforniaException hce) {

        }
    }

    @Test
    public void visualizaAdministradorDefault() {
        String resultadoEsperado = "[ADM1] Vini Malvadeza (No. Doc. 1234)";
        Assertions.assertEquals(resultadoEsperado, hotel.exibirUsuario("ADM1"));
    }
    
    @Test
    public void listarTodosOsUsuarios() {
        String[] users = new String[4];
        Usuario u1 = new Usuario("CLI1","Arabio Saudito", "CLI", 123456);
        u1.addPatente(cliPatente);
        Usuario u2 = new Usuario("FUN1","Claudio Arraio", "FUN", 55555);
        u2.addPatente(funPatente);
        Usuario u3 = new Usuario("GER1","Caldo de Cano", "GER", 44444);
        u3.addPatente(gerPatente);
        users[0] = "[ADM1] Vini Malvadeza (No. Doc. 1234)";
        users[1] = u1.toString();
        users[2] = u2.toString();
        users[3] = u3.toString();

        Arrays.sort(users);
        String[] resultadoObtido = hotel.listarUsuarios();
        Arrays.sort(resultadoObtido);

        Assertions.assertArrayEquals(users, resultadoObtido);
    }
}