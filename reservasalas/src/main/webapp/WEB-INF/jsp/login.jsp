<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .login-container { background-color: #fff; padding: 40px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); width: 320px; }
        h2 { text-align: center; color: #333; margin-bottom: 20px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; color: #555; }
        input[type="text"], input[type="password"] { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
        .btn { background-color: #007bff; color: white; padding: 10px 15px; border: none; border-radius: 4px; width: 100%; font-size: 16px; cursor: pointer; }
        .btn:hover { background-color: #0056b3; }
        .message { padding: 10px; margin-bottom: 15px; border-radius: 4px; text-align: center; }
        .error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .logout { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
    </style>
</head>
<body>

<div class="login-container">
    <h2>Acessar Sistema</h2>

    <c:if test="${param.error}">
    <div class="message error">
        Usuário ou senha inválidos.
    </div>
    </c:if>

    <form action="<c:url value='/exec_login'/>" method="post">
        <div class="form-inputs">
            <label for="username" >Nome do usuário:</label>
            <input type="text" id="username" name="username" required autofocus>
        </div>

        <div class="form-inputs">
            <label for="password" >Senha:</label>
            <input type="text" id="password" name="password" required>
        </div>

        <button type="submit" class="btn">Entrar</button>
    </form>

</div>

</body>
</html>