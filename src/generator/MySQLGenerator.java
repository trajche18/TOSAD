package generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import rule.AttributeListRule;
import rule.AttributeOtherRule;
import rule.AttributeRangeRule;
import rule.AttributeCompareRule;
import rule.BusinessRule;
import rule.EntityOtherRule;
import rule.InterEntityCompareRule;
import rule.TupleCompareRule;
import trigger.TriggerEvent;

public class MySQLGenerator implements IGenerator{
	public TriggerEvent trigger;
	ServletContext context;
	public TriggerEvent generateTrigger(BusinessRule bs) {
		if (bs instanceof AttributeRangeRule) {
			generateAttributeRangeRule((AttributeRangeRule) bs);
		}
		if (bs instanceof AttributeCompareRule) {
			generateAttributeCompareRule((AttributeCompareRule) bs);
		}
		if (bs instanceof AttributeListRule) {
			generateAttributeListRule((AttributeListRule) bs);
		}
		if (bs instanceof AttributeOtherRule) {
			generateAttributeOtherRule((AttributeOtherRule) bs);
		}
		if (bs instanceof TupleCompareRule) {
			generateTupleCompareRule((TupleCompareRule) bs);
		}
		if (bs instanceof InterEntityCompareRule) {
			generateInterEntityCompareRule((InterEntityCompareRule) bs);
		}
		if (bs instanceof EntityOtherRule) {
			generateEntityOtherRule((EntityOtherRule) bs);
		}
		return trigger;
	}

	public TriggerEvent generateAttributeRangeRule(AttributeRangeRule arng) {
		trigger = new TriggerEvent();
		String string = "";
		BufferedReader fis = null;
		try {
			fis = new BufferedReader(new InputStreamReader(context.getResourceAsStream("/WEB-INF/data/attributeRange.txt")));

			StringBuilder builder = new StringBuilder();
			int content;
			while ((content = fis.read()) != -1) {
				builder.append((char) content);
			}
			Map<String, String> values = new HashMap<String, String>();
			values.put("NAME", arng.getName());
			values.put("TABLENAME", arng.getColumn().getTable().getName());
			values.put("WHEN", "INSERT OR UPDATE");
			values.put("COLNAME", arng.getColumn().getName());
			values.put("MINVALUE", arng.getMinValue());
			values.put("MAXVALUE", arng.getMaxValue());
			values.put("BETWEEN", arng.getBetween());
			values.put("ERRORMESSAGE", arng.getErrormessage());
			String s = builder.toString();
			for (Map.Entry<String, String> e : values.entrySet()) {
				s = s.replaceAll("\\{" + e.getKey() + "\\}", e.getValue());
			}
			string = s;
		} catch (IOException e) {
			e.printStackTrace();
		}
		trigger.setColumn(arng.getColumn());
		trigger.setTable(arng.getColumn().getTable());
		trigger.setTriggerSourceCode(string);
		trigger.setName(arng.getName());
		trigger.setRule(arng);
		return trigger;

	}

	public TriggerEvent generateAttributeCompareRule(AttributeCompareRule acmp) {
		trigger = new TriggerEvent();
		String string = "";
		BufferedReader fis = null;
		try {
			fis = new BufferedReader(new InputStreamReader(context.getResourceAsStream("/WEB-INF/data/attributeCompare.txt")));

			StringBuilder builder = new StringBuilder();
			int content;
			while ((content = fis.read()) != -1) {
				builder.append((char) content);
			}
			Map<String, String> values = new HashMap<String, String>();
			values.put("NAME", acmp.getName());
			values.put("TABLENAME", acmp.getColumn().getTable().getName());
			values.put("WHEN", "INSERT OR UPDATE");
			values.put("COLNAME", acmp.getColumn().getName());
			values.put("OPERATOR", acmp.getOperator());
			values.put("VALUE", acmp.getValue());
			values.put("ERRORMESSAGE", acmp.getErrormessage());
			String s = builder.toString();
			for (Map.Entry<String, String> e : values.entrySet()) {
				s = s.replaceAll("\\{" + e.getKey() + "\\}", e.getValue());
			}
			string = s;
		} catch (IOException e) {
			e.printStackTrace();
		}
		trigger.setColumn(acmp.getColumn());
		trigger.setTable(acmp.getColumn().getTable());
		trigger.setTriggerSourceCode(string);
		trigger.setName(acmp.getName());
		trigger.setRule(acmp);

		return trigger;

	}

