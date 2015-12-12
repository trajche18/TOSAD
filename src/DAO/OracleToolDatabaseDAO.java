package DAO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import rule.AttributeCompareRule;
import rule.AttributeListRule;
import rule.AttributeOtherRule;
import rule.AttributeRangeRule;
import rule.BusinessRule;
import rule.EntityOtherRule;
import rule.InterEntityCompareRule;
import rule.TupleCompareRule;
import trigger.TriggerEvent;
import connection.ToolDBConnection;
import database.Column;
import database.MySQLScheme;
import database.OracleScheme;
import database.Scheme;
import database.Table;

public class OracleToolDatabaseDAO implements IToolDatabaseDAO {
	private Connection toolconnection;
	Statement st;
	ResultSet rs;

	public OracleToolDatabaseDAO() {
		toolconnection = new ToolDBConnection().getToolDatabaseConnection();
	}

	@Override
	public boolean authenticate(String key) {
		boolean authenticated = false;
		try {
			String query = "select * from authorization where key = '" + key
					+ "'";
			st = toolconnection.createStatement();
			rs = st.executeQuery(query);
			System.out.println("Authentication");
			if (rs.next()) {
				authenticated = true;
			}
			String removequery = "delete from authorization where key = '"
					+ key + "'";
			st = toolconnection.createStatement();
			rs = st.executeQuery(removequery);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Authentication Result: " + authenticated);
		return authenticated;
	}

	@Override
	public Scheme getTargetScheme(int id) {
		String query = "SELECT * FROM oraclescheme where id='" + id + "'";
		Scheme targetscheme = null;
		try {
			st = toolconnection.createStatement();
			rs = st.executeQuery(query);
			while (rs.next()) {

				if (rs.getString("TYPE").equals("oracle")) {
					targetscheme = new OracleScheme();
					targetscheme.setId(id);
					((OracleScheme) targetscheme).setHostname(rs
							.getString("HOSTNAME"));
					((OracleScheme) targetscheme).setPort(rs.getInt("PORT"));
					((OracleScheme) targetscheme).setServicename(rs
							.getString("SERVICE"));
					((OracleScheme) targetscheme).setUsername(rs
							.getString("USERNAME"));
					((OracleScheme) targetscheme).setPassword(rs
							.getString("PASSWORD"));
				} else if (rs.getString("TYPE").equals("mysql")) {
					targetscheme = new MySQLScheme();
					targetscheme.setId(id);
					((MySQLScheme) targetscheme).setHostname(rs
							.getString("HOSTNAME"));
					((MySQLScheme) targetscheme).setPort(rs.getInt("PORT"));
					((MySQLScheme) targetscheme).setServicename(rs
							.getString("SERVICE"));
					((MySQLScheme) targetscheme).setUsername(rs
							.getString("USERNAME"));
					((MySQLScheme) targetscheme).setPassword(rs
							.getString("PASSWORD"));
					System.out.println("mysql scheme created");
				}

			}
		} catch (SQLException se2) {
		}

		return targetscheme;
	}

	@Override
	public void writeDatabaseObjectsToToolDatabase(Scheme s) {
		String insertstring = "";
		int aantaltables = 0;
		int aantalcollumns = 0;
		try {
			st = toolconnection.createStatement();
			// rs = st.executeQuery(inserttablequery);
		} catch (SQLException e) {
			System.out.println("Exception during insert of table!");
			e.printStackTrace();
		}
		for (Table t : s.getTables()) {
			try {
				st.addBatch("INSERT INTO ORACLETABLE (ORACLESCHEME, NAME, ID) VALUES ("
						+ s.getId()
						+ ", '"
						+ t.getName()
						+ "', oracle_table.nextval)");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			aantaltables++;

			for (Column c : t.getColumns()) {
				try {
					st.addBatch("INSERT INTO ORACLECOLUMN (ORACLETABLE, NAME, TYPE, ID) VALUES (oracle_table.currval, '"
							+ c.getName()
							+ "', '"
							+ c.getType()
							+ "', oracle_column.nextval)");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				aantalcollumns++;
			}
		}

		try {
			st.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public BusinessRule getBusinessRuleFromDatabase(int id) {
		BusinessRule rule = null;
		String rulequery = "select * from businessrule where id='" + id + "'";
		ResultSet rsc;
		ResultSet rst;
		ResultSet spec;
		String code = "";
		try {
			st = toolconnection.createStatement();
			rs = st.executeQuery(rulequery);
			while (rs.next()) {
				code = rs.getString("CODE");
				if (code.equals("ACMP")) {
					rule = new AttributeCompareRule();
				}
				if (code.equals("ARNG")) {
					rule = new AttributeRangeRule();
				}
				if (code.equals("ALIS")) {
					rule = new AttributeListRule();
				}
				if (code.equals("AOTH")) {
					rule = new AttributeOtherRule();
				}
				if (code.equals("TCMP")) {
					rule = new TupleCompareRule();
				}
				if (code.equals("ICMP")) {
					rule = new InterEntityCompareRule();
				}
				if (code.equals("EOTH")) {
					rule = new EntityOtherRule();
				}
				int columnid = rs.getInt("COLLUMN");
				rule.setId(id);
				rule.setCode(rs.getString("CODE"));
				rule.setErrormessage(rs.getString("ERRORMESSAGE"));
				rule.setName(rs.getString("NAME"));
				String columnquery = "select name,ID from oraclecolumn where id='"
						+ columnid + "'";
				rsc = st.executeQuery(columnquery);
				Column c = new Column();
				while (rsc.next()) {
					c.setName(rsc.getString("NAME"));
					c.setId(rsc.getInt("ID"));
				}
				rule.setColumn(c);
				String tablequery = "select NAME,ID, ORACLESCHEME from oracletable where id = (select oracletable from oraclecolumn where id = '"
						+ columnid + "')";
				rst = st.executeQuery(tablequery);
				Table t = new Table();
				int scheme = 0;
				while (rst.next()) {
					t.setName(rst.getString("NAME"));
					t.setId(rst.getInt("ID"));
					scheme = rst.getInt("ORACLESCHEME");
				}
				c.setTable(t);
				t.setScheme(getTargetScheme(scheme));
				if (code.equals("ARNG")) {
					String arngtablequery = "select * from ARNG where businessruleid='"
							+ id + "'";
					spec = st.executeQuery(arngtablequery);
					while (spec.next()) {
						((AttributeRangeRule) rule).setMinValue(spec
								.getInt("MINVALUE") + "");
						((AttributeRangeRule) rule).setMaxValue(spec
								.getInt("MAXVALUE") + "");
						((AttributeRangeRule) rule).setBetween("BETWEEN");

					}
				}

				if (code.equals("ACMP")) {
					String acmptablequery = "select * from ACMP where businessruleid='"
							+ id + "'";
					spec = st.executeQuery(acmptablequery);
					while (spec.next()) {
						((AttributeCompareRule) rule).setValue(spec
								.getInt("VALUE") + "");
						((AttributeCompareRule) rule).setOperator(spec
								.getString("OPERATOR"));
					}
				}
				if (code.equals("ALIS")) {
					String alistablequery = "select * from ALIS where businessruleid='"
							+ id + "'";
					spec = st.executeQuery(alistablequery);
					while (spec.next()) {
						((AttributeListRule) rule).setValuelist(spec
								.getString("VALUELIST"));
						if (spec.getString("IN_OR_NOTIN").equals("in")) {
							((AttributeListRule) rule).setInlist(true);
						} else if (spec.getString("IN_OR_NOTIN").equals(
								"not in")) {
							((AttributeListRule) rule).setInlist(false);
						}
					}
				}
				if (code.equals("AOTH")) {
					String aothtablequery = "select * from AOTH where businessruleid='"
							+ id + "'";
					spec = st.executeQuery(aothtablequery);
					while (spec.next()) {
						((AttributeOtherRule) rule).setSqlstring(spec
								.getString("SQLSTRING"));
					}
				}
				if (code.equals("TCMP")) {
					int secondcolumnid = 0;
					String tcmptablequery = "select * from tcmp where businessruleid='"
							+ id + "'";
					spec = st.executeQuery(tcmptablequery);
					while (spec.next()) {
						((TupleCompareRule) rule).setOperator(spec
								.getString("OPERATOR"));
						secondcolumnid = spec.getInt("SECONDCOLUMN");
					}
					String secondcolumnquery = "select name,ID from oraclecolumn where id='"
							+ secondcolumnid + "'";
					rsc = st.executeQuery(secondcolumnquery);
					Column secondcolumn = new Column();
					while (rsc.next()) {
						secondcolumn.setName(rsc.getString("NAME"));
						secondcolumn.setId(rsc.getInt("ID"));
					}
					;
					((TupleCompareRule) rule).setSecondcolumn(secondcolumn);

				}

				if (code.equals("ICMP")) {
					int secondcolumn_num = 0;
					int secondpk_num = 0;
					int pk_identifier_num = 0;
					String icmptablequery = "select * from icmp where businessruleid='"
							+ id + "'";
					spec = st.executeQuery(icmptablequery);
					while (spec.next()) {
						((InterEntityCompareRule) rule).setOperator(spec
								.getString("OPERATOR"));
						secondcolumn_num = spec.getInt("SECONDCOLUMN");
						secondpk_num = spec.getInt("SECONDPK");
						pk_identifier_num = spec.getInt("PK_IDENTIFIER");
					}
					Column secondcolumn = new Column();
					Column secondpk = new Column();
					Column pk_identifier = new Column();
					
					String secondcolumnquery = "select name,ID from oraclecolumn where id='"
							+ secondcolumn_num + "'";
					rsc = st.executeQuery(secondcolumnquery);
					while (rsc.next()) {
						secondcolumn.setName(rsc.getString("NAME"));
						secondcolumn.setId(rsc.getInt("ID"));
					}

					String secondpkquery = "select name,ID from oraclecolumn where id='"
							+ secondpk_num + "'";
					rsc = st.executeQuery(secondpkquery);
					while (rsc.next()) {
						secondpk.setName(rsc.getString("NAME"));
						secondpk.setId(rsc.getInt("ID"));
					}

					String pk_identifierquery = "select name,ID from oraclecolumn where id='"
							+ pk_identifier_num + "'";
					rsc = st.executeQuery(pk_identifierquery);
					while (rsc.next()) {
						pk_identifier.setName(rsc.getString("NAME"));
						pk_identifier.setId(rsc.getInt("ID"));
					}
					
					String secondtablequery = "select NAME,ID from oracletable where id = (select oracletable from oraclecolumn where id = '"
							+ secondcolumn.getId() + "')";
					rsc = st.executeQuery(secondtablequery);
					Table secondtable = new Table();
					while (rsc.next()) {
						secondtable.setName(rsc.getString("NAME"));
						secondtable.setId(rsc.getInt("ID"));
					}
					secondcolumn.setTable(secondtable);

					((InterEntityCompareRule) rule)
							.setSecondcolumn(secondcolumn);
					
					((InterEntityCompareRule) rule)
							.setPk_identifier(pk_identifier);
					((InterEntityCompareRule) rule).setSecondpk(secondpk);
System.out.println(((InterEntityCompareRule) rule)
							.getSecondcolumn());
				}
				if (code.equals("EOTH")) {

					String eothtablequery = "select * from eoth where businessruleid='"
							+ id + "'";
					spec = st.executeQuery(eothtablequery);
					while (spec.next()) {
						((EntityOtherRule) rule).setSqlstring(spec
								.getString("sqlstring"));

					}
	

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(rule);
		return rule;
	}

	@Override
	public void writeTriggerToToolDatabase(TriggerEvent ts) {
		try {
			PreparedStatement st = toolconnection
					.prepareStatement("insert into ORACLETRIGGER (oracletable, oraclecolumn, source, active, name, businessruleid) values (?,?,?, '1', ?,?)");
			st.setInt(1, ts.getColumn().getTable().getId());
			st.setInt(2, ts.getColumn().getId());
			st.setString(3, ts.getTriggerSourceCode());
			st.setString(4, ts.getName());
			st.setInt(5, ts.getRule().getId());
			st.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public TriggerEvent getTriggerEvent(int id) {
		String schemeidquery = "select id from oraclescheme where id =(select oraclescheme from oracletable where id = (select oracletable from oracletrigger where businessruleid = '"
				+ id + "'))";
		String triggerquery = "select * from oracletrigger where businessruleid='"
				+ id + "'";
		int schemeid = 0;
		TriggerEvent trigger = new TriggerEvent();
		try {
			st = toolconnection.createStatement();
			rs = st.executeQuery(schemeidquery);
			while (rs.next()) {
				schemeid = rs.getInt("ID");
			}
			Scheme targetscheme = getTargetScheme(schemeid);

			st = toolconnection.createStatement();
			rs = st.executeQuery(triggerquery);
			while (rs.next()) {
				trigger.setTriggerSourceCode(rs.getString("SOURCE"));
				trigger.setScheme(targetscheme);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return trigger;
	}
}