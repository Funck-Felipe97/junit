package br.ce.wcaquino.testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.ce.wcaquino.entidades.Usuario;

public class AssertTest {

	@Test
	public void test() {
		assertTrue(true);
		assertFalse("Falso", false);
		
		assertEquals(1, 1);
		assertEquals(0.51, 0.52, 0.1);
		assertEquals("felipe", "felipe");
		assertNotEquals("felipe", "funck");
		
		Usuario u1 = new Usuario("User 1");
		Usuario u2 = new Usuario("User 1");
		assertEquals(u1, u2);
		
		// Trabalha somente com instancias iguais
		assertSame(u1, u1);
		assertNotSame(u1, u2);
	}
	
}
