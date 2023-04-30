<?php
	session_start();
	// Redirect the user to login page if he is not logged in.
	if(!isset($_SESSION['loggedIn'])){
		header('Location: login.php');
		exit();
	}
	require_once('inc/config/constants.php');
	require_once('inc/config/db.php');
	require_once('inc/header.html');
?>
  <body>
<?php
	require 'inc/navigation.php';
?>
<br><br><br>
<a href="Dashboardbreff.php" style="font-size:20px"><ion-icon name="arrow-back-circle-outline"></ion-icon>Back</a>
<h4 align="center">Total Expenses Details</h4>
<h4 align="center">{Winner's Foam}</h4>
<br><br><br>






















<br><br><br><br>
<br><br><br><br>
<?php
	require 'inc/footer.php';
?>
  </body>
</html>