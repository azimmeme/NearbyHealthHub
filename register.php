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

// Get the POST data
$fullName = $_POST['fullName'];
$username = $_POST['username'];
$password = $_POST['password'];

// Hash the password before storing it
$hashedPassword = password_hash($password, PASSWORD_DEFAULT);

// Prepare and bind the SQL statement
$stmt = $conn->prepare("INSERT INTO users (full_name, username, password) VALUES (?, ?, ?)");
$stmt->bind_param("sss", $fullName, $username, $hashedPassword);

// Set parameters and execute
$stmt->execute();

if ($stmt->affected_rows > 0) {
    echo "User registered successfully";
} else {
    echo "Error: " . $stmt->error;
}

// Close statement
$stmt->close();

// Close connection
$conn->close();
?>
