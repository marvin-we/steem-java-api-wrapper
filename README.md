![SteemJ Logo](https://camo.githubusercontent.com/a325dd7ebceee15b8ca3fd57383c4e8330cc0425/687474703a2f2f696d6775722e636f6d2f784a4c514e31752e706e67)

This project allows you to easily access data stored in the Steem blockchain. The project has been initialized by <a href="https://steemit.com/@dez1337">dez1337 on steemit.com</a>.

#### The latest 0.4.x STABLE can be obtained via jitpack.io:

[![](https://jitpack.io/v/marvin-we/steem-java-api-wrapper.svg)](https://jitpack.io/#marvin-we/steem-java-api-wrapper/0.4.6-20180926-01PRE/steemj-core)

Example below is for latest 0.4.x. Visit https://jitpack.io/#marvin-we/steem-java-api-wrapper/0.4.6-20180926-01PRE/steemj-core to get a list of available builds.

## Gradle
```Gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
    dependencies {
	        compile 'com.github.marvin-we.steem-java-api-wrapper:steemj-core:0.4.x-SNAPSHOT'
	}
```

## Maven
File: <i>pom.xml</i>
```Xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
    ...
	<dependency>
	    <groupId>com.github.marvin-we.steem-java-api-wrapper</groupId>
	    <artifactId>steemj-core</artifactId>
	    <version>0.4.x-SNAPSHOT</version>
	</dependency>
```

# Full Documentation
- Please have a look at the [Wiki](https://github.com/marvin-we/steem-java-api-wrapper/wiki) for full documentation, examples, operational details and other information.
- Or have a look at the JavaDoc.

# Communication
- Please contact me at the [Discord Java Channel](https://discord.gg/fsJjr3Q)
- Or directly on [Steemit.com](https://steemit.com/@dez1337)
- Beside that you can also create an [Issue](https://github.com/marvin-we/steem-java-api-wrapper/issues) here at GitHub.

# Contributors
- [philip-healy](https://github.com/philip-healy) took care of the "simplified operations".
- [ray66rus](https://steemit.com/@ray66rus) is testing SteemJ for Android and provided a lot of improvements.
- [inertia](https://steemit.com/@inertia) provided a bunch of unit tests to this project.
- An article from [Kyle](https://steemit.com/@klye) has been used to improve the documentation of the methods.
- The [guide](https://steemit.com/steem/@xeroc/steem-transaction-signing-in-a-nutshell) from [xeroc](https://steemit.com/@xeroc) shows how to create and sign transactions.

# Binaries
SteemJ binaries are pushed into the maven central repository and can be integrated with a bunch of build management tools like Maven.

Please have a look at the [Wiki](https://github.com/marvin-we/steem-java-api-wrapper/wiki/How-to-add-SteemJ-to-your-project) to find examples for Maven, Ivy, Gradle and others.

# How to build the project
The project requires Maven and Java to be installed on your machine. It can be build with the default maven command:

>mvn clean package

The resulting JAR can be found in the target directory as usual. Please notice that some integration tests require different private keys. Please provide them as -D parameter or use the properties file ( *src/test/resources/accountDetailsUsedDuringTests.properties* ) to define them. If you do not want to execute tests at all add *"-Dmaven.test.skip"* to the mvn call which skips the test execution during the build.

# Bugs and Feedback
For bugs or feature requests please create a [GitHub Issue](https://github.com/marvin-we/steem-java-api-wrapper/issues). 

For general discussions or questions you can also:
* Post your questions in the [Discord Java Channel](https://discord.gg/9jZQHv)
* Reply to one of the SteemJ update posts on [Steemit.com](https://steemit.com/@dez1337)
* Contact me on [steemit.chat](https://steemit.chat/channel/dev)

# Example
The [sample module](https://github.com/marvin-we/steem-java-api-wrapper/tree/master/sample) of the SteemJ project provides showcases for the most common acitivies and operations users want to perform. 

Beside that you can find a lot of snippets and examples in the different [Wiki sections](https://github.com/marvin-we/steem-java-api-wrapper/wiki).  
