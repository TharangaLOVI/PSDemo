<%@page import="org.lovi.psdemo.models.Menu"%>
<%@page import="java.util.List"%>
<%@page import="org.lovi.psdemo.models.User"%>
<% 
   List<Menu> menus = ((User)(request.getAttribute("user"))).getArrayListMenu();
%>

<!-- Left Menu -->
<div class="col-lg-3 col-md-3">
	<div class="panel panel-material-light-blue-500">
		<div class="panel-heading">
			<h3 class="panel-title">Welcome</h3>
		</div>
		<div class="panel-body">
			<ul class="nav nav-pills nav-stacked">
			
				<% for(Menu menu : menus){ %>
					
					<li><a class="btn-material-light-blue-500" href="<%=menu.getPageUrl()%>"><%=menu.getMenupageName() %></a></li>	
				
				<% } %>
			</ul>
		</div>
	</div>
</div>
<!-- Left Menu End-->