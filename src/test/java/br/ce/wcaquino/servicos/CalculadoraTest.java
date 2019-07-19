package br.ce.wcaquino.servicos;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CalculadoraTest {

	private Calculadora calc;
	
	@Before
	public void init() {
		calc = new Calculadora();
	}
	
	@Test
	public void somarDoisValores() {
		// cenario
		int a = 5, b = 3;
		
		// ação
		int resultado = calc.somar(a, b);

		// verificação
		assertEquals(8, resultado);
	}

	@Test
	public void subtrairDoisValores() {
		// cenario
		int a = 5, b = 3;
		
		// ação
		int resultado = calc.subtrairr(a, b);

		// verificação
		assertEquals(2, resultado);
	}

}
