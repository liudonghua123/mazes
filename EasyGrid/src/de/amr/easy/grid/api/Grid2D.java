package de.amr.easy.grid.api;

import de.amr.easy.graph.api.Edge;
import de.amr.easy.graph.api.Vertex;

/**
 * A 2D-grid graph with vertex objects ("cells").
 * 
 * @author Armin Reichert
 *
 * @param <V>
 *          vertex type
 * @param <E>
 *          edge type
 */
public interface Grid2D<V, E extends Edge> extends BareGrid2D<E>, Vertex<V> {
}