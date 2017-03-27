function dessin(name) {
	$.ajax({
		type : "GET",
		url : "v1/event/",
		success : function(data) {
			dessinerGride(data,name);
		}
	});
}

function dessinerGride(data,name) {
	var context;
	var canvas;
	canvas = $('#screen')[0];
	context = canvas.getContext("2d");

	context.fillStyle = "white";
	context.fillRect(0, 0, 1000, 500);

	context.fillStyle = "black";
	context.font = "10px Calibri,Geneva,Arial"

	var cpt = 9;
	for (var i = 100; i <= 1075; i += 75) {
		context.fillText(cpt + ":00", i, 10);
		cpt++;
	}

	context.font = "18px Calibri,Geneva,Arial"
	var jour = [ 'lundi', 'mardi', 'mercredi', 'jeudi', 'vendredi', 'samedi', 'dimanche' ];
	cpt = 0;
	for (var i = 35; i < 25 + 75 * 7; i += 75) {
		context.fillText(jour[cpt], 15, i);
		cpt++;
	}

	context.strokeStyle = "black";
	for (var j = 15; j < 15 + 75 * 7; j += 75) {//ligne
		for (var i = 100; i < 1150; i += 75) {//colone
			context.strokeRect(i, j, 75, 75);
		}
	}
	
	context.fillText("Mes rendez vous",130,570);
	context.fillText("Créneaux réservés",460,570);
	context.fillText("Créneaux disponible",790,570);
	
	context.strokeRect(100,555,15,15);
	context.strokeRect(430,555,15,15);
	context.strokeRect(760,555,15,15);
	
	context.fillStyle=" rgb(131,166,151)";
	context.fillRect(101,556,13,13);
	context.fillStyle=" rgb(255,110,110)";
	context.fillRect(431,556,13,13);
	
	if(name === "Admin"){
		context.fillStyle="white";
		context.fillRect(99,554,200,50);
	}
	
	context.fill();
	context.stroke();

	dessinerAll(data,context,name);
}

function dessinerAll(data, context,name){
	var canvas;
	canvas = $('#screen')[0];
	context = canvas.getContext("2d");
	
	
	var y=15;
	
	for(var i=0; i<data.length; i++){
		for(var j=0; j<7; j++){
			if(data[i].date.substring(0,2) === "2"+j){
				if(name === data[i].nom){
					context.fillStyle=" rgb(131,166,151)";
				}else{
					context.fillStyle=" rgb(255,110,110)";
				}
				context.fillRect(data[i].date.substring(10,11)*75+325+1,y+(75*j)+1,73,73);
				if(name == "Admin"){
					context.fillStyle = "black";
					context.fillText(data[i].nom,data[i].date.substring(10,11)*75+325+4,y+(75*j)+20);
				}
			}
		}
	}

	context.fill();
}

function postRdvBdd(nom, date, heure){
	
	var d  = convertDate(date,heure);
	console.log(d);
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : '/v1/event',
		dataType : "json",
		data : JSON.stringify({
			"nom" : nom,
			"date" : d,
		}),
		success : function(data, textStatus, jqXHR) {
			console.log("add");
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log("non add");
		}
	});
}

function convertDate(date,heure){
	var tab = (date+"-"+heure).split("-");
	return tab[2]+"/"+tab[1].substring(1,2)+"/"+tab[0]+" "+tab[3];
}