	public TriggerEvent generateAttributeListRule(AttributeListRule alis) {
		trigger = new TriggerEvent();
		String string = "";
		BufferedReader fis = null;
		try {
			fis = new BufferedReader(new InputStreamReader(context.getResourceAsStream("/WEB-INF/data/mySQLTrigger/attributeList.txt")));

			StringBuilder builder = new StringBuilder();
			int content;
			while ((content = fis.read()) != -1) {
				builder.append((char) content);
			}
			Map<String, String> values = new HashMap<String, String>();
			values.put("NAME", alis.getName());
			values.put("TABLENAME", alis.getColumn().getTable().getName());
			values.put("WHEN", "INSERT OR UPDATE");
			values.put("COLNAME", alis.getColumn().getName());
			values.put("LIST", alis.getValuelist());
			if (alis.isInlist()) {
				values.put("IN_OR_NOT", "IN");
			} else {
				values.put("IN_OR_NOT", "NOT IN");
			}
			values.put("ERRORMESSAGE", alis.getErrormessage());
			String s = builder.toString();
			for (Map.Entry<String, String> e : values.entrySet()) {
				s = s.replaceAll("\\{" + e.getKey() + "\\}", e.getValue());
			}
			string = s;
		} catch (IOException e) {
			e.printStackTrace();
		}
		trigger.setColumn(alis.getColumn());
		trigger.setTable(alis.getColumn().getTable());
		trigger.setTriggerSourceCode(string);
		trigger.setName(alis.getName());
		trigger.setRule(alis);

		return trigger;

	}
	public TriggerEvent generateAttributeOtherRule(AttributeOtherRule aoth) {
		trigger = new TriggerEvent();
		String string = "";
		BufferedReader fis = null;
		try {
			fis = new BufferedReader(new InputStreamReader(context.getResourceAsStream("/WEB-INF/data/mySQLTrigger/attributeOther.txt")));

			StringBuilder builder = new StringBuilder();
			int content;
			while ((content = fis.read()) != -1) {
				builder.append((char) content);
			}
			Map<String, String> values = new HashMap<String, String>();
			values.put("NAME", aoth.getName());
			values.put("TABLENAME", aoth.getColumn().getTable().getName());
			values.put("WHEN", "INSERT OR UPDATE");
			values.put("COLNAME", aoth.getColumn().getName());
			values.put("SQLSTRING", aoth.getSqlstring());
			
			values.put("ERRORMESSAGE", aoth.getErrormessage());
			String s = builder.toString();
			for (Map.Entry<String, String> e : values.entrySet()) {
				s = s.replaceAll("\\{" + e.getKey() + "\\}", e.getValue());
			}
			string = s;
		} catch (IOException e) {
			e.printStackTrace();
		}
		trigger.setColumn(aoth.getColumn());
		trigger.setTable(aoth.getColumn().getTable());
		trigger.setTriggerSourceCode(string);
		trigger.setName(aoth.getName());
		trigger.setRule(aoth);

		return trigger;

	}
	public TriggerEvent generateTupleCompareRule(TupleCompareRule tcmp) {
		trigger = new TriggerEvent();
		String string = "";
		BufferedReader fis = null;
		try {
			fis = new BufferedReader(new InputStreamReader(context.getResourceAsStream("/WEB-INF/data/mySQLTrigger/tupleCompareRule.txt")));

			StringBuilder builder = new StringBuilder();
			int content;
			while ((content = fis.read()) != -1) {
				builder.append((char) content);
			}
			Map<String, String> values = new HashMap<String, String>();
			values.put("NAME", tcmp.getName());
			values.put("TABLENAME", tcmp.getColumn().getTable().getName());
			values.put("WHEN", "INSERT OR UPDATE");
			values.put("COLNAME", tcmp.getColumn().getName());
			values.put("SECONDCOLUMN", tcmp.getSecondcolumn().getName());
			values.put("OPERATOR", tcmp.getOperator());
			values.put("ERRORMESSAGE", tcmp.getErrormessage());
			String s = builder.toString();
			for (Map.Entry<String, String> e : values.entrySet()) {
				s = s.replaceAll("\\{" + e.getKey() + "\\}", e.getValue());
			}
			string = s;
		} catch (IOException e) {
			e.printStackTrace();
		}
		trigger.setColumn(tcmp.getColumn());
		trigger.setTable(tcmp.getColumn().getTable());
		trigger.setTriggerSourceCode(string);
		trigger.setName(tcmp.getName());
		trigger.setRule(tcmp);

		return trigger;

	}

	public ServletContext getContext() {
		return context;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}

	@Override
	public TriggerEvent generateInterEntityCompareRule(
			InterEntityCompareRule icmp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TriggerEvent generateEntityOtherRule(EntityOtherRule eoth) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
