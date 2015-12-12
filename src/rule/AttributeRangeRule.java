package rule;

public class AttributeRangeRule extends BusinessRule {
	private String minvalue;
	private String maxvalue;
	private String between;
	public AttributeRangeRule(){}
	
	public String getMinValue() {
		return minvalue;
	}
	public void setMinValue(String minvalue) {
		this.minvalue = minvalue;
	}
	public String getMaxValue() {
		return maxvalue;
	}
	public void setMaxValue(String maxvalue) {
		this.maxvalue = maxvalue;
	}
	public String getBetween() {
		return between;
	}
	public void setBetween(String between) {
		this.between = between;
	}

}
