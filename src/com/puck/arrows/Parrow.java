package com.puck.arrows;

import java.awt.Color;
import java.awt.geom.Point2D;

import org.piccolo2d.PNode;
import org.piccolo2d.nodes.PPath;

public abstract class Parrow extends PNode{
    public Parrow(Point2D from, Point2D to,Point2D virtuaFrom,Point2D virtualTo){
    	line = PPath.createLine(0.0, 0.0, 0.0, 0.0);
    }
    protected PNode virtualFrom;
    protected PNode virtualto;
    protected PNode from;
    protected PNode to;
    protected boolean isAllowed;
    protected String violation;
    protected PPath line;;
    
    public void changeColor(Color c) {
    	this.line.setStrokePaint(c);
    }

    public String getViolation() {
		return violation;
	}

	public void setViolation(String violation) {
		this.violation = violation;
	}

	public PNode getFrom() {
        return from;
    }

    public PNode getTo() {
        return to;
    }

    public void setFrom(PNode from){
        this.from=from;
    }

    public void setTo(PNode to){
        this.to=to;
    }

    public Parrow(PNode from,PNode to,PNode virtuaFrom,PNode virtualto){
        this.from=from;
        this.to=to;
        this.virtualFrom = virtuaFrom;
        this.virtualto = virtualto;
        this.isAllowed = true;
        this.violation = "0";
        
    }

    public abstract Parrow redraw();
    public abstract Parrow redraw(PNode virtuaFrom);
    public abstract Parrow redrawTo(PNode virtuaFrom);

    @Override
    public boolean equals(Object arrow){
        if(!(arrow instanceof Parrow))
            return false;
        Parrow p = (Parrow) arrow;
        return this.from==p.from
                &&this.to==p.to
               ;
    }

	public PNode getVirtualFrom() {
		return virtualFrom;
	}

	public void setVirtualFrom(PNode virtualFrom) {
		this.virtualFrom = virtualFrom;
	}

	public PNode getVirtualto() {
		return virtualto;
	}

	public void setVirtualto(PNode virtualto) {
		this.virtualto = virtualto;
	}

	public boolean isAllowed() {
		return isAllowed;
	}

	public void setAllowed(boolean isAllowed) {
		this.isAllowed = isAllowed;
	}

	public void setLine(PPath line2) {
		this.line = line2;
		
	}
	
    
}
