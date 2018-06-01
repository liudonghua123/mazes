package de.amr.demos.maze.swing;

import static de.amr.easy.grid.api.GridPosition.TOP_LEFT;

import java.util.stream.IntStream;

import de.amr.easy.graph.traversal.BreadthFirstTraversal;
import de.amr.easy.grid.impl.Top4;
import de.amr.easy.grid.ui.swing.SwingBFSAnimation;
import de.amr.easy.grid.ui.swing.SwingGridSampleApp;
import de.amr.easy.maze.alg.BinaryTree;

public class BinaryTreeApp extends SwingGridSampleApp {

	public static void main(String[] args) {
		launch(new BinaryTreeApp());
	}

	public BinaryTreeApp() {
		super(128, Top4.get());
		setAppName("Binary Tree Maze");
	}

	@Override
	public void run() {
		IntStream.of(128, 64, 32, 16, 8, 4, 2).forEach(cellSize -> {
			resizeGrid(cellSize);
			new BinaryTree(grid).run(grid.cell(TOP_LEFT));
			new SwingBFSAnimation(grid, canvas).run( new BreadthFirstTraversal(grid, grid.cell(TOP_LEFT)));
			sleep(1000);
		});
		System.exit(0);
	}
}