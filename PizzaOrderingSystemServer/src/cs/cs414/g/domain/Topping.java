package cs.cs414.g.domain;

public class Topping {

	public enum Coverage
	{
		WHOLE,
		LEFT_HALF,
		RIGHT_HALF
	};
	
	private String type;
	private Coverage coverage = Coverage.WHOLE;
	
	public Topping(String type) {
		this.type = type;
	}
	
	public Topping(Topping other) {
		this.type = new String(other.type);
		this.coverage = other.getCoverage();
	}
	
	public String getType() {
		return type;
	}

	public Coverage getCoverage() {
		return coverage;
	}

	public void setCoverage(Coverage coverage) {
		this.coverage = coverage;
	}
	public String toString() {
		return this.getType();
	}

}
