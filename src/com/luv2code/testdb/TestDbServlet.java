package com.luv2code.testdb;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

@WebServlet(name = "TestDbServlet", value = "/TestDbServlet")
public class TestDbServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // setup connection variables
        String user = "hbustudent";
        String pass = "hbustudent";
        String jdbcUrl = "jdbc:postgresql://localhost:5432/student";
        String driver = "org.postgresql.Driver";
        // get connection to database
        try {
            PrintWriter out = response.getWriter();

            out.println("Connecting to database: " + jdbcUrl);

            Class.forName(driver);

            Connection myConn = DriverManager.getConnection(jdbcUrl,user,pass);

            out.println("CONNECTION SUCCESS!!!");

            myConn.close();

        } catch (Exception exception) {
            exception.printStackTrace();
            throw new ServletException(exception);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
