package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

public class UpdatePurchaseAction extends Action {

	public UpdatePurchaseAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		System.out.println("업데이트퍼체이스액션 execute() start!!");

		int tranNo = Integer.parseInt(request.getParameter("tranNo"));

		
		PurchaseVO purchaseVO = new PurchaseVO();

		//HttpSession session = request.getSession();
		//System.out.println("업데이트퍼체이스액션 session::" + session);

		//purchaseVO.setBuyer((UserVO)session.getAttribute("user"));
		//purchaseVO.setTranNo(Integer.parseInt(request.getParameter("tranNo")));
		purchaseVO.setTranNo(tranNo);
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));
		purchaseVO.setReceiverName(request.getParameter("receiverName"));
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));
		purchaseVO.setDivyAddr(request.getParameter("receiverAddr"));
		purchaseVO.setDivyRequest(request.getParameter("receiverRequest"));
		purchaseVO.setDivyDate(request.getParameter("divyDate"));

		
		PurchaseService purService = new PurchaseServiceImpl();
		purService.updatePurcahse(purchaseVO);

		//request.setAttribute("purchaseVO", purchaseVO);

		System.out.println("업데이트퍼체이스액션 execute() end!!");

		return "forward:/getPurchase.do?tranNo="+tranNo;
		//return "redirect:/getPurchase.do?tranNo=" + tranNo;
	}

}
