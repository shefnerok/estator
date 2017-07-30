
public class Price {
	private String apartmentPrice;

	public Price(String apartmentPrice) {
		super();
		this.apartmentPrice = apartmentPrice;
	}

	public String getPrice() {
		return apartmentPrice;
	}

	public void setPrice(String apartmentPrice) {
		this.apartmentPrice = apartmentPrice;
	}

	@Override
	public String toString() {
		return "Ціна: " + apartmentPrice + " ";
	}

}
