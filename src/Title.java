
public class Title {

private String apartmentLink;
private String apartmentTitle;


public Title(String apartmentLink, String apartmentTitle) {
	super();
	this.apartmentLink = apartmentLink;
	this.apartmentTitle = apartmentTitle;

}

public  String getLink() {
	return apartmentLink;
}
public void setLink(String apartmentLink) {
	this.apartmentLink = apartmentLink;
}
public String getName() {
	return apartmentTitle;
}
public void setName(String apartmentTitle) {
	this.apartmentTitle = apartmentTitle;
}
@Override
public String toString() {
	
	return "Посилання: https://vashmagazin.ua" + apartmentLink + " Заголовок: " + apartmentTitle+" ";
}






}





