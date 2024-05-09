package test.org.fugerit.java.core.util;

import lombok.extern.slf4j.Slf4j;
import org.fugerit.java.core.util.PropsUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class TestPropsUtils {

    @Test
    public void testDuplicatedCustom() throws IOException {
        Collection<Map.Entry<String,String>> duplicated = PropsUtils.findDuplicatedKeys( "file://src/main/resources/core/util/props_utils/duplicated_custom.properties" ).getDuplications();
        log.info( "duplicated count : {}, keys : {}", duplicated.size(), duplicated.stream().map( e -> e.getKey() ).collect( Collectors.toSet() ) );
        Assert.assertNotNull( duplicated );
    }

    @Test
    public void testDuplicated1File() throws IOException {
        Collection<Map.Entry<String,String>> duplicated = PropsUtils.findDuplicatedKeys( "file://src/main/resources/core/util/props_utils/duplicated_1.properties" ).getDuplications();
        log.info( "test duplicated on property file : {}", duplicated );
        Assert.assertEquals( 1, duplicated.size() );
    }

    @Test
    public void testDuplicated1Col() throws IOException {
        Collection<Map.Entry<String,String>> duplicated = PropsUtils.findDuplicatedKeys( "cl://core/util/props_utils/duplicated_1.properties" ).getDuplications();
        log.info( "test duplicated on property from class loader : {}", duplicated );
        Assert.assertEquals( 1, duplicated.size() );
    }

    @Test
    public void testDuplicated0File() throws IOException {
        Collection<Map.Entry<String,String>> duplicated = PropsUtils.findDuplicatedKeys( "file://src/main/resources/core/util/props_utils/duplicated_0.properties" ).getDuplications();
        log.info( "test no duplicated on property file : {}", duplicated );
        Assert.assertEquals( 0, duplicated.size() );
    }

    @Test
    public void testDuplicated0Col() throws IOException {
        Collection<Map.Entry<String,String>> duplicated = PropsUtils.findDuplicatedKeys( "cl://core/util/props_utils/duplicated_0.properties" ).getDuplications();
        log.info( "test no duplicated on property from class loader : {}", duplicated );
        Assert.assertEquals( 0, duplicated.size() );
    }

    @Test
    public void testDuplicated1FileXml() throws IOException {
        Collection<Map.Entry<String,String>> duplicated = PropsUtils.findDuplicatedKeysFromXml( "file://src/main/resources/core/util/props_utils/duplicated_1.xml" ).getDuplications();
        log.info( "test xml duplicated on property file : {}", duplicated );
        Assert.assertEquals( 1, duplicated.size() );
    }

    @Test
    public void testDuplicated1ColXml() throws IOException {
        Collection<Map.Entry<String,String>> duplicated = PropsUtils.findDuplicatedKeysFromXml( "cl://core/util/props_utils/duplicated_1.xml" ).getDuplications();
        log.info( "test xml duplicated on property from class loader : {}", duplicated );
        Assert.assertEquals( 1, duplicated.size() );
    }

    @Test
    public void testDuplicated0FileXml() throws IOException {
        Collection<Map.Entry<String,String>> duplicated = PropsUtils.findDuplicatedKeysFromXml( "file://src/main/resources/core/util/props_utils/duplicated_0.xml" ).getDuplications();
        log.info( "test xml no duplicated on property file : {}", duplicated );
        Assert.assertEquals( 0, duplicated.size() );
    }

    @Test
    public void testDuplicated0ColXml() throws IOException {
        Collection<Map.Entry<String,String>> duplicated = PropsUtils.findDuplicatedKeysFromXml( "cl://core/util/props_utils/duplicated_0.xml" ).getDuplications();
        log.info( "test xml no duplicated on property from class loader : {}", duplicated );
        Assert.assertEquals( 0, duplicated.size() );
    }

}
