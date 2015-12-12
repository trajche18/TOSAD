package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import DAO.ITargetDatabaseDAO;
import DAO.IToolDatabaseDAO;
import DAO.TargetMySQLDAO;
import DAO.TargetOracleDAO;
import DAO.OracleToolDatabaseDAO;

import database.MySQLScheme;
import database.OracleScheme;
import database.Scheme;

public class InitDatabaseServlet extends HttpServlet {
	private String id;
	private String hash;
	private String error;
	ITargetDatabaseDAO target;
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		setId(req.getParameter("id"));
		setHash(req.getParameter("hash"));
		IToolDatabaseDAO db = new OracleToolDatabaseDAO();
		if (db.authenticate(hash)) {
			Scheme scheme = db.getTargetScheme(Integer.parseInt(id));
			if (scheme instanceof OracleScheme) {
				target = new TargetOracleDAO();
			} else if (scheme instanceof MySQLScheme) {
				target = new TargetMySQLDAO();
			}
			target.getAllTables(scheme);
			target.getAllColumns(scheme);
			System.out.println(scheme);
			
			db.writeDatabaseObjectsToToolDatabase(scheme);
		}
	}

	@Path("/error")
	@Produces({ "application/json", "text/plain" })
	public String error() {
		return "erro";
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

}
