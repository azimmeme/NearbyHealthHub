<?php
// Database connection parameters
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

// Function to safely handle user input
function test_input($data) {
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
}

$registration_message = ""; // Initialize the message variable

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Fetch and validate user input
    $full_name = test_input($_POST['full_name']);
    $username = test_input($_POST['username']);
    $password = $_POST['password'];
    $confirm_password = $_POST['confirm_password'];

    // Additional validation if needed

    // Check if passwords match
    if ($password !== $confirm_password) {
        $registration_message = "Error: Passwords do not match.";
    } else {
        // Hash the password
        $hashed_password = password_hash($password, PASSWORD_DEFAULT);

        // Perform SQL query
        $sql = "INSERT INTO admin (full_name, username, password) VALUES ('$full_name', '$username', '$hashed_password')";

        if ($conn->query($sql) === TRUE) {
            header("Location: login_form.php");
            exit();
        } else {
            echo "Error: " . $sql . "<br>" . $conn->error;
        }
    }
}

// Close connection
$conn->close();
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration Form</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        header {
            background-color: #8C8E90;
            padding: 10px;
            color: #fff;
            text-align: right;
            width: 100%;
            position: fixed;
            top: 0;
            z-index: 1000;
        }

        header a {
            color: #fff;
            text-decoration: none;
            margin-right: 15px;
        }

        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 500px; /* Adjusted width */
            display: grid;
            grid-template-columns: 1fr 1fr; /* Two columns layout */
            grid-gap: 10px;
        }

        h2 {
            color: #141516;
            background-color: #DFE0E1; /* Background color */
            padding: 10px; /* Padding around the heading */
            margin: 0; /* Reset margin to remove default margin */
            grid-column: span 2; /* Make the heading span both columns */
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        input {
            width: calc(100% - 20px); /* Adjusted width for the input fields */
            padding: 8px;
            margin-bottom: 10px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        button {
            background-color: #4caf50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            grid-column: span 2; 
        }

        button:hover {
            background-color: #45a049;
        }

        p {
            color: green;
            margin: 10px 0;
            grid-column: span 2; /* Make the message span both columns */
        }
    </style>
</head>
<body>

<header>
    <a href="login_form.php">Login</a>
    <a href="register_form.php">Register</a>
</header>

<form action="register_form.php" method="post">
    <h2>Register</h2>

    <?php
    // Display the registration message
    echo "<p>$registration_message</p>";
    ?>

    <label for="full_name">Full Name:</label>
    <input type="text" id="full_name" name="full_name" required>

    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required>

    <label for="confirm_password">Confirm Password:</label>
    <input type="password" id="confirm_password" name="confirm_password" required>

    <button type="submit">Register</button>
</form>

</body>
</html>
