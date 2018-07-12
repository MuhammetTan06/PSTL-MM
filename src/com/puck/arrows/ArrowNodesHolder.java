package com.puck.arrows;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.piccolo2d.PNode;

import com.puck.nodes.piccolo2d.PiccoloCustomNode;

public class ArrowNodesHolder extends PNode {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public ArrowNodesHolder() {
	}

	public void addArrow(Parrow arrow) {
		for (Parrow arr : getAllArrows()) {
			if (arr.equals(arrow))
				removeArrow(arr);
		}
		
		addChild(arrow);
		// updatePosition(arrow);
	}

	public void removeArrow(Parrow arrow) {
		removeChild(arrow);
	}

	public void hideArrow(Parrow arrow) {
		
//		for (Parrow p : hiddenArrows) {
//			if (p.equals(arrow)) {
//				removeArrow(p);
//				hiddenArrows.add(p);
//				break;
//			}
//		}
		arrow.setVisible(false);
	}

	public void showArrow(Parrow arrow) {
		arrow.setVisible(true);
	}

	public boolean isHidden(Parrow arrow) {
		return arrow.getVisible();
	}

	
	public Collection<Parrow> getVisibleArrows() {
		Collection<Parrow> set = new HashSet<>();
		for (Iterator<Parrow> iterator = getChildrenIterator(); iterator.hasNext();) {
			PNode n = iterator.next();
			
			if (n instanceof Parrow ) {
				if(((Parrow) n).getVisible()) {
					set.add((Parrow) n);
				}
			}
		}
		return set;
	}
	
	public Collection<Parrow> getHiddenArrows() {
		Collection<Parrow> set = new HashSet<>();
		for (Iterator<Parrow> iterator = getChildrenIterator(); iterator.hasNext();) {
			PNode n = iterator.next();
			
			if (n instanceof Parrow ) {
				if(!((Parrow) n).getVisible())
					set.add((Parrow) n);
			}
		}
		return set;
	
	}

	public Collection<Parrow> getAllArrows() {
		Collection<Parrow> set = new HashSet<>();
		for (Iterator<PNode> iterator = getChildrenIterator(); iterator.hasNext();) {
			PNode n = iterator.next();
			if (n instanceof Parrow)
				set.add((Parrow) n);
		}
		return set;
	}

