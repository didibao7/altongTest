function fShowMenu2(){
	console.log("test");
	var menuBox = $('#menuFriend');
	
	if ($(this).next().attr('id') == 'menuFriend')
		fHideMenu();
	else 
		menuBox.insertAfter(this).show();
}