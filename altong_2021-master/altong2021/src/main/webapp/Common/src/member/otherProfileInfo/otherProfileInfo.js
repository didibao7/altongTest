function goSubmit(FormName, URL, Target) {
    console.log('before eval : ' + FormName)
    var FormName = eval(FormName);
    console.log('after eval : ' + FormName)
    FormName.target = Target;
    FormName.action = "" + URL + "";
    FormName.submit();
}