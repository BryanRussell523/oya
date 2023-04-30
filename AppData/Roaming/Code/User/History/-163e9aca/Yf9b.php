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
							$uPrice = 0;
							$qty = 0;
							$totalPrice = 0;
							
							$purchaseDetailsSearchSql = 'SELECT * FROM purchase';
							$purchaseDetailsSearchStatement = $conn->prepare($purchaseDetailsSearchSql);
							$purchaseDetailsSearchStatement->execute();
						
							$output = '<table id="purchaseReportsTable" class="table table-sm table-striped table-bordered table-hover" style="width:100%">
										<thead>
											<tr>
												<th>Expense ID</th>
												<th>Date</th>
												<th>Amount</th>
												<th>Purpose For Expense</th>
												<th>Edit</th>
												<th>Delete</th>
										
											</tr>
										</thead>
										<tbody>';
							
							// Create table rows from the selected data
							while($row = $purchaseDetailsSearchStatement->fetch(PDO::FETCH_ASSOC)){
								$uPrice = $row['unitPrice'];
								$qty = $row['quantity'];
								$totalPrice = $uPrice * $qty;
								
								$output .= '<tr>' .
												'<td>' . $row['purchaseID'] . '</td>' .
												'<td>' . $row['purchaseDate'] . '</td>' .
												'<td>' . $row['unitPrice'] . '</td>' .
												'<td>' .$row['description'] . '</td>' .
												'<td> <a href="editExpense2.php" class="btn btn-primary">Edit</a></td>' .
						                        '<td> <a href="deleteExpense2.php" class="btn btn-danger">Delete</a></td>' .
											'</tr>';
							}
							
							$purchaseDetailsSearchStatement->closeCursor();
							
							$output .= '</tbody>
											<tfoot>
												<tr>

													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													
												</tr>
											</tfoot>
										</table>';
							echo $output;
						?>
	

<br><br><br><br>
<br><br><br><br>
<br><br><br><br>
<?php
	require 'inc/footer.php';
?>
  </body>
</html>