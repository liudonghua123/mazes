package de.amr.easy.grid.ui.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.Optional;

import javax.swing.JComponent;

import de.amr.easy.data.Stack;
import de.amr.easy.grid.api.BareGrid2D;
import de.amr.easy.grid.ui.swing.rendering.GridRenderer;

/**
 * A Swing component for displaying a grid.
 * 
 * @author Armin Reichert
 *
 * @param <G>
 *          the Grid type
 */
public class GridCanvas<G extends BareGrid2D<?>> extends JComponent {

	protected G grid;
	protected final Stack<GridRenderer> rendererStack = new Stack<>();
	protected BufferedImage drawingBuffer;
	protected Graphics2D g2;

	public GridCanvas(G grid, int cellSize) {
		if (grid == null) {
			throw new IllegalArgumentException("NULL grid not allowed");
		}
		this.grid = grid;
		setDoubleBuffered(false);
		adaptSize(cellSize);
	}

	public G getGrid() {
		return grid;
	}

	public void setGrid(G grid) {
		if (grid == null) {
			throw new IllegalArgumentException("Canvas must have a grid");
		}
		this.grid = grid;
	}

	public BufferedImage getDrawingBuffer() {
		return drawingBuffer;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(drawingBuffer, 0, 0, null);
	}

	public void clear() {
		getRenderer().ifPresent(r -> fill(r.getModel().getGridBgColor()));
	}

	public void fill(Color bgColor) {
		g2.setColor(bgColor);
		g2.fillRect(0, 0, getWidth(), getHeight());
		repaint();
	}

	public void drawGridCell(int cell) {
		getRenderer().ifPresent(r -> r.drawCell(g2, grid, cell));
		repaint();
	}

	public void drawGridPassage(int either, int other, boolean visible) {
		getRenderer().ifPresent(r -> r.drawPassage(g2, grid, either, other, visible));
		repaint();
	}

	public void drawGrid() {
		getRenderer().ifPresent(r -> r.drawGrid(g2, grid));
		repaint();
	}

	public void adaptSize(int cellSize) {
		Dimension size = new Dimension(grid.numCols() * cellSize, grid.numRows() * cellSize);
		setSize(size);
		setPreferredSize(size);
		GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		drawingBuffer = config.createCompatibleImage(size.width, size.height);
		g2 = drawingBuffer.createGraphics();
		repaint();
	}

	public Optional<GridRenderer> getRenderer() {
		return rendererStack.top();
	}

	public void pushRenderer(GridRenderer newRenderer) {
		getRenderer().ifPresent(oldRenderer -> {
			if (oldRenderer.getModel().getCellSize() != newRenderer.getModel().getCellSize()) {
				adaptSize(newRenderer.getModel().getCellSize());
			}
		});
		rendererStack.push(newRenderer);
		repaint();
	}

	public void popRenderer() {
		if (rendererStack.isEmpty()) {
			throw new IllegalStateException("Cannot remove last renderer");
		}
		GridRenderer oldRenderer = rendererStack.pop();
		getRenderer().ifPresent(newRenderer -> {
			if (oldRenderer.getModel().getCellSize() != newRenderer.getModel().getCellSize()) {
				adaptSize(newRenderer.getModel().getCellSize());
			}
		});
		repaint();
	}
}