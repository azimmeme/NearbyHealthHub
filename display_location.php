<?php
// Start the session
session_start();

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

// Fetch data from the 'locations' table
$sql = "SELECT * FROM locations";
$result = $conn->query($sql);

// Check if there are rows in the result set
if ($result->num_rows > 0) {
    // Fetch admin data for the welcome message based on the current session username
    if (isset($_SESSION['admin_username'])) {
        $currentUsername = $_SESSION['admin_username'];
        $adminSql = "SELECT * FROM admin WHERE username = '$currentUsername'";
        $adminResult = $conn->query($adminSql);

        if ($adminResult->num_rows > 0) {
            $adminRow = $adminResult->fetch_assoc();
            $adminFullName = $adminRow['full_name'];
        } else {
            $adminFullName = "Guest";
        }
    } else {
        $adminFullName = "Guest";
    }
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Data from Locations Table</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        header {
            background-color: #333;
            padding: 10px;
            color: #fff;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logout-link {
            color: #3498db;
            text-decoration: none;
        }

        .dashboard {
            max-width: 1200px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            overflow-x: auto; /* Enable horizontal scrolling on small devices */
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #f2f2f2;
        }

        tr:hover {
            background-color: #f5f5f5;
        }
    </style>
</head>
<body>
    <header>
        <div>Welcome, <?php echo $adminFullName; ?>!</div>
        <div><a href='logout_form.php' class='logout-link'>Logout</a></div>
    </header>

    <div class='dashboard'>
        <h2>Data from Locations Table</h2>
        <table>
            <tr>
                <th>ID</th>
                <th>Full Name</th> 
                <th>Latitude</th>
                <th>Longitude</th>
                <th>Address</th>
                <th>User Agent</th>
                <th>Date/Time</th>
            </tr>

            <?php
            // Output data of each row
            while($row = $result->fetch_assoc()) {
                echo "<tr>
                        <td>" . $row["id"]. "</td>
                        <td>" . $row["fullname"]. "</td> 
                        <td>" . $row["latitude"]. "</td>
                        <td>" . $row["longitude"]. "</td>
                        <td>" . $row["address"]. "</td>
                        <td>" . $row["user_agent"]. "</td>
                        <td>" . $row["datetime"]. "</td>
                      </tr>";
            }
            ?>
        </table>
    </div>
</body>
</html>

<?php
} else {
    echo "<p>No data found</p>";
}

$conn->close();
?>
