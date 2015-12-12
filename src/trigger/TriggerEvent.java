package trigger;

import java.util.ArrayList;

import rule.BusinessRule;

import database.Column;
import database.Scheme;
import database.Table;

public class TriggerEvent {
	private BusinessRule rule;
	private String name;
	private Column column;
	private Table table;
	private Scheme scheme;
	private ArrayList<TriggerTypes> triggertypes = new ArrayList<TriggerTypes>();
	private TriggerTiming triggertiming;
	private String triggersourcecode;
	private boolean active;
	
	public TriggerEvent(){}

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<TriggerTypes> getTriggertypes() {
		return triggertypes;
	}

	public void setTriggertypes(ArrayList<TriggerTypes> triggertypes) {
		this.triggertypes = triggertypes;
	}

	public void addTriggerType(TriggerTypes tp){
		triggertypes.add(tp);
	}
	public void deleteTriggerType(TriggerTypes tp){
		triggertypes.remove(tp);
	}

	public TriggerTiming getTriggertiming() {
		return triggertiming;
	}

	public void setTriggertiming(TriggerTiming triggertiming) {
		this.triggertiming = triggertiming;
	}


	public String getTriggerSourceCode() {
		return triggersourcecode;
	}

	public void setTriggerSourceCode(String triggersourcecode) {
		this.triggersourcecode = triggersourcecode;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public BusinessRule getRule() {
		return rule;
	}

	public void setRule(BusinessRule rule) {
		this.rule = rule;
	}

	public Scheme getScheme() {
		return scheme;
	}

	public void setScheme(Scheme scheme) {
		this.scheme = scheme;
	}
	
	
}