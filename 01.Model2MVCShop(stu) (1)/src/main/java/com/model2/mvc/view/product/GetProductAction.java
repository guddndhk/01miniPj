package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class GetProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		
		ProductService service = new ProductServiceImpl();
		ProductVO vo = service.getProduct(prodNo);
		
		request.setAttribute("vo", vo);
		
		//System.out.println("메뉴확인!!"+request.getParameter("menu"));
		
		if (request.getParameter("menu").equals("manage")) {
				
			return "forward:/updateProductView.do?prodNo="+prodNo;
		}else {
			return "forward:/product/getProduct.jsp";
		}
		
	}

	
}
