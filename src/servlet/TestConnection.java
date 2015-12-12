package servlet;

import generator.Generator;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAO.IToolDatabaseDAO;
import DAO.OracleToolDatabaseDAO;

import rule.BusinessRule;
import trigger.TriggerEvent;

public class TestConnection extends HttpServlet {
	private String hostname;
	private int port;
	private String username;
	private String password;
	private String servicename;
	private Connection connection;
	private String type;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		setHostname(req.getParameter("hostname"));
		setPort(Integer.parseInt(req.getParameter("port")));
		setUsername(req.getParameter("username"));
		setPassword(req.getParameter("password"));
		setServicename(req.getParameter("servicename"));
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		if(req.getParameter("type").equals("mysql")){
			try {
				Class.forName("com.mysql.jdbc.Driver");
				System.out.println("MySQL JDBC Driver Registered!");
				
			} catch (ClassNotFoundException e) {
				System.out.println("Where is your MySQL Driver?");
				e.printStackTrace();
				return;
			}
			try {
				connection = DriverManager.getConnection(
						"jdbc:mysql://" + hostname + ":" + port + "/"
								+ servicename, username, password);
				out.write("Success");

			} catch (SQLException e) {
				out.write("Failed");

				e.printStackTrace();
			}
			
		}else if(req.getParameter("type").equals("oracle")){
			try {
				Class.forName("oracle.jdbc.OracleDriver");
				System.out.println("Oracle JDBC Driver Registered!");
			} catch (ClassNotFoundException e) {
				System.out.println("Where is your Oracle JDBC Driver?");
				e.printStackTrace();
				return;
			}
			try {
				connection = DriverManager.getConnection("jdbc:oracle:thin:@"
						+ hostname + ":" + port + ":" + servicename, username,
						password);
				out.write("Success");

			} catch (SQLException e) {
				out.write("Failed");

				e.printStackTrace();
			}
		}
		
		
		}

	

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}

}
