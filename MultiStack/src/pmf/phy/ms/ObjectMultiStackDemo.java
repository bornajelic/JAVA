package pmf.phy.ms;

public class ObjectMultiStackDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ObjectMultiStack multistack = new ObjectMultiStack();
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000));
		multistack.push("year", year);
		ValueWrapper price = new ValueWrapper(200.51);
		multistack.push("price", price);
		System.out.println("Current value for year: "
				+ multistack.peek("year").getInitialValue());
		System.out.println("Current value for price: "
				+ multistack.peek("price").getInitialValue());
		multistack.push("year", new ValueWrapper(Integer.valueOf(1900)));
		System.out.println("Current value for year: "
				+ multistack.peek("year").getInitialValue());
		multistack.peek("year").setInitialValue(
				((Integer) multistack.peek("year").getInitialValue()).intValue() + 50);
		System.out.println("Current value for year: "
				+ multistack.peek("year").getInitialValue());
		multistack.pop("year");
		System.out.println("Current value for year: "
				+ multistack.peek("year").getInitialValue());
		multistack.peek("year").add("5");
		System.out.println("Current value for year: "
				+ multistack.peek("year").getInitialValue());
		multistack.peek("year").add(5);
		System.out.println("Current value for year: "
				+ multistack.peek("year").getInitialValue());
		multistack.peek("year").add(5.0);
		System.out.println("Current value for year: "
				+ multistack.peek("year").getInitialValue());
	}

}
