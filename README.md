This is a command line Spring Boot application. It is built using gradle which is part of the source code ditribution.

To build it execute this command from the project root folder (I am using MacOS, so UNIX syntax):

```
./gradlew
```

It will build an uber jar in `build/libs` folder.

To run the application execute this command:

```
java -jar knightSequences.jar [<sequence length>]
```

where `<sequence length>` is the desired sequence length. If you don't put an argument, it will be defaulted to 10. I assume that java 8 is installed on the computer and the `java` executable is in the PATH. Also, I assume that your current folder contains the `knightSequence.jar` file. If the the jar is located in some other folder, you need to provide path to it.

The program is written in Scala and built by Gradle as I mentioned before. All required libraries (Spring Boot and Scala) are loaded from central maven repository by Gradle and packaged inside uber jar by the Spring Boot plugin.

I used functional patterns in the code where it was critical. The program can be run for quite large sequence length. I ran it on MacBook Pro for length of 1000. The resulting number took several lines on my screen. The entire process executed in under a second. So, I decided that it is unnecessary to further refine the code to remove non functional loops.# knightSequences
