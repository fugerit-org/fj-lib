package test.org.fugerit.java.core.db.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.fugerit.java.core.db.dao.RSExtractor;
import org.fugerit.java.core.db.daogen.BasicRSExtractor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ModelUser implements Serializable {

	private static final long serialVersionUID = -8853895889949181548L;

	public static final RSExtractor<ModelUser> RSE = new ModeUserRSE();
	
	@Getter @Setter private BigDecimal id;
	
	@Getter @Setter private String username;
	
}

class ModeUserRSE extends BasicRSExtractor<ModelUser> {
	private static final long serialVersionUID = 3313000515747412218L;
	@Override
	public ModelUser extractNext(ResultSet rs) throws SQLException {
		ModelUser user = new ModelUser();
		user.setId( rs.getBigDecimal( "id" ) );
		user.setUsername( "username" );
		return user;
	}	
}
