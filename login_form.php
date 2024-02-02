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

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Fetch and validate user input
    $username = test_input($_POST['username']);
    $password = $_POST['password'];

    // Perform SQL query to retrieve user information
    $sql = "SELECT * FROM admin WHERE username = '$username'";
    $result = $conn->query($sql);

    if ($result->num_rows > 0) {
        $row = $result->fetch_assoc();
        // Verify password using password_verify
        if (password_verify($password, $row['password'])) {
            // Start the session (if not already started)
            session_start();

            // Set session variables if needed
            $_SESSION['admin_username'] = $row['username'];
            // Add more session variables as needed

            // Redirect to the display_location.php page after successful login
            header("Location: display_location.php");
            exit();
        } else {
            echo "Invalid password.";
        }
    } else {
        echo "User not found.";
    }
}

// Close connection
$conn->close();

// Function to safely handle user input
function test_input($data) {
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Form</title>
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
            width: 300px;
            text-align: center;
            margin-top: 20px; /* Added margin-top to create space between header and form */
        }

        h2 {
            color: #141516;
            background-color: #DFE0E1; /* Background color */
            padding: 10px; /* Padding around the heading */
            margin: 0; /* Reset margin to remove default margin */
            grid-column: span 2;
        }

        label {
            display: block;
            margin: 10px 0 5px;
            font-weight: bold;
            text-align: left;
        }

        input {
            width: 100%;
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
        }

        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

<header>
    <a href="login_form.php">Login</a>
    <a href="register_form.php">Register</a>
</header>

<form action="login_form.php" method="post">
    <h2>Log in</h2>

    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required>

    <button type="submit">Login</button>
</form>

</body>
</html>
