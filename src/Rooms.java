
public class Rooms {
private String rooms;




public Rooms(String rooms) {
	super();
	this.rooms = rooms;
}

public String getRooms() {
	return rooms;
}

public void setRooms(String rooms) {
	this.rooms = rooms;
}

@Override
public String toString() {
	return "Кімнат: " + rooms + " ";
}






}
