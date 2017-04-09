# Jigsaw
Demo to explain jigsaw for sq-dev

## What is Jigsaw
The primary goals of this Project are to:

* Improve security and maintainability of Java SE Platform Implementations and JDK (mostly jdk)
* Make more easily scalable down to small computing devices; (embedded device, containers)
* Better performances (less classes to take care of, no runtime magic);
* Make it easier for developers to construct and maintain libraries and large applications.

http://openjdk.java.net/projects/jigsaw/

Note: .Net does it... but oracle didn't copy it, they were inspired :)

### Ex1 
One of the problem is that people aim to use stuff 
that are not part of the API but part of the internal implementation.

```java
import org.springframework.util.Assert;

Assert.notNull(obj, "Object must not be null");
```
If you read javadoc of the class: 
```
Mainly for internal use within the framework;
consider Apache's Commons Lang for a more comprehensive 
suite of String utilities.
```

* sun.misc.Unsafe
* sun.* removed, hidden
* *.internal.*

### Ex2
Classpath is flat where as in your dependencies it's tree.

Which version of slf4j is used at runtime ?
```
slf4j-1.7.jar   Logger with 10 public methods
slf4j-1.6.jar   Logger with 9 public methods
myapp.jar       Uses the new method
```

### How they did it ?
rt.jar & tools.jar -> replaced by [modular jdk]

By default a public class is not visible outside the jar.
You must specify it.
```
// module-info.java
module my.module.name {
    requires    jdk.httpserver;
    exports     my.public.package.name;
}
```

### What does it means for us ?
300 image

    Ready your breakfast and eat hearty... For tonight, we dine in hell! 

* we need to wait frameworks to move to java9 first
* upgrade or remove libraries from our stack (core-bl-commons)
    * clean upgrade -> costly
    * dirty upgrade, you will pay it later
* automate builds with java 9 to know the cost of migration (jenkins plugin)


## Example Web Service
```bash
localhost:8080/ping
```

### Build and run with java 8
```bash
./bin/build-j8.sh && ./bin/run-j8.sh
```

### Version
```bash
./bin/version.sh
```

### How the app
```bash
./bin/read-items.sh
./bin/add-new-items.sh
./bin/read-items.sh
```

### Build with java 8 run with java 9
```bash
./bin/build-j8.sh && ./bin/run-j9.sh

Error: A JNI error has occurred, please check your installation and try again
Exception in thread "main" java.lang.NoClassDefFoundError: com/sun/net/httpserver/HttpHandler
	at java.base/java.lang.Class.getDeclaredMethods0(Native Method)
	at java.base/java.lang.Class.privateGetDeclaredMethods(Class.java:3113)
	at java.base/java.lang.Class.getMethodsRecursive(Class.java:3254)
	at java.base/java.lang.Class.getMethod0(Class.java:3240)
	at java.base/java.lang.Class.getMethod(Class.java:2058)
	at java.base/sun.launcher.LauncherHelper.validateMainClass(LauncherHelper.java:686)
	at java.base/sun.launcher.LauncherHelper.checkAndLoadMain(LauncherHelper.java:544)
Caused by: java.lang.ClassNotFoundException: com.sun.net.httpserver.HttpHandler
	at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:552)
	at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:186)
	at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:476)
	... 7 more
```

# Tools to help migration

## jdeps (since java8)
```bash
jdeps -s .
. -> java.base
. -> jdk.httpserver
```

## retro-compatibility flags on JVM
Sometimes when you cannot change the jar you can force it with some flags (done for simplify transition betweem java8 - java9)
```
// removed in java 10
--add-reads
--add-exports
--add-modules
...
--permit-illegal-access
```

## Tests...
Well, way more complex but possible. Hopefully your build tool, and your IDE will do it magically for you.
This will force maintainer to make a better architecture (ex: junit5)

# Resources
* Alexis Hassler https://github.com/hasalex
* https://fr.slideshare.net/sewatech/lausannejug-2017-jigsaw
* https://docs.oracle.com/javase/9/migrate/toc.htm
* https://docs.oracle.com/javase/9/index.html
