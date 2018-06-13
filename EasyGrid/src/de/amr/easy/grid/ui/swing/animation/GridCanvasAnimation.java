package de.amr.easy.grid.ui.swing.animation;

import java.util.function.IntSupplier;

import de.amr.easy.graph.api.ObservableGraph;
import de.amr.easy.graph.api.WeightedEdge;
import de.amr.easy.graph.api.event.EdgeAddedEvent;
import de.amr.easy.graph.api.event.EdgeChangeEvent;
import de.amr.easy.graph.api.event.EdgeRemovedEvent;
import de.amr.easy.graph.api.event.GraphObserver;
import de.amr.easy.graph.api.event.VertexChangeEvent;
import de.amr.easy.grid.api.BareGrid2D;
import de.amr.easy.grid.ui.swing.GridCanvas;

public class GridCanvasAnimation<G extends BareGrid2D<Integer>> implements GraphObserver<WeightedEdge<Integer>> {

	private final GridCanvas<G> canvas;
	private boolean enabled;

	/** Function supplying the delay in milliseconds. */
	public IntSupplier fnDelay;

	public GridCanvasAnimation(GridCanvas<G> canvas) {
		this.canvas = canvas;
		enabled = true;
		fnDelay = () -> 0;
	}

	public int getDelay() {
		return fnDelay.getAsInt();
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public void vertexChanged(VertexChangeEvent event) {
		if (enabled) {
			delayed(() -> canvas.drawGridCell(event.getVertex()));
		}
	}

	@Override
	public void edgeAdded(EdgeAddedEvent<WeightedEdge<Integer>> event) {
		if (enabled) {
			delayed(() -> canvas.drawGridPassage(event.getEdge(), true));
		}
	}

	@Override
	public void edgeRemoved(EdgeRemovedEvent<WeightedEdge<Integer>> event) {
		if (enabled) {
			delayed(() -> canvas.drawGridPassage(event.getEdge(), false));
		}
	}

	@Override
	public void edgeChanged(EdgeChangeEvent<WeightedEdge<Integer>> event) {
		if (enabled) {
			delayed(() -> canvas.drawGridPassage(event.getEdge(), true));
		}
	}

	@Override
	public void graphChanged(ObservableGraph<WeightedEdge<Integer>> graph) {
		if (enabled) {
			canvas.drawGrid();
		}
	}

	private void delayed(Runnable code) {
		if (fnDelay.getAsInt() > 0) {
			try {
				Thread.sleep(fnDelay.getAsInt());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		code.run();
		canvas.repaint();
	}
}