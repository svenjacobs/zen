rootProject.name = "zen"

include(
    "common",
    "core",
    "android",
    "di-support-katana",
    "android-example"
)

project(":common").name = "zen-common"
project(":core").name = "zen-core"
project(":android").name = "zen-android"
project(":di-support-katana").name = "zen-di-support-katana"
