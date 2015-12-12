package rule;

public class AttributeListRule extends BusinessRule {
	private String valuelist;
	private boolean inlist;

	public AttributeListRule() {
	}

	public String getValuelist() {
		return valuelist;
	}

	public void setValuelist(String valuelist) {
		this.valuelist = valuelist;
	}

	public boolean isInlist() {
		return inlist;
	}

	public void setInlist(boolean inlist) {
		this.inlist = inlist;
	}

}
