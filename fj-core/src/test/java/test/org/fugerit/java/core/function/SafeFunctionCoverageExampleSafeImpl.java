package test.org.fugerit.java.core.function;

import java.util.function.Consumer;

import org.fugerit.java.core.cfg.ConfigRuntimeException;
import org.fugerit.java.core.function.SafeFunction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SafeFunctionCoverageExampleSafeImpl implements SafeFunctionCoverageExample {
	
	private static final Consumer<Exception> EX_HADLER = e -> { throw new ConfigRuntimeException( e ); };
	
	@Override
	public void error(SafeFunctionCoverageExampleInput input) {
		SafeFunction.apply( () -> log.error( "value -> {}", input.getValue() ) , EX_HADLER );
	}

	@Override
	public void warn(SafeFunctionCoverageExampleInput input) {
		SafeFunction.apply( () -> log.warn( "value -> {}", input.getValue() ) , EX_HADLER );
	}

	@Override
	public void info(SafeFunctionCoverageExampleInput input) {
		SafeFunction.apply( () -> log.info( "value -> {}", input.getValue() ) , EX_HADLER );
	}

}
