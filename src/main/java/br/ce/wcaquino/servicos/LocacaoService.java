package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.ce.wcaquino.dao.LocacaoDao;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoService {
	
	private LocacaoDao dao;
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {
		if (filmes == null || filmes.isEmpty()) {
			throw new LocadoraException("Filme vazio");
		}
		
		for(Filme filme: filmes) {
			if (filme.getEstoque() <= 0) {
				throw new FilmeSemEstoqueException("Filme sem estoque");
			}
		}
		
		if (usuario == null) {
			throw new LocadoraException("Usuario vazio");
		}
		
		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		
		Double precoLocacao = 0D;
		for (int i = 0; i < filmes.size(); ++i) {
			if (i == 2)
				precoLocacao += filmes.get(i).getPrecoLocacao() * 0.75;
			else if (i == 3)
				precoLocacao += filmes.get(i).getPrecoLocacao() * 0.50;
			else if (i == 4)
				precoLocacao += filmes.get(i).getPrecoLocacao() * 0.25;
			else if (i == 5)
				precoLocacao += 0;
			else
				precoLocacao += filmes.get(i).getPrecoLocacao();
		}
		locacao.setValor(precoLocacao);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		
		if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
			dataEntrega = adicionarDias(dataEntrega, 1);
		}
		
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		dao.salvar(locacao);
		
		return locacao;
	}
	
	public void setDao(LocacaoDao dao) {
		this.dao = dao;
	}
	
}