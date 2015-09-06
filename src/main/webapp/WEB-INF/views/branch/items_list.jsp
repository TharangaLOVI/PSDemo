<%@page import="java.util.List"%>
<%@page import="org.lovi.psdemo.models.Item"%>
<select required="required" id=itemCategory name='items[<c:out value="${index}"/>].itemId'
	class="form-control">
	<option disabled="disabled" selected="selected" value="0">Select
		a Item</option>

	<%
		for (Item item : (List<Item>) request.getAttribute("items")) {
	%>
	<option value="<%=item.getItemId()%>"><%=item.getName()%></option>
	<%
		}
	%>
</select>