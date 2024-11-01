import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("signup".equals(action)) {
            signup(request, response);
        } else {
            login(request, response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String url = "jdbc:mysql://localhost:3306/mydb";
        String dbUsername = "root"; // Your DB username
        String dbPassword = "Vishwas123#"; // Your DB password

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password); // Hash in production
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    // Successful login - redirect to another page
                    response.sendRedirect("welcome.html"); // Change to your desired page
                } else {
                    response.getWriter().println("Invalid username or password.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void signup(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String url = "jdbc:mysql://localhost:3306/mydb";
        String dbUsername = "root"; // Your DB username
        String dbPassword = "yourpassword"; // Your DB password

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password); // Hash in production
                int rowsAffected = statement.executeUpdate();

                if (rowsAffected > 0) {
                    response.getWriter().println("Signup successful!");
                } else {
                    response.getWriter().println("Signup failed.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
