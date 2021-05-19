<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Page is unavailable</title>
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
        <div class="col-12">
            <div class="no-music">
                <#if message??>
                    <h1>${message}</h1>
                <#else >
                    <h1>Sorry, page is unavailable</h1>
                </#if>
            </div>
        </div>

    </div>

</div>


<div class="position-fixed bottom-0 end-0 p-3" style="z-index: 999999;position: fixed; bottom: 0; right: 0;">
    <div id="liveToast" class="toast hide" role="alert" aria-live="assertive" data-autohide="false" aria-atomic="true">
        <div class="toast-header" style="display: block">
            <strong class="me-auto"></strong>
            <small class="song-name-cls"></small>
            <button type="button" style="float: right !important;" class="ml-2 mb-1 close" data-dismiss="toast"
                    aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="toast-body">
        </div>
    </div>
</div>

<#include "templates/footer.ftlh"/>

</body>

</html>