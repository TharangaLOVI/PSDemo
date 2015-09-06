<%@page import="org.json.JSONArray"%>
<%@page import="org.lovi.psdemo.config.CommonConfig"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.lovi.psdemo.models.ItemCategory"%>
<%@page import="org.lovi.psdemo.models.Item"%>
<%@ include file="../includes/header.jsp"%>
<nav></nav>


<div class="container">
	
	<div class="row">
		<!-- 1 -->
		<div class="col-lg-12 col-md-12">

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
								<a href="javascript:submit()" style="float: right;" class="btn btn-success  btn-fab btn-raised mdi-action-done"  ></a>
								<table class="table table-striped table-hover " >
									<thead>
										<tr>
											<th >Drivers</th>
											<th	>Branches</th>
											<th >Items Plates</th>
										</tr>
									</thead>
									<tbody>
										<% 
											JSONArray dataJSONArray = (JSONArray) request.getAttribute("dataJSONArray");
										   	for(int d = 0 ; d < dataJSONArray.length() ;d++){
										   		JSONObject driver = dataJSONArray.getJSONObject(d);
										   		String userId = driver.getString("userId");
										   		String firstName = driver.getString("firstName");
										   		
										   		JSONArray branches = driver.getJSONArray("branches");
										%>
										<tr>
											<td id="tr_dri_<%=userId%>">
												<a id="a_dri_<%=userId%>" name="a_fire_driver" style="width: 150px" href="javascript:void(0)" class="btn btn-danger"><%=firstName%></a>
											</td>
											
											<td>
												<% 	
													for(int b = 0 ; b < branches.length() ; b++){
														JSONObject branch = branches.getJSONObject(b);
														int branchId = branch.getInt("branchId");
														String branchName = branch.getString("branchName");
												%>
												<div class="row">
													<div class="col-lg-12 col-md-12">
														<a id="a_dri_<%=userId%>_b_<%=branchId%>" name="a_fire_branch" style="width: 150px" href="javascript:void(0)" class="btn btn-warning"><%=branchName%></a>
													</div>
												</div>
												<%} %>
												
											</td>
											
											<td align="left" >
												
												<% 
													for(int bp = 0 ; bp < branches.length() ; bp++){	
														JSONObject branch = branches.getJSONObject(bp);
														int branchId = branch.getInt("branchId");
														String branchName = branch.getString("branchName");
														JSONArray itemsPlates = branch.getJSONArray("itemsPlates");
												%>
												<div class="row" >
													
													<div class="col-lg-12 col-md-12" style="width: 300px;overflow-x: auto;overflow-y: hidden;">
														
														<table >
															<tr>
																<%
																	for(int ip = 0 ; ip < itemsPlates.length() ; ip++){
																	JSONObject itemsPlate = itemsPlates.getJSONObject(ip);
																	int plateId = itemsPlate.getInt("plateId");
																	String plateName = itemsPlate.getString("plateName");
																%>
																<td >
																	<a id = "a_dri_<%=userId%>_plt_<%=plateId%>" name="a_fire_plate" href="javascript:void(0)" class="btn btn-success" data-placement="bottom"  data-toggle="tooltip" title="<%=plateName%>"><%=plateId%></a>
																</td>
																<%} %>
															</tr>
														</table>
													</div>
													
												</div>
												<%} %>
												
											</td>
										</tr>
										<%} %>
								
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

<script src="app/dash_board/dash_board.js" type="text/javascript"></script>

<!-- 
<script>
	$('a').tooltip();
</script>
 -->

<%@ include file="../includes/footer.jsp"%>