	public void updatePosition(Parrow arrow) {
		PNode from = arrow.getFrom();
		PNode to = arrow.getTo();
		PNode virtualFrom = arrow.getVirtualFrom();
		PNode virtualTo = arrow.getVirtualto();
		// Parrow ar2 = null ;
		ArrayList<PiccoloCustomNode> fromAscendency = ((PiccoloCustomNode) from).getAscendency();
		ArrayList<PiccoloCustomNode> toAscendency = ((PiccoloCustomNode) to).getAscendency();
		
		for (PiccoloCustomNode pnode : fromAscendency) {
			if (!pnode.isHidden()) {
				if (pnode.equals((PiccoloCustomNode) from)) {
					arrow.setVirtualFrom(from);
					
					break;
				} else {
					
					arrow.setVirtualFrom(pnode);
					
					break;
				
				}
			}
		}
		for (PiccoloCustomNode pnode : toAscendency) {
			if (!pnode.isHidden()) {
				if (pnode.equals((PiccoloCustomNode) to) && from.equals(arrow.getVirtualFrom())) {
					arrow.setVirtualto(to);
					arrow.setArrowType(Parrow.REAL_TYPE);
					break;
				} else if (pnode.equals((PiccoloCustomNode) to)) {
					
					arrow.setVirtualto(to);
					arrow.setArrowType(Parrow.VIRTUAL_TYPE);
					break;
				} else {
					
					arrow.setVirtualto(pnode);
					arrow.setArrowType(Parrow.VIRTUAL_TYPE);
					break;
				}
			}
			
		}
		
		int thick = arrow.getViolation().equals("1") ? 3 : 1;
		Color color = arrow.getViolation().equals("1") ? Color.RED : Color.BLACK;
		arrow.draw(((PiccoloCustomNode)arrow.getVirtualFrom()).getContent().getText().getGlobalBounds().getCenter2D(),
				((PiccoloCustomNode)arrow.getVirtualto()).getContent().getText().getGlobalBounds().getCenter2D(),
				arrow.getArrowType(),
				color,
				thick
			);
		
		
		
	
		// Pour afficher les cardinalités
		// if (virtualFrom != null &&
		// ((PiccoloCustomNode)virtualFrom).getContent().getType().equals("package")) {
		// if (virtualTo != null &&
		// ((PiccoloCustomNode)virtualTo).getContent().getType().equals("package")) {
		// System.out.println("package");
		// int [] tab = addCard(virtualFrom,virtualTo);
		// if(arrow instanceof ParrowUses) {
		// ((ParrowUses)arrow).addCard(tab[0], tab[1]);
		// }
		// }
		// }
		
	}
	


	
	public void hide_show_arrows(PiccoloCustomNode node) {
		Collection<PiccoloCustomNode> hierarchy = node.getHierarchy();
		//hierarchy.add(node);
		if(node.isCollapsed()) {
			for (Parrow arrow : getVisibleArrows()) {
				PiccoloCustomNode PCNF = (PiccoloCustomNode) arrow.getVirtualFrom();
				PiccoloCustomNode PCNT = (PiccoloCustomNode) arrow.getVirtualto();
				
				if (node.getidNode().equals(PCNF.getidNode()) && node.getidNode().equals(PCNT.getidNode()) ) {
			
					arrow.setVisible(false);
					// System.out.println("hide
					// "+getVisibleArrows().size()+"-"+getHiddenArrows().size());
				}
			}
		}
		else {
			for (PiccoloCustomNode PCN : hierarchy) {
				Collection<Parrow> hiddenArrows = this.getHiddenArrows();
				for (Parrow p : hiddenArrows) {
					PiccoloCustomNode PCNF = (PiccoloCustomNode) p.getFrom();
					PiccoloCustomNode PCNT = (PiccoloCustomNode) p.getTo();
					if ((!PCNT.isHidden() || !PCNF.isHidden()) ) {
						p.setVisible(true);
						// System.out.println("show
						// "+getVisibleArrows().size()+"-"+getHiddenArrows().size());
					}
				}
			}
		}
	}

	@SuppressWarnings("unckecked")
	public void clearCounters() {
		Collection<PNode> out = new HashSet<>();
		for (Iterator<PNode> pNodeIterator = getChildrenIterator(); pNodeIterator.hasNext();) {
			PNode node = pNodeIterator.next();
			if (node instanceof ArrowCounter) {
				out.add(node);
			}
		}
		for (PNode node : out) {
			removeChild(node);
		}
	}
	
	

		
	public void updateAllPosition() {
		for (Parrow parrow : getVisibleArrows()) {
			updatePosition(parrow);
		}
	}

	private int margin = 10;

	public int[] addCard(PNode from, PNode to) {
		int[] tab = new int[2];
		int a = 0;
		int b = 0;
		for (Parrow parrow : this.getAllArrows()) {
			if (parrow.getVirtualFrom().equals(from) && parrow.getVirtualto().equals(to)) {
				a++;
			}
			if (parrow.getVirtualFrom().equals(to) && parrow.getVirtualto().equals(from)) {
				b++;
			}
		}
		tab[0] = a;
		tab[1] = b;
		System.err.println(a);
		System.err.println(b);
		return tab;

	}
	// public void updateCount(ParrowDottedFat parrow) {
	// int i = 0;
	// for (Parrow parrow1 : getVisibleArrows())
	// if (parrow1 instanceof ParrowDottedFat && parrow.getFrom() ==
	// parrow1.getFrom()
	// && parrow.getTo() == parrow1.getTo())
	// i++;
	// ArrowCounter AC = new ArrowCounter(String.valueOf(i));
	// addChild(AC);
	// PBounds bounds = parrow.getUnionOfChildrenBounds(null);
	// parrow.setBounds(bounds);
	// AC.translate(parrow.getBounds().getCenterX() + margin,
	// parrow.getBounds().getCenterY() + margin);
	//
	// }
}
