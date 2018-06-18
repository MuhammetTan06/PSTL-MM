package com.puck.nodes.piccolo2d;



public class Edge {
	private String type ; 
	private String id;
	private String from ;
	private String to ;
	
	
	public Edge(){
		this.type = "";
		this.id = "";
		this.from = "";
		this.to = "";
	}
	
	public Edge(String type, String id, String from, String to) {
		this.type = type;
		this.id = id;
		this.from = from;
		this.to = to;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o.getClass().equals(this.getClass())))
			return false;
		Edge e = (Edge) o;
		if(e.from.equals(this.from) && e.to.equals(this.to) && e.type.equals(this.type))
			return true;
		return false;
	}
	
}
