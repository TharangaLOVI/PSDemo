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
<nav></nav>


<div class="container">

	<div class="row">
		<!-- 1 -->
		<div class="col-lg-6 col-md-6">

			<table class="table table-striped">
				<thead>
					<tr>
						<th>Branches</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td align="center">

							<div id="div_drivers_container"
								style="height: 200px; overflow-y: auto;">
								<div id="drag1" class="draggable js-drag">Drag me…</div>
								<div id="drag2" class="draggable js-drag">Drag me…</div>
								<div id="drag3" class="draggable js-drag">Drag me…</div>
								<div id="drag4" class="draggable js-drag">Drag me…</div>
							</div>

						</td>
					</tr>
				</tbody>
			</table>




		</div>
		<!-- 1 End-->

		<div class="col-lg-6 col-md-6">

			<div>
				<div id="drop1" class="dropzone js-drop">Dropzone</div>
				<div id="drop2" class="dropzone js-drop">Dropzone</div>
				<div id="drop3" class="dropzone js-drop">Dropzone</div>
			</div>


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