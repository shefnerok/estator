
public class Floor {
	private String floor;

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public Floor(String floor) {
		super();
		this.floor = floor;
	}

	@Override
	public String toString() {
		return floor + " ";
	}

}
