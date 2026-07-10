<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>

<footer class="f-mobile-dark">
	<div class="tt-footer-default tt-color-scheme-02">
		<div class="container">
		</div>
	</div>
	<div class="tt-footer-col tt-color-scheme-01">
		<div class="container">
		</div>
	</div>
</footer>
<a href="#" class="tt-back-to-top">BACK TO TOP</a>


<div class="modal  fade"  id="modalAddToCartProduct" tabindex="-1" role="dialog" aria-label="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content ">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true"><span class="icon icon-clear"></span></button>
			</div>
			<div class="modal-body">
				
			</div>
		</div>
	</div>
</div>
<script src="external/jquery/jquery.min.js"></script>
<script src="external/bootstrap/js/bootstrap.min.js"></script>
<script src="external/elevatezoom/jquery.elevatezoom.js"></script>
<script src="external/slick/slick.min.js"></script>
<script src="external/perfect-scrollbar/perfect-scrollbar.min.js"></script>
<script src="external/panelmenu/panelmenu.js"></script>
<script src="external/instafeed/instafeed.min.js"></script>
<script src="external/countdown/jquery.plugin.min.js"></script>
<script src="external/countdown/jquery.countdown.min.js"></script>
<script src="external/magnific-popup/jquery.magnific-popup.min.js"></script>
<script src="external/rs-plugin/js/jquery.themepunch.tools.min.js"></script>
<script src="external/rs-plugin/js/jquery.themepunch.revolution.min.js"></script>
<script src="external/lazyLoad/lazyload.min.js"></script>
<script src="js/main.js"></script>
<!-- form validation and sending to mail -->
<script src="external/form/jquery.form.js"></script>
<script src="external/form/jquery.validate.min.js"></script>
<script src="external/form/jquery.form-init.js"></script>

<script type="text/javascript">
	function addcart(shangpinid){
		$.ajax({
            //请求方式
            type : "POST",
            contentType: "application/json;charset=UTF-8",
            url : "shopservlet?method=addcart&shangpinid="+shangpinid,
            //请求成功
            success : function(result) {
            	$("#modalAddToCartProduct").find('.modal-body').html(result);
            	$("#modalAddToCartProduct").modal({  
            	}); 
            }
        });
	}
	 
</script>







