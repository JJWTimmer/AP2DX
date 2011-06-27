cd jar

cd coordinator
start "Coordinator" start.bat
cd ..

cd motor
start "Motor" start.bat
cd ..

cd reflex
start "Reflex" start.bat
cd ..

cd planner
start "Planner" start.bat
cd ..

cd mapper
start "mapper" start.bat
cd ..

cd sensor
start "Sensor" start.bat
cd ..\..
