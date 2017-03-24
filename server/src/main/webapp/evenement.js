function dessin(name) {
	$.ajax({
		type : "GET",
		url : "v1/event/" + name,
		success : function(data) {
			dessinerGride(data);
		}
	});
}

function dessinerGride(data) {
	var context;
	var canvas;
	canvas = $('#screen')[0];
	context = canvas.getContext("2d");

	context.fillStyle = "whitesmoke";
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

	context.fill();
	context.stroke();

	dessinerContenue(data, context);
}

function dessinerContenue(data, context) {
	var y=15;
	
	console.log(data);
	
	context.fillStyle = "red";
	
	for(var i=0; i<data.length; i++){
		if(data[i].date.substring(0,2) === "20"){
			context.fillRect(data[i].date.substring(10,11)*75+300,y,75,75);
		}else if(data[i].date.substring(0,2) === "21"){
			context.fillRect(data[i].date.substring(10,11)*75+300,y+75,75,75);
		}else if(data[i].date.substring(0,2) === "22"){
			context.fillRect(data[i].date.substring(10,11)*75+300,y+75*2,75,75);
		}else if(data[i].date.substring(0,2) === "23"){
			context.fillRect(data[i].date.substring(10,11)*75+300,y+75*3,75,75);
		}else if(data[i].date.substring(0,2) === "24"){
			context.fillRect(data[i].date.substring(10,11)*75+300,y+75*4,75,75);
		}else if(data[i].date.substring(0,2) === "25"){
			context.fillRect(data[i].date.substring(10,11)*75+300,y+75*5,75,75);
		}else if(data[i].date.substring(0,2) === "26"){
			context.fillRect(data[i].date.substring(10,11)*75+300,y+75*6,75,75);
		}
	}

	context.fill();
}