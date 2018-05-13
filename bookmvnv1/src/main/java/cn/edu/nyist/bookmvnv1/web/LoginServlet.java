package cn.edu.nyist.bookmvnv1.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.edu.nyist.bookmvnv1.biz.LoginBiz;
import cn.edu.nyist.bookmvnv1.biz.impl.LoginBizmpl;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public LoginServlet() {
        super();
       
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("name");
		String pwd=request.getParameter("pwd");
		//获取验证码
		String vcode=request.getParameter("vcode");
		HttpSession session = request.getSession(); 
		//session.setAttribute("validateCode", randomCode.toString()); 
		String serverVcode=(String) session.getAttribute("validateCode");
		if(!serverVcode.equalsIgnoreCase(vcode)) {
			request.setAttribute("msg", "验证码错误");
	    	request.setAttribute("name", name);
	    	request.getRequestDispatcher("login.jsp").forward(request, response);
	    	return ;
		}
		
		//在数据库中比对
		 LoginBiz loginBiz = new LoginBizmpl();
		 boolean ret = loginBiz.findLoginByNameAndPwd(name , pwd);
		    if(ret) {
		    	response.sendRedirect("main.jsp");
		    }
		    else {
		    	request.setAttribute("msg", "用户名或者密码错误");
		    	request.setAttribute("name", name);
		    	request.getRequestDispatcher("login.jsp").forward(request, response);
		    }
	}

}

