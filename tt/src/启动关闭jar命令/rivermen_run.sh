exec -a "rivermen" $JAVA_HOME/bin/java -server -verbose:gc -Xmx256m -XX:MaxDirectMemorySize=256M -jar Rivermen-0.0.1-SNAPSHOT.jar >> stdout.log 2>&1 &
echo $! > RivermenServer.pid
