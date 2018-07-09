package com.puck.arrows;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Point2D;

import org.piccolo2d.PNode;
import org.piccolo2d.nodes.PPath;
import org.piccolo2d.nodes.PText;

import com.puck.nodes.piccolo2d.Edge;
import com.puck.nodes.piccolo2d.PiccoloCustomNode;

public class ParrowExtends extends Parrow {
    final static float dash1[] = {10.0f};
    private PPath line;
	private static double OFFSET = 12;
	final static BasicStroke dashed =
            new BasicStroke(2.0f,
                            BasicStroke.CAP_BUTT,
                            BasicStroke.JOIN_MITER,
                            10.0f, dash1, 0.0f);
	public ParrowExtends(Point2D from, Point2D to, Point2D virtuaFrom, Point2D virtualTo, String edgeViolation) {
		super(from, to, virtuaFrom, virtualTo);
		from = virtuaFrom;
		to = virtualTo;
		Triangle head = null;
		
		double fromX = from.getX();
		double fromY = from.getY();
		double toX = to.getX();
		double toY = to.getY();
		
		double xDiff = Math.abs(from.getX()-to.getX());
		if(from.distance(to) != 0.0) {
			
			double angle = Math.acos((xDiff / from.distance(to)));
		
			double xOffset = Math.cos(angle) * OFFSET ;
			double yOffset = Math.sin(angle) * OFFSET;
			
			if(fromX-toX<0) {
				fromX += xOffset;
				toX -= xOffset;
			}
			else {
				fromX -= xOffset;
				toX += xOffset;
			}
			if(fromY-toY<0) {
				fromY+=yOffset;
				toY-=yOffset;
			}else {
				fromY-=yOffset;
				toY+=yOffset;
			}
		}

		line = PPath.createLine(fromX, fromY, toX, toY);
		
		this.violation = edgeViolation;
		if(this.violation.equals("1")) {
			line.setStroke(new BasicStroke(3));
			line.setStrokePaint(Color.RED);
			head = new Triangle(Color.RED, 3);
		}else if(this.violation.equals("0")) {
			line.setStroke(new BasicStroke(1));
			line.setStrokePaint(Color.BLACK);
			head = new Triangle(Color.WHITE, 1);
		}
        
		line.setStroke(dashed);
		double theta = Math.atan2(to.getY() - from.getY(), to.getX() - from.getX()) + Math.toRadians(90);
		head.translate(toX, toY);
		head.rotate(theta);

		addChild(line);
		addChild(head);
		
//		PText text= new PText("arrowExtends");
//		text.setBounds((from.getX()+to.getX())/2, (from.getY()+to.getY())/2, text.getWidth(), text.getHeight());
//		text.rotateInPlace(line.getGlobalRotation());
//		addChild(text);

	}

	public ParrowExtends(PNode from, PNode to, PNode virtualForm, PNode virtualTo, String edgeViolation) {
	    this(((PiccoloCustomNode)from).getContent().getText().getGlobalBounds().getCenter2D(),
        		((PiccoloCustomNode)to).getContent().getText().getGlobalBounds().getCenter2D(),
        		((PiccoloCustomNode)virtualForm).getContent().getText().getGlobalBounds().getCenter2D(),
        		((PiccoloCustomNode)virtualTo).getContent().getText().getGlobalBounds().getCenter2D(),
        		edgeViolation);
		
		this.from = from;
		this.to = to;
		this.virtualFrom = virtualForm;
		this.virtualto = virtualTo;
	}

	@Override
	public Parrow redraw() {
		removeAllChildren();
		return new ParrowExtends(from, to, from, to, violation);
	}

	@Override
	public Parrow redraw(PNode virtuaFrom) {
		removeAllChildren();
		return new ParrowExtends(from, to, virtuaFrom, to, violation);
	}

	@Override
	public Parrow redrawTo(PNode virtualTo) {
		removeAllChildren();
		return new ParrowExtends(from, to, virtualFrom, virtualTo, violation);
	}

}
