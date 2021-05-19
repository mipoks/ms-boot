"use strict"

let username = document.getElementById("newname");
document.getElementById("changeprofile").onclick=function(e){
    e.preventDefault();
    // username.value = encodeURIComponent(username.value);
    document.forms["profileform"].submit();
}