
public class Price {
	private String price;
	
	
	

	public Price(String poverh) {
		super();
		this.price = poverh;
	}

	public String getPoverh() {
		return price;
	}

	public void setPoverh(String poverh) {
		this.price = poverh;
	}

	@Override
	public String toString() {
		return "Ціна: " + price + " ";
	}
	
	
	

}
