<?php
// Assuming you have a MySQL connection established
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

// Retrieve the username from the GET request
$username = $_GET['username'];

// Use a prepared statement to prevent SQL injection
$sql = "SELECT full_name FROM users WHERE username = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("s", $username);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    // Output the full name
    $row = $result->fetch_assoc();
    echo $row['full_name'];
} else {
    // User not found
    echo "User not found";
}

$stmt->close();
$conn->close();
?>
