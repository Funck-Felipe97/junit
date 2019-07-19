package br.ce.wcaquino.servicos.matchers;

import java.util.Calendar;

public class MatchersProprios {

	public static DiaSemanaMatcher caiEm(Integer diaSemana) {
		return new DiaSemanaMatcher(diaSemana);
	}
	
	public static DiaSemanaMatcher caiNumaSegundaFeira() {
		return new DiaSemanaMatcher(Calendar.MONDAY);
	}
	
	public static DataDiferencaDiasMatcher ehHojeComDiferencaoDias(Integer qtdDias) {
		return new DataDiferencaDiasMatcher(qtdDias);
	}
	
	public static DataDiferencaDiasMatcher ehHoje() {
		return new DataDiferencaDiasMatcher(0);
	}
	
}
