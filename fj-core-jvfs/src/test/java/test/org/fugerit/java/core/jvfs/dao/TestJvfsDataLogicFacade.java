package test.org.fugerit.java.core.jvfs.dao;

import org.fugerit.java.core.db.dao.DAOException;
import org.fugerit.java.core.jvfs.db.daogen.def.facade.EntityDbJvfsFileFacade;
import org.fugerit.java.core.jvfs.db.daogen.facade.impl.JvfsDataLogicFacade;
import org.junit.Assert;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestJvfsDataLogicFacade {

	@Test
	public void testJvfsDataLogicFacade() throws DAOException {
		JvfsDataLogicFacade facade = new JvfsDataLogicFacade() {
			private static final long serialVersionUID = -2886569273678640433L;
			@Override
			public EntityDbJvfsFileFacade getEntityDbJvfsFileFacade() throws DAOException {
				this.setEntityDbJvfsFileFacade( null );
				return super.getEntityDbJvfsFileFacade();
			}
		};
		log.info( "facade {}", facade );
		log.info( "facade {}", JvfsDataLogicFacade.getInstance() );
		log.info( "facade {}", JvfsDataLogicFacade.getInstance().newInstanceWithTableName( "test" ) );
		log.info( "facade {}", JvfsDataLogicFacade.getInstance().newInstanceWithTablePrefix( "prefix" ) );
		Assert.assertNull( facade.getEntityDbJvfsFileFacade() );
	}
	
}
