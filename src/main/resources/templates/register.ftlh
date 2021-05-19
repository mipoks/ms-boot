<#import "spring.ftl" as spring>
<!DOCTYPE html>
<html xmlns:form="http://www.w3.org/1999/html">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><@spring.messageText code="register.title" text="Registration" /></title>
    <link rel="icon" href="${rc.getContextPath()}/static/img/favicon.ico">
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

        <@spring.bind "personForm"/>
        <form class="col-md-7 colomn-center" method="POST" name="registerform">
            <div class="form-group">
                <label for="exampleInputEmail1"><@spring.message code="register.name" /></label>
                <@spring.bind path="personForm.name" />
                <input type="text" class="form-control" name="name" id="exampleName1" placeholder="Иван"
                       value=${personForm.getName()! "Иван"}  aria-describedby="textHelp">
            </div>

            <div class="form-group">
                <@spring.bind path="personForm.email" />
                <label for="exampleInputEmail1"><@spring.message code="register.email" /></label>
                <input type="email" class="form-control" name="email" id="exampleInputEmail1"
                        <#if personForm.getEmail()??>
                            value="${personForm.getEmail()}"
                        </#if>
                       placeholder="example@email.com" aria-describedby="emailHelp">
            </div>

            <div class="form-group">
                <label class="control-pwd" for="exampleInputPassword1"><@spring.message code="register.password" /></label>
                <@spring.bind path="personForm.password" />
                <input type="password" class="form-control form-control-40" name="password" placeholder="********"
                       id="exampleInputPassword1">
                <@spring.bind path="personForm.pwd2" />
                <input type="password" class="form-control form-control-40 down" name="pwd2" placeholder="********"
                       id="exampleInputPassword2">
            </div>

            <div class="form-check">
                <@spring.bind path="personForm.agree" />
                <input class="form-check-input" type="checkbox" name="agree" value="on" id="defaultCheck1">
                <label class="form-check-label" for="defaultCheck1">
                    <@spring.message code="register.agreement" />
                </label>
            </div>

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-default login-submit" id="register"><@spring.message code="register.submit" /></button>
        </form>
    </div>
</div>

<#include "templates/footer.ftlh"/>

<script src="${rc.getContextPath()}/static/js/submitPreventer.js"></script>
</body>
</html>