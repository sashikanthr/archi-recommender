package com.archimatetool.recommender.contribution;

public class RecommendedComponent {
	
	private String type;
	
	private String name;
	
	private String id;
	
	private double score;
	
	public RecommendedComponent() {
		
	}
	
	public RecommendedComponent(String type, String name, String id, double score) {
		
		this.type = type;
		this.name = name;
		this.id = id;
		this.score = score;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
	
	

}
