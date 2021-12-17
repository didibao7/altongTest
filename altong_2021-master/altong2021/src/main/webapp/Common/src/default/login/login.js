function checkNum(obj) {
    function isNumeric(str) {
        for (i = 0; i < str.length; i++)
            if (str.charAt(i) < '0' || str.charAt(i) > '9') return false;
        return true;
    }

    function excluChar(str) {
        var val = "";
        for (i = 0; i < str.length; i++)
            if (str.charAt(i) >= '0' && str.charAt(i) <= '9') val += str.charAt(i);
        return val;
    }

    var val = obj.value;

    if (!isNumeric(val)) {
        alert("숫자만 입력 가능합니다.");
        obj.value = excluChar(val);
        obj.focus();
        return false;
    }
    return true;
}