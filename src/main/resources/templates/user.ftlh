<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="${rc.getContextPath()}/static/img/favicon.ico">
    <script type="text/javascript" src="${rc.getContextPath()}/static/js/jquery.min.js"></script>
    <script src="${rc.getContextPath()}/static/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${rc.getContextPath()}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${rc.getContextPath()}/static/css/style.css">
    <link rel="stylesheet" href="${rc.getContextPath()}/static/css/audioplayer.css">
</head>

<body>
<#include "templates/navbar.ftlh"/>

<div class="container">
    <div class="row">
        <div class="col-md-7 colomn-center">
            <#if userInfo??>
                <button id="1" data-id="${userInfo.getPerson().getId()}">Добавить в друзья</button>
                <h6>У ${userInfo.getPerson().getName()} ${userInfo.getFriends().size()} друзей</h6>
            </#if>
        </div>
    </div>
</div>

<#include "templates/footer.ftlh"/>

<script src="${rc.getContextPath()}/static/js/notify.min.js"></script>

<script>
    $(document).ready(function () {
        $("#1").button().click(function () {
            let recepient = $(this).data('id');
            let tempId = "";
            for (let i = 0; i < audioId.length; i++) {
                if (audioId[i] >= '0' && audioId[i] <= '9') {
                    tempId += audioId[i];
                }
            }
            audioId = tempId;
            console.log(audioId);
            $.ajax({
                type: 'GET',
                url: "/song/" + audioId,
                success: success
            });


            let toast = $("#liveToast");
            toast.find(".close").click(function () {
                toast.toast('hide');
            })

            function success(data) {
                console.log(data);
                console.log(item);
                $(document).ready(function () {
                    if (data.name !== undefined) {
                        toast.find(".me-auto").text(data.name);
                        toast.find(".toast-body").html(data.text.replaceAll('\n', "<br>"));
                        toast.toast('show');
                    } else {
                        toast.find(".me-auto").text("Not found");
                        toast.find(".toast-body").html("Text not found. Do not worry.");
                        toast.toast('show');
                    }
                });
            }
        });
    });
</script>

</body>

</html>