var HttpRequest = null;

function GetXMLHttpRequest()
{
	if (window.ActiveXObject)
	{
		try
		{
			return new ActiveXObject("Msxml2.XMLHTTP");
		}
		catch (e)
		{
			try
			{
				return new ActiveXObject("Microsoft.XMLHTTP");
			}
			catch (e1)
			{
				return null;
			}
		}
	}
	else if (window.XMLHttpRequest)
	{
		return new XMLHttpRequest();
	}
	else
	{
		return null;
	}
}

function SendRequest(URL, Param, CallBack, Method)
{
	HttpRequest = GetXMLHttpRequest();

	var HttpMethod = Method ? Method : 'GET';

	if (HttpMethod != 'GET' && HttpMethod != 'POST')
	{
		HttpMethod = 'GET';
	}

	var HttpParam = (Param == null || Param == '') ? null : Param;
	var HttpURL = URL;

	if (HttpMethod == 'GET' && HttpParam != null)
	{
		HttpURL = HttpURL + "?" + HttpParam;
	}

	HttpRequest.open(HttpMethod, HttpURL, true);
	HttpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	HttpRequest.onreadystatechange = CallBack;
	HttpRequest.send(HttpMethod == 'POST' ? HttpParam : null);
}

