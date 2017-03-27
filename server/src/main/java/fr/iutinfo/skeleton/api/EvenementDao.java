package fr.iutinfo.skeleton.api;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapperFactory;
import org.skife.jdbi.v2.tweak.BeanMapperFactory;

public interface EvenementDao {
	@SqlUpdate("create table evenement (nom varchar(100), date varchar(100),dispo boolean)")
	void createEvenementTable();

	@SqlUpdate("insert into evenement (nom,date,dispo) values (:nom, :date, :dispo)")
	int insert(@BindBean Evenement evenement);

	@SqlQuery("select * from evenement where nom = :nom order by date")
	@RegisterMapperFactory(BeanMapperFactory.class)
	java.util.List<Evenement> findByName(@Bind("nom") String nom);
	
	@SqlQuery("select * from evenement where date = :date order by date")
	@RegisterMapperFactory(BeanMapperFactory.class)
	Evenement findByDate(@Bind("date") String date);

	@SqlQuery("select * from evenement order by nom")
	@RegisterMapperFactory(BeanMapperFactory.class)
	java.util.List<Evenement> all();

	@SqlUpdate("drop table if exists evenement")
	void droptTableEvenement();

	@SqlUpdate("delete from evenement where date = :date")
	void delete(@Bind("date") String date);
	
	void close();
}
