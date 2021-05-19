"use strict"

let username = document.getElementById("exampleName1");
document.getElementById("register").onclick=function(e){
    e.preventDefault();
    // username.value = encodeURIComponent(username.value);
    document.forms["registerform"].submit();
}