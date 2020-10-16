<%@page import="com.dn.amonir.model.Request"%>
<%

    Request Request7 = (Request) request.getAttribute("Request");


%>


<div class="noti__item js-item-menu" onClick="location.href = './call'">
    <i class="fas fa fa-phone fa-sm"></i>     
    <%        if (Request7.Account.Device.Selected.Statics.Call.Unread != 0) {
    %>
    <span class="quantity"><%= Request7.Account.Device.Selected.Statics.Call.Unread%></span>
    <%}%>

</div>

<div class="noti__item js-item-menu" onClick="location.href = './messages-sms'">
    <i class="fa fa-comment-alt fa-sm"></i>
     <%        if (Request7.Account.Device.Selected.Statics.Messages.SMS.Unread != 0) {
    %>
    <span class="quantity"><%= Request7.Account.Device.Selected.Statics.Messages.SMS.Unread%></span>
     <%}%>
</div>