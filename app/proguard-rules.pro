# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# ================================================================================================
# PROGUARD RULES FOR ROKIU APP
# ================================================================================================

# ================================================================================================
# GENERAL ANDROID RULES
# ================================================================================================
-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses
-keepattributes EnclosingMethod
-keepattributes SourceFile, LineNumberTable
-keepattributes Exceptions

# Keep Android components
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.view.View

# Keep native methods
-keepclasseswithmembernames class * {
    native <methods>;
}

# Keep custom views
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# Keep enums
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep Parcelables
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep Serializables
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Keep R class
-keepclassmembers class **.R$* {
    public static <fields>;
}

# ================================================================================================
# XML PULL PARSER FIX (Tu error principal)
# ================================================================================================
-dontwarn org.xmlpull.v1.**
-dontwarn xpp3.**
-keep class org.xmlpull.v1.** { *; }
-keep class xpp3.** { *; }
-dontnote android.content.res.XmlResourceParser

# SimpleXML
-keep class org.simpleframework.xml.** { *; }
-keepclassmembers class * {
    @org.simpleframework.xml.* <fields>;
    @org.simpleframework.xml.* <init>(...);
}
-keepattributes ElementList, Element, Root, Convert

# ================================================================================================
# RETROFIT 2
# ================================================================================================
# Retrofit
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepattributes AnnotationDefault

-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# Retrofit with Kotlin Coroutines
-if interface * { @retrofit2.http.* public *** *(...); }
-keep,allowoptimization,allowshrinking,allowobfuscation class <3>

# Keep generic signatures for Retrofit
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# ================================================================================================
# OKHTTP 3
# ================================================================================================
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# ================================================================================================
# MOSHI (JSON Parser)
# ================================================================================================
-keepclasseswithmembers class * {
    @com.squareup.moshi.* <methods>;
}

-keep @com.squareup.moshi.JsonQualifier @interface *

-keepclassmembers class kotlin.Metadata {
    public <methods>;
}

-keepclassmembers class * {
    @com.squareup.moshi.FromJson <methods>;
    @com.squareup.moshi.ToJson <methods>;
}

-keepnames @kotlin.Metadata class com.levi.rokiu.**
-keep class com.levi.rokiu.** { *; }
-keepclassmembers class com.levi.rokiu.** { *; }

# ================================================================================================
# DAGGER HILT
# ================================================================================================
-dontwarn dagger.hilt.**
-dontwarn com.google.errorprone.annotations.**

# Keep Hilt generated classes
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.managers.ViewComponentManager$FragmentContextWrapper { *; }

# Keep @Inject constructors
-keepclasseswithmembernames class * {
    @javax.inject.Inject <init>(...);
}

# Keep Hilt ViewModels
-keepnames @dagger.hilt.android.lifecycle.HiltViewModel class * extends androidx.lifecycle.ViewModel

# Keep Hilt modules
-keep @dagger.Module class *
-keep @dagger.hilt.InstallIn class *

# Keep generated Hilt classes
-keep class **_HiltComponents { *; }
-keep class **_HiltModules* { *; }
-keep class **_Factory { *; }
-keep class **_MembersInjector { *; }

# ================================================================================================
# ANDROIDX NAVIGATION
# ================================================================================================
-keepnames class androidx.navigation.fragment.NavHostFragment
-keep class * extends androidx.fragment.app.Fragment {}
-keepclassmembers class * extends androidx.fragment.app.Fragment {
    public <init>(...);
}

# Keep SafeArgs generated classes
-keep class **.*Args { *; }
-keep class **.*Directions { *; }

# ================================================================================================
# GLIDE
# ================================================================================================
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
    <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
    *** rewind();
}

# ================================================================================================
# ANDROIDSVG
# ================================================================================================
-keep class com.caverock.androidsvg.** { *; }
-dontwarn com.caverock.androidsvg.**

# ================================================================================================
# KOTLIN
# ================================================================================================
-keep class kotlin.** { *; }
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}

# Kotlin Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}

# ================================================================================================
# VIEW BINDING
# ================================================================================================
-keep class com.levi.rokiu.databinding.** { *; }
-keep class * extends androidx.viewbinding.ViewBinding {
    public static *** bind(android.view.View);
    public static *** inflate(android.view.LayoutInflater);
}

# ================================================================================================
# YOUR APP MODELS (Ajusta según tu estructura)
# ================================================================================================
# Si tienes modelos de datos, agrégalos aquí
-keep class com.levi.rokiu.data.models.** { *; }
-keep class com.levi.rokiu.domain.models.** { *; }

# Si usas data classes para API responses
-keepclassmembers class com.levi.rokiu.data.remote.dto.** {
    <fields>;
    <init>(...);
}

# ================================================================================================
# COMMON WARNINGS TO SUPPRESS
# ================================================================================================
-dontwarn java.lang.instrument.ClassFileTransformer
-dontwarn sun.misc.SignalHandler
-dontwarn java.lang.management.**
-dontwarn javax.naming.**
-dontwarn org.slf4j.**
-dontwarn org.apache.log4j.**

# ================================================================================================
# OPTIMIZATION FLAGS
# ================================================================================================
# Remove logging in release
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}

# Remove debug code
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    public static void check*(...);
    public static void throw*(...);
}

# ================================================================================================
# DEBUGGING (Comentar en producción para mejor ofuscación)
# ================================================================================================
# Mantener nombres de clase para stack traces más legibles
# -keepnames class ** { *; }

# Mantener números de línea para crashes
-keepattributes SourceFile,LineNumberTable

# Renombrar el archivo SourceFile a algo genérico
-renamesourcefileattribute SourceFile