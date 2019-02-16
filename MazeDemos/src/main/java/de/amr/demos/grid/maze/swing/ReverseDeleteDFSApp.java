package de.amr.demos.grid.maze.swing;

import static de.amr.graph.grid.ui.animation.BFSAnimation.floodFill;

import java.util.stream.IntStream;

import de.amr.demos.grid.SwingGridSampleApp;
import de.amr.graph.grid.impl.OrthogonalGrid;
import de.amr.maze.alg.core.MazeGenerator;
import de.amr.maze.alg.mst.ReverseDeleteMST_DFS;

public class ReverseDeleteDFSApp extends SwingGridSampleApp {

	public static void main(String[] args) {
		launch(new ReverseDeleteDFSApp());
	}

	public ReverseDeleteDFSApp() {
		super(128);
		setAppName("Reverse-Delete-MST Maze (DFS)");
	}

	@Override
	public void run() {
		IntStream.of(128, 64, 32).forEach(cellSize -> {
			setCellSize(cellSize);
			MazeGenerator<OrthogonalGrid> generator = new ReverseDeleteMST_DFS(getCanvas().getWidth() / cellSize,
					getCanvas().getHeight() / cellSize);
			setGrid(generator.getGrid());
			generator.createMaze(0, 0);
			floodFill(getCanvas(), 0, false);
			sleep(1000);
		});
		System.exit(0);
	}
}