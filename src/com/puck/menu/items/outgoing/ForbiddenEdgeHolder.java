package com.puck.menu.items.outgoing;

import java.util.ArrayList;
import java.util.List;

import com.puck.nodes.piccolo2d.Edge;

public class ForbiddenEdgeHolder {

	private static List<Edge> edges;
	
	public ForbiddenEdgeHolder() {
		this.edges = new ArrayList<Edge>();
	}
	
	public void addEdge(Edge e ) {
		this.edges.add(e);
	}
	
	public List<Edge> getEdges() {
		return this.edges;
	}

	public static boolean isForbidden(Edge edge) {
		for(Edge e : edges) {
			if(e.equals(edge))
				return true;
		}
		return false;
	}
	
	
}
