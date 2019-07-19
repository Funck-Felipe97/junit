package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;

import br.ce.wcaquino.dao.LocacaoDao;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

	public LocacaoService service;
	
	@Parameter
	public List<Filme> filmes;
	
	@Parameter(value = 1)
	public Double valorLocacao;
	
	@Parameter(value = 2)
	public String descricao;
	
	private static Filme filme1 = new Filme("Filme 1", 2, 4.0);
	private static Filme filme2 = new Filme("Filme 2", 2, 4.0);
	private static Filme filme3 = new Filme("Filme 3", 2, 4.0);
	private static Filme filme4 = new Filme("Filme 4", 2, 4.0);
	private static Filme filme5 = new Filme("Filme 5", 2, 4.0);

	@Before
	public void stup() {
		System.out.println("Before");
		service = new LocacaoService();
		service.setDao(Mockito.mock(LocacaoDao.class));
	}

	@Parameters(name = "Teste {index} = {2}")
	public static Collection<Object[]> getParametros() {
		return Arrays.asList(new Object[][] { 
			{Arrays.asList(filme1, filme2, filme3), 11.0D, "3 filmes: 25%"},
			{Arrays.asList(filme1, filme2, filme3, filme4), 13.0D, "4 filmes: 50%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.0D, "5 filmes: 75%"},
			{Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.0D, "6 filmes: 10%"}
		});
	}

	@Test
	public void deveCalcularValorLocacaoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException {
		// cenário
		Usuario usuario = new Usuario("Usuario 1");

		// ação
		Locacao resultado = service.alugarFilme(usuario, filmes);

		// verificação
		assertThat(resultado.getValor(), is(valorLocacao));
	}

}
