<?php
// Start the session
session_start();

// Check if the user is logged in
if (isset($_SESSION['admin_username'])) {
    // Unset all session variables
    $_SESSION = array();

    // Destroy the session
    session_destroy();

    // Redirect to the login page or any other page after logout
    header("Location: login_form.php");
    exit();
} else {
    // If the user is not logged in, redirect them to the login page
    header("Location: login_form.php");
    exit();
}
?>
