package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.MySQLScheme;
import database.OracleScheme;

import DAO.ITargetDatabaseDAO;
import DAO.IToolDatabaseDAO;
import DAO.TargetMySQLDAO;
import DAO.TargetOracleDAO;
import DAO.OracleToolDatabaseDAO;

import trigger.TriggerEvent;


public class InsertTriggerInTargetDatabase extends HttpServlet {
	private String hash;
	private int id;
	ITargetDatabaseDAO target;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		setId(Integer.parseInt(req.getParameter("id")));
		setHash(req.getParameter("hash"));
		IToolDatabaseDAO db = new OracleToolDatabaseDAO();
		if (db.authenticate(hash)) {
		TriggerEvent trigger = db.getTriggerEvent(id);
		if (trigger.getScheme() instanceof OracleScheme) {
			target = new TargetOracleDAO();
		} else if (trigger.getScheme() instanceof MySQLScheme) {
			target = new TargetMySQLDAO();
		}
		target.writeTriggerToTargetDatabase(trigger);
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
