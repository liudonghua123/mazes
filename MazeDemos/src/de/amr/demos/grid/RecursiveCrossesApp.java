package de.amr.demos.grid;

import static de.amr.easy.graph.api.TraversalState.COMPLETED;

import java.util.stream.IntStream;

import de.amr.easy.grid.impl.Top4;
import de.amr.easy.grid.iterators.traversals.RecursiveCrosses;

public class RecursiveCrossesApp extends SwingGridSampleApp {

	public static void main(String[] args) {
		launch(new RecursiveCrossesApp());
	}

	public RecursiveCrossesApp() {
		super(64, Top4.get());
		setAppName("Recursive Crosses");
	}

	@Override
	public void run() {
		IntStream.of(64, 32, 16, 8, 4, 2).forEach(cellSize -> {
			canvasAnimation.setDelay(cellSize > 4 ? 1 : 0);
			resizeGrid(cellSize);
			new RecursiveCrosses(grid).forEach(cell -> grid.set(cell, COMPLETED));
			sleep(1000);
		});
		System.exit(0);
	}
}