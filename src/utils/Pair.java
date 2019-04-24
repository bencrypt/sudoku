package utils;

public class Pair<T1, T2>
{
	private T1 elem1;
	private T2 elem2;

	public Pair(T1 elem1, T2 elem2)
	{
		this.elem1 = elem1;
		this.elem2 = elem2;
	}

	public T1 getFirst()
	{
		return this.elem1;
	}
	
	public T2 getSecond()
	{
		return this.elem2;
	}

	public void print()
	{
		System.out.println("Paar: ");
		System.out.println("1: " + this.elem1.toString());
		System.out.println("2: " + this.elem2.toString());
	}
}
