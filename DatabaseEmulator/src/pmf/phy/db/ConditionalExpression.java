package pmf.phy.db;

public class ConditionalExpression {

	IFieldValueGetter field;
	
	String strLiteral;
	
	IComparisonOperator operator;

	/*
	 * ConditionalExpression which gets through constructor three arguments: a
	 * reference to IFieldValueGetter strategy, a reference to string literal
	 * and a reference to IComparisonOperator strategy. Add getters for these
	 * properties (and everythingelse you deem appropriate)
	 */
	
	public ConditionalExpression(IFieldValueGetter field, String strLiteral, IComparisonOperator operator) {
		super();
		this.field = field;
		this.strLiteral = strLiteral;
		this.operator = operator;
	}

	public IFieldValueGetter getField() {
		return field;
	}

	public String getStrLiteral() {
		return strLiteral;
	}

	public IComparisonOperator getOperator() {
		return operator;
	}
	
	

	public static void main(String[] args) {
		
		ConditionalExpression expr = new ConditionalExpression(FieldValueGetters.LAST_NAME,"Jelic",ComparisonOperators.EQUALS); //lastName LIKE "Jelic";
		StudentRecord record = new StudentRecord("0035177046", "Jelic", "Borna", 5);

		boolean recordSatisfies = expr.getOperator().satisfied(expr.getField().get(record), expr.getStrLiteral());
		
		System.out.println(recordSatisfies);
		//System.out.println(expr.getField().get(record));
		//System.out.println(expr.getStrLiteral());
	}
	
	
	
	
	
	
	
}
