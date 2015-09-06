<%@page import="org.lovi.psdemo.config.CommonConfig"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.lovi.psdemo.models.ItemCategory"%>
<%@page import="org.lovi.psdemo.models.Item"%>
<%@ include file="../includes/header.jsp"%>
<nav></nav>
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
								<h3 class="panel-title">Item</h3>

							</div>
							<div class="panel-body">
								
								<a class="btn btn-info btn-raised" style="float: right" href="#"
									data-toggle="modal" data-target="#itemAddModal"> 
									<span class="glyphicon glyphicon-plus"></span>&nbsp;Item  
								</a>
								
								<table class="table table-striped table-hover ">
									<thead>
										<tr>
											<th>Name</th>
											<th>Description</th>
											<th>Price (Rs)</th>
											<th>Category</th>
										</tr>
									</thead>
									<tbody>
								
									<% 
										List<Item> items = (List<Item>) request.getAttribute("items");
										
										for(Item item : items){
									%>
										<tr>
											<td><%=item.getName()%></td>
											<td><%=item.getComment()%></td>
											<td><%=item.getPrice()%></td>
											<td><%=item.getItemCategory().getCategoryName()%></td>
										</tr>
									
									<%	} %>
								
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
							<label class="col-lg-2 control-label" for="inputEmail">Category*</label>
							<div class="col-lg-10">
								
								<select required="required" id=itemCategory name="itemCategory" class="form-control">
								<option disabled="disabled" selected="selected" value="0">Select a category</option>	
								
								<%	List<ItemCategory> categories = (List<ItemCategory>)request.getAttribute("categories");
									for(ItemCategory category : categories){
								%>
									<option value="<%=category.getCategoryId() %>" ><%=category.getCategoryName()%></option>
								<% }%>
								</select>
								
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