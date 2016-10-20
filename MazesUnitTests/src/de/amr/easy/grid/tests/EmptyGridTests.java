package de.amr.easy.grid.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.amr.easy.grid.api.NakedGrid2D;
import de.amr.easy.grid.impl.NakedGrid;

public class EmptyGridTests {

	private NakedGrid2D grid;

	@Before
	public void setUp() {
		grid = new NakedGrid(0, 0);
	}

	@After
	public void tearDown() {

	}

	@Test
	public void testGridSize() {
		assertEquals(grid.edgeCount(), 0);
		assertEquals(grid.vertexCount(), 0);
		assertEquals(grid.numCols(), 0);
		assertEquals(grid.numRows(), 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGridAccessException() {
		grid.cell(0, 0);
	}

	@Test
	public void testGridEdgeStream() {
		assertTrue(grid.edgeStream().count() == 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGridEdgeAccess() {
		grid.edge(0, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGridEdgeAdd() {
		grid.addEdge(0, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGridVertexDegree() {
		grid.degree(0);
	}
}