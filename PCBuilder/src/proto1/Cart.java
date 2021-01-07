package proto1;

public class Cart {

	private String Name;
	private float price;
	private String username;
	
	public Cart () {}

	public Cart (String username, String Name, float price) {
		super();
		this.Name = Name;
		this.price = price;
		this.username = username;
	}

	public String getName() {
		return Name;
	}

	public void setName(String Name) {
		this.Name = Name;
	}


	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
