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
		
		// a��o
		int resultado = calc.somar(a, b);

		// verifica��o
		assertEquals(8, resultado);
	}

	@Test
	public void subtrairDoisValores() {
		// cenario
		int a = 5, b = 3;
		
		// a��o
		int resultado = calc.subtrairr(a, b);

		// verifica��o
		assertEquals(2, resultado);
	}

}
