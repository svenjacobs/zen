rootProject.name = "zen"

include("common",
        "core",
        "android")

project(":common").name = "zen-common"
project(":core").name = "zen-core"
project(":android").name = "zen-android"
