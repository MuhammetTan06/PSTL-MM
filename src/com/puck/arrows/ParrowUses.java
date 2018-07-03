package com.puck.arrows;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Point2D;

import org.piccolo2d.PNode;
import org.piccolo2d.nodes.PPath;
import org.piccolo2d.nodes.PText;

import com.puck.nodes.piccolo2d.Edge;
import com.puck.nodes.piccolo2d.PiccoloCustomNode;

public class ParrowUses extends Parrow{

    private double spacing;
    private PPath line;
    	
    
    public ParrowUses(Point2D from, Point2D to, double spacing,Point2D virtuaFrom,Point2D virtualTo, String edgeViolation){
    	super(from, to, virtuaFrom, virtualTo);
		from = virtuaFrom;
		to = virtualTo;
		
		TriangleHollow head = null;
		
		line = PPath.createLine(from.getX(), from.getY(), to.getX(), to.getY());
		
		
		this.violation = edgeViolation;
		if(this.violation.equals("1")) {
			line.setStroke(new BasicStroke(3));
			line.setStrokePaint(Color.RED);
			head = new TriangleHollow(Color.RED);
		}else if(this.violation.equals("0")) {
			
			line.setStroke(new BasicStroke(1));
			line.setStrokePaint(Color.BLACK);
			head = new TriangleHollow(Color.BLACK);
		}
	
		double theta = Math.atan2(to.getY() - from.getY(), to.getX() - from.getX()) + Math.toRadians(90);
		head.translate(to.getX(), to.getY());
		head.rotate(theta);
		addChild(line);
		addChild(head);	
    }

    public ParrowUses(PNode from, PNode to, double spacing, PNode virtualForm, PNode virtualTo, String edgeViolation){
        this(((PiccoloCustomNode)from).getRect().getGlobalBounds().getCenter2D(),
        		((PiccoloCustomNode)to).getRect().getGlobalBounds().getCenter2D(),
        		spacing,
        		((PiccoloCustomNode)virtualForm).getRect().getGlobalBounds().getCenter2D(),
        		((PiccoloCustomNode)virtualTo).getRect().getGlobalBounds().getCenter2D(), 
        		edgeViolation);
        this.from = from;
        this.to = to;
        this.virtualFrom = virtualForm;
        this.virtualto = virtualTo;
		
       
        
    }

    @Override
    public Parrow redraw() {
        removeAllChildren();
        return new ParrowUses(from,to,spacing,from,to, violation);
    }

	@Override
	public Parrow redraw(PNode virtuaFrom) {
		
		removeAllChildren();
        return new ParrowUses(from,to,spacing,virtuaFrom,virtualto, violation);
	}
	@Override
	public Parrow redrawTo(PNode virtualTo) {
		removeAllChildren();
        return new ParrowUses(from,to,spacing,virtualFrom,virtualTo, violation);
	}
	
	public void addCard(int from, int to) {
		System.out.println("j'ajoute la cardinalité ");
		PText text= new PText("<"+from+","+to+">");
		text.setFont(new Font("Courier New", Font.BOLD, 18));
		text.setBounds(line.getGlobalFullBounds().getCenterX(),line.getGlobalFullBounds().getCenterY() , text.getWidth(), text.getHeight());
		addChild(text);
	}
	
}

