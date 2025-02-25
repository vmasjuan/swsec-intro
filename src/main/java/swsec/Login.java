package swsec;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static swsec.Helpers.checkUserLogin;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LogManager.getLogger(Login.class);

	public Login() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(true);
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		LOGGER.info("Attempted login user: " + username);
		if (checkUserLogin(username, password)) {
			session.setAttribute("username", username);
			session.setAttribute("isAdmin", username.equals("admin") ? "true" : "false");
			String landingPage = request.getParameter("landingPage");
			response.sendRedirect(landingPage != null ? landingPage : "saludos.jsp");
		} else {
			response.sendRedirect("login.jsp?notFoundError=1");
		}
	}

}
