package database;

import java.util.ArrayList;

import trigger.TriggerEvent;

public class Table {
	private String name;
	private Scheme scheme;
	private int id;
	private ArrayList<Column> columns = new ArrayList<Column>();
	private ArrayList<TriggerEvent> triggers = new ArrayList<TriggerEvent>();
	
	public Table(){	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Column> getColumns() {
		return columns;
	}

	public void setColumns(ArrayList<Column> columns) {
		this.columns = columns;
	}
	public void addTrigger(TriggerEvent te){
		triggers.add(te);
	}
	public ArrayList<TriggerEvent> getTriggers() {
		return triggers;
	}

	public void setTriggers(ArrayList<TriggerEvent> triggers) {
		this.triggers = triggers;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Scheme getScheme() {
		return scheme;
	}

	public void setScheme(Scheme scheme) {
		this.scheme = scheme;
	}

	public String toString(){
		return getName();
	}
}
