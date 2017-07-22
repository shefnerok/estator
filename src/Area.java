
public class Area {
	private String apartmentArea;

	public Area(String apartmentArea) {
		super();
		this.apartmentArea = apartmentArea;
	}

	public String getArea() {
		return apartmentArea;
	}

	public void setArea(String apartmentArea) {
		this.apartmentArea = apartmentArea;
	}

	@Override
	public String toString() {
		return "Площа " + apartmentArea + " ";
	}

}
