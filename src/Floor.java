
public class Floor {
	private String apartmentFloor;

	public String getFloor() {
		return apartmentFloor;
	}

	public void setFloor(String apartmentFloor) {
		this.apartmentFloor = apartmentFloor;
	}

	public Floor(String apartmentFloor) {
		super();
		this.apartmentFloor = apartmentFloor;
	}

	@Override
	public String toString() {
		return apartmentFloor + " ";
	}

}
