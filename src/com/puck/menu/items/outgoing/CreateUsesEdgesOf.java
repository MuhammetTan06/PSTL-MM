package com.puck.menu.items.outgoing;



import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;

import org.piccolo2d.PNode;
import org.piccolo2d.extras.pswing.PSwingCanvas;

import com.puck.arrows.ArrowNodesHolder;
import com.puck.arrows.ParrowUses;
import com.puck.display.piccolo2d.NewDisplayDG;
import com.puck.display.piccolo2d.NodeToPnodeParser;
import com.puck.menu.Menu;
import com.puck.nodes.piccolo2d.Edge;
import com.puck.nodes.piccolo2d.Node;
import com.puck.nodes.piccolo2d.PiccoloCustomNode;
import com.puck.utilities.piccolo2d.XmlToStructure;

public class CreateUsesEdgesOf extends JMenuItem {
	private static HashMap<String, PiccoloCustomNode> allPNodes;

	private Map<String, Node> listNodes;
	private PiccoloCustomNode pnode;
	private PSwingCanvas canvas;
	private Menu menu;
	private ArrowNodesHolder ANH;

	public CreateUsesEdgesOf(PiccoloCustomNode pnode, PSwingCanvas canvas, HashMap<String, PiccoloCustomNode> allPNodes,
		Menu menu, ArrowNodesHolder ANH, Map<String, Node> listNodes) {
		super();
		this.setText("show uses outgoing");
		this.allPNodes = allPNodes;
		this.pnode = pnode;
		this.canvas = canvas;
		this.menu = menu;
		this.ANH = ANH;
		this.listNodes= listNodes;
		addActionListener();
		
	}

	public void DrawEdges(PiccoloCustomNode target, PSwingCanvas canvas) {
		Node node = listNodes.get(target.getidNode());
		
		if ( node!= null && node.getRelation() !=  null) {
			HashMap<String, Edge> relation = node.getRelation();
			for (Entry<String, Edge> edgeEntry : relation.entrySet()) {
				Edge e = edgeEntry.getValue();
				if (e.getType().equals("uses")) {
					PNode from = target;
					PNode to = (allPNodes.get(e.getTo()));
					
					if (to.getParent() instanceof PiccoloCustomNode
							&& !((PiccoloCustomNode) to.getParent()).isHidden()) {
						ANH.addArrow(new ParrowUses(from, to, 10, from, to, e.getViolation()));
						ANH.updateAllPosition();
					} else {
						for (PiccoloCustomNode pnode : ((PiccoloCustomNode) to).getAscendency()) {
							if (!pnode.isHidden()) {
								ParrowUses p = new ParrowUses(from, to, 10, from, pnode, e.getViolation());
								ANH.addArrow(p);
								break;
								
							}
						}
					}
				}
			}
		}
		menu.hideMenu();
	}

	public void addActionListener() {
		this.addActionListener(new AbstractAction() {

			public void actionPerformed(ActionEvent arg0) {
				DrawEdges(pnode, canvas);
			}
		});
	}
}
