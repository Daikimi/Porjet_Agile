function getUserBdd(name) {
	getUserGeneric(name, "v1/user/");
}

function getUserGeneric(name, url) {
	$.getJSON(url + name, function(data) {
		afficheUser(data);
	});
}

function conection(name, mdp) {
	$.ajax({
		type : "GET",
		url : "v1/user/" + name,
		success : function(data) {
			$('#panel-conection').hide();
			$('#conecter').show();
			if (data.name === "Admin") {
				$('#non-admin').hide();
				$('#admin').show();
				$('#rdv').show();
				$('#profil').hide();
				dessin(name);
			} else {
				$('#Login').empty();
				$('#Login').append("<h1>Bienvenue " + name + "</h1>");
				donnePerso(name);
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			$('#panel-conection').hide();
			$('#erreur').show();
		}
	});
}

function donnePerso(name) {
	$.ajax({
		type : "GET",
		url : "v1/user/" + name,
		success : function(data) {
			$('#contenant-profil').empty();
			$('#contenant-profil').append(
					"<p><h3>Nom : " + data.name + "<br>Prenom : " + data.alias
							+ "<br>Email : " + data.email + "</h3></p>");
		}
	});
}

function getForAll() {
	getSecure("v1/secure/who");
}

function getByAnnotation() {
	getSecure("v1/secure/byannotation");
}

function getSecure(url) {
	if ($("#userlogin").val() != "") {
		verifierUser(data);
		$.ajax({
			type : "GET",
			url : url,
			dataType : 'json',
			beforeSend : function(req) {
				req.setRequestHeader("Authorization", "Basic "
						+ btoa($("#userlogin").val() + ":"
								+ $("#passwdlogin").val()));
			},
			success : function(data) {
				afficheUser(data);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert('error: ' + textStatus);
			}
		});
	} else {
		$.getJSON(url, function(data) {
			afficheUser(data);
		});
	}
}

function verifierUser(data) {
	if (data.id !== -1) {
		$('#panel-conection').hide();
	}
}

function postUserBdd(name, alias, email, pwd) {
	console.log("postUserBdd " + name)
	postUserGeneric(name, alias, email, pwd, "v1/user/");
	$('#panel-conection').hide();
	$('#Login').empty();
	$('#Login').append("<h1>Bienvenue " + name + "</h1>");
	donnePerso(name);
	$('#conecter').show();
}

function postUserGeneric(name, alias, email, pwd, url) {
	console.log("postUserGeneric " + url)
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : url,
		dataType : "json",
		data : JSON.stringify({
			"name" : name,
			"alias" : alias,
			"email" : email,
			"password" : pwd,
			"id" : 0
		}),
		success : function(data, textStatus, jqXHR) {
			afficheUser(data);

		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log('postUser error: ' + textStatus);
		}
	});
}

function listUsersBdd() {
	listUsersGeneric("v1/user/");
}

function listUsersGeneric(url) {
	$.getJSON(url, function(data) {
		afficheListUsers(data)
	});
}

function afficheUser(data) {
	console.log("test");
	console.log(data);
	$("#reponse").html(
			data.id + " : <b>" + data.alias + "</b> (" + data.name + ")");
}

function afficheListUsers(data) {
	var html = '<ul>';
	var index = 0;
	for (index = 0; index < data.length; ++index) {
		html = html + "<li>" + data[index].name + "</li>";
	}
	html = html + "</ul>";
	$("#reponse").html(html);
}