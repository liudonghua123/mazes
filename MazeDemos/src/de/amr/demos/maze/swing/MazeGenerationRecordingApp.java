package de.amr.demos.maze.swing;

import static java.lang.String.format;

import java.awt.Color;

import de.amr.demos.grid.swing.core.DefaultGridRenderingModel;
import de.amr.demos.grid.swing.core.GridCanvas;
import de.amr.demos.maze.swing.tools.GridGifRecorder;
import de.amr.easy.graph.api.TraversalState;
import de.amr.easy.grid.api.Grid2D;
import de.amr.easy.grid.api.ObservableGrid2D;
import de.amr.easy.grid.impl.ObservableGrid;
import de.amr.easy.maze.alg.BinaryTree;
import de.amr.easy.maze.alg.BinaryTreeRandom;
import de.amr.easy.maze.alg.Eller;
import de.amr.easy.maze.alg.EllerInsideOut;
import de.amr.easy.maze.alg.GrowingTree;
import de.amr.easy.maze.alg.HuntAndKill;
import de.amr.easy.maze.alg.HuntAndKillRandom;
import de.amr.easy.maze.alg.IterativeDFS;
import de.amr.easy.maze.alg.MazeAlgorithm;
import de.amr.easy.maze.alg.RandomBFS;
import de.amr.easy.maze.alg.RecursiveDFS;
import de.amr.easy.maze.alg.RecursiveDivision;
import de.amr.easy.maze.alg.Sidewinder;
import de.amr.easy.maze.alg.mst.BoruvkaMST;
import de.amr.easy.maze.alg.mst.KruskalMST;
import de.amr.easy.maze.alg.mst.PrimMST;
import de.amr.easy.maze.alg.mst.ReverseDeleteMST;
import de.amr.easy.maze.alg.wilson.WilsonUSTCollapsingCircle;
import de.amr.easy.maze.alg.wilson.WilsonUSTCollapsingWalls;
import de.amr.easy.maze.alg.wilson.WilsonUSTExpandingCircle;
import de.amr.easy.maze.alg.wilson.WilsonUSTExpandingCircles;
import de.amr.easy.maze.alg.wilson.WilsonUSTExpandingRectangle;
import de.amr.easy.maze.alg.wilson.WilsonUSTExpandingSpiral;
import de.amr.easy.maze.alg.wilson.WilsonUSTHilbertCurve;
import de.amr.easy.maze.alg.wilson.WilsonUSTLeftToRightSweep;
import de.amr.easy.maze.alg.wilson.WilsonUSTMooreCurve;
import de.amr.easy.maze.alg.wilson.WilsonUSTNestedRectangles;
import de.amr.easy.maze.alg.wilson.WilsonUSTPeanoCurve;
import de.amr.easy.maze.alg.wilson.WilsonUSTRandomCell;
import de.amr.easy.maze.alg.wilson.WilsonUSTRecursiveCrosses;
import de.amr.easy.maze.alg.wilson.WilsonUSTRightToLeftSweep;
import de.amr.easy.maze.alg.wilson.WilsonUSTRowsTopDown;

public class MazeGenerationRecordingApp {

	public static void main(String[] args) {
		new MazeGenerationRecordingApp().run(40, 25, 8);
	}

	private final Class<?>[] generatorClasses = {
	/*@formatter:off*/
		BoruvkaMST.class, 
		KruskalMST.class, 
		PrimMST.class, 
		ReverseDeleteMST.class,
//		AldousBroderUST.class, 
		BinaryTree.class,
		BinaryTreeRandom.class, 
		Eller.class,
		EllerInsideOut.class, 
		GrowingTree.class, 
		HuntAndKill.class, 
		HuntAndKillRandom.class,
		IterativeDFS.class, 
		RandomBFS.class, 
		RecursiveDFS.class, 
		RecursiveDivision.class, 
		Sidewinder.class,
		WilsonUSTCollapsingCircle.class, 
		WilsonUSTCollapsingWalls.class,
		WilsonUSTExpandingCircle.class, 
		WilsonUSTExpandingCircles.class, 
		WilsonUSTExpandingRectangle.class, 
		WilsonUSTExpandingSpiral.class, 
		WilsonUSTHilbertCurve.class, 
		WilsonUSTLeftToRightSweep.class, 
		WilsonUSTMooreCurve.class, 
		WilsonUSTNestedRectangles.class, 
		WilsonUSTPeanoCurve.class, 
		WilsonUSTRandomCell.class,
		WilsonUSTRecursiveCrosses.class, 
		WilsonUSTRightToLeftSweep.class, 
		WilsonUSTRowsTopDown.class, 
	/*@formatter:on*/
	};

	private ObservableGrid2D<TraversalState, Integer> grid;
	private GridCanvas canvas;
	private DefaultGridRenderingModel renderModel;
	private GridGifRecorder gif;

	public MazeGenerationRecordingApp() {
		renderModel = new DefaultGridRenderingModel() {

			@Override
			public Color getCellBgColor(int cell) {
				if (grid.get(cell) == TraversalState.COMPLETED)
					return Color.WHITE;
				if (grid.get(cell) == TraversalState.VISITED)
					return Color.BLUE;
				if (grid.get(cell) == TraversalState.UNVISITED)
					return Color.BLACK;
				return Color.BLACK;
			}
		};
	}

	public void run(int numCols, int numRows, int cellSize) {
		renderModel.setCellSize(cellSize);
		renderModel.setPassageWidth(cellSize / 2);
		for (Class<?> generatorClass : generatorClasses) {
			grid = new ObservableGrid<>(numCols, numRows, TraversalState.UNVISITED);
			canvas = new GridCanvas(grid, renderModel);
			try {
				MazeAlgorithm generator = (MazeAlgorithm) generatorClass.getConstructor(Grid2D.class).newInstance(grid);
				String outputPath = format("images/maze_%dx%d_%s.gif", grid.numCols(), grid.numRows(),
						generatorClass.getSimpleName());
				gif = new GridGifRecorder(canvas, outputPath, 1, false);
				gif.setScanRate(10);
				gif.beginRecording();
				generator.run(0);
				gif.endRecording();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}