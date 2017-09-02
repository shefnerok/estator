
public class Apartment {
private String title;
private String url;
private String rooms;
private String floor;
private String area;
private String price;
public Apartment(String title, String url, String rooms, String floor, String area, String price) {
	super();
	this.title = title;
	this.url = url;
	this.rooms = rooms;
	this.floor = floor;
	this.area = area;
	this.price = price;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public String getRooms() {
	return rooms;
}
public void setRooms(String rooms) {
	this.rooms = rooms;
}
public String getFloor() {
	return floor;
}
public void setFloor(String floor) {
	this.floor = floor;
}
public String getArea() {
	return area;
}
public void setArea(String area) {
	this.area = area;
}
public String getPrice() {
	return price;
}
public void setPrice(String price) {
	this.price = price;
}
@Override
public String toString() {
	return "Назва: " + title + ", Посилання: " + url + ", К-сть. кімнат: " + rooms + ", поверх: " + floor + ", площа: " + area
			+ ", ціна: " + price;
}

}
