package de.amr.demos.grid;

import static de.amr.easy.graph.api.TraversalState.COMPLETED;
import static java.util.stream.IntStream.of;

import de.amr.easy.grid.iterators.traversals.RecursiveCrosses;

public class RecursiveCrossesApp extends GridSampleApp {

	public static void main(String[] args) {
		launch(new RecursiveCrossesApp());
	}

	public RecursiveCrossesApp() {
		super("Recursive Crosses", 2);
	}

	@Override
	public void run() {
		setDelay(4);
		of(64, 32, 16, 8, 4, 2).forEach(cellSize -> {
			setCellSize(cellSize);
			new RecursiveCrosses(grid).forEach(cell -> grid.set(cell, COMPLETED));
			sleep(1000);
			clear();
		});
		System.exit(0);
	}
}