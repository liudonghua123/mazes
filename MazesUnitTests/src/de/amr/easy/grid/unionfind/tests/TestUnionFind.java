package de.amr.easy.grid.unionfind.tests;

import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import de.amr.easy.data.Partition;
import de.amr.easy.data.PartitionComp;

public class TestUnionFind {

	@Test
	public void testEmptyPartition() {
		Partition<Integer> p = new Partition<>();
		assertEquals(0, p.size());
	}

	@Test
	public void testSingletonsPartition() {
		Partition<Integer> p = new Partition<>(1, 2, 3);
		assertEquals(3, p.size());
	}

	@Test
	public void testUnion() {
		Partition<Integer> p1 = new Partition<>(1, 2, 3);
		assertEquals(3, p1.size());
		p1.union(1, 2);
		assertEquals(2, p1.size());
		PartitionComp<Integer> c1 = p1.find(1);
		assertEquals(2, c1.size());
		assertEquals(new HashSet<>(Arrays.asList(1, 2)), c1.elements().collect(toSet()));
	}
}
