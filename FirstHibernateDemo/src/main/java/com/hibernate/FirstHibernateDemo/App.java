package com.hibernate.FirstHibernateDemo;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class App {

	public static void update(SessionFactory sessionFactory) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		tx.begin();
		Product product = session.load(Product.class, 1L);
		product.setName("Tables");
		tx.commit();
		session.close();
	}

	public static void delete(SessionFactory sessionFactory) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		tx.begin();
		Product product = session.load(Product.class, 1L);
		session.delete(product);
		tx.commit();
		session.close();
	}

	public static void insert(SessionFactory sessionFactory) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		tx.begin();
		Product product = new Product("Chairs", 5000);
		long productId = (Long) session.save(product);
		tx.commit();
		System.out.println("Created product id:" + productId);
	}

	public static void getAllProductsUsingSQL(SessionFactory sessionFactory) {
		Session session = sessionFactory.openSession();
		SQLQuery query = session.createSQLQuery("INSERT INTO PRODUCT_MASTER(P_ID,NAME,COST) VALUES(2,'Lamps',2000)");
		int update = query.executeUpdate();
		if (update == 0 || update == 1) {
			System.out.println(update + " row affected");
		} else
			System.out.println(update + " rows affected");
		System.out.println("Inserted Records Successfully");
		System.out.println("Successfully updated");
		SQLQuery query1 = session.createSQLQuery("SELECT * FROM PRODUCT_MASTER");
		@SuppressWarnings("unchecked")
		List<Object[]> productList = query1.list();
		for (Object[] obj : productList) {
			System.out.println(obj[0] + " " + obj[1] + " " + obj[2]);
		}
		session.close();
	}

	public static List<Product> getAllProducts(SessionFactory sessionFactory) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Product");
		@SuppressWarnings("unchecked")
		List<Product> productList = query.list();
		session.close();
		return productList;
	}

	public static void main(String[] args) throws Exception {
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		SessionFactory sessionFactory = configuration.buildSessionFactory();
		insert(sessionFactory);
		System.out.println(getAllProducts(sessionFactory));
		getAllProductsUsingSQL(sessionFactory);
	}
}