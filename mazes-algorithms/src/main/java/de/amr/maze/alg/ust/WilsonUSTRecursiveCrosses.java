package de.amr.maze.alg.ust;

import java.util.stream.IntStream;

import de.amr.datastruct.StreamUtils;
import de.amr.graph.core.api.TraversalState;
import de.amr.graph.grid.api.GridGraph2D;
import de.amr.graph.grid.traversals.RecursiveCrosses;

/**
 * Wilson's algorithm where the vertices are selected from recursive crosses.
 * 
 * @author Armin Reichert
 */
public class WilsonUSTRecursiveCrosses extends WilsonUST {

	public WilsonUSTRecursiveCrosses(GridGraph2D<TraversalState, Integer> grid) {
		super(grid);
	}

	@Override
	protected IntStream randomWalkStartCells() {
		return StreamUtils.toIntStream(new RecursiveCrosses(grid));
	}
}