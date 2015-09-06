<%@page import="org.lovi.psdemo.config.CommonConfig"%>
<%@page import="org.json.JSONObject"%>
<%@ include file="includes/header.jsp"%>

<div class="container">

	<div class="row">
		<div class="col-lg-6 col-md-6 col-sm-6">

			<h1>Perera and Sons</h1>
			<p class="lead">Admin Console</p>

		</div>

		<div class="col-lg-6 col-md-6 col-sm-6">

			<div class="well bs-component">
				<form class="form-horizontal" action="sign_in" method="post">
					<fieldset>
						<legend>Sign in</legend>
						<div id="error_div"></div>
						<div class="form-group">
							<label class="col-lg-2 control-label" for="inputEmail">Email</label>
							<div class="col-lg-10">
								<input type="email" placeholder="Email" id="inputEmail"
									name="userId" class="form-control" value="${user.userId}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label" for="inputPassword">Password</label>
							<div class="col-lg-10">
								<input type="password" placeholder="Password" id="inputPassword"
									name="password" class="form-control" value="${user.password}">

							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-10 col-lg-offset-2">
								<button class="btn btn-default" type="reset">Clear</button>
								<button class="btn btn-material-light-blue-500" type="submit">Submit</button>
							</div>
						</div>
					</fieldset>
				</form>
				<div class="btn btn-primary btn-xs" id="source-button"
					style="display: none;">&lt; &gt;</div>
			</div>

		</div>
	</div>

</div>

<%
	try {
		JSONObject message = (JSONObject) request.getAttribute(CommonConfig.MESSAGE);
		int status = message.getInt(CommonConfig.STATUS);
		String value = message.getString(CommonConfig.VALUE);
		String type = message.getString(CommonConfig.VIEW_MESSAGE_TYPE);
		
		if (status == 1) {
			
		} else {
			if (type.equals(CommonConfig.VIEW_TYPE_SIGN_IN)) {
				out.print("<script> notify('Error !','"
						+ value
						+ "','danger','#error_div'); </script>");
			} 
		}

	} catch (Exception e) {
	}
%>

<%@ include file="includes/footer.jsp"%>