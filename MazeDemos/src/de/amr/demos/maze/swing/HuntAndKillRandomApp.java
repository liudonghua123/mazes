package de.amr.demos.maze.swing;

import static de.amr.easy.grid.api.GridPosition.TOP_LEFT;

import java.util.stream.IntStream;

import de.amr.demos.grid.swing.core.SwingBFSAnimation;
import de.amr.demos.grid.swing.core.SwingGridSampleApp;
import de.amr.easy.maze.alg.HuntAndKillRandom;

public class HuntAndKillRandomApp extends SwingGridSampleApp {

	public static void main(String[] args) {
		launch(new HuntAndKillRandomApp());
	}

	public HuntAndKillRandomApp() {
		super(128);
		setAppName("Hunt And Kill"); 
	}

	@Override
	public void run() {
		IntStream.of(128, 64, 32, 16, 8, 4, 2).forEach(cellSize -> {
			resizeGrid(cellSize);
			new HuntAndKillRandom(grid).run(grid.cell(TOP_LEFT));
			new SwingBFSAnimation(canvas, grid).run(grid.cell(TOP_LEFT));
			sleep(1000);
		});
		System.exit(0);
	}
}