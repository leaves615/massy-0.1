/**
 * 
 */
package org.smarabbit.massy.model.provider;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.smarabbit.massy.model.persistent.PersistentException;
import org.smarabbit.massy.util.Asserts;

/**
 * 对应于{@link ResultSet}的DataProvider封装
 * @author huangkaihui
 *
 */
public class ResultSetProvider extends AbstractDataProvider<ResultSet> {
	
	/**
	 * 
	 */
	public ResultSetProvider(ResultSet source) {
		super(source);
	}
	
	public ResultSetProvider(ResultSet source, NamedMapper mapper) {
		super(source, mapper);
	}

	@Override
	public boolean doContains(String name) {
		Asserts.argumentNotNull(name, "name");
		try{
			ResultSetMetaData metaData = this.getSource().getMetaData();
			int size = metaData.getColumnCount();
			for (int i=0; i<size; i++){
				if (name.equals(metaData.getColumnLabel(i))){
					return true;
				}
			}
		}catch(SQLException e){
			throw new PersistentException(e.getMessage(),e);
		}
		return false;
	}

	@Override
	public String[] doGetNames() {
		try {
			ResultSetMetaData metaData = this.getSource().getMetaData();
			int size = metaData.getColumnCount();
			String[] result = new String[size];
			for (int i=0; i<size; i++){
				result[i] = metaData.getColumnLabel(i);
			}
			return result;
		} catch (SQLException e) {
			throw new PersistentException(e.getMessage(),e);
		}
	}

	@Override
	public Object doGetValue(String name) {
		Asserts.argumentNotNull(name, "name");
		try {
			return this.getSource().getObject(name);
		} catch (SQLException e) {
			throw new PersistentException(e.getMessage(),e);
		}
	}
	
}
