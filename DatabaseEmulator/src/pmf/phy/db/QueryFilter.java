package pmf.phy.db;

import java.util.List;

public class QueryFilter implements IFilter {

	List<ConditionalExpression> upit;

	public QueryFilter(List<ConditionalExpression> list) {// (firstName LIKE "borna") and (lastName like "Jelic");

		if (list == null) {
			throw new NullPointerException();
		}
		this.upit = list; 
	}

	@Override
	public boolean accepts(StudentRecord record) { // 0003231 BOrna Jelic 5 -> to prima od interneListe

		for (ConditionalExpression i : upit) {
			if (i.getOperator().satisfied(i.getField().get(record), //kako ja znam koji se operator nalazi i koji inputi su
					i.getStrLiteral())) {
				return true;
			}
		}
		return false;
	}
}
