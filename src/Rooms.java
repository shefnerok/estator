
public class Rooms {
	private String numberOfRooms;

	public Rooms(String numberOfRooms) {
		super();
		this.numberOfRooms = numberOfRooms;
	}

	public String getRooms() {
		return numberOfRooms;
	}

	public void setRooms(String numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	@Override
	public String toString() {
		return "Кімнат: " + numberOfRooms + " ";
	}

}
