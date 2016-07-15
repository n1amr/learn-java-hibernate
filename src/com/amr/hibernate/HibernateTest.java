package com.amr.hibernate;

import com.amr.hibernate.entities.Address;
import com.amr.hibernate.entities.Item;
import com.amr.hibernate.entities.User;
import com.amr.hibernate.entities.Vehicle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

public class HibernateTest {
	public static void main(String[] args) {
		Address address = new Address("street", "city", "state", "pincode");
		Vehicle vehicle = new Vehicle("vehicle2");
		User user = new User("Amr Alaa", new Date());
		user.getAddresses().add(address);
		user.setVehicle(vehicle);
		Item item;
		Collection<Item> items = user.getItems();
		for (int i = 1; i <= 5; i++) {
			item = new Item("Item #" + i);
			item.setOwner(user);
			items.add(item);

			User user1 = new User("User #" + i, new Date());
			user.getFollowing().add(user1);

			Vehicle vehicle1 = new Vehicle("Vehicle #" + i);
			user.getRentedVehicles().add(vehicle1);
			vehicle1.getRentingUsers().add(user);
			user1.getRentedVehicles().add(vehicle1);
			vehicle1.getRentingUsers().add(user1);
		}

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		session.save(user);

		session.getTransaction().commit();
		session.close();

//	  Retrieve session
		session = sessionFactory.openSession();

		user = (User) session.get(User.class, 1);
		System.out.println("Retrieved user 1 (" + user.getUserName() + ")");
		Set<Address> addresses = user.getAddresses();

		System.out.println("\nUser has " + addresses.size() + " addresses:");
		for (Address address1 : user.getAddresses())
			System.out.println(address1.getStreet());

		vehicle = user.getVehicle();
		System.out.println("\nUser has vehicle: " + vehicle.getName());

		System.out.println("\nUser has " + user.getItems().size() + " items:");
		for (Item item1 : user.getItems())
			System.out.println(item1.getName());

		item = (Item) session.get(Item.class, 2);
		System.out.print("\nItem 2 (" + item.getName() + ")");
		System.out.println("'s owner is " + item.getOwner().getUserName());

		Collection<User> following = user.getFollowing();
		System.out.println("\nUser is following " + following.size() + " users");
		for (User user1 : following)
			System.out.println(user1.getUserName());

		Collection<Vehicle> rentedVehicles = user.getRentedVehicles();
		System.out.println("\nUser rented " + rentedVehicles.size() + " vehicles");
		for (Vehicle vehicle1 : rentedVehicles)
			System.out.println(vehicle1.getName());

		vehicle = (Vehicle) session.get(Vehicle.class, 4);
		Collection<User> rentingUsers = vehicle.getRentingUsers();
		System.out.println("\nVehicle 4 (" + vehicle.getName() + ") is rented by " + rentingUsers.size() + " users");
		for (User user1 : rentingUsers)
			System.out.println(user1.getUserName());


		session.close();
	}
}
