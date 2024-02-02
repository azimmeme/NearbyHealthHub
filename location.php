<?php
$servername = "localhost";
$username = "root";
$password = "root";
$dbname = "mymap";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Get latitude, longitude, address, full name, user agent, and date/time from POST request
$latitude = isset($_POST['lat']) ? $_POST['lat'] : '';
$longitude = isset($_POST['lng']) ? $_POST['lng'] : '';
$address = isset($_POST['address']) ? $_POST['address'] : '';
$fullName = isset($_POST['fullname']) ? $_POST['fullname'] : '';
$userAgent = isset($_POST['user_agent']) ? $_POST['user_agent'] : '';
$dateTime = isset($_POST['datetime']) ? $_POST['datetime'] : '';

// Check if latitude, longitude, address, full name, user agent, and date/time are not empty
if (empty($latitude) || empty($longitude) || empty($address) || empty($fullName) || empty($userAgent) || empty($dateTime)) {
    echo "Latitude, longitude, address, full name, user agent, or date/time is empty";
    exit;
}

// Insert data into the 'locations' table
$sql = "INSERT INTO locations (latitude, longitude, address, fullname, user_agent, datetime) VALUES ('$latitude', '$longitude', '$address', '$fullName', '$userAgent', '$dateTime')";

if ($conn->query($sql) === TRUE) {
    echo "success";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}

$conn->close();
?>
