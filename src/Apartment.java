
public class Apartment {

private String apartmentLink;
private String apartmentTitle;


public Apartment(String link, String name) {
	super();
	this.apartmentLink = link;
	this.apartmentTitle = name;

}

public  String getLink() {
	return apartmentLink;
}
public void setLink(String link) {
	this.apartmentLink = link;
}
public String getName() {
	return apartmentTitle;
}
public void setName(String name) {
	this.apartmentTitle = name;
}
@Override
public String toString() {
	
	return "Посилання: https://vashmagazin.ua" + apartmentLink + " Заголовок: " + apartmentTitle+" ";
}






}





