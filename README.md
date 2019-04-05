# README
Download or clone repository and perform "gradle build" </br>
I had problem with SL4fj dependency on the classpath when trying to start app from command line.</br>
That is way you have to open project in the IDE and paste absolute path to the resource log json file to the Main method in Main.class: </br>
<code>
  dataLogService.processLogFile(args[0])
</code></br>
replace "args[0]" for absolute path to json file (String)</br>
The solution is mono threaded.
