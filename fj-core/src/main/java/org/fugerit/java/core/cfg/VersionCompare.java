package org.fugerit.java.core.cfg;

/**
 * Utility class for comparing version strings in the format "MAJOR.MINOR.PATCH",
 * optionally including qualifiers (e.g. "-SNAPSHOT"). Provides a compare method
 * that handles versions with different lengths and a convenience method for
 * checking if one version is greater than another.
 *
 * <p>This implementation follows standard Semantic Versioning comparison rules:
 * numeric comparison of components, missing components treated as zero.</p>
 *
 * @author Your Name
 * @since 1.0
 */
public class VersionCompare {

    private VersionCompare() {
        // Prevent instantiation
    }

    /**
     * Extracts the numeric portion of a version string component, ignoring any
     * qualifier suffix that begins with a hyphen.
     *
     * @param part the version component, e.g. "1", "2-SNAPSHOT"
     * @return the integer value of the numeric portion
     * @throws NumberFormatException if the numeric portion is not a valid integer
     */
    private static int convertVersionPart(String part) {
        String numeric = part.split("-", 2)[0];
        return Integer.parseInt(numeric);
    }

    /**
     * Compares two version strings in "MAJOR.MINOR.PATCH(...)" format.
     * Missing components are treated as zero.
     *
     * @param v1 the first version string to compare
     * @param v2 the second version string to compare
     * @return a negative integer if {@code v1 < v2}, zero if equal, or a positive
     *         integer if {@code v1 > v2}
     * @throws NumberFormatException if any component is not a valid integer
     *
     * @since 1.0
     */
    public static int compare(String v1, String v2) {
        String[] parts1 = v1.split("\\.");
        String[] parts2 = v2.split("\\.");
        int length = Math.max(parts1.length, parts2.length);

        for (int i = 0; i < length; i++) {
            int p1 = i < parts1.length ? convertVersionPart(parts1[i]) : 0;
            int p2 = i < parts2.length ? convertVersionPart(parts2[i]) : 0;
            if (p1 != p2) {
                return Integer.compare(p1, p2);
            }
        }
        return 0;
    }

    /**
     * Determines if the first version string represents a version strictly greater
     * than the second.
     *
     * @param v1 the first version string
     * @param v2 the second version string
     * @return {@code true} if {@code v1} is greater than {@code v2}, {@code false}
     *         otherwise (including equality)
     *
     * @since 1.0
     */
    public static boolean isGreaterThan(String v1, String v2) {
        return compare(v1, v2) > 0;
    }

}
