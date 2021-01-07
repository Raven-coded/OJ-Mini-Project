package proto1;

public class ProcessorParts
{
	int Id,Cores,Power_W;
	String Name;
	float Price,Speed_GHz;
	
	
	public ProcessorParts(int Id,String Name,int Cores,float Speed_GHz,int Power_W,float Price)
	{
		super();
		this.Id=Id;
		this.Name=Name;
		this.Cores=Cores;
		this.Speed_GHz=Speed_GHz;
		this.Power_W=Power_W;
		this.Price=Price;
	}
	
	public ProcessorParts(String Name, float Price)
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
	
	public int getCores()
	{
		return Cores;
	}
	public float getSpeed_GHz()
	{
		return Speed_GHz;
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
