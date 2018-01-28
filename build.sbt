//<-- aggiunti dal progetto c05m1x/android-akka-sbt-scala per fare funzionare akka actor
import android.Keys._

enablePlugins(AndroidApp)
android.useSupportVectors
//--->

scalaVersion := "2.11.8"

versionCode := Some(1)
version := "0.1-SNAPSHOT"

instrumentTestRunner :=
  "android.support.test.runner.AndroidJUnitRunner"

platformTarget := "android-27"

javacOptions in Compile ++= "-source" :: "1.7" :: "-target" :: "1.7" :: Nil

libraryDependencies ++=
  "com.android.support" % "appcompat-v7" % "24.0.0" ::
  "com.android.support.test" % "runner" % "0.5" % "androidTest" ::
  "com.android.support.test.espresso" % "espresso-core" % "2.2.2" % "androidTest" ::
  Nil

//<-- aggiunti dal progetto c05m1x/android-akka-sbt-scala per fare funzionare akka actor
scalacOptions in Compile += "-feature"

updateCheck in Android := {} // disable update check

proguardOptions in Android ++= Seq("-dontobfuscate",
  "-dontoptimize",
  "-keepattributes Signature",
  "-printseeds target/seeds.txt",
  "-printusage target/usage.txt",
  "-dontwarn scala.collection.**", // required from Scala 2.11.4
  "-dontwarn com.unicorncoding.a2s2t.**", // this can be omitted if current Android Build target is android-16

  //  AKKA START
  //  see https://gist.github.com/bjornharrtell/3307987
  "-keep class akka.actor.LightArrayRevolverScheduler { *; }",
  "-keep class akka.actor.LocalActorRefProvider { *; }",
  "-keep class akka.actor.CreatorFunctionConsumer { *; }",
  "-keep class akka.actor.TypedCreatorFunctionConsumer { *; }",
  "-keep class akka.dispatch.BoundedDequeBasedMessageQueueSemantics { *; }",
  "-keep class akka.dispatch.UnboundedMessageQueueSemantics { *; }",
  "-keep class akka.dispatch.UnboundedDequeBasedMessageQueueSemantics { *; }",
  "-keep class akka.dispatch.DequeBasedMessageQueueSemantics { *; }",
  "-keep class akka.actor.LocalActorRefProvider$Guardian { *; }",
  "-keep class akka.actor.LocalActorRefProvider$SystemGuardian { *; }",
  "-keep class akka.dispatch.UnboundedMailbox { *; }",
  "-keep class akka.actor.DefaultSupervisorStrategy { *; }",
  "-keep class akka.event.Logging$LogExt { *; }",
  "-keep class com.typesafe.**",
  "-keep class akka.**",
  "-keep class scala.collection.immutable.StringLike {*;}",
  "-keepclasseswithmembers class * { public <init>(java.lang.String, akka.actor.ActorSystem$Settings, akka.event.EventStream, akka.actor.Scheduler, akka.actor.DynamicAccess);}",
  "-keepclasseswithmembers class * {public <init>(akka.actor.ExtendedActorSystem);}",
  "-keep class scala.collection.SeqLike {public protected *;}",
  "-dontwarn sun.misc.Unsafe"
)

libraryDependencies ++= Seq(
  "com.android.support" % "support-v4" % "19.1.0",
  "com.android.support" % "appcompat-v7" % "22.0.0",
  "com.typesafe.akka" %% "akka-actor" % "2.3.14")


run <<= run in Android
install <<= install in Android

// without this, @Config throws an exception,
unmanagedClasspath in Test ++= (bootClasspath in Android).value
//-->


