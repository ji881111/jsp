package com.javalec.ex;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/ModifyOk")
public class ModifyOk extends HttpServlet {
	
	private Connection connection;
	private Statement stmt;

	private String name, id, pw, phone1, phone2, phone3, gender;
	
	HttpSession httpSession;
	
	public ModifyOk() {
	// TODO Auto-generated constructor stub
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doGet");
		actionDo(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doPost");
		actionDo(request, response);
	}
	
	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.setCharacterEncoding("UTF-8");
		httpSession  = request.getSession();
		
		name = request.getParameter("name");
		id = request.getParameter("id");
		pw = request.getParameter("pw");
		phone1 = request.getParameter("phone1");
		phone2 = request.getParameter("phone2");
		phone3 = request.getParameter("phone3");
		gender = request.getParameter("gender");
		
		if(pwConfirm()) {
			
			System.out.println("OK");
			
			String query = "update member set name ='" + name + "',phone1 = '" + phone1 + "',phone2 = '" + phone2 + "',phone3 = '" + phone3 + "', gender ='" + gender + "'where pw = '" + pw + "'";
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "scott", "tiger");
				stmt = connection.createStatement();
				int i = stmt.executeUpdate(query); //반환값이 int형 몇개의 코드가 수정됐는지 
				if(i == 1) {
					System.out.println("update success");
					httpSession.setAttribute("name", name); //이름이 수정될수도 있으닌깐
					response.sendRedirect("modifyResult.jsp");
				} else {
					System.out.println("update fail");
					response.sendRedirect("modify.jsp");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if(stmt != null) stmt.close();
					if(connection != null) connection.close();
				} catch (Exception e) {}
			}
		
		} else {
			System.out.println("NG");
		}
	}
	
	private boolean pwConfirm() {
		boolean rs = false;
		
		String sessionPw = (String)httpSession.getAttribute("pw");
		
		if(sessionPw.equals(pw)) {
			rs = true;
		} else {
			rs = false;
		}
		return rs;
	}
}
