package rule;

import database.Column;

public class InterEntityCompareRule extends BusinessRule {
	private Column secondcolumn;
	private Column secondpk;
	private Column pk_identifier;
	private String operator;
	public InterEntityCompareRule(){}

	public Column getSecondcolumn() {
		return secondcolumn;
	}

	public void setSecondcolumn(Column secondcolumn) {
		this.secondcolumn = secondcolumn;
	}

	public Column getSecondpk() {
		return secondpk;
	}

	public void setSecondpk(Column secondpk) {
		this.secondpk = secondpk;
	}

	public Column getPk_identifier() {
		return pk_identifier;
	}

	public void setPk_identifier(Column pk_identifier) {
		this.pk_identifier = pk_identifier;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
}
