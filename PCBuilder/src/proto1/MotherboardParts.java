package proto1;

public class MotherboardParts
{
	int Id;
	String Name;
	String FormFactor;
	int RamSlots,Max_Ram;
	float Price;
	
	
	public MotherboardParts(int Id,String Name,String FormFactor,int RamSlots,int Max_Ram,float Price)
	{
		super();
		this.Id=Id;
		this.Name=Name;
		this.FormFactor=FormFactor;
		this.RamSlots=RamSlots;
		this.Max_Ram=Max_Ram;
		this.Price=Price;
	}
	
	public MotherboardParts(String Name, float Price)
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
	
	public String getFormFactor()
	{
		return FormFactor;
	}
	public int getRamSlots()
	{
		return RamSlots;
	}
	
	public int getMax_Ram()
	{
		return Max_Ram;
	}
	
	public float getPrice()
	{
		return Price;
	}
	
	
}
