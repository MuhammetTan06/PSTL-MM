package com.puck.menu.items.outgoing;



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

public class CreateISAEdgesOf extends JMenuItem {
	private PiccoloCustomNode pnode;
	private NewDisplayDG frame;

	public CreateISAEdgesOf(PiccoloCustomNode pnode, JFrame frame) {
		super("show extends outgoing",new ImageIcon("right-arrow.png"));
		this.pnode = pnode;
		this.frame = (NewDisplayDG)frame;
		addActionListener();
	}

	public void drawExtendsEdges(PiccoloCustomNode target, PSwingCanvas canvas) {
		Node node = this.frame.getListNodes().get(target.getidNode());
		if (node != null && node.getRelation() != null) {
			HashMap<String, Edge> relation = node.getRelation();
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
				if (e.getType().equals("isa")) {
					PNode from = target;
					PNode to = (this.frame.getAllPNodes().get(e.getTo()));
					if (to.getParent() instanceof PiccoloCustomNode
							&& !((PiccoloCustomNode) to.getParent()).isHidden()) {
						this.frame.getANH().addArrow(new ParrowExtends(from, to, from, to, e.getViolation(), Parrow.REAL_TYPE));
						this.frame.getANH().updateAllPosition();
					} else {
						for (PiccoloCustomNode pnode : ((PiccoloCustomNode) to).getAscendency()) {
							if (!pnode.isHidden()) {
								this.frame.getANH().addArrow(new ParrowExtends(from, to, from, pnode, e.getViolation(), Parrow.REAL_TYPE));
								break;
							}
						}
					}
				}
			}
		}
		this.frame.getMenu().hideMenu();
	}

	public void addActionListener() {
		this.addActionListener(new AbstractAction() {

			public void actionPerformed(ActionEvent arg0) {
				drawExtendsEdges(pnode, frame.getCanvas());
			}
		});
	}

}
