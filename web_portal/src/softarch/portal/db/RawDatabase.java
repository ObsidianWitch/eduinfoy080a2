package softarch.portal.db;

import java.util.List;

import softarch.portal.data.RawData;
import softarch.portal.data.RegularData;

public interface RawDatabase {
	public List<RawData> getRawData() throws DatabaseException;
	public RawData getRawData(int id) throws DatabaseException;
	public int getNumberOfRawRecords() throws DatabaseException;
	
	public void addRawData(RegularData regularData) throws DatabaseException;
	public void deleteRawData(RawData rd) throws DatabaseException;
	public void updateRawData(RawData rd) throws DatabaseException;
}
