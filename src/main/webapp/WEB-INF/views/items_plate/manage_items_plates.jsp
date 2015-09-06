<%@page import="org.lovi.psdemo.config.CommonConfig"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.lovi.psdemo.models.ItemCategory"%>
<%@page import="org.lovi.psdemo.models.Item"%>
<%@page import="org.lovi.psdemo.models.Branch"%>
<%@ include file="../includes/header.jsp"%>
<nav></nav>

<script type="text/javascript">

	var itemRowCount = 0;
	var items = [];

	//Load branches start
	function setShopId(ele){
		document.getElementById("a_fire_add_plates").removeAttribute("disabled");
		document.getElementById("branch").value = ele.value;
		getItemsPlate(ele.value);
	}

	function getItemsPlate(shopId){
		
		var url = '<%=CommonConfig.URL_ITEMS_PLATE.substring(1)%>'+'/shop/' + shopId;
		console.log(url);
		
		$.ajax({
			'url' : url,
			'type' : 'GET',
			success: function(data) {
				console.log(data);
				var response = JSON.parse(data);
				
				if(response.status == 1){
					clearItemsPlatesContainer();
					var itemsPlates = response.value;
					var i;
					if(itemsPlates.length != 0){
						
						var table = document.getElementById("tbl_items_plates_container");
						
						for(i = 0 ; i < itemsPlates.length ; i++){

							//start from second row
							var row = table.insertRow(i + 1);
							var cell1 = row.insertCell(0);
							var cell2 = row.insertCell(1);
							var cell3 = row.insertCell(2);

							cell1.innerHTML = itemsPlates[i].plateId;
							cell2.innerHTML = itemsPlates[i].plateName;

							createItemsOfPlate(cell3,itemsPlates[i].items);
						}

						console.log("Total Row Count : " + table.rows.length);
					}else{
						notify('Warning !','No any items plates for this branch','info','nav');
					}
					
					
				}else{
					notify('Error !','Unable to load items plates [Server ERROR]','danger','nav');
				}
			
				
			},
		});
		
	}

	//create items for the plate
	function createItemsOfPlate(cell,items){
		var table=document.createElement("table");
		table.setAttribute("class","table table-hover ");

		//set header for table
		var row = table.insertRow(0);
		row.setAttribute("class","active");
		var th1 = document.createElement('th');
		th1.innerHTML = "Item Id"; 

		var th2 = document.createElement('th');
		th2.innerHTML = "Item Name"; 

		var th3 = document.createElement('th');
		th3.innerHTML = "Item Qty"; 

		row.appendChild(th1);
		row.appendChild(th2);
		row.appendChild(th3);

		var i;
		for(i = 0 ; i < items.length ; i++){

			var row = table.insertRow(i + 1);
			var cell1 = row.insertCell(0);
			var cell2 = row.insertCell(1);
			var cell3 = row.insertCell(2);

			cell1.innerHTML = items[i].itemId;
			cell2.innerHTML = items[i].name;
			cell3.innerHTML = items[i].plateItemQty;
		}

		//add created table to the parent cell
		cell.appendChild(table);
	}
	
	function clearItemsPlatesContainer(){

		var table = document.getElementById("tbl_items_plates_container");
		console.log("Goint to remove : " + table.rows.length);
		var i;
		//start from 2nd row
		for(i = 1 ; i < table.rows.length ; i++){
			table.deleteRow(i);
		}
	}
	//Load branches end

	//Add items plate start
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
		
	}

	function removeItem(row){
		var table = document.getElementById("items_container");
		table.deleteRow(row);
	}
	//Add items plate end
	
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
								<h3 class="panel-title">Item Plate</h3>

							</div>
							<div class="panel-body">
							
								
								<div class="row">
									<div class="col-lg-6 col-md-6">
										<div class="form-group">
											<label class="col-lg-2 control-label" for="inputEmail">Branch </label>
											<div class="col-lg-10">
												<select onchange="setShopId(this)" class="form-control">
													<option selected="selected" disabled="disabled" value="">Select a Branch</option>
													<%
														List<Branch> branches = (List<Branch>)request.getAttribute("branches"); 
														for(Branch branch : branches){
													%>
													<option value="<%=branch.getBranchId()%>"><%=branch.getBranchName()%></option>
													<% }%>
													
													
												</select>
											</div>
										</div>
										
									</div>
									
									<div class="col-lg-6 col-md-6">
										<a id="a_fire_add_plates" disabled class="btn btn-info btn-raised" style="float: right" href="#"
											data-toggle="modal" data-target="#plateAddModal"> 
											<span class="glyphicon glyphicon-plus"></span>&nbsp;Items Plate  
										</a>
									</div>
								</div>
									
								<table id="tbl_items_plates_container" class="table table-striped table-hover ">
									<thead>
										<tr>
											<th>Items Plate ID</th>
											<th>Items Plate Name</th>
											<th>Items</th>
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
						
						<input type="hidden" id="branch" name="branch" />
						
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
													a Item
												</option>
		
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
										<td >
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

<%@ include file="../includes/footer.jsp"%>