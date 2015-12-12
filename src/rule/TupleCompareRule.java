package rule;

import database.Column;

public class TupleCompareRule extends BusinessRule {
	private String operator;
	private Column secondcolumn;
	public TupleCompareRule(){}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Column getSecondcolumn() {
		return secondcolumn;
	}

	public void setSecondcolumn(Column secondcolumn) {
		this.secondcolumn = secondcolumn;
	}
	
	
	
}
