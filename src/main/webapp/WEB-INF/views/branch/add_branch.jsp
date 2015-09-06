<%@page import="org.lovi.psdemo.models.Branch"%>
<%@page import="org.lovi.psdemo.config.CommonConfig"%>
<%@page import="org.json.JSONObject"%>
<%@page import="org.lovi.psdemo.models.ItemCategory"%>
<%@page import="org.lovi.psdemo.models.Item"%>
<!-- shop add form modal -->
<div class="modal fade modal-primary" id="branchAddModal" role="dialog">
	<div class="modal-dialog">
		<div class="modal-content" style="overflow: auto;">
			<div class="modal-header shadow btn-material-blue-500"
				style="border-color: #09f;">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">New Branch</h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" method="post" action="<%=CommonConfig.URL_BRANCH.substring(1)%>">
					<fieldset>
						<br>
						<div id="error_div"></div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label" for="inputEmail">Name*</label>
							<div class="col-lg-10">
								<input required="required" type="text" placeholder="Input branch name" id="branchName"
									name="branchName" class="form-control" value="${branch.branchName}">
							</div>
						</div>
						
						
						 
						<div class="form-group">
							<label class="col-lg-2 control-label" for="inputEmail">Contact*</label>
							<div class="col-lg-10">
								<input required="required" type="text"  placeholder="Input branch contact no" id="phoneNumber"
									name="phoneNumber" class="form-control"  value="${branch.phoneNumber}">
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-lg-2 control-label" for="inputEmail">Address*</label>
							<div class="col-lg-10">
								<textarea required="required" placeholder="" id="address"
									name="address" class="form-control">
									${branch.address}
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
	if (type.equals(CommonConfig.VIEW_TYPE_BRANCH_ADD)) {
		
		out.print("<script> notify('Error !','"
				+ value
				+ "','danger','#error_div'); </script>");
		
		out.print("<script>javascript:$('#branchAddModal').modal('show');</script>");
		
	}

		}

	} catch (Exception e) {

	}
%>