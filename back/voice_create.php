<?php
define("ADDRESS", "192.168.32.32");
define("USER", "pi");
define("PASSWORD", "xxxxxxxxxxxxxxxx");

if(!is_null($_GET["mode"])){
$sconnection = ssh2_connect(ADDRESS, 22);
ssh2_auth_password($sconnection, USER, PASSWORD);
$command = "python"." "."/home/pi/build-in-app/VoiceCreate.py"." ".$_GET["mode"];
$stdio_stream = ssh2_exec($sconnection, $command);

if($_GET["mode"] == "start"){
    echo "processing\n";
} else if($_GET["mode"] == "end" || $_GET["mode"] == "end-force"){
    echo "standby\n";
}
}
?>