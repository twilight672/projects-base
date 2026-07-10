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
							<li>商品名：<input type="text" name="shangpinming"  /></li>
							<li>库存量：<input type="text" name="kucunliang"  /></li>
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
	<input id="tableName" name="tableName" type="hidden" value="kucun" />
	<script>
	
		var prefix = "kucunservlet";
		$(function() {
			var options = {
				url : prefix + "?method=data",
				createUrl : prefix + "?method=insert",
				search : false,
				showExport : true,
				modalName : "库存管理",
				columns : [ {
					checkbox : true
				},
				{	field : 'id',
					title : 'id',
					visible : false
				},
				{	field : 'shangpinming',
					title : '商品名',
					sortable : true,
				},

				{	field : 'kucunliang',
					title : '库存量',
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
