package test.org.fugerit.java.core.function;

import org.fugerit.java.core.cfg.ConfigRuntimeException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SafeFunctionCoverageExampleClassicImpl implements SafeFunctionCoverageExample {
	
	@Override
	public void error(SafeFunctionCoverageExampleInput input) {
		try {
			log.error( "value -> {}", input.getValue() );
		} catch (Exception e) {
			throw new ConfigRuntimeException( e );
		}
	}

	@Override
	public void warn(SafeFunctionCoverageExampleInput input) {
		try {
			log.warn( "value -> {}", input.getValue() );
		} catch (Exception e) {
			throw new ConfigRuntimeException( e );
		}
	}

	@Override
	public void info(SafeFunctionCoverageExampleInput input) {
		try {
			log.info( "value -> {}", input.getValue() );
		} catch (Exception e) {
			throw new ConfigRuntimeException( e );
		}
	}

}
