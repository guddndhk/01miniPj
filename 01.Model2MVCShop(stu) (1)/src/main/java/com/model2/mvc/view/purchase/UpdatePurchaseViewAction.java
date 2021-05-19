package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class UpdatePurchaseViewAction extends Action {

	public UpdatePurchaseViewAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		System.out.println("업데이트퍼체이스액션 tranNo유무:"+tranNo);
		
		PurchaseService purService = new PurchaseServiceImpl();
		PurchaseVO purchaseVO = purService.getPurchase(tranNo);
		System.out.println("업데이트퍼체이스액션 purchaseVO :"+purchaseVO);
		
		request.setAttribute("purchaseVO", purchaseVO);
		
		return "forward:/purchase/updatePurchase.jsp";
	}

}
