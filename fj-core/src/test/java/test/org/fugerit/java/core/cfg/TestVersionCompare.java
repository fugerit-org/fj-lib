package test.org.fugerit.java.core.cfg;

import org.fugerit.java.core.cfg.VersionCompare;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestVersionCompare {

    // Version constants
    private static final String V_1_0_0 = "1.0.0";
    private static final String V_2_0_0 = "2.0.0";
    private static final String V_1_9_9 = "1.9.9";
    private static final String V_3_0_0 = "3.0.0";
    private static final String V_2_5_6 = "2.5.6";
    private static final String V_1_2_0 = "1.2.0";
    private static final String V_1_1_9 = "1.1.9";
    private static final String V_1_3 = "1.3";
    private static final String V_1_2_9 = "1.2.9";
    private static final String V_1_0_1 = "1.0.1";
    private static final String V_1_0_5 = "1.0.5";
    private static final String V_1_0_4 = "1.0.4";
    private static final String V_1_0 = "1.0";
    private static final String V_1_0_0_SNAPSHOT = "1.0.0-SNAPSHOT";
    private static final String V_1_0_1_RC1 = "1.0.1-RC1";
    private static final String V_1_A_0 = "1.a.0";
    private static final String V_X_Y_Z = "x.y.z";

    @Test
    void testEqualVersions() {
        assertEquals(0, VersionCompare.compare(V_1_0_0, V_1_0_0));
        assertFalse(VersionCompare.isGreaterThan(V_1_0_0, V_1_0_0));
    }

    @Test
    void testGreaterMajorVersion() {
        assertTrue(VersionCompare.isGreaterThan(V_2_0_0, V_1_9_9));
        assertEquals(1, VersionCompare.compare(V_3_0_0, V_2_5_6));
    }

    @Test
    void testGreaterMinorVersion() {
        assertTrue(VersionCompare.isGreaterThan(V_1_2_0, V_1_1_9));
        assertEquals(1, VersionCompare.compare(V_1_3, V_1_2_9));
    }

    @Test
    void testGreaterPatchVersion() {
        assertTrue(VersionCompare.isGreaterThan(V_1_0_1, V_1_0_0));
        assertEquals(1, VersionCompare.compare(V_1_0_5, V_1_0_4));
    }

    @Test
    void testVersionWithDifferentLength() {
        assertEquals(0, VersionCompare.compare(V_1_0, V_1_0_0));
        assertTrue(VersionCompare.isGreaterThan(V_1_0_1, V_1_0));
        assertFalse(VersionCompare.isGreaterThan(V_1_0, V_1_0_1));
    }

    @Test
    void testVersionWithQualifierSuffix() {
        assertEquals(0, VersionCompare.compare(V_1_0_0_SNAPSHOT, V_1_0_0));
        assertTrue(VersionCompare.isGreaterThan(V_1_0_1_RC1, V_1_0_0));
    }

    @Test
    void testInvalidVersionShouldThrowException() {
        assertThrows(NumberFormatException.class, () -> VersionCompare.compare(V_1_A_0, V_1_0_0));
        assertThrows(NumberFormatException.class, () -> VersionCompare.compare(V_1_0, V_X_Y_Z));
    }

}
