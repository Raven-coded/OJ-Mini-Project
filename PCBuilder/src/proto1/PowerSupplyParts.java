package proto1;

public class PowerSupplyParts
{
	int Id,Power_W;
	String Name,Type,Efficiency;
	float Price;
	
	
	public PowerSupplyParts(int Id,String Name,String Type,int Power_W,String Efficiency,float Price)
	{
		super();
		this.Id=Id;
		this.Name=Name;
		this.Type=Type;
		this.Power_W=Power_W;
		this.Efficiency=Efficiency;
		this.Price=Price;
	}
	
	public PowerSupplyParts(String Name, float Price)
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
	
	public String getType()
	{
		return Type;
	}
	public String getEfficiency()
	{
		return Efficiency;
	}
	
	public int getPower_W()
	{
		return Power_W;
	}
	
	public float getPrice()
	{
		return Price;
	}
	
	
}
