# LoudKicks
An in-memory Twitter-like console application, written in Scala, which runs on the Java Virtual Machine.

## Building and running

You'll need the [Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html) SE Development Kit installed - either version 7 or 8 will work.

The console application is built using [SBT](http://www.scala-sbt.org/download.html) - version 0.13.7 or later will work.

Once JDK and SBT are installed and set up, clone the project into a directory, cd into it and then create an executable, using

	$ git clone https://github.com/timothygordon32/loudkicks.git
	$ cd loudkicks
	$ sbt stage

(Note the first run of SBT can take some time to run from a fresh install as it downloads artifacts and resolves dependendies.)

Once the project is built, run it with

	$ ./target/universal/stage/bin/console

