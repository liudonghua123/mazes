package de.amr.demos.maze.swing;

import static java.lang.String.format;

import java.awt.Color;

import de.amr.easy.graph.api.ObservableGraph;
import de.amr.easy.graph.api.SimpleEdge;
import de.amr.easy.graph.api.TraversalState;
import de.amr.easy.graph.api.event.EdgeEvent;
import de.amr.easy.graph.api.event.GraphObserver;
import de.amr.easy.graph.api.event.VertexEvent;
import de.amr.easy.grid.api.Grid2D;
import de.amr.easy.grid.api.ObservableGrid2D;
import de.amr.easy.grid.impl.ObservableGrid;
import de.amr.easy.grid.impl.Top4;
import de.amr.easy.grid.ui.swing.rendering.ConfigurableGridRenderer;
import de.amr.easy.grid.ui.swing.rendering.GridCanvas;
import de.amr.easy.grid.ui.swing.rendering.GridRenderer;
import de.amr.easy.grid.ui.swing.rendering.WallPassageGridRenderer;
import de.amr.easy.maze.alg.BinaryTree;
import de.amr.easy.maze.alg.BinaryTreeRandom;
import de.amr.easy.maze.alg.Eller;
import de.amr.easy.maze.alg.EllerInsideOut;
import de.amr.easy.maze.alg.GrowingTree;
import de.amr.easy.maze.alg.HuntAndKill;
import de.amr.easy.maze.alg.HuntAndKillRandom;
import de.amr.easy.maze.alg.RecursiveDivision;
import de.amr.easy.maze.alg.Sidewinder;
import de.amr.easy.maze.alg.core.MazeAlgorithm;
import de.amr.easy.maze.alg.mst.BoruvkaMST;
import de.amr.easy.maze.alg.mst.KruskalMST;
import de.amr.easy.maze.alg.mst.PrimMST;
import de.amr.easy.maze.alg.mst.ReverseDeleteDFSMST;
import de.amr.easy.maze.alg.traversal.IterativeDFS;
import de.amr.easy.maze.alg.traversal.RandomBFS;
import de.amr.easy.maze.alg.traversal.RecursiveDFS;
import de.amr.easy.maze.alg.ust.WilsonUSTCollapsingCircle;
import de.amr.easy.maze.alg.ust.WilsonUSTCollapsingWalls;
import de.amr.easy.maze.alg.ust.WilsonUSTExpandingCircle;
import de.amr.easy.maze.alg.ust.WilsonUSTExpandingCircles;
import de.amr.easy.maze.alg.ust.WilsonUSTExpandingRectangle;
import de.amr.easy.maze.alg.ust.WilsonUSTExpandingSpiral;
import de.amr.easy.maze.alg.ust.WilsonUSTHilbertCurve;
import de.amr.easy.maze.alg.ust.WilsonUSTLeftToRightSweep;
import de.amr.easy.maze.alg.ust.WilsonUSTMooreCurve;
import de.amr.easy.maze.alg.ust.WilsonUSTNestedRectangles;
import de.amr.easy.maze.alg.ust.WilsonUSTPeanoCurve;
import de.amr.easy.maze.alg.ust.WilsonUSTRandomCell;
import de.amr.easy.maze.alg.ust.WilsonUSTRecursiveCrosses;
import de.amr.easy.maze.alg.ust.WilsonUSTRightToLeftSweep;
import de.amr.easy.maze.alg.ust.WilsonUSTRowsTopDown;
import de.amr.easy.util.GifRecorder;

public class MazeGenerationRecordingApp {

	public static void main(String[] args) {
		new MazeGenerationRecordingApp().run(20, 20, 4, 4, 200);
	}

	private final Class<?>[] generatorClasses = {
	/*@formatter:off*/
		BoruvkaMST.class, 
		KruskalMST.class, 
		PrimMST.class, 
		ReverseDeleteDFSMST.class,
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

	private ObservableGrid2D<TraversalState, SimpleEdge> grid;
	private GridCanvas canvas;

	public void run(int numCols, int numRows, int cellSize, int scanRate, int delayMillis) {
		for (Class<?> generatorClass : generatorClasses) {
			grid = new ObservableGrid<>(numCols, numRows, Top4.get(), TraversalState.UNVISITED, false, SimpleEdge::new);
			canvas = new GridCanvas(grid, cellSize);
			canvas.pushRenderer(createRenderer(cellSize));
			try {
				MazeAlgorithm<SimpleEdge> generator = (MazeAlgorithm<SimpleEdge>) generatorClass.getConstructor(Grid2D.class)
						.newInstance(grid);
				try (GifRecorder recorder = new GifRecorder(canvas.getDrawingBuffer().getType())) {
					attachRecorderToGrid(recorder);
					recorder.setDelayMillis(delayMillis);
					recorder.setLoop(true);
					recorder.setScanRate(scanRate);
					recorder.start(createFileName(generatorClass));
					generator.run(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private GridRenderer createRenderer(int cellSize) {
		ConfigurableGridRenderer renderer = new WallPassageGridRenderer();
		renderer.fnCellBgColor = cell -> {
			if (grid.get(cell) == TraversalState.COMPLETED)
				return Color.WHITE;
			if (grid.get(cell) == TraversalState.VISITED)
				return Color.BLUE;
			if (grid.get(cell) == TraversalState.UNVISITED)
				return Color.BLACK;
			return Color.BLACK;
		};
		renderer.fnCellSize = () -> cellSize;
		renderer.fnPassageWidth = () -> cellSize / 2;
		return renderer;
	}

	private String createFileName(Class<?> generatorClass) {
		return format("images/maze_%dx%d_%s.gif", grid.numCols(), grid.numRows(), generatorClass.getSimpleName());
	}

	private void attachRecorderToGrid(GifRecorder recorder) {
		grid.addGraphObserver(new GraphObserver() {

			@Override
			public void vertexChanged(VertexEvent event) {
				recorder.addFrame(canvas.getDrawingBuffer());
			}

			@Override
			public void graphChanged(ObservableGraph<?> graph) {
				recorder.addFrame(canvas.getDrawingBuffer());
			}

			@Override
			public void edgeRemoved(EdgeEvent event) {
				recorder.addFrame(canvas.getDrawingBuffer());
			}

			@Override
			public void edgeChanged(EdgeEvent event) {
				recorder.addFrame(canvas.getDrawingBuffer());
			}

			@Override
			public void edgeAdded(EdgeEvent event) {
				recorder.addFrame(canvas.getDrawingBuffer());
			}
		});
	}
}