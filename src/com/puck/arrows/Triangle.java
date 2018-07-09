package com.puck.arrows;


import java.awt.BasicStroke;
import java.awt.Color;

import org.piccolo2d.PNode;
import org.piccolo2d.nodes.PPath;

public class Triangle extends PNode{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Triangle(Color color, int thickness){
        PPath t = new PPath.Float();
        t.moveTo(0,0);
        t.setStroke(new BasicStroke(thickness));
        t.lineTo(-5,5);
        t.lineTo(5,5);
        t.lineTo(0,0);
        t.closePath();
        t.setPaint(color);
        this.setBounds(t.getBounds().x,t.getBounds().y,t.getBounds().width,t.getBounds().height);
        addChild(t);
    }
}
