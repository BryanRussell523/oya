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
<h4 align="center">{Mouka Shop}</h4>
<br><br><br>
<?php
	require_once('inc/config/constants.php');
	require_once('inc/config/db.php');
	$itemDetailsSearchSql = 'SELECT * FROM item';
	$itemDetailsSearchStatement = $conn->prepare($itemDetailsSearchSql);
	$itemDetailsSearchStatement->execute();
	
	$output = '<table id="itemDetailsTable" class="table table-sm table-striped table-bordered table-hover" style="width:100%">
				<thead>
					<tr>
					<th>Item Number</th>
					<th>Item Name</th>
					<th>Stock</th>
					<th>&#8358; Unit Price</th>
					<th>Description</th>
					<th>Item Total Amount</th>
					<th>Update</th>
					<th>Edit</th>
					<th>Delete</th>
					</tr>
				</thead>
				<tbody>';
	
	// Create table rows from the selected data
	while($row = $itemDetailsSearchStatement->fetch(PDO::FETCH_ASSOC)){
		
		$output .= '<tr>' .
		                                            '<td>'. $row['itemNumber'] .'</td>' .
		                                            '<td>'. $row['itemName'] .'</td>' .
													'<td>' . $row['stock'] . '</td>' .
													'<td>' . $row['unitPrice'] . '</td>' .
													'<td>' . $row['description'] . '</td>' .
													'<td>' . $row['stock'] *  $row['unitPrice'] . '</td>' .
													'<td> <a href="updateItem1.php" class="btn btn-warning">Update Item</a></td>' .
													'<td> <a href="editItem1.php" class="btn btn-primary">Edit Item</a></td>' .
						                             '<td> <a href="deleteItem1.php" class="btn btn-danger">Delete</a></td>' .
													
						
					'</tr>';
	}
	
	$itemDetailsSearchStatement->closeCursor();
	
	$output .= '</tbody>
					<tfoot>
						<tr>
						<th>Item Number</th>
						<th>Item Name</th>
						<th>Stock</th>
						<th>&#8358; Unit Price</th>
						<th>Description</th>
						<th>Item Total Amount</th>
						<th>Update</th>
						<th>Edit</th>
						<th>Delete</th>
						</tr>
					</tfoot>
				</table>';
	echo $output;
?>


<br><br><br><br>
<br><br><br><br>
<?php
	require 'inc/footer.php';
?>
  </body>
</html>