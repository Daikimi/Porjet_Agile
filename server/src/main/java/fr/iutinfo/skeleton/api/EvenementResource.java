package fr.iutinfo.skeleton.api;

import static fr.iutinfo.skeleton.api.BDDFactory.getDbi;
import static fr.iutinfo.skeleton.api.BDDFactory.tableExist;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import fr.iutinfo.skeleton.common.dto.EvenementDto;

@Path("/event")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EvenementResource {
	private static EvenementDao dao = getDbi().open(EvenementDao.class);
	
	public EvenementResource() throws SQLException, ParseException {
		if(!tableExist("evenement")){
			dao.createEvenementTable();
			GregorianCalendar d = new GregorianCalendar();
			
			Random rdm = new Random();
			for(int i=0; i<5; i++){
				d.set(2017, 3, rdm.nextInt(7)+20, rdm.nextInt(9)+1, 0);
				String date = d.get(Calendar.DAY_OF_MONTH)+"-"+d.get(Calendar.MONTH)+"-"+d.get(Calendar.YEAR)+" "+d.get(Calendar.HOUR);
				dao.insert(new Evenement("Olivier",date));
			}
			for(int i=0; i<5; i++){
				d.set(2017, 3, rdm.nextInt(7)+20, rdm.nextInt(9)+1, 0);
				String date = d.get(Calendar.DAY_OF_MONTH)+"-"+d.get(Calendar.MONTH)+"-"+d.get(Calendar.YEAR)+" "+d.get(Calendar.HOUR);
				dao.insert(new Evenement("Marc",date));
			} 
		}
	}
	
	@POST
	public EvenementDto createEvenement(EvenementDto dto){
		Evenement e = new Evenement();
		e.intiFromDto(dto);
		if(dao.findByDate(dto.getDate()) == null){
			dao.insert(e);
			return dto;
		}else{
			throw new WebApplicationException(404);
		}
	}
	
	@GET
	@Path("/{nom}")
	public List<EvenementDto> getEvenement(@PathParam("nom") String nom){
		List<Evenement> e;
		e = dao.findByName(nom);
		if(e.isEmpty()){
			throw new WebApplicationException(404);
		}
		return e.stream().map(Evenement::convertToDto).collect(Collectors.toList());
	}
	
	@GET
	public List<EvenementDto> getAllEvent(){
		List<Evenement> e;
		e=dao.all();
		return e.stream().map(Evenement::convertToDto).collect(Collectors.toList());
	}
	
	@DELETE
	@Path("/{date}")
	public void deleteEvenement(@PathParam("date") String date){
		dao.delete(date);
	}
}
