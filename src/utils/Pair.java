package utils;

public class Pair<T1, T2>
{
	/* --> Fields <-- */

	private T1 elem1;
	private T2 elem2;

	/* --> Constructor <-- */

	/**
	 * Creates a Pair with the both given elements.
	 *
	 * @param elem1
	 * 		the first element
	 * @param elem2
	 * 		the second element
	 */
	public Pair(T1 elem1, T2 elem2)
	{
		this.elem1 = elem1;
		this.elem2 = elem2;
	}

	/* --> Methods <-- */

	/**
	 * Returns a String representation of the calling Pair including both elements.
	 * @return
	 * 		a String representation of the Pair
	 */
	@Override
	public String toString() {
		return "Pair[elem1: " + elem1 + ", elem2: " + elem2 + "]";
	}

	/* --> Getters and Setters <-- */

	/**
	 * @return the first element of the Pair
	 */
	public T1 getFirst()
	{
		return this.elem1;
	}

	/**
	 * @return the second element of the Pair
	 */
	public T2 getSecond()
	{
		return this.elem2;
	}


}
