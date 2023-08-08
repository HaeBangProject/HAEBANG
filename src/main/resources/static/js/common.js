
function showLoadingImage() {
    var top = ( $(window).height() - 50 ) / 2 + $(window).scrollTop();
    var left = ( $(window).width() - 50 ) / 2 + $(window).scrollLeft();
    document.getElementById("load_image").hidden = false;
    if($("#load_image").length != 0) {
        $("#load_image").css({
            "position": "absolute",
            "top": top + "px",
            "left": left + "px"
        });
    }
}
function hideLoadImage() {
    document.getElementById("load_image").hidden = true;
    }

function login_or_logout(){
    var to_login = document.getElementById("to_login");
    var logout_btn = document.getElementById("logout_btn");

    if(getCookie("username")=='' || getCookie("ATK")== ''){// 로그인 보이게
        to_login.hidden = false;
        logout_btn.hidden = true;
    }
    else{// 로그아웃 보이게
        logout_btn.hidden = false;
        to_login.hidden = true;
    }
}
function logout() {
    var data = {
        grant_type : "Bearer",
        access_token : getCookie("ATK").substring(4),
        refresh_token : getCookie("RTK").substring(4),
        username : getCookie("username").substring(9)
    }
    console.log(data);
    $.ajax({
        type: "POST",
        url: "/api/member/logout",
        headers: {"content-type": "application/json", 'Authorization':'Bearer '+getCookie("ATK").substring(4)},
        dataType: "text",
        data: JSON.stringify(data)})
        .done(function (result) {
            alert(result);
            window.location.href = '/';
        })
        .fail(function (jqXHR, textStatus, errorThrown) {
            alert("실패 : "+jqXHR.responseText);
        })
};
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
function setCookie(name, value, expiredays) {
    var todayDate = new Date();
    todayDate.setTime(todayDate.getTime() + 0);
    if (todayDate > expiredays) {
        document.cookie = name + "=" + value + "; path=/; expires=" + expiredays + ";";
    } else if (todayDate < expiredays) {
        todayDate.setDate(todayDate.getDate() + expiredays);
        document.cookie = name + "=" + value + "; path=/; expires=" + todayDate.getTime()+0 + ";";
    }
}