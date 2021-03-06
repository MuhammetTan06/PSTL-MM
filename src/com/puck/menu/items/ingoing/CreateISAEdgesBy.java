package com.puck.menu.items.ingoing;



import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;

import org.piccolo2d.PNode;
import org.piccolo2d.extras.pswing.PSwingCanvas;

import com.puck.arrows.ArrowNodesHolder;
import com.puck.arrows.Parrow;
import com.puck.arrows.ParrowExtends;
import com.puck.display.piccolo2d.NewDisplayDG;
import com.puck.menu.Menu;
import com.puck.nodes.piccolo2d.Edge;
import com.puck.nodes.piccolo2d.Node;
import com.puck.nodes.piccolo2d.PiccoloCustomNode;
import com.puck.utilities.piccolo2d.XmlToStructure;

public class CreateISAEdgesBy extends JMenuItem {
	
	private PiccoloCustomNode pnode;
	private NewDisplayDG frame;


	public CreateISAEdgesBy(PiccoloCustomNode pnode, JFrame frame) {
		super("show extends ingoing",new ImageIcon("left-arrow.png"));
		//this.setText();
	
		this.pnode = pnode;
		this.frame = (NewDisplayDG)frame;
	
		addActionListener();
	}

	public void drawExtendsEdges(PiccoloCustomNode target, PSwingCanvas canvas) {
		// Node node = listNodes.get(target.getidNode());
		// if (node != null && node.getRelation() != null) {

		HashMap<String, Edge> relation = getRelationInGoing();
		for (Entry<String, Edge> edgeEntry : relation.entrySet()) {
			Edge e = edgeEntry.getValue();
			for(Edge ed : this.frame.getForbiddenEdges()) {
				if(ed.getId().equals(e.getId())) {
					e.setViolation("1");
					break;
				}
				else 
					e.setViolation("0");
			}
				PNode to = target;
				PNode from = (this.frame.getAllPNodes().get(e.getFrom()));
				if (from.getParent() instanceof PiccoloCustomNode && !((PiccoloCustomNode) from.getParent()).isHidden()) {
					Parrow arow = new ParrowExtends(from, to, from, to, e.getViolation(), Parrow.REAL_TYPE);
					this.frame.getANH().addArrow(arow);
				} else {
					for (PiccoloCustomNode pnode : ((PiccoloCustomNode) from).getAscendency()) {
						if (!pnode.isHidden()) {
							this.frame.getANH().addArrow(new ParrowExtends(from, to, pnode, to, e.getViolation(), Parrow.REAL_TYPE));
							break;
						}
					}
				}
			}
		// }
		this.frame.getMenu().hideMenu();
	}

	public void addActionListener() {
		this.addActionListener(new AbstractAction() {

			public void actionPerformed(ActionEvent arg0) {
				drawExtendsEdges(pnode, frame.getCanvas());
			}
		});
	}

	public HashMap<String, Edge> getRelationInGoing() {
		Node currentNode = this.frame.getListNodes().get(pnode.getidNode());
		HashMap<String, Edge> relationInGoing = new HashMap<>();
		if (currentNode != null) {

			for (Entry<String, Node> nodeEntry : this.frame.getListNodes().entrySet()) {
				Node n = nodeEntry.getValue();
				HashMap<String, Edge> relation = n.getRelation();
				for (Entry<String, Edge> edgeEntry : relation.entrySet()) {
					Edge e = edgeEntry.getValue();
					if (e.getType().equals("isa") && (e.getTo().equals(currentNode.getId()))) {
						relationInGoing.put(e.getId(), e);
					}
				}
			}

		}
		return relationInGoing;
	}
}
