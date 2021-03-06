<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
    <title>Title</title>
    <%@ include file="../header.jsp" %>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    </head>
<body>
    <div id="users_table_container">
    <table id="example" class="table table-striped table-bordered" style="width:100%">
        <thread>
        <tr>
            <th><input type="checkbox" id="checkAll"></th>
            <th>Name</th>
            <th>login</th>
            <th>Role</th>
        </tr>
        </thread>
        <c:forEach var="user" items="${users}">
        <tbody>
        <tr>
            <th><input type="checkbox" value="${user.id}" name="users-check"></th>
        <th><a href="<c:url value="/admin/${user.id}"/>">${user.firstName} ${user.lastName}</a></th>
            <th>${user.login}</th>
            <th>${user.role.name}</th>
        </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>
    <button name="add-user" type="button" class="btn btn-primary" onclick="window.location.href='/admin/create'">Add</button>
    <button id="delete-user" type="button" class="btn btn-primary">Delete</button>

    <script src="<c:url value="/resources/js/jquery-3.3.1.js"/>"></script>
    <script src="<c:url value="/resources/js/bootstrap.js"/>"></script>
    <script src="<c:url value="/resources/js/admin/admin.js"/>"></script>
    <script src="<c:url value="/resources/js/header.js"/>"></script>
</body>
</html>