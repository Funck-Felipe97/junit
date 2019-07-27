package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.servicos.matchers.MatchersProprios.caiEm;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.dao.LocacaoDao;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {

	@InjectMocks
	private LocacaoService service;

	@Mock
	private LocacaoDao locacaoDao;

	@Mock
	private SPCService spcService;

	@Mock
	private EmailService emailService;

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void stup() {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() {
		System.out.println("After");
	}

	@BeforeClass
	public static void stupClass() {
		System.out.println("Before class");
	}

	@AfterClass
	public static void tearDownClass() {
		System.out.println("After class");
	}

	@Test
	public void teste() throws Exception {
		assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

		// cenário
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);

		// ação
		Locacao locacao;

		locacao = service.alugarFilme(usuario, Arrays.asList(filme));
		// verificação
		assertTrue(locacao.getValor() == 5.0);
		assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));

		assertThat(locacao.getValor(), is(5.0));
		assertThat(locacao.getValor(), not(6.0));
		assertThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));

		error.checkThat(locacao.getValor(), is(5.0));
		error.checkThat(locacao.getValor(), not(6.0));
	}

	@Test(expected = FilmeSemEstoqueException.class)
	public void testLocacao_filmeSemEstoque() throws Exception {
		// cenário
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// ação
		service.alugarFilme(usuario, Arrays.asList(filme));
	}

	@Test
	public void testLocacao_filmeSemEstoque2() {
		// cenário
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// ação
		try {
			service.alugarFilme(usuario, Arrays.asList(filme));
			fail("Deveria ter lançado uma exceção");
		} catch (Exception e) {
			assertThat(e.getMessage(), is("Filme sem estoque"));
		}
	}

	@Test
	public void testLocacao_filmeSemEstoque3() throws Exception {
		// cenário
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		exception.expect(Exception.class);
		exception.expectMessage("Filme sem estoque");

		// ação
		service.alugarFilme(usuario, Arrays.asList(filme));
	}

	@Test
	public void testLocacao_usuarioVazio() throws FilmeSemEstoqueException {
		// cenário
		Filme filme = new Filme("Filme 1", 1, 5.0);

		try {
			service.alugarFilme(null, Arrays.asList(filme));
			fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio"));
			e.printStackTrace();
		}
	}

	@Test
	public void devePagar75PcNoFilme3() throws FilmeSemEstoqueException, LocadoraException {
		// cenário
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0));

		// ação
		Locacao resultado = service.alugarFilme(usuario, filmes);

		// verificação
		assertThat(11.0, equalTo(resultado.getValor()));
	}

	@Test
	public void devePagar50PcNoFilme4() throws FilmeSemEstoqueException, LocadoraException {
		// cenário
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0));

		// ação
		Locacao resultado = service.alugarFilme(usuario, filmes);

		// verificação
		assertThat(13.0, equalTo(resultado.getValor()));
	}

	@Test
	public void devePagar25PcNoFilme5() throws FilmeSemEstoqueException, LocadoraException {
		// cenário
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0), new Filme("Filme 5", 2, 4.0));

		// ação
		Locacao resultado = service.alugarFilme(usuario, filmes);

		// verificação
		assertThat(14.0, equalTo(resultado.getValor()));
	}

	@Test
	public void devePagar0PcNoFilme6() throws FilmeSemEstoqueException, LocadoraException {
		// cenário
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0), new Filme("Filme 5", 2, 4.0));

		// ação
		Locacao resultado = service.alugarFilme(usuario, filmes);

		// verificação
		assertThat(14.0, equalTo(resultado.getValor()));
	}

	@Test
	public void naoDeveDevolverFilmeNoDomingo() throws FilmeSemEstoqueException, LocadoraException {
		assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

		// cenário
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0));

		// ação
		Locacao resultado = service.alugarFilme(usuario, filmes);

		// verificação
		assertThat(resultado.getDataRetorno(), caiEm(Calendar.MONDAY));
	}

	@Test
	public void naoDeveAlugarFilmeUsuarioNegativado() throws FilmeSemEstoqueException, LocadoraException {
		// cenário
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0));

		exception.expect(LocadoraException.class);
		exception.expectMessage("Usuario negativado");

		Mockito.when(spcService.usuarioNegativado(usuario)).thenReturn(true);

		// ação
		service.alugarFilme(usuario, filmes);

		// verificação
		Mockito.verify(spcService).usuarioNegativado(usuario);
	}

	@Test
	public void deveNotificarAtrasosLocacao() throws FilmeSemEstoqueException, LocadoraException {
		// cenário
		Usuario usuario = new Usuario("Usuario 1");
		Usuario usuario2 = new Usuario("Usuario 2");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0));
		Locacao locacao = service.alugarFilme(usuario, filmes);
		locacao.setDataRetorno(DataUtils.adicionarDias(new Date(), -2));

		// ação
		Mockito.when(locacaoDao.obterLocacoesAtrasadas()).thenReturn(Arrays.asList(locacao));
		service.notificarAtrasos();

		// verificação
		Mockito.verify(emailService).notificarAtrasos(usuario);
		// Mockito.verify(service.getEmailService(),
		// Mockito.times(2)).notificarAtrasos(usuario); verifica a quantidade de vezes
		// Mockito.verify(service.getEmailService(),
		// Mockito.times(2)).notificarAtrasos(Mockito.any(Usuario.class)); verifica se
		// houve pelo menos 2 chamadas para qualquer tipo de usuário
		Mockito.verify(emailService, Mockito.never()).notificarAtrasos(usuario2); // verifica se o método do mock não
																					// foi chamado para o usuário 2
		Mockito.verifyNoMoreInteractions(emailService); // verifica se não houve mais chamadas no método de envio de
														// email
	}

	// Capturando argumentos
	@Test
	public void deveProrrogarLocacao() throws FilmeSemEstoqueException, LocadoraException {
		// cenário
		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0), new Filme("Filme 5", 2, 4.0));
		Locacao locacao = service.alugarFilme(usuario, filmes);
		
		service.prorrogarLocacao(locacao, 3);
		
		ArgumentCaptor<Locacao> argumentCaptor = ArgumentCaptor.forClass(Locacao.class);
		Mockito.verify(locacaoDao).salvar(argumentCaptor.capture());
		Locacao locacao2 = argumentCaptor.capture();
		
	}

}
