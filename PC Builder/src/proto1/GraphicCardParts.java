package proto1;

public class GraphicCardParts
{
	int Id;
	String Name;
	String Memory;
	String Chipset;
	int Speed;
	float Price;
	
	
	public GraphicCardParts(int Id,String Name,String Memory,String Chipset,int Speed,float Price)
	{
		super();
		this.Id=Id;
		this.Name=Name;
		this.Memory=Memory;
		this.Chipset=Chipset;
		this.Speed=Speed;
		this.Price=Price;
	}
	
	public GraphicCardParts(String Name, float Price)
	{
		this.Name=Name;
		this.Price=Price;
	}
	public int getId()
	{
		return Id;
	}
		
	
	public String getName()
	{
		return Name;
	}
	
	public String getMemory()
	{
		return Memory;
	}
	public String getChipset()
	{
		return Chipset;
	}
	
	public int getSpeed()
	{
		return Speed;
	}
	
	public float getPrice()
	{
		return Price;
	}
	
	
}
