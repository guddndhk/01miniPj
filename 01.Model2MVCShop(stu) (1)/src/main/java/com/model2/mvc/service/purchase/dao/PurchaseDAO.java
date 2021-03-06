package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.apache.catalina.connector.Request;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class PurchaseDAO {

	// constructor
	public PurchaseDAO() {
	}

	public void insertPurchase(PurchaseVO purchaseVO) throws Exception {

		// ProductVO productVO = new ProductVO();

		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO transaction VALUES (seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
		stmt.setString(2, purchaseVO.getBuyer().getUserId());
		stmt.setString(3, purchaseVO.getPaymentOption());
		stmt.setString(4, purchaseVO.getReceiverName());
		stmt.setString(5, purchaseVO.getReceiverPhone());
		stmt.setString(6, purchaseVO.getDivyAddr());
		stmt.setString(7, purchaseVO.getDivyRequest());
		stmt.setString(8, purchaseVO.getTranCode());
		stmt.setDate(9, purchaseVO.getOrderDate());
		stmt.setString(10, purchaseVO.getDivyDate());

		stmt.executeUpdate();
		System.out.println("insertPurchase 들어갔는지 확인" + purchaseVO);

		con.close();

	}

	public PurchaseVO findPurchase2(int prodNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "select * from transaction where PROD_NO=?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();
		UserService service = new UserServiceImpl();

		ProductService service2 = new ProductServiceImpl();

		PurchaseVO purchaseVO = null;
		while (rs.next()) {
			purchaseVO = new PurchaseVO();
			purchaseVO.setTranNo(rs.getInt("TRAN_NO"));
			purchaseVO.setPurchaseProd(service2.getProduct(prodNo));
			purchaseVO.setBuyer(service.getUser(rs.getString("BUYER_ID")));
			purchaseVO.setPaymentOption(rs.getString("payment_Option"));
			purchaseVO.setReceiverName(rs.getString("receiver_Name"));
			purchaseVO.setReceiverPhone(rs.getString("receiver_Phone"));
			purchaseVO.setDivyAddr(rs.getString("dlvy_addr"));
			purchaseVO.setDivyRequest(rs.getString("dlvy_request"));
			purchaseVO.setTranCode(rs.getString("TRAN_STATUS_CODE"));
			purchaseVO.setOrderDate(rs.getDate("order_data"));
			purchaseVO.setDivyDate(rs.getString("dlvy_date"));

		}

		con.close();

		return purchaseVO;

	}

	public PurchaseVO findPurchase(int tranNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "select * from transaction where TRAN_NO = ?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);

		ResultSet rs = stmt.executeQuery();
		UserService service = new UserServiceImpl();

		ProductService service2 = new ProductServiceImpl();

		PurchaseVO purchaseVO = null;
		while (rs.next()) {
			// ProductVO ProductVO = new ProductVO();
			purchaseVO = new PurchaseVO();

			purchaseVO.setTranNo(rs.getInt("TRAN_NO"));
			purchaseVO.setPurchaseProd(service2.getProduct(rs.getInt("prod_No")));
			purchaseVO.setBuyer(service.getUser(rs.getString("BUYER_ID")));
			purchaseVO.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			purchaseVO.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchaseVO.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchaseVO.setDivyAddr(rs.getString("DEMAILADDR"));
			purchaseVO.setDivyRequest(rs.getString("dlvy_request"));
			purchaseVO.setTranCode(rs.getString("TRAN_STATUS_CODE"));
			purchaseVO.setOrderDate(rs.getDate("order_data"));
			purchaseVO.setDivyDate(rs.getString("dlvy_date"));

		}

		con.close();
		System.out.println("!!!!!!!! : " + purchaseVO);

		return purchaseVO;

	}

	public HashMap<String, Object> getPurchaseList(SearchVO searchVO, String buyerId) throws Exception {

		System.out.println("PurchaseDAO getPurchaseList buyerId체크 : " + buyerId);
		Connection con = DBUtil.getConnection();
		// sql문 txt버전 참조
		// SELECT tran_no,buyer_id ,receiver_name, receiver_phone, tran_status_code
		// from transaction
		// WHERE buyer_id = 'user01';
		// String sql = "SELECT" + " tran_no, buyer_id, receiver_name, receiver_phone,
		// tran_status_code"
		// + " FROM transaction" + " WHERE buyer_id = ?";
		String sql = "SELECT * FROM TRANSACTION  WHERE buyer_id = '" + buyerId + "' ORDER BY tran_no";
		System.out.println("PurchaseDAO getPurchaseList sql : " + sql);

		PreparedStatement stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);
		// stmt.setString(1, buyerId);
		System.out.println("getPurchaseList에서 stmt후 sql체크 ::" + sql);
		ResultSet rs = stmt.executeQuery();

		rs.last();

		int total = rs.getRow();

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("count", total);

		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit() + 1);
		System.out.println("PurDAO LIST getPage():" + searchVO.getPage());
		System.out.println("PurDAO LIST getPageUnit():" + searchVO.getPageUnit());

		PurchaseVO purchaseVO = null;
		UserService userService = new UserServiceImpl();
		ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();

		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				purchaseVO = new PurchaseVO();
				purchaseVO.setTranNo(rs.getInt("TRAN_NO"));
				purchaseVO.setBuyer(userService.getUser(rs.getString("BUYER_ID")));
				purchaseVO.setReceiverName(rs.getString("RECEIVER_NAME"));
				purchaseVO.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
				purchaseVO.setTranCode(rs.getString("TRAN_STATUS_CODE"));

				list.add(purchaseVO);

				if (!rs.next()) {
					break;
				}
			}
		}
		stmt.close();
		con.close();
		map.put("list", list);
		System.out.println("list : " + map.get("list"));
		return map;
	}

	public HashMap<String, Object> getSaleList(SearchVO searchVO) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM product";
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0")) {
				sql += " WHERE prod_no LIKE '%" + searchVO.getSearchKeyword() + "%'";
			} else if (searchVO.getSearchCondition().equals("1")) {
				sql += " WHERE prod_name LIKE '%" + searchVO.getSearchKeyword() + "%'";
			}
		}
		sql += " ORDER BY prod_no";

		System.out.println("퍼체이스DAO getSaleList sql::" + sql);

		PreparedStatement stmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_UPDATABLE);

		ResultSet rs = stmt.executeQuery();

		rs.last();

		int total = rs.getRow();
		System.out.println("퍼체이스DAO getSaleList total::" + total);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("count", new Integer(total));

		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit() + 1);
		System.out.println("PurDAO SALELIST getPage():" + searchVO.getPage());
		System.out.println("PurDAO SALELIST getPageUnit():" + searchVO.getPageUnit());

		ArrayList<ProductVO> list = new ArrayList<ProductVO>();

		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {

				ProductVO productVO = new ProductVO();
				productVO.setProdNo(rs.getInt("PROD_NO"));
				productVO.setProdName(rs.getString("PROD_NAME"));
				productVO.setProdDetail(rs.getString("PROD_DETAIL"));
				productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
				productVO.setPrice(rs.getInt("PRICE"));
				productVO.setFileName(rs.getString("IMAGE_FILE"));
				productVO.setRegDate(rs.getDate("REG_DATE"));
				if (this.findPurchase2(productVO.getProdNo()) != null) {
					productVO.setProTranCode(this.findPurchase2(productVO.getProdNo()).getTranCode());
				}
				list.add(productVO);

				if (!rs.next()) {
					break;
				}

			}
		}

		map.put("list", list);
		System.out.println("퍼체이스DAO saleLIST map.size()::" + map.size());
		System.out.println("퍼체이스DAO saleLIST map.get(list)::" + map.get("list"));

		stmt.close();
		con.close();

		return map;
	}

	public void updatePurchase(PurchaseVO purchaseVO) throws Exception {

		System.out.println("updatePurchaseDAO 시작");
		Connection con = DBUtil.getConnection();

		String sql = "UPDATE transaction SET payment_option=?, receiver_name=?, receiver_phone=?, demailaddr=?, dlvy_request=?, dlvy_date=? WHERE tran_no=?";
		System.out.println("updatePurchase SQL::" + sql);

		PreparedStatement stmt = con.prepareStatement(sql);
		System.out.println("updatePurchase stmt::" + stmt);

		stmt.setString(1, purchaseVO.getPaymentOption());
		stmt.setString(2, purchaseVO.getReceiverName());
		stmt.setString(3, purchaseVO.getReceiverPhone());
		stmt.setString(4, purchaseVO.getDivyAddr());
		stmt.setString(5, purchaseVO.getDivyRequest());
		stmt.setString(6, purchaseVO.getDivyDate());
		stmt.setInt(7, purchaseVO.getTranNo());
		stmt.executeUpdate();

		stmt.close();
		con.close();
		System.out.println("업데이트퍼체이스DAO");

	}
	
	public void updateTranCode (PurchaseVO purchaseVO)throws Exception{
		
		System.out.println("PurchaseDAO updateTranCode() starts and purchaseVO::"+purchaseVO);
		
		Connection con = DBUtil.getConnection();
		
		String sql = " UPDATE transaction SET tran_status_code=? WHERE tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchaseVO.getTranCode());
		stmt.setInt(2, purchaseVO.getTranNo());
		
		stmt.executeUpdate();
		
		stmt.close();
		con.close();
		
		System.out.println("PurchaseDAO updateTranCode() end");
		
	}
	
	
}
