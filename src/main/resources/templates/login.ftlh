<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="icon" href="${rc.getContextPath()}/static/img/favicon.ico">
    <title><@spring.message code="login.title" /></title>
    <script type="text/javascript" src="${rc.getContextPath()}/static/js/jquery.min.js"></script>
    <script src="${rc.getContextPath()}/static/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${rc.getContextPath()}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${rc.getContextPath()}/static/css/style.css">
</head>

<body>
<#include "templates/navbar.ftlh"/>

<div class="container">
    <div class="row">
        <#include "tags/alert.ftlh"/>

        <form method="POST" class="col-md-7 colomn-center">
            <div class="form-group">
                <label for="exampleInputEmail1"><@spring.message code="login.email" /></label>
                <input type="email" class="form-control" name="email" id="exampleInputEmail1"
                       placeholder="example@email.com" aria-describedby="emailHelp">
            </div>
            <div class="form-group">
                <label class="control-pwd" for="exampleInputPassword1"><@spring.message code="login.password" /></label>
                <input type="password" class="form-control form-control-40" name="password" placeholder="********"
                       id="exampleInputPassword1">
            </div>
            <button type="submit" class="btn btn-default login-submit"><@spring.message code="login.enter" /></button>
            <a href="register" class="btn btn-default login-submit left"><@spring.message code="login.register" /></a>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <a href="${rc.getContextPath()}/oauth2/authorization/google" class="btn btn-default login-submit left"><@spring.message code="login.google" /></a>
            <a href="${rc.getContextPath()}/oauth2/authorization/github" class="btn btn-default login-submit right"><@spring.message code="login.github" /></a>
        </form>
    </div>
</div>

<#include "templates/footer.ftlh"/>


</body>

</html>