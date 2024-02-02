<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Homepage</title>
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
        

        .container {
            text-align: center;
        }

        .welcome {
            text-shadow: 2px 2px 5px ;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            font-size: 50px;
            color: #333;
            margin-top: 10px;
            margin-bottom: 20px;
        }

        .logo {
            max-width: 100%;
            height: auto;
            margin-bottom: 20px;
            border-radius: 8px;
            display: block;
            margin-left: auto;
            margin-right: auto;
        }

        button {
            background-color: #4caf50;
            color: #fff;
            padding: 15px 30px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 18px;
            margin: 10px;
        }

        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

<div class="container">
    <!-- Relative path to the image in the mymap project -->
    <h2 class="welcome">Welcome to MyMap!</h2>
    <img class="logo" src="map.png" alt="Logo">

    <button onclick="location.href='login_form.php'">Login</button>
    <button onclick="location.href='register_form.php'">Register</button>
</div>

</body>
</html>
