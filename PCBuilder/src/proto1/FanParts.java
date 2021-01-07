package proto1;

public class FanParts
{
	int Id,Rpm,Noise_dBA;
	String Name;
	float Price;
	
	
	public FanParts(int Id,String Name,int Rpm,int Noise_dBA,float Price)
	{
		super();
		this.Id=Id;
		this.Name=Name;
		this.Rpm=Rpm;
		this.Noise_dBA=Noise_dBA;
		this.Price=Price;
	}
	
	public FanParts(String Name, float Price)
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
	
	public int getNoise_dBA()
	{
		return Noise_dBA;
	}
	
	public float getPrice()
	{
		return Price;
	}
	
	
}
