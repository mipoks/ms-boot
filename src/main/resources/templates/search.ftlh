<#import "spring.ftl" as spring/>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><@spring.message code="search.title" /></title>
    <link rel="icon" href="${rc.getContextPath()}/static/img/favicon.ico">
    <script type="text/javascript" src="${rc.getContextPath()}/static/js/jquery.min.js"></script>
    <script src="${rc.getContextPath()}/static/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${rc.getContextPath()}/static/css/style.css">
    <link rel="stylesheet" href="${rc.getContextPath()}/static/css/bootstrap.min.css">
</head>

<body>
<#include "templates/navbar.ftlh"/>

<div class="container-important">
    <h1 class="text-important"><@spring.message code="search.find.text"/></h1>
    <form class="col-md-7 colomn-center" method="GET" action="${rc.getContextPath()}/search">
        <input class="form-control form-control-3 in-big-block search-form" name="search" id="main-search" type="text"
               placeholder="<@spring.message code="search.example" />, <#if somemusic??>${somemusic}<#else>Feduk останься</#if>">
    </form>
</div>

<#include "templates/footer.ftlh"/>
</body>
</html>