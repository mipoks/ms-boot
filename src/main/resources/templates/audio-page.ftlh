<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
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
        <#include "tags/alert.ftlh"/>
        <div class="col-12">
            <#if !songs??>
                <div class="no-music">
                    <h1>${songemptytext}</h1>
                </div>
            <#else>
                <#list songs as song>
                    <div class="col-8 limited" style="float: left !important;">
                        <div class="audio-div img-song">
                            <audio src="${song.getUrl()}" name="${song.getSongName()}" id="${song.getId()}"
                                   orig="${song.getOriginalUrl()}" own="${song.isOwn()?string('true', 'false')}"
                                   _csrf="${_csrf.token}" preload="auto" controls>
                            </audio>
                        </div>
                    </div>
                </#list>
            </#if>
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


<script src="${rc.getContextPath()}/static/js/audioplayer.js"></script>
<script src="${rc.getContextPath()}/static/js/notify.min.js"></script>

<script>$(document).ready(function () {
        $('audio').audioPlayer();
        let i = 0;
        let bbt;
        <#list songs as song>
            bbt = document.createElement('div');
            bbt.innerHTML = "<button data-id='${song.getId()}' class='btn btn-song-text'>Get Text</button>";
            $('.audioplayer-volume').get(i).append(bbt);
            i++;
        </#list>
    });

    $(document).ready(function () {
        $(".btn-song-text").button().click(function () {
            let item = $(this).parent().get();
            let audioId = $(this).data('id');
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
                url: "/song/text/" + audioId,
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