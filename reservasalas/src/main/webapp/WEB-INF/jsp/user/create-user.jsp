<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Criar Usuário</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .login-container { background-color: #fff; padding: 40px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); width: 320px; }
        h2 { text-align: center; color: #333; margin-bottom: 20px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; color: #555; }
        input[type="text"], input[type="password"] { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
        .btn { text-decoration: none; background-color: #007bff; color: white; padding: 10px 15px; border: none; border-radius: 4px; width: 100%; font-size: 16px; cursor: pointer; }
        .btn:hover { background-color: #0056b3; }
        .message { padding: 10px; margin-bottom: 15px; border-radius: 4px; text-align: center; }
        .error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
    </style>
</head>
<body>

<div class="container">

     <h2>Criar Novo Usuário</h2>

    <%-- Mensagens de sucesso ou erro --%>
    <c:if test="${not empty success}"><div class="message success">${success}</div></c:if>
    <c:if test="${not empty erro}"><div class="message error">${erro}</div></c:if>

    <form:form modelAttribute="user" action="/user/save" method="post">
        <div class="form-group">
            <form:label path="username">Nome de Usuário:</form:label>
            <form:input path="username" type="text" required="true" />
        </div>

        <div class="form-group">
            <form:label path="password">Senha:</form:label>
            <form:password path="password" required="true" />
        </div>

        <div class="form-group">
            <form:label path="confirmPassword">Confirmar senha:</form:label>
            <form:password path="confirmPassword" required="true" />
        </div>

                <sec:authorize access="hasRole('ADMINISTRADOR')">
                    <div class="form-group">
                        <label>Papel do Usuário:</label>
                        <select name="roles" id="roles" required>
                            <option value="">Selecione um papel</option>
                            <c:forEach items="${roles}" var="role">
                                <option value="${role}">${role}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="form-group" id="sectors-group" style="display: none;">
                        <label for="sector">Entidade:</label>

                        <select name="sectorId" id="sector-select">

                        </select>
                    </div>
                </sec:authorize>

        <div style="align-content: center">
            <button type="submit" class="btn" style="margin-bottom: 15%">Salvar Usuário</button>
            <a href="<c:url value="${backUrl}"/>" class="btn" >Voltar</a>
        </div>
    </form:form>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function () {

        const rolesSelect = document.getElementById('roles');
        const sectorsGroup = document.getElementById('sectors-group');
        const sectorsSelect = document.getElementById('sector-select');

        const TARGET_ROLE = 'RECEPCIONISTA';

        rolesSelect.addEventListener('change', function () {
            const selectedRole = this.value;

            if (selectedRole === TARGET_ROLE) {

                sectorsGroup.style.display = 'block';
                sectorsSelect.required = true;
                fetchSectors();
            } else {

                sectorsGroup.style.display = 'none';
                sectorsSelect.required = false;
                sectorsSelect.innerHTML = '';
            }
        });

        function fetchSectors() {

            sectorsSelect.innerHTML = '<option value="">Carregando...</option>';

            fetch('/sector/entities')
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Erro ao buscar setores');
                    }
                    return response.json();
                })
                .then(data => {
                    sectorsSelect.innerHTML = '<option value="">Selecione um setor</option>';

                    data.forEach(sector => {
                        const option = document.createElement('option');
                        option.value = sector.id;
                        option.textContent = sector.name;
                        sectorsSelect.appendChild(option);
                    });
                })
                .catch(error => {
                    console.error('Falha na requisição:', error);
                    sectorsSelect.innerHTML = '<option value="">Erro ao carregar</option>';
                });
        }
    });
</script>

</body>
</html>