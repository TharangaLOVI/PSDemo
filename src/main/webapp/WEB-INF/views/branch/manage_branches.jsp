<%@page import="org.lovi.psdemo.models.Branch"%>
<%@page import="org.lovi.psdemo.config.CommonConfig"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.lovi.psdemo.models.ItemCategory"%>
<%@page import="org.lovi.psdemo.models.Item"%>
<%@ include file="../includes/header.jsp"%>

<script type="text/javascript">
	
	/*
	* Load models for perticular options
	*/
	function loadOperation(selectElement){
		//reset operation selected option
		var option = selectElement.selectedIndex;
		selectElement.selectedIndex = 0;
		//clearModels();

		var branchId = selectElement.id.substr(7);

		document.getElementById("branch").value = branchId;
		
		if(option == 1){
			$('#plateAddModal').modal('show');
		}else if(option == 2){
			alert("Add Manager");
		}else if(option == 3){
			alert("Show Item Plate");
		}else{
			alert("Show Manager");
		}
		
	}

	/*
	* Clear the model filled data
	*/
	function clearModels(){
		//shop add
		document.getElementById("shopName").value = "";
		document.getElementById("phoneNumber").value = "";
		document.getElementById("address").innerHTML = "";

		//add plate
		document.getElementById("shop").value = "";
		document.getElementById("shopName").value = "";
		//document.getElementById("items_container").innerHTML = "";
	} 

	
</script>

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
								<h3 class="panel-title">Branch</h3>

							</div>
							<div class="panel-body">
								
								<a class="btn btn-info btn-raised" style="float: right" href="#"
									data-toggle="modal" data-target="#branchAddModal"> 
									<span class="glyphicon glyphicon-plus"></span>&nbsp;Branch  
								</a>
								
								<table class="table table-striped table-hover ">
									<thead>
										<tr>
											<th width="200px">Name</th>
											<th width="200px">Address</th>
											<th width="200px">Contact No</th>
											<th>&nbsp;</th>
										</tr>
									</thead>
									<tbody>
								
									<%
									List<Branch> branches = (List<Branch>) request.getAttribute("branches");
																								
									for(Branch branch : branches){
									%>
										<tr>
											<td><%=branch.getBranchName()%></td>
											<td><%=branch.getAddress()%></td>
											<td><%=branch.getPhoneNumber()%></td>
											<td>
												<select class="form-control" id="sel_op_<%=branch.getBranchId()%>" onchange="loadOperation(this)">
													<option disabled="disabled" value="0" selected="selected">Actions</option>
													<option value="1">Add Item Plate</option>
													<option value="2">Add Manager</option>
													<option value="3">Show Item Plates</option>
													<option value="4">Show Managers</option>
												</select>
											</td>
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

<%@ include file="add_branch.jsp"%>
<%@ include file="add_items_plate.jsp"%>

<%@ include file="../includes/footer.jsp"%>