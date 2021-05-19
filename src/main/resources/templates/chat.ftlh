<#import "/spring.ftl" as spring/>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="${rc.getContextPath()}/static/img/favicon.ico">
    <script type="text/javascript" src="${rc.getContextPath()}/static/js/jquery.min.js"></script>
    <script src="${rc.getContextPath()}/static/js/bootstrap.min.js"></script>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.4/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>


    <link rel="stylesheet" href="${rc.getContextPath()}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${rc.getContextPath()}/static/css/style.css">
    <link rel="stylesheet" href="${rc.getContextPath()}/static/css/audioplayer.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">
    <title>Chat</title>
    <link rel="stylesheet" href="${rc.getContextPath()}/static/css/chat.css"/>
</head>
<body>
<#include "templates/navbar.ftlh"/>

<div class="container">
    <div class="row">
        <#include "tags/alert.ftlh"/>
        <div class="col-8">
            <div class="interlocutor" id="interlocutors">
            </div>
        </div>
    </div>
</div>

<#include "templates/footer.ftlh"/>

<script>
    'use strict';

    function getAvatarColor(messageSender) {
        var hash = 0;
        for (var i = 0; i < messageSender.length; i++) {
            hash = 31 * hash + messageSender.charCodeAt(i);
        }
        var index = Math.abs(hash % colors.length);
        return colors[index];
    }

    function getInterlocutors() {
        $.ajax({
            type: 'GET',
            url: "/interlocutors",
            success: success
        });
    }

    function success(data) {
        console.log("DATA IS:")
        console.log(data);

        let bbt;
        for (let i = 0; i < data.length; i++) {
            bbt = document.createElement('button');
            bbt.classList.add('chat-message')
            bbt.classList.add('audio-div');
            bbt.classList.add('img-song');
            bbt.innerText = data[i]['chatName'];
            bbt.dataset.id = data[i]['id'];
            $('#interlocutors').append(bbt);
        }
        $('.chat-message').click(function () {
            console.log("redirecting...");
            window.location = "${rc.getContextPath()}" + "/chat/" + $(this).data('id');
        })
    }
    getInterlocutors();

</script>

</body>
</html>