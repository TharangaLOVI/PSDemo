<%@page import="java.util.List"%>
<%@page import="org.lovi.psdemo.models.Item"%>
<%@page import="org.lovi.psdemo.models.Branch"%>
<%@page import="org.lovi.psdemo.config.CommonConfig"%>
<%@page import="org.json.JSONObject"%>

<script type="text/javascript">

	var itemRowCount = 0;
	var items = [];

	function addItemInsertRow(){
		console.log("itemRowCount : " + itemRowCount);
		var table = document.getElementById("items_container");

		/*
		* Table row 0 - used for header
		* Table row 1 - Is defalt row
	    * So start in 2 nd row
		*/
		var row = table.insertRow(itemRowCount + 2);
		itemRowCount++;
		
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		var cell3 = row.insertCell(2);
		cell3.setAttribute("align","right");
	
		cell1.innerHTML = 	'<select onchange="checkItemExsisting(this)" required="required" id=itemCategory name="items['+itemRowCount+'].itemId" class="form-control">'
							+
							'<option disabled="disabled" selected="selected" value="">Select a Item</option>'
							+
							'<%for (Item item : (List<Item>) request.getAttribute("items")) {%>'
							+ 
							'<option value="<%=item.getItemId()%>"><%=item.getName()%></option>'
							+
							'<%}%>'
							+
							'</select>';
							
		cell2.innerHTML = '<input required="required" type="number" min="1" name="items[' + itemRowCount + '].plateItemQty"/>';
		cell3.innerHTML = '<a href="javascript:removeItem('+(itemRowCount + 2)+')" class="btn-sm btn-danger"><span class="glyphicon glyphicon-remove"></span></a>';
	} 

	function checkItemExsisting(item){
		console.log("Item : " + item.value);
		
		/*var i;
		for(i=0 ; i < items.length ; i++){
			console.log(i);
			if(item[i] == item.value){
				console.log("Found : " + item[i]);
				return;
			}
		}
		console.log("Test");
		items.push(item.value);*/
		
	}

	function removeItem(row){
		var table = document.getElementById("items_container");
		table.deleteRow(row);
	}
	
</script>

<div class="modal fade modal-primary" id="plateAddModal" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content" style="overflow: auto;">
			<div class="modal-header shadow btn-material-blue-500"
				style="border-color: #09f;">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">New Items Plate</h4>
			</div>
			<div class="modal-body">
				<form id="form_add_plate" class="form-horizontal" method="post" action="<%=CommonConfig.URL_ITEMS_PLATE.substring(1)%>">
					<fieldset>
						<br>
						<div id="error_div"></div>
						
						<input type="hidden" id="branch" name="branch" value="1"/>
						
						<div class="form-group">
							<label class="col-lg-2 control-label" for="inputEmail">Name*</label>
							<div class="col-lg-10">
								<input required="required" type="text" placeholder="Input shop name" id="plateName"
									name="plateName" class="form-control" value="${item_plate.plateName}">
							</div>
						</div>
						
						<div  class="form-group">
							<label class="col-lg-2 control-label" for="inputEmail">Items*</label>
							<div class="col-lg-10">
								
								<table id="items_container" class="table table-striped table-hover ">
									<thead>
										<tr>
											<td>Item </td>
											<td>Qty </td>
											<td><a onclick="addItemInsertRow()"  href="#" style="float: right;" class="btn-sm btn-info"><span class="glyphicon glyphicon-plus" ></span></a></td>
										</tr>
									</thead>
									<tr>
										<td>
											<select onchange="checkItemExsisting(this)" required="required" id=itemCategory name='items[0].itemId'
												class="form-control">
												<option disabled="disabled" selected="selected" value="">Select
													a Item</option>
		
												<%
												for (Item item : (List<Item>) request.getAttribute("items")) {
												%>
												<option value="<%=item.getItemId()%>"><%=item.getName()%></option>
												<%
												}
												%>
											</select>
										</td>
										<td>
											<input required="required" type="number" min="1" name="items[0].plateItemQty"/>
											
										</td>
										<td align="right">
											<a class="btn-sm btn-danger" href="javascript:removeItem(1)"><span class="glyphicon glyphicon-remove"></span></a>
										</td>
									</tr>
									
								</table>
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
	if (type.equals(CommonConfig.VIEW_TYPE_BRANCH_ADD)) {
		
		out.print("<script> notify('Error !','"
				+ value
				+ "','danger','#error_div'); </script>");
		
		out.print("<script>javascript:$('#plateAddModal').modal('show');</script>");
		
	}

		}

	} catch (Exception e) {

	}
%>


