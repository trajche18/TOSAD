package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connection.OracleDBConnection;
import connection.ToolDBConnection;

import database.Column;
import database.OracleScheme;
import database.Scheme;
import database.Table;

import rule.AttributeCompareRule;
import rule.AttributeListRule;
import rule.AttributeOtherRule;
import rule.AttributeRangeRule;
import rule.BusinessRule;
import rule.TupleCompareRule;
import trigger.TriggerEvent;

public class TargetOracleDAO implements ITargetDatabaseDAO {

	private Connection targetconnection;
	private Connection toolconnection;
	Statement st;
	ResultSet rs;

	public TargetOracleDAO() {}

	public void writeTriggerToTargetDatabase(TriggerEvent ts) {
		try {
			targetconnection = new OracleDBConnection()
					.getTargetDatabaseConnection(
							((OracleScheme) ts.getScheme()).getHostname(),
							((OracleScheme) ts.getScheme()).getPort() + "",
							((OracleScheme) ts.getScheme()).getServicename(),
							((OracleScheme) ts.getScheme()).getUsername(),
							((OracleScheme) ts.getScheme()).getPassword());
			st = targetconnection.createStatement();
			st.execute(ts.getTriggerSourceCode());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Override
	public Scheme getAllTables(Scheme s) {
		targetconnection = new OracleDBConnection().getTargetDatabaseConnection(
				((OracleScheme) s).getHostname(), ((OracleScheme) s).getPort()
						+ "", ((OracleScheme) s).getServicename(),
				((OracleScheme) s).getUsername(),
				((OracleScheme) s).getPassword());
		System.out.println("Target Connection Established!");

		String tablequery = "SELECT table_name FROM user_tables";
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
			String columnquery = "select column_name, data_type from user_tab_columns where table_name = '"
					+ t.getName() + "'";
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
