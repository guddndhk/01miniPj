package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

public class AddPurchaseAction extends Action {

	public AddPurchaseAction() {
		
	}
	
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//request.getParameter("prodNo");
		//request.getParameter("buyerId");
			
		ProductVO productVO = new ProductVO();
		UserVO userVO = new UserVO();
		PurchaseVO purchaseVO = new PurchaseVO();
		
		userVO.setUserId(request.getParameter("buyerId"));
		productVO.setProdNo(Integer.parseInt(request.getParameter("prodNo")));
		//purchaseVO.setBuyer(userVO);
		purchaseVO.setDivyAddr(request.getParameter("receiverAddr"));		
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));
		purchaseVO.setReceiverName(request.getParameter("receiverName"));
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));
		purchaseVO.setDivyRequest(request.getParameter("receiverRequest"));
		purchaseVO.setTranCode(request.getParameter("tranCode"));
		//purchaseVO.setOrderDate(request.getParameter("orderDate"));
		purchaseVO.setDivyDate(request.getParameter("receiverDate"));
		purchaseVO.setTranCode("1");
		
		ProductService proService = new ProductServiceImpl();
		ProductVO productVO2 = proService.getProduct(Integer.parseInt(request.getParameter("prodNo")));
		purchaseVO.setPurchaseProd(productVO2);
		
		HttpSession session = request.getSession();
		purchaseVO.setBuyer((UserVO)session.getAttribute("user"));
		
		PurchaseService purService = new PurchaseServiceImpl();
		purService.addPurchase(purchaseVO);
		request.setAttribute("purchaseVO", purchaseVO);
		
		System.out.println("addPurchaseAction:: purchaseVO °ª È®ÀÎ:"+purchaseVO);
		
		
		
		return "forward:/purchase/addPurchase.jsp";
	}

}
