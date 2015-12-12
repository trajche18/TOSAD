package rule;

public class AttributeCompareRule extends BusinessRule {
	private String operator;
	private String value;

	public AttributeCompareRule() {
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
