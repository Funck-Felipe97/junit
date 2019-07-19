package br.ce.wcaquino.servicos.matchers;

import java.util.Date;

import org.hamcrest.Description;
import org.junit.internal.matchers.TypeSafeMatcher;

import br.ce.wcaquino.utils.DataUtils;

@SuppressWarnings("deprecation")
public class DiaSemanaMatcher extends TypeSafeMatcher<Date> {

	private Integer diaSemana;
	
	public DiaSemanaMatcher(Integer diaSemana) {
		this.diaSemana = diaSemana;
	}
	
	@Override
	public void describeTo(Description description) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean matchesSafely(Date item) {
		return DataUtils.verificarDiaSemana(item, diaSemana);
	}

}
