package swsec;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ListaUsuarios extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ServletContext sc;
	
	public ListaUsuarios() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		sc = config.getServletContext();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		renderHeader(request, response);
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conexion = DriverManager.getConnection(Config.SQLITE_URL);
			Statement statement = conexion.createStatement();
			String query = "SELECT * FROM usuarios WHERE type=" + request.getParameter("type");
			ResultSet resultado = statement.executeQuery(query);
			PrintWriter writer = response.getWriter();
			writer.println("<table border='1'>");
			writer.println("<tr><td>Usuarios del sistema</td></tr>");
			while (resultado.next())
				writer.println("<tr><td>" + resultado.getString("username") + "</td></tr>");
			writer.println("</table>");
			statement.close();
			conexion.close();
		} catch (Exception e) {
			throw new ServletException(e);
		}
		renderFooter(request, response);
	}

	private void renderHeader(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		RequestDispatcher dispatcher = sc.getRequestDispatcher("/header.jsp");
		dispatcher.include(request, response);
		HttpSession session = request.getSession(true);
		if (session.getAttribute("username") != null) {
			out.println("<p>Usuario: " + session.getAttribute("username") + "</p>");
		}
	}
	
	private void renderFooter(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		RequestDispatcher dispatcher = sc.getRequestDispatcher("/footer.inc");
		dispatcher.include(request, response);
	}

}
