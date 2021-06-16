package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class UpdateTranCodeAction extends Action {

	public UpdateTranCodeAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("UpdateTranCodeAction execute() start!!");
		
		PurchaseVO purchaseVO = new PurchaseVO();
		System.out.println("UpdateTranCodeAction purchaseVO::"+purchaseVO);
		
		purchaseVO.setTranCode(request.getParameter("tranCode"));
		purchaseVO.setTranNo(Integer.parseInt(request.getParameter("tranNo")));
		
		System.out.println("tranCode value ::"+purchaseVO.getTranCode());
		
		PurchaseService purservice = new PurchaseServiceImpl();
		purservice.updateTranCode(purchaseVO);
		
		System.out.println("UpdateTranCodeAction execute() end!!");
		
		return "redirect:/listPurchase.do?menu=manage&page="+request.getParameter("page");
	}

}
