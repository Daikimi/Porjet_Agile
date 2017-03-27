package fr.iutinfo.skeleton.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class TestEvenementDao {

	EvenementDao dao ;
	
	@Before
	public void init(){
		dao = BDDFactory.getDbi().open(EvenementDao.class);
		dao.droptTableEvenement();
		dao.createEvenementTable();
	}
	
	@Test
	public void shouldReturnEventWhenDateExist(){
		
		dao.insert(new Evenement("Test","27/04/2017 9"));
		Evenement e = dao.findByDate("27/04/2017 9");
		Assert.assertEquals("Test", e.getNom());
	}
	
	@Test
	public void shouldReturnNullWhenDateIsEmpty(){
		Evenement e = dao.findByDate("28/04/2017 9");
		Assert.assertEquals(null, e);
	}
}
