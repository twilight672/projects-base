<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@include file="/pages/common/base.jsp"%>
<base href="<%=basePath%>">
<html lang="zh">
<body class="gray-bg">

	<div class="container-div">
		<div class="row">
			<div class="col-sm-12 search-collapse">
				<form id="formId">
					<div class="select-list">
						<ul>
							<li>订单编号：<input type="text" name="dingdanbianhao"  /></li>
							<li>价格：<input type="text" name="jiage"  /></li>
							<li>商品：<input type="text" name="shangpin"  /></li>
							<li>数量：<input type="text" name="shuliang"  /></li>
							<li>总价：<input type="text" name="zongjia"  /></li>
							<li>商品id：<input type="text" name="shangpinid"  /></li>
							<li><a class="btn btn-primary btn-rounded btn-sm"
								onclick="$.table.search()"><i class="fa fa-search"></i>&nbsp;搜索</a>
								<a class="btn btn-warning btn-rounded btn-sm"
								onclick="$.form.reset()"><i class="fa fa-refresh"></i>&nbsp;重置</a>
							</li>
						</ul>
					</div>
				</form>
			</div>

			<div class="btn-group-sm hidden-xs" id="toolbar" role="group">
				<a class="btn btn-success" onclick="$.operate.add()"> <i class="fa fa-plus"></i>添加</a> 
			</div>
			<div class="col-sm-12 select-table table-striped">
				<table id="bootstrap-table" data-mobile-responsive="true"></table>
			</div>
		</div>
	</div>
	<input id="tableName" name="tableName" type="hidden" value="dingdanmingxi" />
	<script>
	
		var prefix = "dingdanmingxiservlet";
		$(function() {
			var options = {
				url : prefix + "?method=data",
				createUrl : prefix + "?method=insert",
				search : false,
				showExport : true,
				modalName : "订单明细管理",
				columns : [ {
					checkbox : true
				},
				{	field : 'id',
					title : 'id',
					visible : false
				},
				{	field : 'dingdanbianhao',
					title : '订单编号',
					sortable : true,
				},

				{	field : 'jiage',
					title : '价格',
					sortable : true,
				},

				{	field : 'shangpin',
					title : '商品',
					sortable : true,
				},

				{	field : 'shuliang',
					title : '数量',
					sortable : true,
				},

				{	field : 'zongjia',
					title : '总价',
					sortable : true,
				},

				{	field : 'shangpinid',
					title : '商品id',
					sortable : true,
				},

				 {
		            title: '操作',
		            align: 'center',
		            formatter: function(value, row, index) {
		                var actions = [];
		                actions.push('<a class="btn btn-success btn-xs " href="javascript:void(0)" onclick="edit(\'' + row.id + '\')"><i class="fa fa-edit"></i>编辑</a> ');
		                actions.push('<a class="btn btn-danger btn-xs " href="javascript:void(0)" onclick="del(\'' + row.id + '\')"><i class="fa fa-remove"></i>删除</a> ');
		                return actions.join('');
		            }
		        }
				]
			};
			$.table.init(options);
		});
		function del(id) {
			$.modal.confirm("确认要删除吗？", function() {
				$.operate.post(prefix , { "method": "delete", "id": id });
		    })
		}
		function edit(id) {
	        $.modal.open("编辑", prefix + "?method=update&id="+id);

		}
	</script>
</body>
</html>
