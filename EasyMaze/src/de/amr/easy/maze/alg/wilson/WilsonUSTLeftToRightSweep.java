package de.amr.easy.maze.alg.wilson;

import static de.amr.easy.grid.api.GridPosition.TOP_LEFT;

import java.util.stream.Stream;

import de.amr.easy.graph.api.TraversalState;
import de.amr.easy.grid.api.Grid2D;
import de.amr.easy.grid.iterators.traversals.LeftToRightSweep;

/**
 * Wilson's algorithm where the vertices are selected column-wise left-to-right.
 * 
 * @author Armin Reichert
 */
public class WilsonUSTLeftToRightSweep extends WilsonUST {

	public WilsonUSTLeftToRightSweep(Grid2D<TraversalState,Integer> grid) {
		super(grid);
	}

	@Override
	protected Stream<Integer> cellStream() {
		return new LeftToRightSweep(grid).stream();
	}

	@Override
	protected Integer customStartCell(Integer start) {
		return grid.cell(TOP_LEFT);
	}
}