package br.ce.wcaquino.servicos.matchers;

import java.util.Date;

import org.hamcrest.Description;
import org.junit.internal.matchers.TypeSafeMatcher;

import br.ce.wcaquino.utils.DataUtils;

@SuppressWarnings("deprecation")
public class DataDiferencaDiasMatcher extends TypeSafeMatcher<Date>{

	private Integer qtdDias;
	
	public DataDiferencaDiasMatcher(Integer qtdDias) {
		this.qtdDias = qtdDias;
	}
	
	@Override
	public void describeTo(Description description) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean matchesSafely(Date item) {
		return DataUtils.isMesmaData(item, DataUtils.obterDataComDiferencaDias(qtdDias));
	}

}
