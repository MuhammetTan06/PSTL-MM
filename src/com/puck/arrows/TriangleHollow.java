package com.puck.arrows;

import java.awt.BasicStroke;
import java.awt.Color;

import org.piccolo2d.PNode;
import org.piccolo2d.nodes.PPath;
import org.piccolo2d.nodes.PText;

public class TriangleHollow extends PNode{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TriangleHollow(Color c, int thickness){
        PPath t = new PPath.Float();
        t.setStrokePaint(c);
        t.setStroke(new BasicStroke(thickness));
        t.moveTo(0,0);
        t.lineTo(-10,10);
        t.moveTo(0,0);
        t.lineTo(10,10);
        this.setBounds(0,0,0,0);
        addChild(t);
    }
}
