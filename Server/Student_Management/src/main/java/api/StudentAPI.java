package api;

import com.google.gson.Gson;
import dto.StudentDTO;
import util.InMemory;

import javax.annotation.Resource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/students")
public class StudentAPI extends HttpServlet {
    @Resource(name = "connectionPool")
    DataSource pool;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String option = req.getParameter("option");
        if (option != null && option.equalsIgnoreCase("all")) {
            getAll(req, resp);
            return;
        }

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = pool.getConnection();
            String sql = "SELECT * FROM student WHERE id = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, req.getParameter("id"));
            //If the query is a select
            resultSet = ps.executeQuery();
            if (resultSet.next()) {
                StudentDTO obj = new StudentDTO();
                String name = resultSet.getString(2);
                String address = resultSet.getString(3);
                String gender = resultSet.getString(4);
                String contactNumber = resultSet.getString(5);
                int age = resultSet.getInt(6);

                obj.setId(req.getParameter("id"));
                obj.setName(name);
                obj.setAddress(address);
                obj.setGender(gender);
                obj.setContactNumber(contactNumber);
                obj.setAge(age);
                resp.setContentType("application/json");
                String toSend = new Gson().toJson(obj);
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write(toSend);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Student Not Found");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        } finally {
            try {
                if (ps != null && !ps.isClosed()) ps.close();
                if (resultSet != null && !resultSet.isClosed()) resultSet.close();
                if (connection != null && !connection.isClosed()) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void getAll(HttpServletRequest req, HttpServletResponse resp) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = pool.getConnection();
            String sql = "SELECT * FROM student";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            List<StudentDTO> list = new ArrayList<>();
            while (rs.next()){
                StudentDTO obj = new StudentDTO();
                obj.setId(rs.getString("id"));
                obj.setName(rs.getString("name"));
                obj.setAddress(rs.getString("address"));
                obj.setGender(rs.getString("gender"));
                obj.setContactNumber(rs.getString("contact_number"));
                obj.setAge(rs.getInt("age"));
                list.add(obj);
            }

            resp.setContentType("application/json");
            String toSend = new Gson().toJson(list);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(toSend);

        } catch (SQLException | IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try {
                resp.getWriter().println("Error " + e.getMessage());
            } catch (IOException ex) {
                e.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            try {
                if (ps != null && !ps.isClosed()) ps.close();
                if (rs != null && !rs.isClosed()) rs.close();
                if (connection != null && !connection.isClosed()) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String header = req.getHeader("Content-Type");
        String status = "Student Adding Failed";
        if (header.equals("application/json")) {
            StudentDTO studentDTO = new Gson().fromJson(req.getReader(), StudentDTO.class);
            Connection connection = null;
            PreparedStatement ps = null;
            try {
                connection = pool.getConnection();
                ps = connection.prepareStatement("INSERT INTO student(id,name,address,gender,contact_number,age) VALUES (?,?,?,?,?,?)");
                ps.setString(1, studentDTO.getId());
                ps.setString(2, studentDTO.getName());
                ps.setString(3, studentDTO.getAddress());
                ps.setString(4, studentDTO.getGender());
                ps.setString(5, studentDTO.getContactNumber());
                ps.setInt(6, studentDTO.getAge());

                //If the query changes the database
                int affectedRows = ps.executeUpdate();
                status = affectedRows > 0 ? "Student Added Success" : status;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (ps != null && !ps.isClosed()) ps.close();
                    if (connection != null && !connection.isClosed()) connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        resp.getWriter().write(status);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String header = req.getHeader("Content-Type");
        String status = "Student Update Operation Failed";
        if (header.equals("application/json")) {
            StudentDTO student = new Gson().fromJson(req.getReader(), StudentDTO.class);
            Connection connection = null;
            PreparedStatement ps = null;
            try {
                connection = pool.getConnection();
                ps = connection.prepareStatement(
                        "UPDATE student SET name=? ,address=? , gender =? ,contact_number = ? , age = ? WHERE id = ?");
                ps.setString(1, student.getName());
                ps.setString(2, student.getAddress());
                ps.setString(3, student.getGender());
                ps.setString(4, student.getContactNumber());
                ps.setInt(5, student.getAge());
                ps.setString(6, student.getId());

                int affectedRows = ps.executeUpdate();
                status = affectedRows > 0 ? "Student Update Operation Success" : status;
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println("Status : " + status);
            } catch (SQLException e) {
                e.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().println("Status : " + status);
            } finally {
                try {
                    if (connection != null && !connection.isClosed()) connection.close();
                    if (ps != null && !ps.isClosed()) ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Connection connection = null;
        PreparedStatement ps = null;
        String status = "Student Delete Operation Failed";
        try {
            connection = pool.getConnection();
            ps = connection.prepareStatement("DELETE FROM student WHERE id = ?");
            ps.setString(1,id);
            int affectedRows = ps.executeUpdate();
            status = affectedRows>0 ? "Student Delete Operation Success":status;
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("Status : "+status);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("Status : "+status);
        }finally {
            try {
                if (ps!=null && !ps.isClosed())ps.close();
                if (connection!=null && !connection.isClosed())connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
