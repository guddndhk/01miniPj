<%@ page contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR" %>

<%@ page import="java.util.*" %>
<%@ page import="com.model2.mvc.service.purchase.vo.*" %>
<%@ page import="com.model2.mvc.common.*" %>

  <%
	HashMap<String,Object> map=(HashMap<String,Object>)request.getAttribute("map");
	SearchVO searchVO = (SearchVO)request.getAttribute("searchVO");
	
	int total=0;
	ArrayList<PurchaseVO> list=null;

	if(map != null){
		total=((Integer)map.get("count")).intValue();
		list=(ArrayList<PurchaseVO>)map.get("list");
		System.out.println("total : "+total);
		System.out.println("list :"+list);
		
	}
	
	
	int currentPage = searchVO.getPage();
	
	int totalPage=0;
	if(total > 0){
		totalPage= total / searchVO.getPageUnit();
		if(total%searchVO.getPageUnit() > 0){
			totalPage += 1;
		}
	}
%>


<html>
<head>
<title>구매 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	function fncGetUserList() {
		document.detailForm.submit();
	}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/listUser.do" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">구매목록조회</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">전체 <%= total %> 건수, 현재 <%=currentPage %> 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">전화번호</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">배송현황</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">정보수정</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<% 	
		int no=list.size();
			for(int i=0; i<list.size(); i++) {
				PurchaseVO purchase = (PurchaseVO)list.get(i);
				System.out.println("listPurchase.jsp에서 purchase 가져오기"+purchase);
			
	%>
				
	<tr class="ct_list_pop">
		<td align="center"><a href="/getPurchase.do?tranNo=<%=purchase.getTranNo()%>"><%=no-- %></a>
		</td>
		<td></td>		
				<td align="left"><a href="/getUser.do?userId=<%=purchase.getBuyer().getUserId()%>"><%=purchase.getBuyer().getUserId() %></a>
		</td>		
		<td></td>
		
		<td align="left"><%=purchase.getReceiverName() %></td>
		<td></td>
		<td align="left"><%=purchase.getReceiverPhone()%></td>
		<td></td>
		<td align="left">
			<% if(purchase.getTranCode().trim().equals("1")){ %>
					현재 구매완료 상태입니다.
			<% }else if(purchase.getTranCode().trim().equals("2")){ %>
					현재 배송중 상태입니다.
			<% }else if(purchase.getTranCode().trim().equals("3")){ %>
					현재 배송완료 상태입니다.
			<% } %>
		
		</td>
		<td></td>	
		<td align="left">
				<% if(purchase.getTranCode().trim().equals("2")){ %>
					<a href="/updateTranCode.do?tranNo=<%=purchase.getTranNo()%>&tranCode=3&page=<%=currentPage %>">물건도착</a>
				<% } %> 
			</td>
	</tr>


	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	<% } %>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
	<tr>
		<td align="center">
		 <% for(int i=1;i<=totalPage;i++){ %>
		
			 <a href="/listPurchase.do?page=<%=i%>&"><%=i %></a>
		
		<% }%>	
		
		</td>
	</tr>
</table>

<%--  페이지 Navigator 끝 --%>
</form>

</div>

</body>
</html>