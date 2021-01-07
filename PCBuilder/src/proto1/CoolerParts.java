package proto1;

public class CoolerParts
{
	int Id,Rpm;
	String Name;
	float Price,Noise_dBA;
	
	
	public CoolerParts(int Id,String Name,int Rpm,float Noise_dBA,float Price)
	{
		super();
		this.Id=Id;
		this.Name=Name;
		this.Rpm=Rpm;
		this.Noise_dBA=Noise_dBA;
		this.Price=Price;
	}
	
	public CoolerParts(String Name, float Price)
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
	
	public int getRpm()
	{
		return Rpm;
	}
	
	public float getNoise_dBA()
	{
		return Noise_dBA;
	}
	
	public float getPrice()
	{
		return Price;
	}
	
	
}
