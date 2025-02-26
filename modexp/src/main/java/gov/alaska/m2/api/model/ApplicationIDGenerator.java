package gov.alaska.m2.api.model;

import java.sql.ResultSet;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

// Generates IDs for Application Numbers. 'T' + XXXXXXXX
public class ApplicationIDGenerator implements IdentifierGenerator {

	@Override
	public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

		String prefix = "T";

		return session.doReturningWork(connection -> {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("Select count(app_num) as Id from ar_application_for_aid");

			if (rs.next()) {
				int id = rs.getInt(1) + 1;
				String generatedId = prefix + String.format("%08d", id);
				return generatedId;
			}
			return null;

		});
	}
}
