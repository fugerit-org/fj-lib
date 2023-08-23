package org.fugerit.java.core.util.filterchain;

import org.fugerit.java.core.lang.helpers.ClassHelper;
import org.fugerit.java.core.lang.helpers.StringUtils;

public class MiniFilterLoadSafe extends MiniFilterBase {

	private MiniFilter wrapped;
	
	@Override
	public void config(String key, String description, Integer defaultBehaviour) {
		super.config(key, description, defaultBehaviour);
		if ( StringUtils.isEmpty( this.getParam01() ) ) {
			logger.info( "param01 must be set for {}", this.getClass().getSimpleName() );
		} else {
			try {
				this.wrapped = (MiniFilter) ClassHelper.newInstance( this.getParam01() );
				this.wrapped.config(key, description, defaultBehaviour);
				logger.info( "Load ok for {}", this.getParam01());
			} catch (Exception e) {
				logger.warn( "Load failed for {}", this.getParam01());
			}
		}
	}

	@Override
	public int apply(MiniFilterContext context, MiniFilterData data) throws Exception {
		int exit = this.getDefaultBehaviour();
		if ( this.wrapped != null ) {
			exit = this.wrapped.apply(context, data);
		}
		return exit;
	}

}
