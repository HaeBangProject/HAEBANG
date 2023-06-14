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