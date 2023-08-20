function createLoadingImage(){
    var loadImage = document.createElement("img");
    loadImage.id = "load_image";
    loadImage.src = "/img/loading_image.gif";
    loadImage.style.visibility = "hidden";
    loadImage.style.position = "absolute";

    document.body.appendChild(loadImage);
}
function showLoadingImage() {
    var top = ( $(window).height() - 50 ) / 2 + $(window).scrollTop();
    var left = ( $(window).width() - 50 ) / 2 + $(window).scrollLeft();
    document.getElementById("load_image").style.visibility = "visible";
    document.getElementById("load_image").style.zIndex = "9999";
    document.getElementById("load_image").style.top = top+"px";
    document.getElementById("load_image").style.left = left+"px";
}
function hideLoadImage() {
    document.getElementById("load_image").style.visibility = "hidden";
}

function login_or_logout(){
    var to_login = document.getElementById("to_login");
    var logout_btn = document.getElementById("logout_btn");

    if(getCookie('ATK').length>0 && getCookie('user_id').length>0 && getCookie('username').length>0){
        // 필요한게 다 있으면 로그아웃 보이게
        logout_btn.hidden = false;
        to_login.hidden = true;
    }
    else{// 나머지 경우 로그인 보이게
        setCookie('username', '', -1);
        setCookie('user_id', '', -1);
        setCookie('ATK', '', -1);
        logout_btn.hidden = true;
        to_login.hidden = false;
    }
}
function logout() {
    showLoadingImage();
    var data = {
        grant_type : "Bearer",
        access_token : getCookie("ATK").substring(4),
        refresh_token : localStorage.getItem('RTK'),
        username : getCookie("username").substring(9),
        user_id : getCookie("user_id").substring(8)
    }
    console.log(data);
    $.ajax({
        type: "POST",
        url: "/api/member/logout",
        headers: {"content-type": "application/json"},
        dataType: "text",
        data: JSON.stringify(data)})
        .done(function (result) {
            hideLoadImage();
            alert(result);
            window.location.href = '/';
        })
        .fail(function (jqXHR, textStatus, errorThrown) {
            setCookie('ATK', '', -1);
            setCookie('username', '', -1);
            setCookie('user_id', '', -1);
            hideLoadImage();
            alert('로그아웃');
            window.location.href = '/';
        })
};

function reissue(){
    $.ajax({
        type: "GET",
        url: "/api/member/reissue",
        headers: {"content-type": "application/json", 'Authorization':'Bearer '+localStorage.getItem('RTK')},
        dataType: "text"
    })
        .done(function (result) {
            hideLoadImage();
            alert("다시 시도해 주세요");
        })
        .fail(function (jqXHR, textStatus, errorThrown) {
            hideLoadImage();
            setCookie('ATK', '', -1);
            setCookie('username', '', -1);
            setCookie('user_id', '', -1);
            window.location.href = "/memberLogin";
            alert("새 로그인 필요");
        })
}
function getCookie(name){
    var search = name +"=";
    if (document.cookie.length>0){
        var start = document.cookie.lastIndexOf(search);
        if(start==-1) return "";
        var end = document.cookie.indexOf(";", start);
        if(end==-1){
            end = document.cookie.length;
        }
        return document.cookie.substring(start, end);
    }
    return "";
}
function setCookie(name, value, days) {
    var expires = "";
    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + value + expires + "; path=/";
}