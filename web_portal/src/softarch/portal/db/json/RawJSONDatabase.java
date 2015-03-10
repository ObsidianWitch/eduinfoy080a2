package softarch.portal.db.json;

import java.util.List;
import java.util.Properties;

import softarch.portal.data.RawData;
import softarch.portal.data.RegularData;
import softarch.portal.db.DatabaseException;
import softarch.portal.db.RawDatabase;

public class RawJSONDatabase extends JSONDatabase implements RawDatabase {

	public RawJSONDatabase(Properties properties) {
		super(properties);
	}

	public List<RawData> getRawData() throws DatabaseException {
		throw new DatabaseException("not implemented");
	}

	public RawData getRawData(int id) throws DatabaseException {
		throw new DatabaseException("not implemented");
	}

	public int getNumberOfRawRecords() throws DatabaseException {
		throw new DatabaseException("not implemented");
	}

	public void addRawData(RegularData regularData) throws DatabaseException {
		throw new DatabaseException("not implemented");
	}

	public void deleteRawData(RawData rd) throws DatabaseException {
		throw new DatabaseException("not implemented");
	}

	public void updateRawData(RawData rd) throws DatabaseException {
		throw new DatabaseException("not implemented");
	}

}
