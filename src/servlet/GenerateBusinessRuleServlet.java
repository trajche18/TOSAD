package servlet;

import generator.Generator;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.IToolDatabaseDAO;
import DAO.OracleToolDatabaseDAO;

import rule.BusinessRule;
import trigger.TriggerEvent;

public class GenerateBusinessRuleServlet extends HttpServlet {

	private int id;
	private String hash;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		setId(Integer.parseInt(req.getParameter("id")));
		setHash(req.getParameter("hash"));
		IToolDatabaseDAO db = new OracleToolDatabaseDAO();
		if (db.authenticate(hash)) {
			BusinessRule bs = db.getBusinessRuleFromDatabase(id);
			Generator generator = new Generator();
			generator.setContext(getServletContext());
			TriggerEvent trigger = generator.generate(bs);
			db.writeTriggerToToolDatabase(trigger);
		}

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

}
