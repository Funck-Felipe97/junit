package br.ce.wcaquino.servicos;

import org.junit.Test;
import org.mockito.Mockito;

public class CalculadoreaMockTest {

	@Test
	public void testCalculadorMock() {
		Calculadora calculadora = Mockito.mock(Calculadora.class);
		Mockito.when(calculadora.somar(1, 2)).thenReturn(5);
		
		System.out.println(calculadora.somar(1, 2));  // 5
		System.out.println(calculadora.somar(1, 7));  // 8
	}
	
}
