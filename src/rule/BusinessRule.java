package rule;



import database.Column;
import database.Table;
import trigger.TriggerEvent;

public class BusinessRule{
	private int id;
	private String name;
	private String code;
	private String explanation;
	private String errormessage;
	private Column column;
	private TriggerEvent triggerevent;

	public BusinessRule() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public String getErrormessage() {
		return errormessage;
	}

	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}

	public TriggerEvent getTriggerevent() {
		return triggerevent;
	}

	public void setTriggerevent(TriggerEvent triggerevent) {
		this.triggerevent = triggerevent;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



}
