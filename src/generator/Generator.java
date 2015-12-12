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

import database.MySQLScheme;
import database.OracleScheme;


import rule.BusinessRule;
import trigger.TriggerEvent;

public class Generator {
	private TriggerEvent trigger;
	ServletContext context;
	private IGenerator igenerator;
	public TriggerEvent generate(BusinessRule bs) {
		if (bs.getColumn().getTable().getScheme() instanceof OracleScheme) { 
			igenerator = new OracleGenerator();
		} else 	if (bs.getColumn().getTable().getScheme() instanceof MySQLScheme) {
			igenerator = new MySQLGenerator();
		}
		igenerator.setContext(context);
		trigger = igenerator.generateTrigger(bs);
		return trigger;
	}


	public ServletContext getContext() {
		return context;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}
	
	
}
