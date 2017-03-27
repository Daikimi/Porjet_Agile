package fr.iutinfo.skeleton.api;

import fr.iutinfo.skeleton.common.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static fr.iutinfo.skeleton.api.BDDFactory.getDbi;
import static fr.iutinfo.skeleton.api.BDDFactory.tableExist;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
	final static Logger logger = LoggerFactory.getLogger(UserResource.class);
	private static UserDao dao = getDbi().open(UserDao.class);

	public UserResource() throws SQLException {
		if (!tableExist("users")) {
			logger.debug("Crate table users");
			dao.createUserTable();
			dao.insert(new User(0, "Admin", "Admin"));
			dao.insert(new User(0, "Marc", "t"));
			dao.insert(new User(0, "Olivier", "a"));
		}
	}

	@POST
	public UserDto createUser(UserDto dto) {
		User user = new User();
		user.initFromDto(dto);
		user.setPasswdHash(buildHash(dto.getPassword()));
		int id = dao.insert(user);
		dto.setId(id);
		return dto;
	}

	@GET
	@Path("/{name}")
	public UserDto getUser(@PathParam("name") String name) {
		User user = dao.findByName(name);
		if (user == null) {
			throw new WebApplicationException(404);
		}
		return user.convertToDto();
	}

	@GET
	public List<UserDto> getAllUsers(@QueryParam("q") String query) {
		List<User> users;
		if (query == null) {
			users = dao.all();
		} else {
			logger.debug("Search users with query: " + query);
			users = dao.search("%" + query + "%");
		}
		return users.stream().map(User::convertToDto).collect(Collectors.toList());
	}

	@DELETE
	@Path("/{id}")
	public void deleteUser(@PathParam("id") int id) {
		dao.delete(id);
	}

	public static String buildHash(String password) {
		Hasher hasher = Hashing.sha256().newHasher();
		hasher.putString(password, Charsets.UTF_8);
		return hasher.hash().toString();
	}

}
