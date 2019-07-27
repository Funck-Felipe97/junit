package br.ce.wcaquino.servicos;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class CalculadoraTest {

	@Mock
	private Calculadora calcMock;
	
	@Spy
	private Calculadora calcSpy;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Spy necessita de uma implementação real para ser executado
	 */
	@Test
	public void deveMostrarDiferencaEntreMockESpy() {
		System.out.println("Calc mock: " + calcMock.somar(1, 2));  // retorna implementação padrão do mockito 
		System.out.println("Calc spy:" + calcSpy.somar(1, 2));     // retorna implementação real do método
		
		doCallRealMethod().when(calcMock).somar(1, 2);
		System.out.println("Cal mock chamando metodo real: " + calcMock.somar(1, 2));
		
		doReturn(0).when(calcSpy).somar(1, 2);         
		System.out.println("Calc spy sem fazer nada:" + calcSpy.somar(1, 2));
	}
	
	@Test
	public void somarDoisValores() {
		// cenario
		int a = 5, b = 3;
		doReturn(8).when(calcMock).somar(5, 3);
		
		// ação
		int resultado = calcMock.somar(a, b);

		// verificação
		assertEquals(8, resultado);
	}

	@Test
	public void subtrairDoisValores() {
		// cenario
		int a = 5, b = 3;
		doReturn(2).when(calcMock).subtrairr(5, 3);
		
		// ação
		int resultado = calcMock.subtrairr(a, b);

		// verificação
		assertEquals(2, resultado);
	}

}
