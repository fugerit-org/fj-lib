package org.fugerit.java.core.util.mvn;

import java.util.Optional;

public class FJCoreMaven {

    private FJCoreMaven() {}

    public static final String FJ_CORE_GROUP_ID = "org.fugerit.java";

    public static final String FJ_CORE_ARTIFACT_ID = "fj-core";

    public static Optional<String> getFJCoreVersion() {
        return MavenProps.getPropertyOptional( FJ_CORE_GROUP_ID, FJ_CORE_ARTIFACT_ID, MavenProps.VERSION );
    }

}
