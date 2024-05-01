package pti.sb_squash_mvc.db;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.SelectionQuery;
import org.springframework.stereotype.Repository;

import pti.sb_squash_mvc.model.Game;
import pti.sb_squash_mvc.model.Location;
import pti.sb_squash_mvc.model.User;

@Repository
public class Database {
	
	private SessionFactory sessionFactory;
	
	public Database()
	{
		Configuration config = new Configuration();
		config.configure();
		
		this.sessionFactory = config.buildSessionFactory();
	}
	
	public void closeDb()
	{
		this.sessionFactory.close();
	}

	public User getUserByNameAndPassword(String userName, String password) {
		
		List<User> userList = null;
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		SelectionQuery<User> query =session.createSelectionQuery("SELECT u FROM User u WHERE u.username = ?1 AND u.password = ?2 ",User.class);
		query.setParameter(1, userName);
		query.setParameter(2, password);
		
		userList = query.getResultList();
		
		tx.commit();
		session.close();
		
		User user = null;
		
		if(userList.size() > 0)
		{
			user = userList.get(0);
		}
		
		return user;
	}

	public void updateUser(User user) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		session.merge(user);
		
		tx.commit();
		session.close();
		
		
	}


	public User getUserByID(int userID) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		User user = session.get(User.class, userID);
		
		tx.commit();
		session.close();
		
		return user;
	}

	public List<Game> getGames() {
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		SelectionQuery<Game> query = session.createSelectionQuery("SELECT g FROM Game g",Game.class);
		List<Game> gameList = query.getResultList();
		
		tx.commit();
		session.close();
		
		return gameList;
	}

	public Location getLocationByID(int locationID) {
		
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Location location = session.get(Location.class, locationID);
		
		tx.commit();
		session.close();
		
		return location;
	}

}
