package test.org.fugerit.java.core.util.filterchain;

import org.fugerit.java.core.util.filterchain.MiniFilter;
import org.fugerit.java.core.util.filterchain.MiniFilterBase;
import org.fugerit.java.core.util.filterchain.MiniFilterContext;
import org.fugerit.java.core.util.filterchain.MiniFilterData;

public class SkipStep extends MiniFilterBase implements MiniFilter {

	@Override
	public int apply(MiniFilterContext context, MiniFilterData data) throws Exception {
		int exit = SKIP;
		logger.info( "apply() MiniFilter res:"+exit+" id:"+this.getKey()+" - "+this.getDescription()+" - "+this.getClass().getSimpleName() );
		return exit;
	}

	
	
}
