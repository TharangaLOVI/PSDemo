<%@page import="java.util.List"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.lovi.psdemo.config.CommonConfig"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.lovi.psdemo.models.ItemCategory"%>
<%@page import="org.lovi.psdemo.models.Item"%>
<%@ include file="../includes/header.jsp"%>

<script src="app/dash_board/drag_and_drop/interact/interact.js"
	type="text/javascript"></script>
<script src="app/dash_board/drag_and_drop/js/dropzones.js"
	type="text/javascript"></script>
<link href="app/dash_board/drag_and_drop/css/dropzones.css"
	rel="stylesheet" type="text/css" />

<!--
<script>
	
	function allowDrop(ev) {
	    ev.preventDefault();
	}
	
	function drag(ev) {
	    ev.dataTransfer.setData("text", ev.target.id);
	}
	
	function dropDriver(ev) {
	    ev.preventDefault();
	    var data = ev.dataTransfer.getData("text");
	    
	    ev.target.innerHTML = "";
	    ev.target.appendChild(document.getElementById(data));
	    
	}

	function dropBranch(ev) {
	    ev.preventDefault();
	    var data = ev.dataTransfer.getData("text");

	   	/*var table = document.getElementById("drop_container_drivers");
	    var row = table.insertRow(table.rows.length);
	    var cell = row.insertCell(0);
	    cell.appendChild(document.getElementById(data));*/
	    ev.target.innerHTML = "";
	    ev.target.appendChild(document.getElementById(data));
	    
	}
</script>
  -->

<%
	List<User> drivers = (List<User>) request.getAttribute("drivers");
	
	JSONArray branches = (JSONArray) request.getAttribute("branches");
	for (int d = 0; d < branches.length(); d++) {
		JSONObject branch = branches.getJSONObject(d);
	}
%>

<script>
	
</script>

<div class="container">

	<div class="row">
		<!-- 1 -->
		<div class="col-lg-9 col-md-9">

			<!-- 1-1 -->
			<div class="container-fluid">

				<div class="row">
					<div class="col-lg-12 col-md-12">

						<!-- Content -->
						<div class="panel panel-material-light-blue-500">
							<div class="panel-heading">
								<h3 class="panel-title">Dash Board</h3>

							</div>
							<div class="panel-body">
								<div id="error_div"></div>
								<a href="javascript:submit()" style="float: right;"
									class="btn btn-success  btn-fab btn-raised mdi-action-done"></a>
								<table class="table table-striped table-hover ">
									<thead>
										<tr>
											<th>Drivers</th>
											<th>Branches</th>
											<th>Items Plates</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td>
												<div ondrop="dropDriver(event)" ondragover="allowDrop(event)" align="center" style="height:60px; border: 2px dashed #CCC;overflow-y: auto;" >
													<p class="text-muted">Drop Drivers</p>
												</div>
												<a style="float: right;display: none" href="#"  ><span class="mdi-content-remove-circle"></span></a>
											</td>
											<td>
												<div align="center" style="height:60px; border: 2px dashed #CCC;overflow-y: auto;">
													<p class="text-muted">Drop Branches</p>
												</div>
											</td>
											<td>
												<div align="center" style="height:60px; border: 2px dashed #CCC;overflow-y: auto;">
													<p class="text-muted">Items Plate</p>
												</div>
											</td>
										</tr>
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

		<!-- 2 -->
		<div class="col-lg-3 col-md-3">
			<div class="panel panel-material-light-blue-500">
				<div class="panel-heading">
					<h3 class="panel-title">Welcome</h3>
				</div>
				<div class="panel-body">
					<div class="well panel">
						<div class="row">
							<!-- Drivers Container -->
							<div class="col-lg-12 col-md-12">
								<table class="table table-striped">
									<thead>
										<tr>
											<th>Drivers</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td align="center">

												<div id="div_drivers_container"
													style="height: 200px; overflow-x: hidden; overflow-y: auto;">
													
												</div>

											</td>
										</tr>
									</tbody>
								</table>

							</div>
							<!-- End Drivers Container -->

							<!-- Branches Container -->
							<div class="col-lg-12 col-md-12">
								<table class="table table-striped">
									<thead>
										<tr>
											<th>Branches</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td align="center">

												<div class="btn-group-vertical"
													style="height: 150px; overflow-x: hidden; overflow-y: auto;">
													<a name="a_drg" href="javascript:void(0)" class="btn btn-default">Button</a>
													<a href="javascript:void(0)" class="btn btn-default">Button</a>
													<a href="javascript:void(0)" class="btn btn-default">Button</a>
													<a href="javascript:void(0)" class="btn btn-default">Button</a>
													<a href="javascript:void(0)" class="btn btn-default">Button</a>
													<a href="javascript:void(0)" class="btn btn-default">Button</a>
													<a href="javascript:void(0)" class="btn btn-default">Button</a>
													<a href="javascript:void(0)" class="btn btn-default">Button</a>
												</div>

											</td>
										</tr>
									</tbody>
								</table>

							</div>
							<!-- End Branches Container -->
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 2 End-->

	</div>
	<!-- row end -->
</div>
<!-- Container End -->

<script src="app/dash_board/dash_board.js" type="text/javascript"></script>

<script>
	function fillDrivers(){
		var div = document.getElementById("div_drivers_container");
		<%
		
		for (User driver : drivers) {
			String userId = driver.getUserId();
			String firstName = driver.getFirstName();
			
		%>
			console.log('<%=firstName%>');
			var a = document.createElement("a");
			a.setAttribute("id","dri_<%=userId%>");
			a.setAttribute("name","a_drg");
			a.setAttribute("draggable","true");
			a.setAttribute("ondragstart","drag(event)");
			a.setAttribute("href","#");
			a.setAttribute("class","btn btn-info");
			a.innerHTML="<%=firstName%>";
			
			div.appendChild(a);
			
		<%
		}
		%>
		
	}
	fillDrivers();
</script>


<%@ include file="../includes/footer.jsp"%>