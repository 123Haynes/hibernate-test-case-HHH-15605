package org.hibernate.bugs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.bugs.ZonedDateTimeConverter.BERLIN;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import jakarta.persistence.TypedQuery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * This template demonstrates how to develop a test case for Hibernate ORM, using the Java Persistence API.
 */
public class JPAUnitTestCase {

	private EntityManagerFactory entityManagerFactory;

	@Before
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory( "templatePU" );
	}

	@After
	public void destroy() {
		entityManagerFactory.close();
	}

	// Entities are auto-discovered, so just add them anywhere on class-path
	// Add your tests, using standard JUnit.
	@Test
	public void hhh15605Test() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();

		ZonedDateTime today = ZonedDateTime.now(BERLIN);

		DemoEntity testEntity = new DemoEntity();
		testEntity.setIdentificationString("test");
		testEntity.setUpdated(ZonedDateTime.now(ZoneId.of("Europe/Berlin")).minusDays(14L));

		entityManager.persist(testEntity);

		TypedQuery<DemoEntity> query = entityManager.createQuery(
				"select d1_0 from DemoEntity d1_0 where d1_0.updated<?1", DemoEntity.class);
		DemoEntity demo = query.setParameter(1, today).getSingleResult();

		assertThat(demo.getUpdated()).isEqualTo(testEntity.getUpdated());

		// Do stuff...
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
