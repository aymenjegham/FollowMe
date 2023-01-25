plugins {
    id("java-library")
    id("kotlin")
    id("kotlin-kapt")
}
dependencies {
    implementation("javax.inject:javax.inject:1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("androidx.paging:paging-common:3.0.1")


    implementation("androidx.annotation:annotation:1.5.0")
    implementation("androidx.arch.core:core-common:2.1.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.21")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.6.4")

    testImplementation("junit:junit:4.13.2")


}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}