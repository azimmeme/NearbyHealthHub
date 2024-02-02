<?php
session_start();
require_once 'db_connect.php';

// Redirect to login.php if the user is not logged in
if (!isset($_SESSION['username'])) {
    header("Location: homepage.php");
    exit();
}

// Get the username from the session
$username = $_SESSION['username'];

// Fetch user location data from the database
$stmt = $conn->prepare("SELECT * FROM user_location WHERE username = :username ORDER BY report_time DESC");
$stmt->bindParam(':username', $username);
$stmt->execute();
$userLocations = $stmt->fetchAll(PDO::FETCH_ASSOC);
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome</title>
    <!-- Add your stylesheets or styling here -->
    <style>
        body {
            font-family: Arial, sans-serif;
        }

        h1 {
            color: #333;
        }

        p {
            margin-bottom: 20px;
        }

        a {
            color: #007bff;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }

        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h1>Welcome, <?php echo $username; ?>!</h1>
    <p>This is the main page after login. Customize this page according to your application needs.</p>
    <p><a href="logout.php">Logout</a></p>

    <h2>User Location History</h2>
    <table>
        <thead>
            <tr>
                <th>Username</th>
                <th>Report Time</th>
                <th>Coordinate</th>
            </tr>
        </thead>
        <tbody>
            <?php foreach ($userLocations as $location): ?>
                <tr>
                    <td><?php echo $location['username']; ?></td>
                    <td><?php echo $location['report_time']; ?></td>
                    <td><?php echo $location['coordinate']; ?></td>
                </tr>
            <?php endforeach; ?>
        </tbody>
    </table>
</body>
</html>
