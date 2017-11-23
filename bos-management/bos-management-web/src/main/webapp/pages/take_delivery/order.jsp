<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="UTF-8">
		<title>业务受理</title>
		<!-- 导入jquery核心类库 -->
		<script type="text/javascript" src="../../js/jquery-1.8.3.js"></script>
		<!-- 导入easyui类库 -->
		<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="../../js/easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="../../js/easyui/ext/portal.css">
		<link rel="stylesheet" type="text/css" href="../../css/default.css">
		<script type="text/javascript" src="../../js/easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="../../js/easyui/ext/jquery.portal.js"></script>
		<script type="text/javascript" src="../../js/easyui/ext/jquery.cookie.js"></script>
		<script src="../../js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
		<!-- 省市区三级联动 -->
		<link rel="stylesheet" type="text/css" href="../../js/citypicker/css/city-picker.css">
		<script type="text/javascript" src="../../js/citypicker/js/city-picker.data.js"></script>
		<script type="text/javascript" src="../../js/citypicker/js/city-picker.js"></script>
		<script type="text/javascript" src="../../js/citypicker/js/city-picker.min.js"></script>
		
		
		<script type="text/javascript">
			$(function(){
				$("body").css({visibility:"visible"});
				// 对save按钮条件 点击事件
				$('#save').click(function(){
					// 对form 进行校验
					if($('#orderForm').form('validate')){
						$('#orderForm').submit();
					}
				});
			});
		</script>
	</head>
	
	<body class="easyui-layout" style="visibility:hidden;">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">新单</a>
				<a id="edit" icon="icon-edit" href="../../pages/take_delivery/work_order.html" class="easyui-linkbutton" plain="true">工单操作</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="orderForm" action="../../orderAction_save.action" method="post">
				<table class="table-edit" width="95%" align="center">
					<tr class="title">
						<td colspan="4">客户信息</td>
					</tr>
					<tr>
						<td style="width: 300px;">来电号码:</td>
						<td>
							<input type="text" class="easyui-validatebox" name="telephone" required="true" />
						</td>
						
					</tr>
					<tr>
						<td>客户姓名:</td>
						<td>
							<input type="text" id="ustomer_id" name="ustomer_id"
							class="easyui-combobox"
							data-options="required:true,valueField:'id',textField:'username',
											url:'../../orderAction_findCustomer.action'" />
						</td>
						<!-- <td>客户姓名:</td>
						<td colspan="3">
							<input type="text" class="easyui-validatebox" name="" required="true" />
						</td> -->
					</tr>
					<tr class="title">
						<td colspan="4">寄件人信息</td>
					</tr>
					<tr>
						<td>姓名:</td>
						<td>
							<input type="text" class="easyui-validatebox" name="sendName" required="true" />
						</td>
						<td>联系方式:</td>
						<td>
							<input type="text" class="easyui-validatebox" name="sendMobile" required="true" />
						</td>
					</tr>
					<tr>
						<td>寄件公司:</td>
						<td>
							<input type="text" class="easyui-validatebox" name="sendCompany" required="true" />
						</td>
						<td>省市区:</td>
						<td>
							<div style="position: relative; width: 250px">
								<input style="width: 250px" readonly type="text" name="sendAreaInfo" required="true"
									data-toggle="city-picker">
							</div>
						</td>
						
					</tr>
					<tr>
						<td>详细地址:</td>
						<td colspan="3">
							<input type="text" class="easyui-textbox" name="sendAddress" required="true" size="60"/>
						</td>
					</tr>
					<tr class="title">
						<td colspan="4">收件人信息</td>
					</tr>
					<tr>
						<td>姓名:</td>
						<td>
							<input type="text" class="easyui-validatebox" name="recName" required="true" />
						</td>
						<td>联系方式:</td>
						<td>
							<input type="text" class="easyui-validatebox" name="recMobile" required="true" />
						</td>
					</tr>
					<tr>
						<td>收件公司:</td>
						<td>
							<input type="text" class="easyui-validatebox" name="recCompany" required="true" />
						</td>
						<td>省市区:</td>
						<td>
							<div style="position: relative; width: 250px">
								<input style="width: 250px" readonly type="text" name="recAreaInfo" required="true"
									data-toggle="city-picker">
							</div>
						</td>
					</tr>
					<tr>
						<td>详细地址:</td>
						<td colspan="3">
							<input type="text" class="easyui-textbox" name="recAddress" required="true" size="60"/>
						</td>
					</tr>
					<tr class="title">
						<td colspan="4">货物信息</td>
					</tr>
					<tr>
						<td>托寄物:</td>
						<td>
							<select class="easyui-combobox" name="goodsType" required="true" >
								<option value="文件">文件</option>
								<option value="衣服">衣服</option>
								<option value="食品">食品</option>
								<option value="电子产品">电子产品</option>
							</select>
						</td>
						<td>重量:</td>
						<td>
							<input type="text" class="easyui-numberbox" name="weight" required="true" />
						</td>
					</tr>
					<tr>
						<td>快递产品:</td>
						<td>
							<select class="easyui-combobox" name="sendProNum" required="true" >
								<option value="速运当日">速运当日</option>
								<option value="速运次日">速运次日</option>
								<option value="速运隔日">速运隔日</option>
							</select>
						</td>
						<td>付款方式：</td>
						<td>
							<select class="easyui-combobox" name="payTypeNum" required="true" >
								<option value="寄付日结">寄付日结</option>
								<option value="寄付月结">寄付月结</option>
								<option value="到付">到付</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>备注:</td>
						<td colspan="3">
							<input type="text" class="easyui-textbox" name="remark" required="true" size="60"/>
						</td>
						<td>给快递员捎话:</td>
						<td colspan="3">
							<input type="text" class="easyui-textbox" name="sendMobileMsg" required="true" size="60"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>