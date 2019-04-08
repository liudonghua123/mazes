package de.amr.maze.alg.traversal;

import java.util.List;

import de.amr.graph.core.api.TraversalState;
import de.amr.graph.grid.api.GridGraph2D;

/**
 * Growing tree algorithm where either the last or a random vertex is selected from the frontier.
 * 
 * @author Armin Reichert
 */
public class GrowingTreeLastOrRandom extends GrowingTree {

	public GrowingTreeLastOrRandom(GridGraph2D<TraversalState, Integer> grid) {
		super(grid);
	}

	@Override
	protected int selectCell(List<Integer> frontier) {
		return frontier.remove(rnd.nextBoolean() ? frontier.size() - 1 : rnd.nextInt(frontier.size()));
	}
}