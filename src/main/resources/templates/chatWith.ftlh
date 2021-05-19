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

<div class="container" id="chat-page">
    <div class="row">
        <div class="col-8">
            <div class="chat-container">
                <div class="chat-header">
                    <h2>Chat</h2>
                </div>
                <div class="connecting">
                    Connecting...
                </div>
                <ul id="messageArea">

                </ul>
                <form id="messageForm" name="messageForm">
                    <div class="form-group">
                        <div class="input-group clearfix">
                            <input type="text" id="message" placeholder="Type a message..." autocomplete="off"
                                   class="form-control"/>
                            <button type="submit" class="btn-default">Send</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<#include "templates/footer.ftlh"/>

<script>
    'use strict';

    var chatPage = document.querySelector('#chat-page');
    var messageForm = document.querySelector('#messageForm');
    var messageInput = document.querySelector('#message');
    var messageArea = document.querySelector('#messageArea');
    var connectingElement = document.querySelector('.connecting');
    var stompClient = null;
    var username = null;

    var colors = [
        '#2196F3', '#32c787', '#00BCD4', '#ff5652',
        '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
    ];

    function connect(event) {
        chatPage.classList.remove('hidden');

        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        console.log("Trying to connect");
        stompClient.connect({}, onConnected, onError);
    }


    function onConnected() {
        console.log("Connected");
        stompClient.subscribe("/user/" + ${user.getId()} + "/queue/messages", onMessageReceived);
        connectingElement.classList.add('hidden');
    }


    function onError(error) {
        console.log("error:")
        console.log(error);
        connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
        connectingElement.style.color = 'red';
    }


    function sendMessage(event) {
        var messageContent = messageInput.value.trim();
        if (messageContent && stompClient) {
            var chatMessage = {
                chatId: "${chatId}",
                recipientId: ${recipientId},
                senderId: ${user.getId()},
                content: messageInput.value
            };
            stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
            messageInput.value = '';
        }
        event.preventDefault();
    }

    function createOldMsg() {
        <#list messages as message>
        console.log("${message}");
        var messageElement = document.createElement('li');
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode("${message.getSenderName()}"[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor("${message.getSenderName()}");
        messageElement.appendChild(avatarElement);
        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode("${message.getSenderName()}");
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);

        var textElement = document.createElement('p');
        var messageText = document.createTextNode("${message.getContent()}");
        textElement.appendChild(messageText);
        messageElement.appendChild(textElement);
        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;
        </#list>
    }

    function onMessageReceived(payload) {
        var message = JSON.parse(payload.body);
        console.log(message);

        var messageElement = document.createElement('li');

        if (message.type === 'JOIN') {
            messageElement.classList.add('event-message');
            message.content = message.senderName + ' joined!';
        } else if (message.type === 'LEAVE') {
            messageElement.classList.add('event-message');
            message.content = message.senderName + ' left!';
        } else {
            messageElement.classList.add('chat-message');

            var avatarElement = document.createElement('i');
            var avatarText = document.createTextNode(message.senderName[0]);
            avatarElement.appendChild(avatarText);
            avatarElement.style['background-color'] = getAvatarColor(message.senderName);

            messageElement.appendChild(avatarElement);

            var usernameElement = document.createElement('span');
            var usernameText = document.createTextNode(message.senderName);
            usernameElement.appendChild(usernameText);
            messageElement.appendChild(usernameElement);
        }

        var textElement = document.createElement('p');
        var messageText = document.createTextNode(message.content);
        textElement.appendChild(messageText);

        messageElement.appendChild(textElement);

        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;
    }


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
            bbt = document.createElement('div');
            bbt.innerHTML = "<li data-id=" + data[i]['id'] + " class='chat-message'>" + "Какой то чат :(" + "</li>";
            $('#interlocutors').append(bbt);
        }
    }

    getInterlocutors();
    createOldMsg();
    connect();
    messageForm.addEventListener('submit', sendMessage, true)
</script>

</body>
</html>