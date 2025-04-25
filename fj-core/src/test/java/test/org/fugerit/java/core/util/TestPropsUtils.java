package test.org.fugerit.java.core.util;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.util.CheckDuplicationProperties;
import org.fugerit.java.core.util.PropsUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import test.org.fugerit.java.BasicTest;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
class TestPropsUtils extends BasicTest {

    @Test
    public void testDuplicatedCustom() throws IOException {
        CheckDuplicationProperties props = PropsUtils.findDuplicatedKeys( "file://src/main/resources/core/util/props_utils/duplicated_custom.properties" );
        Collection<Map.Entry<String,String>> duplicated = props.getDuplications();
        log.info( "duplicated count : {}, keys : {}", duplicated.size(), duplicated.stream().map( e -> e.getKey() ).collect( Collectors.toSet() ) );
        Assertions.assertNotNull( duplicated );
        CheckDuplicationProperties obj = (CheckDuplicationProperties) this.fullSerializationTest( props );
        Assertions.assertNotNull( obj );
        Assertions.assertEquals( props, obj );
        Assertions.assertEquals( props.hashCode(), obj.hashCode() );
        log.info( "equals : {}", props.equals( obj ) );
    }

    @Test
    public void testDuplicated1File() throws IOException {
        Collection<Map.Entry<String,String>> duplicated = PropsUtils.findDuplicatedKeys( "file://src/main/resources/core/util/props_utils/duplicated_1.properties" ).getDuplications();
        log.info( "test duplicated on property file : {}", duplicated );
        Assertions.assertEquals( 1, duplicated.size() );
    }

    @Test
    public void testDuplicated1Col() throws IOException {
        Collection<Map.Entry<String,String>> duplicated = PropsUtils.findDuplicatedKeys( "cl://core/util/props_utils/duplicated_1.properties" ).getDuplications();
        log.info( "test duplicated on property from class loader : {}", duplicated );
        Assertions.assertEquals( 1, duplicated.size() );
    }

    @Test
    public void testDuplicated0File() throws IOException {
        Collection<Map.Entry<String,String>> duplicated = PropsUtils.findDuplicatedKeys( "file://src/main/resources/core/util/props_utils/duplicated_0.properties" ).getDuplications();
        log.info( "test no duplicated on property file : {}", duplicated );
        Assertions.assertEquals( 0, duplicated.size() );
    }

    @Test
    public void testDuplicated0Col() throws IOException {
        Collection<Map.Entry<String,String>> duplicated = PropsUtils.findDuplicatedKeys( "cl://core/util/props_utils/duplicated_0.properties" ).getDuplications();
        log.info( "test no duplicated on property from class loader : {}", duplicated );
        Assertions.assertEquals( 0, duplicated.size() );
    }

    @Test
    public void testDuplicated1FileXml() throws IOException {
        Collection<Map.Entry<String,String>> duplicated = PropsUtils.findDuplicatedKeysFromXml( "file://src/main/resources/core/util/props_utils/duplicated_1.xml" ).getDuplications();
        log.info( "test xml duplicated on property file : {}", duplicated );
        Assertions.assertEquals( 1, duplicated.size() );
    }

    @Test
    public void testDuplicated1ColXml() throws IOException {
        Collection<Map.Entry<String,String>> duplicated = PropsUtils.findDuplicatedKeysFromXml( "cl://core/util/props_utils/duplicated_1.xml" ).getDuplications();
        log.info( "test xml duplicated on property from class loader : {}", duplicated );
        Assertions.assertEquals( 1, duplicated.size() );
    }

    @Test
    public void testDuplicated0FileXml() throws IOException {
        Collection<Map.Entry<String,String>> duplicated = PropsUtils.findDuplicatedKeysFromXml( "file://src/main/resources/core/util/props_utils/duplicated_0.xml" ).getDuplications();
        log.info( "test xml no duplicated on property file : {}", duplicated );
        Assertions.assertEquals( 0, duplicated.size() );
    }

    @Test
    public void testDuplicated0ColXml() throws IOException {
        Collection<Map.Entry<String,String>> duplicated = PropsUtils.findDuplicatedKeysFromXml( "cl://core/util/props_utils/duplicated_0.xml" ).getDuplications();
        log.info( "test xml no duplicated on property from class loader : {}", duplicated );
        Assertions.assertEquals( 0, duplicated.size() );
    }

}
