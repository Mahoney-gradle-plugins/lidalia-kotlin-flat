package uk.org.lidalia.gradle.plugin.kotlinflat

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

@Suppress("unused")
class KotlinFlatPlugin : Plugin<Project> {

  override fun apply(project: Project) {
    val mainSrc = setOf("src")
    val mainSrcResources = setOf("src-resources")
    val testSrc = setOf("test")
    val testSrcResources = setOf("test-resources")

    project.pluginManager.withPlugin("java") {
      project.configure<JavaPluginExtension> {
        project.configure<SourceSetContainer> {
          named("main") {
            java.setSrcDirs(mainSrc)
            resources.setSrcDirs(mainSrcResources)
          }
          named("test") {
            java.setSrcDirs(testSrc)
            resources.setSrcDirs(testSrcResources)
          }
        }
      }
    }

    project.pluginManager.withPlugin("kotlin") {
      project.configure<KotlinProjectExtension> {
        sourceSets {
          named("main") {
            kotlin.setSrcDirs(mainSrc)
            resources.setSrcDirs(mainSrcResources)
          }
          named("test") {
            kotlin.setSrcDirs(testSrc)
            resources.setSrcDirs(testSrcResources)
          }
        }
      }
    }

    project.pluginManager.withPlugin("java-test-fixtures") {
      val testFixturesSrc = setOf("test-fixtures")

      project.configure<JavaPluginExtension> {
        project.configure<SourceSetContainer> {
          named("testFixtures") { java.setSrcDirs(testFixturesSrc) }
        }
      }
      project.configure<KotlinProjectExtension> {
        sourceSets {
          named("testFixtures") { kotlin.setSrcDirs(testFixturesSrc) }
        }
      }
    }
  }
}
