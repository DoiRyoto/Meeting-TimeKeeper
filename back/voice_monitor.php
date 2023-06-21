<?php
define("ADDRESS", "192.168.32.32");
define("USER", "pi");
define("PASSWORD", "xxxxxxxxxxxxxxxx");

$sconnection = ssh2_connect(ADDRESS, 22);
ssh2_auth_password($sconnection, USER, PASSWORD);
$command = "python"." "."/home/pi/build-in-app/volume.py";
$stdio_stream = ssh2_exec($sconnection, $command);
?>