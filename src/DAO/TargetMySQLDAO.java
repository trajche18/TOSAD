package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connection.MySQLDBConnection;
import connection.OracleDBConnection;
import connection.ToolDBConnection;

import database.Column;
import database.MySQLScheme;
import database.Scheme;
import database.Table;

import rule.AttributeCompareRule;
import rule.AttributeListRule;
import rule.AttributeOtherRule;
import rule.AttributeRangeRule;
import rule.BusinessRule;
import rule.TupleCompareRule;
import trigger.TriggerEvent;

public class TargetMySQLDAO implements ITargetDatabaseDAO {

	private Connection targetconnection;
	Statement st;
	ResultSet rs;

	public TargetMySQLDAO() {	}

	public void writeTriggerToTargetDatabase(TriggerEvent ts) {
		try {
			targetconnection = new MySQLDBConnection()
					.getTargetDatabaseConnection(
							((MySQLScheme) ts.getScheme()).getHostname(),
							((MySQLScheme) ts.getScheme()).getPort() + "",
							((MySQLScheme) ts.getScheme()).getServicename(),
							((MySQLScheme) ts.getScheme()).getUsername(),
							((MySQLScheme) ts.getScheme()).getPassword());
			st = targetconnection.createStatement();
			st.execute(ts.getTriggerSourceCode());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Scheme getAllTables(Scheme s) {
		targetconnection = new MySQLDBConnection().getTargetDatabaseConnection(
				((MySQLScheme) s).getHostname(), ((MySQLScheme) s).getPort()
						+ "", ((MySQLScheme) s).getServicename(),
				((MySQLScheme) s).getUsername(),
				((MySQLScheme) s).getPassword());
		System.out.println("Target Connection Established!");

		String tablequery = "SELECT table_name FROM INFORMATION_SCHEMA.tables where table_schema = '"+ ((MySQLScheme) s).getServicename() +"'";
		ArrayList<Table> tables = new ArrayList<Table>();
		try {
			st = targetconnection.createStatement();
			rs = st.executeQuery(tablequery);
			while (rs.next()) {
				Table table = new Table();
				table.setName(rs.getString("table_name"));
				tables.add(table);
			}
		} catch (SQLException se2) {
		}
		s.setTables(tables);
		return s;
	}

	@Override
	public Scheme getAllColumns(Scheme s) {
		for (Table t : s.getTables()) {
			String columnquery = "SELECT column_name, data_type FROM INFORMATION_SCHEMA.columns where table_schema = '"+ ((MySQLScheme) s).getServicename() +"' and table_name = '" + t.getName() + "'";
			ArrayList<Column> columns = new ArrayList<Column>();
			try {
				st = targetconnection.createStatement();
				rs = st.executeQuery(columnquery);
				while (rs.next()) {
					Column column = new Column();
					column.setName(rs.getString("column_name"));
					column.setType(rs.getString("data_type"));
					columns.add(column);
				}
				t.setColumns(columns);
			} catch (SQLException se2) {
			}
		}
		return s;
	}


}
