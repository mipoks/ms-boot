<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><@spring.message code="profile.title" /></title>
    <link rel="icon" href="${rc.getContextPath()}/static/img/favicon.ico">
    <script type="text/javascript" src="${rc.getContextPath()}/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="${rc.getContextPath()}/static/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${rc.getContextPath()}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${rc.getContextPath()}/static/css/style.css">
</head>

<body>
<#include "templates/navbar.ftlh"/>

<div class="container">
    <div class="row">
        <#include "tags/alert.ftlh"/>
        <form method="POST" class="col-md-7 colomn-center" name="profileform">
            <div class="form-group">
                <label for="newname"><@spring.message code="profile.new.name" /></label>
                <input type="text" class="form-control form-control-40" name="newname"
                       placeholder="${user.getName()}"
                       id="newname">
            </div>
            <div class="form-group">
                <label for="passold"><@spring.message code="profile.old.password" /></label>
                <input type="password" class="form-control" name="passwordold" id="passold"
                       placeholder="********">
            </div>
            <div class="form-group">
                <label class="control-pwd" for="passnew"><@spring.message code="profile.new.password" /></label>
                <input type="password" class="form-control form-control-40" name="passwordnew"
                       placeholder="********"
                       id="passnew">
            </div>
            <div class="form-group">
                <label for="songcount"><@spring.message code="profile.song.count" /></label>
                <input type="text" class="form-control form-control-40" name="songcount"
                       placeholder="${user.getSongCount()}"
                       id="songcount">
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" id="changeprofile" class="btn btn-default login-submit"><@spring.message code="profile.save" /></button>
        </form>
        <form method="POST" class="col-md-7 colomn-center" action="profile">
            <div class="form-group" style="margin-top: 40px">
                <label for="passdelete"><@spring.message code="profile.password.delete" /></label>
                <input type="password" class="form-control" name="passworddelete" id="passdelete"
                       placeholder="********">
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <button type="submit" id="deleteprofile" class="btn btn-right btn-danger login-submit"><@spring.message code="profile.delete.account" />
            </button>
        </form>
    </div>
</div>

<#include "templates/footer.ftlh"/>


<script type="text/javascript" src="${rc.getContextPath()}/static/js/profileSubmitPreventer.js"></script>

</body>
</html>