<%@page import="org.lovi.psdemo.models.Branch"%>
<%@page import="org.lovi.psdemo.config.CommonConfig"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.lovi.psdemo.models.ItemCategory"%>
<%@page import="org.lovi.psdemo.models.Item"%>
<%@ include file="../includes/header.jsp"%>
<nav></nav>

<script type="text/javascript">

	function findItemsPlate(ele){
		
		var shopId = ele.value;
		
		var url = '<%=CommonConfig.URL_ITEMS_PLATE.substring(1)%>'+'/shop/' + shopId;
		console.log(url);
		
		$.ajax({
			'url' : url,
			'type' : 'GET',
			success: function(data) {
				console.log(data);
				//document.getElementById("items_plate_container").innerHTML="";
			},
		});
		console.log("OK");
	}

</script>
<div class="container">
	<div class="row">

		<%@ include file="../includes/left_menu.jsp" %>

		<!-- 1 -->
		<div class="col-lg-9 col-md-9">

			<!-- 1-1 -->
			<div class="container-fluid">

				<div class="row">
					<div class="col-lg-12 col-md-12">

						<!-- Content -->
						<div class="panel panel-material-light-blue-500">
							<div class="panel-heading">
								<h3 class="panel-title">Items Plate log</h3>

							</div>
							<div class="panel-body">
								
								<div class="row">
									<div class="col-lg-4 col-md-3">
										<div class="form-group">
											<label class="col-lg-2 control-label" for="inputEmail">Shop </label>
											<div class="col-lg-10">
												<select onchange="findItemsPlate(this)" class="form-control">
													<option selected="selected" disabled="disabled" value="">Select a Shop</option>
													<%
														List<Branch> branches = (List<Branch>)request.getAttribute("shops"); 
														for(Branch branch : branches){
													%>
													<option value="<%=branch.getBranchId()%>"><%=branch.getBranchName()%></option>
													<% }%>
													
													
												</select>
											</div>
										</div>
										
									</div>
									<div class="col-lg-4 col-md-3">
										<div class="form-group">
											<label class="col-lg-2 control-label" for="inputEmail">Plate </label>
											<div class="col-lg-10" id="items_plate_container">
												<select class="form-control">
													<option selected="selected" disabled="disabled" value="">Select a Items Plate</option>
													
												</select>
											</div>
										</div>
									</div>
									<div class="col-lg-4 col-md-6">
										<a class="btn btn-info btn-raised" style="float: right" href="#"
											data-toggle="modal" data-target="#itemAddModal"> 
											<span class="glyphicon glyphicon-plus"></span>&nbsp;Plate Log  
										</a>
									</div>
								</div>
								
								
								
								<table class="table table-striped table-hover ">
									<thead>
										<tr>
											
										</tr>
									</thead>
									<tbody>
								
									
								
									</tbody>
								</table>
								
							</div>
						</div>


					</div>
				</div>
				<!-- row end -->

			</div>
			<!-- 1-1 End-->
		</div>
		<!-- 1 End-->


	</div>
	<!-- row end -->
</div>
<!-- Container End -->

<!-- item add form modal -->
<div class="modal fade modal-primary" id="itemAddModal" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content" style="overflow: auto;">
			<div class="modal-header shadow btn-material-blue-500"
				style="border-color: #09f;">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">New Item</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" method="post" action="<%=CommonConfig.URL_ITEM.substring(1)%>">
					<fieldset>
						<br>
						<div id="error_div"></div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label" for="inputEmail">Name*</label>
							<div class="col-lg-10">
								<input required="required" type="text" placeholder="Input item name" id="name"
									name="name" class="form-control" value="${item.name}">
							</div>
						</div>
						 
						<div class="form-group">
							<label class="col-lg-2 control-label" for="inputEmail">Price*</label>
							<div class="col-lg-10">
								<input required="required" type="number" min="1" placeholder="Input item price" id="price"
									name="price" class="form-control"  value="${item.price}">
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label" for="inputEmail">Description</label>
							<div class="col-lg-10">
								<textarea required="required" placeholder="Input description for the item" id="comment"
									name="comment" class="form-control">
									${item.comment}
								</textarea>
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-lg-10 col-lg-offset-2">
								<button data-dismiss="modal" class="btn btn-danger" type="reset">Cancel</button>
								<button class="btn btn-material-light-blue-500" type="submit">Submit</button>
							</div>
						</div>
						
					</fieldset>
				</form>
			</div>
		</div>
	</div>
</div>

<%
	try {
		JSONObject message = (JSONObject) request
				.getAttribute(CommonConfig.MESSAGE);
		int status = message.getInt(CommonConfig.STATUS);
		String value = message.getString(CommonConfig.VALUE);
		String type = message.getString(CommonConfig.VIEW_MESSAGE_TYPE);
		
		if (status == 1) {
			out.print("<script> notify('Success !','"
					+ value
					+ "','success','nav'); </script>");
		} else {
			if (type.equals(CommonConfig.VIEW_TYPE_ITEM_ADD)) {
				
				out.print("<script> notify('Error !','"
						+ value
						+ "','danger','#error_div'); </script>");
				
				out.print("<script>javascript:$('#itemAddModal').modal('show');</script>");
				
			}

		}

	} catch (Exception e) {

	}
%>

<%@ include file="../includes/footer.jsp"%>