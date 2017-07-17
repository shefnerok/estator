
public class Apartment {

private String link;
private String name;


public Apartment(String link, String name) {
	super();
	this.link = link;
	this.name = name;

}

public  String getLink() {
	return link;
}
public void setLink(String link) {
	this.link = link;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
@Override
public String toString() {
	
	return "Посилання: https://vashmagazin.ua" + link + " Заголовок: " + name+" ";
}






}





