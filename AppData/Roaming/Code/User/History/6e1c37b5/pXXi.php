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
	require 'navigation.php';
?>
    <!-- Page Content -->
    <div class="container-fluid">
	  <div class="row"> 
		<div class="col-lg-2">
		<h1 class="my-4"></h1>
		<br><br><br>
		<a href="MakeSale.php">MakeSale(Mouka Shop)</a>
		<a href="MakeSale2.php">MakeSale(Ware House)</a>
		<a href="MakeSale3.php">MakeSale(Winner's Foam)</a>
		<a href="Dashboardbreff.php" style="font-size:20px" id="reg">Dashboard <ion-icon name="clipboard-outline"></ion-icon></a>
		<br>
		<a href="Usermanagement.php"  style="font-size:20px" id="reg">Usermanagement <ion-icon name="person-circle-outline"></ion-icon></a>
		<br><br>
		<br><br>
			  <h4>Mouka Shop</h4>
			  <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
			  <a class="nav-link active" id="v-pills-item-tab"  data-toggle="pill" href="#v-pills-item" role="tab" aria-controls="v-pills-item" aria-selected="true" style="font-size: 20px">Item<ion-icon name="pricetags-outline"></ion-icon></a>
			  <!-- <a class="nav-link" id="v-pills-sale-tab" data-toggle="pill" href="#v-pills-sale" role="tab" aria-controls="v-pills-sale" aria-selected="false"  style="font-size: 20px">Sale<ion-icon name="person-add-outline"></ion-icon></a> -->
			   
			  <a class="nav-link" id="v-pills-customer-tab" data-toggle="pill" href="#v-pills-customer" role="tab" aria-controls="v-pills-customer" aria-selected="false"  style="font-size: 20px">Customer<ion-icon name="person-add-outline"></ion-icon></a>
			  <a class="nav-link" id="v-pills-purchase-tab" data-toggle="pill" href="#v-pills-purchase" role="tab" aria-controls="v-pills-purchase" aria-selected="false" style="font-size: 20px">Expenses-</a>

			  <a class="nav-link" id="v-pills-reports-tab" data-toggle="pill" href="#v-pills-reports" role="tab" aria-controls="v-pills-reports" aria-selected="false" style="font-size: 20px">Reports<ion-icon name="book-outline"></ion-icon></a>
			  </div>
			  <br><br><br><br>
			  <br><br><br><br>
			  <br><br>
			  <h4>Ware House</h4>
			  <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
			  <a class="nav-link active" id="v-pills-item-tab2"  data-toggle="pill" href="#v-pills-item2" role="tab" aria-controls="v-pills-item" aria-selected="true" style="font-size: 20px">Item<ion-icon name="pricetags-outline"></ion-icon></a>
			  <a class="nav-link" id="v-pills-customer-tab2" data-toggle="pill" href="#v-pills-customer2" role="tab" aria-controls="v-pills-customer" aria-selected="false"  style="font-size: 20px">Customer<ion-icon name="person-add-outline"></ion-icon></a>
			  <a class="nav-link" id="v-pills-reports-tab2" data-toggle="pill" href="#v-pills-reports2" role="tab" aria-controls="v-pills-reports" aria-selected="false" style="font-size: 20px">Reports<ion-icon name="book-outline"></ion-icon></a>
			</div>
			<br><br>
			<br><br><br><br>
			<br><br><br><br>
			<br><br><br><br>
			<br><br><br><br>
			  <h4>Winner's Foam</h4>
			  <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">
			  <a class="nav-link active" id="v-pills-item-tab3"  data-toggle="pill" href="#v-pills-item3" role="tab" aria-controls="v-pills-item" aria-selected="true" style="font-size: 20px">Item<ion-icon name="pricetags-outline"></ion-icon></a>
			  <a class="nav-link" id="v-pills-customer-tab3" data-toggle="pill" href="#v-pills-customer3" role="tab" aria-controls="v-pills-customer" aria-selected="false"  style="font-size: 20px">Customer<ion-icon name="person-add-outline"></ion-icon></a>
			  <a class="nav-link" id="v-pills-reports-tab3" data-toggle="pill" href="#v-pills-reports3" role="tab" aria-controls="v-pills-reports" aria-selected="false" style="font-size: 20px">Reports<ion-icon name="book-outline"></ion-icon></a>
			</div>
			</div>
			<!-- MOUKA SHOP PAGE CONTENT -->
			 <div class="col-lg-10">
			<div class="tab-content" id="v-pills-tabContent">
			  <div class="tab-pane fade show active" id="v-pills-item" role="tabpanel" aria-labelledby="v-pills-item-tab">
				<div class="card card-outline-secondary my-4">
					<h4>Mouka Shop</h4>
				  <div class="card-header">Add Item</div>
				  <div class="card-body">
					<ul class="nav nav-tabs" role="tablist">
						<li class="nav-item">
							<a class="nav-link active" data-toggle="tab" href="#itemDetailsTab">Item</a>
						</li>
					</ul>					
	            	<!-- Tab panes for item details and image sections -->
					<div class="tab-content">
						<div id="itemDetailsTab" class="container-fluid tab-pane active">
							<br>
							<!-- Div to show the ajax message from validations/db submission -->
							<div id="itemDetailsMessage"></div>
							<form>
							  <div class="form-row">
								<div class="form-group col-md-3" style="display:inline-block">
								  <label for="itemDetailsItemNumber">Item Number<span class="requiredIcon">*</span></label>
								  <input type="text" class="form-control" name="itemDetailsItemNumber" id="itemDetailsItemNumber" autocomplete="off">
								  <div id="" class="customListDivWidth"></div>
								</div>
								<div class="form-group col-md-3">
								  
								</div>
							  </div>
							  <div class="form-row">
								  <div class="form-group col-md-6">
									<label for="itemDetailsItemName">Item Name<span class="requiredIcon">*</span></label>
									<input type="text" class="form-control" name="itemDetailsItemName" id="itemDetailsItemName" autocomplete="off">
									<div id="itemDetailsItemNameSuggestionsDiv" class="customListDivWidth"></div>
								  </div>
								  <div class="form-group col-md-2">
									</select>
								  </div>
							  </div>
							  <div class="form-row">
								<div class="form-group col-md-6" style="display:inline-block">
								  <!-- <label for="itemDetailsDescription">Description</label> -->
								  <textarea rows="4" class="form-control" placeholder="Description" name="itemDetailsDescription" id="itemDetailsDescription"></textarea>
								</div>
							  </div>
							  <div class="form-row">
								
								<div class="form-group col-md-3">
								  <label for="itemDetailsQuantity">Quantity<span class="requiredIcon">*</span></label>
								  <input type="number" class="form-control" value="" name="itemDetailsQuantity" id="itemDetailsQuantity">
								</div>
								<div class="form-group col-md-3">
								  <label for="itemDetailsUnitPrice"> &#8358; Unit Price<span class="requiredIcon">*</span></label>
								  <input type="text" class="form-control" value="" name="itemDetailsUnitPrice" id="itemDetailsUnitPrice">
								</div>
								<div class="form-group col-md-3">
								  <label for="itemDetailsTotalStock">Total Stock</label>
								  <input type="text" class="form-control" name="itemDetailsTotalStock" id="itemDetailsTotalStock" readonly>
								</div>
								<div class="form-group col-md-3">
									<div id="imageContainer"></div>
								</div>
							  </div>
							  <button type="button" id="addItem" class="btn btn-success">Add Item</button>
							  
							  <button type="reset" class="btn" id="itemClear">Clear</button>
							</form>
						</div>
					</div>
				  </div> 
				</div>
			  </div>
			  <div class="tab-pane fade" id="v-pills-purchase" role="tabpanel" aria-labelledby="v-pills-purchase-tab">
				<div class="card card-outline-secondary my-4">
				  <div class="card-header">Expense Details</div>
				  <div class="card-body">
					<div id="purchaseDetailsMessage"></div>
					<form>
					  <div class="form-row">
						<div class="form-group col-md-3">
						
								  <!-- <label for="itemDetailsDescription">Description</label> -->
								  <textarea rows="4" class="form-control" placeholder="Purpose" name="purchaseDetailsDescription" id="purchaseDetailsDescription"></textarea>

						</div>
						<div class="form-group col-md-3">
						  <label for="purchaseDetailsPurchaseDate">Date<span class="requiredIcon">*</span></label>
						  <input type="text" class="form-control datepicker" id="purchaseDetailsPurchaseDate" name="purchaseDetailsPurchaseDate" readonly value="">
						</div>
						<div class="form-group col-md-2">
						  <label for="purchaseDetailsPurchaseID">Expense ID</label>
						  <input type="text" class="form-control invTooltip" id="purchaseDetailsPurchaseID" name="purchaseDetailsPurchaseID" title="This will be auto-generated when you add a new record" autocomplete="off" readonly>
						  <div id="purchaseDetailsPurchaseIDSuggestionsDiv" class="customListDivWidth"></div>
						</div>
					  </div>
					  <div class="form-row"> 
						  <div class="form-group col-md-4">
						  </div>
						  <div class="form-group col-md-2">
							  <!-- <label for="purchaseDetailsCurrentStock">Current Stock</label>
							  <input type="text" class="form-control" id="purchaseDetailsCurrentStock" name="purchaseDetailsCurrentStock" readonly> -->
						  </div>
						  <div class="form-group col-md-4">
							
						  </div>
					  </div>
					  <div class="form-group col-md-2">
						  <label for="purchaseDetailsUnitPrice">Amount<span class="requiredIcon">*</span></label>
						  <input type="text" class="form-control" id="purchaseDetailsUnitPrice" name="purchaseDetailsUnitPrice" value="0">
						  
						</div>
					  <div class="form-row">
						<div class="form-group col-md-2">
						 
						</div>
						
						<div class="form-group col-md-2">
						
						</div>
					  </div>
					  <button type="button" id="addPurchase" class="btn btn-success">Deduct(-)</button>
					  <button type="button" id="updatePurchaseDetailsButton" class="btn btn-primary">Update</button>
					  <button type="reset" class="btn">Clear</button>
					</form>
				  </div> 
				</div>
			  </div>
			  
			  <div class="tab-pane fade" id="v-pills-vendor" role="tabpanel" aria-labelledby="v-pills-vendor-tab">
				<div class="card card-outline-secondary my-4">
				  <div class="card-header">Vendor Details</div>
				  <div class="card-body">
				  <!-- Div to show the ajax message from validations/db submission -->
				  <div id="vendorDetailsMessage"></div>
					 <form> 
					  <div class="form-row">
						<div class="form-group col-md-6">
						  <label for="vendorDetailsVendorFullName">Full Name<span class="requiredIcon">*</span></label>
						  <input type="text" class="form-control" id="vendorDetailsVendorFullName" name="vendorDetailsVendorFullName" placeholder="">
						</div>
						<div class="form-group col-md-2">
							<label for="vendorDetailsStatus">Status</label>
							<select id="vendorDetailsStatus" name="vendorDetailsStatus" class="form-control chosenSelect">
								<?php include('inc/statusList.html'); ?>
							</select>
						</div>
						 <div class="form-group col-md-3">
							<label for="vendorDetailsVendorID">Vendor ID</label>
							<input type="text" class="form-control invTooltip" id="vendorDetailsVendorID" name="vendorDetailsVendorID" title="This will be auto-generated when you add a new vendor" autocomplete="off">
							<div id="vendorDetailsVendorIDSuggestionsDiv" class="customListDivWidth"></div>
						</div>
					  </div>
					  <div class="form-row">
						  <div class="form-group col-md-3">
							<label for="vendorDetailsVendorMobile">Phone (mobile)<span class="requiredIcon">*</span></label>
							<input type="text" class="form-control invTooltip" id="vendorDetailsVendorMobile" name="vendorDetailsVendorMobile" title="Do not enter leading 0">
						  </div>
						  <div class="form-group col-md-3">
							<label for="vendorDetailsVendorPhone2">Phone 2</label>
							<input type="text" class="form-control invTooltip" id="vendorDetailsVendorPhone2" name="vendorDetailsVendorPhone2" title="Do not enter leading 0">
						  </div>
						  <div class="form-group col-md-6">
							<label for="vendorDetailsVendorEmail">Email</label>
							<input type="email" class="form-control" id="vendorDetailsVendorEmail" name="vendorDetailsVendorEmail">
						</div>
					  </div>
					  <div class="form-group">
						<label for="vendorDetailsVendorAddress">Address<span class="requiredIcon">*</span></label>
						<input type="text" class="form-control" id="vendorDetailsVendorAddress" name="vendorDetailsVendorAddress">
					  </div>
					  <div class="form-group">
						<label for="vendorDetailsVendorAddress2">Address 2</label>
						<input type="text" class="form-control" id="vendorDetailsVendorAddress2" name="vendorDetailsVendorAddress2">
					  </div>
					  <div class="form-row">
						<div class="form-group col-md-6">
						  <label for="vendorDetailsVendorCity">City</label>
						  <input type="text" class="form-control" id="vendorDetailsVendorCity" name="vendorDetailsVendorCity">
						</div>
						<div class="form-group col-md-4">
						  <label for="vendorDetailsVendorDistrict">District</label>
						  <select id="vendorDetailsVendorDistrict" name="vendorDetailsVendorDistrict" class="form-control chosenSelect">
							<?php include('inc/districtList.html'); ?>
						  </select>
						</div>
					  </div>					  
					  <button type="button" id="addVendor" name="addVendor" class="btn btn-success">Add Vendor</button>
					  <button type="button" id="updateVendorDetailsButton" class="btn btn-primary">Update</button>
					  <button type="button" id="deleteVendorButton" class="btn btn-danger">Delete</button>
					  <button type="reset" class="btn">Clear</button>
					 </form>
				  </div> 
				</div>
			  </div>
			  <div class="tab-pane fade" id="v-pills-sale" role="tabpanel" aria-labelledby="v-pills-sale-tab">
				<div class="card card-outline-secondary my-4">
				  <div class="card-header">Sale Details</div>
				  <div class="card-body">
					<div id="saleDetailsMessage"></div>
					<form>
					  <div class="form-row">
						<div class="form-group col-md-3">
						  <label for="saleDetailsItemNumber">Item Number<span class="requiredIcon">*</span></label>
						  <input type="text" class="form-control" id="saleDetailsItemNumber" name="saleDetailsItemNumber" autocomplete="off">
						  <div id="saleDetailsItemNumberSuggestionsDiv" class="customListDivWidth"></div>
						</div>
						<div class="form-group col-md-3">
							<label for="saleDetailsCustomerID">Customer ID<span class="requiredIcon">*</span></label>
							<input type="text" class="form-control" id="saleDetailsCustomerID" name="saleDetailsCustomerID" autocomplete="off">
							<div id="saleDetailsCustomerIDSuggestionsDiv" class="customListDivWidth"></div>
						</div>
						<div class="form-group col-md-4">
						  <label for="saleDetailsCustomerName">Customer Name</label>
						  <input type="text" class="form-control" id="saleDetailsCustomerName" name="saleDetailsCustomerName" readonly>
						</div>
						<div class="form-group col-md-2">
						  <label for="saleDetailsSaleID">Sale ID</label>
						  <input type="text" class="form-control invTooltip" id="saleDetailsSaleID" name="saleDetailsSaleID" title="This will be auto-generated when you add a new record" autocomplete="off">
						  <div id="saleDetailsSaleIDSuggestionsDiv" class="customListDivWidth"></div>
						</div>
					  </div>
					  <div class="form-row">
						  <div class="form-group col-md-5">
							<label for="saleDetailsItemName">Item Name</label>
							<!--<select id="saleDetailsItemNames" name="saleDetailsItemNames" class="form-control chosenSelect"> -->
								<?php 
									//require('model/item/getItemDetails.php');
								?>
							<!-- </select> -->
							<input type="text" class="form-control invTooltip" id="saleDetailsItemName" name="saleDetailsItemName" readonly title="This will be auto-filled when you enter the item number above">
						  </div>
						  <div class="form-group col-md-3">
							  <label for="saleDetailsSaleDate">Sale Date<span class="requiredIcon">*</span></label>
							  <input type="text" class="form-control datepicker" id="saleDetailsSaleDate" value="2018-05-24" name="saleDetailsSaleDate" readonly>
						  </div>
					  </div>
					  <div class="form-row">
						<div class="form-group col-md-2">
								  <label for="saleDetailsTotalStock">Total Stock</label>
								  <input type="text" class="form-control" name="saleDetailsTotalStock" id="saleDetailsTotalStock" readonly>
								</div>
						<div class="form-group col-md-2">
						  <label for="saleDetailsDiscount">Discount %</label>
						  <input type="text" class="form-control" id="saleDetailsDiscount" name="saleDetailsDiscount" value="0">
						</div>
						<div class="form-group col-md-2">
						  <label for="saleDetailsQuantity">Quantity<span class="requiredIcon">*</span></label>
						  <input type="number" class="form-control" id="saleDetailsQuantity" name="saleDetailsQuantity" value="0">
						</div>
						<div class="form-group col-md-2">
						  <label for="saleDetailsUnitPrice">Unit Price<span class="requiredIcon">*</span></label>
						  <input type="text" class="form-control" id="saleDetailsUnitPrice" name="saleDetailsUnitPrice" value="0">
						</div>
						<div class="form-group col-md-3">
						  <label for="saleDetailsTotal">Total</label>
						  <input type="text" class="form-control" id="saleDetailsTotal" name="saleDetailsTotal">
						</div>
					  </div>
					  <div class="form-row">
						  <div class="form-group col-md-3">
							<div id="saleDetailsImageContainer"></div>
						  </div>
					 </div>
					  <button type="button" id="addSaleButton" class="btn btn-success">Add Sale</button>
					  <button type="button" id="updateSaleDetailsButton" class="btn btn-primary">Update</button>
					  <button type="reset" id="saleClear" class="btn">Clear</button>
					</form>
				  </div> 
				</div>
			  </div>
			  <div class="tab-pane fade" id="v-pills-customer" role="tabpanel" aria-labelledby="v-pills-customer-tab">
				<div class="card card-outline-secondary my-4">
				  <div class="card-header">Customer Details</div>
				  <br>
				  <a href="addCustomer.php" style="font-size:20px;border-style:solid;border-radius:20px;width: 14%;"><ion-icon name="person-add-outline"></ion-icon>Add Customer</a>
				  <div class="card-body">
				  <!-- Div to show the ajax message from validations/db submission -->
				  <?php
	$customerDetailsSearchSql = 'SELECT * FROM customer';
	$customerDetailsSearchStatement = $conn->prepare($customerDetailsSearchSql);
	$customerDetailsSearchStatement->execute();

	$output = '<table id="customerDetailsTable" class="table table-sm table-striped table-bordered table-hover" style="width:100%">
				<thead>
					<tr>
						<th>Full Name</th>
						<th>Email</th>
						<th>Mobile</th>
						<th>Address</th>
						<th>Edit</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>';
	
	// Create table rows from the selected data
	while($row = $customerDetailsSearchStatement->fetch(PDO::FETCH_ASSOC)){
		$output .= '<tr>' .
						'<td>' . $row['fullName'] . '</td>' .
						'<td>' . $row['email'] . '</td>' .
						'<td>' . $row['mobile'] . '</td>' .
						'<td>' . $row['address'] . '</td>' .
						'<td> <a href="editCustomer1.php" class="btn btn-primary">Edit Customer Details</a></td>' .
						'<td> <a href="deleteCustomer1.php" class="btn btn-danger">Delete Customer</a></td>' .
					'</tr>';
	}
	$customerDetailsSearchStatement->closeCursor();
	
	$output .= '</tbody>
					<tfoot>
						<tr>
							
							<th>Full Name</th>
							<th>Email</th>
							<th>Mobile</th>
							<th>Address</th>
							<th>Edit</th>
						<th>Delete</th>
						</tr>
					</tfoot>
				</table>';
	echo $output;
?>
				  </div> 
				</div>
			  </div>
			  
			  <div class="tab-pane fade" id="v-pills-search" role="tabpanel" aria-labelledby="v-pills-search-tab">
				<div class="card card-outline-secondary my-4">
				<h4>Mouka Shop</h4>
				  <div class="card-header">Search Inventory</div>
				  <a href="index.php">Refresh</a>
				  <div class="card-body">										
					<ul class="nav nav-tabs" role="tablist">
						<li class="nav-item">
							<a class="nav-link active" data-toggle="tab" href="#itemSearchTab">Item</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#customerSearchTab">Customer</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#saleSearchTab">Sale</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#purchaseSearchTab"></a>
						</li>
						<li class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#vendorSearchTab"></a>
						</li>
					</ul>
  
					<!-- Tab panes -->
					<div class="tab-content">
						<div id="itemSearchTab" class="container-fluid tab-pane active">
						  <br>
						  <p>Use the grid below to search all details of items</p>
						  <!-- <a href="#" class="itemDetailsHover" data-toggle="popover" id="10">wwwee</a> -->
							<div class="table-responsive" id="itemDetailsTableDiv2">
								<?php 
								$itemDetailsSearchSql = 'SELECT * FROM item';
								$itemDetailsSearchStatement = $conn->prepare($itemDetailsSearchSql);
								$itemDetailsSearchStatement->execute();
								
								$output = '<table id="itemDetailsTable" class="table table-sm table-striped table-bordered table-hover" style="width:100%">
											<thead>
												<tr>
													<th>Product ID</th>
													
													<th>Item Name</th>
													<th>Discount %</th>
													<th>Stock</th>
													<th>&#8358; Unit Price</th>
													<th>Status</th>
													<th>Description</th>
												</tr>
											</thead>
											<tbody>';
								// Create table rows from the selected data
								while($row = $itemDetailsSearchStatement->fetch(PDO::FETCH_ASSOC)){
									
									$output .= '<tr>' .
													'<td>' . $row['productID'] . '</td>' .
													'<td>' . $row['itemNumber'] . '</td>' .
													'<td><a href="#" class="itemDetailsHover" data-toggle="popover" id="' . $row['productID'] . '">' . $row['itemName'] . '</a></td>' .
													'<td>' . $row['discount'] . '</td>' .
													'<td>' . $row['stock'] . '</td>' .
													'<td>' . $row['unitPrice'] . '</td>' .
													'<td>' . $row['status'] . '</td>' .
													'<td>' . $row['description'] . '</td>' .
												'</tr>';
								}
								
								$itemDetailsSearchStatement->closeCursor();
								
								$output .= '</tbody>
												<tfoot>
													<tr>
														<th>Product ID</th>
														<th>Item Number</th>
														<th>Item Name</th>
														<th>Discount %</th>
														<th>Stock</th>
														<th>&#8358; Unit Price</th>
														<th>Status</th>
														<th>Description</th>
													</tr>
												</tfoot>
											</table>';
								echo $output;
								
								?>

							</div>
						</div>
						<div id="customerSearchTab" class="container-fluid tab-pane fade">
						  <br>
						  <p>Use the grid below to search all details of customers</p>
							<div class="table-responsive" id="customerDetailsTableDiv2">
							<?php 
							$customerDetailsSearchSql = 'SELECT * FROM customer';
							$customerDetailsSearchStatement = $conn->prepare($customerDetailsSearchSql);
							$customerDetailsSearchStatement->execute();
						
							$output = '<table id="customerReportsTable" class="table table-sm table-striped table-bordered table-hover" style="width:100%">
										<thead>
											<tr>
												<th>Customer ID</th>
												<th>Full Name</th>
												<th>Email</th>
												<th>Mobile</th>
												<th>Phone 2</th>
												<th>Address</th>
												<th>Address 2</th>
												<th>City</th>
												<th>District</th>
												<th>Status</th>
											</tr>
										</thead>
										<tbody>';
							
							// Create table rows from the selected data
							while($row = $customerDetailsSearchStatement->fetch(PDO::FETCH_ASSOC)){
								$output .= '<tr>' .
												'<td>' . $row['customerID'] . '</td>' .
												'<td>' . $row['fullName'] . '</td>' .
												'<td>' . $row['email'] . '</td>' .
												'<td>' . $row['mobile'] . '</td>' .
												'<td>' . $row['phone2'] . '</td>' .
												'<td>' . $row['address'] . '</td>' .
												'<td>' . $row['address2'] . '</td>' .
												'<td>' . $row['city'] . '</td>' .
												'<td>' . $row['district'] . '</td>' .
												'<td>' . $row['status'] . '</td>' .
											'</tr>';
							}
							
							$customerDetailsSearchStatement->closeCursor();
							
							$output .= '</tbody>
											<tfoot>
												<tr>
													<th>Customer ID</th>
													<th>Full Name</th>
													<th>Email</th>
													<th>Mobile</th>
													<th>Phone 2</th>
													<th>Address</th>
													<th>Address 2</th>
													<th>City</th>
													<th>District</th>
													<th>Status</th>
												</tr>
											</tfoot>
										</table>';
							echo $output;	
								?>
							</div>
						</div>
						<div id="saleSearchTab" class="container-fluid tab-pane fade">
							<br>
							<p>Use the grid below to search sale details</p>
							<div class="table-responsive" id="saleDetailsTableDiv2">
							<?php
							$uPrice = 0;
							$qty = 0;
							$totalPrice = 0;
							
							$saleDetailsSearchSql = 'SELECT * FROM sale';
							$saleDetailsSearchStatement = $conn->prepare($saleDetailsSearchSql);
							$saleDetailsSearchStatement->execute();
						
							$output = '<table id="saleDetailsTable" class="table table-sm table-striped table-bordered table-hover" style="width:100%">
										<thead>
											<tr>
												<th>Sale ID</th>
												
												<th>Customer ID</th>
												<th>Customer Name</th>
												<th>Item Name</th>
												<th>Sale Date</th>
												<th>Discount %</th>
												<th>Quantity</th>
												<th> &#8358; Unit Price</th>
												<th> &#8358; Total Price</th>
											</tr>
										</thead>
										<tbody>';
							
							// Create table rows from the selected data
							while($row = $saleDetailsSearchStatement->fetch(PDO::FETCH_ASSOC)){
								$uPrice = $row['unitPrice'];
								$qty = $row['quantity'];
								$discount = $row['discount'];
								$totalPrice = $uPrice * $qty * ((100 - $discount)/100);
									
								$output .= '<tr>' .
												'<td>' . $row['saleID'] . '</td>' .
												
												'<td>' . $row['customerID'] . '</td>' .
												'<td>' . $row['customerName'] . '</td>' .
												'<td>' . $row['itemName'] . '</td>' .
												'<td>' . $row['saleDate'] . '</td>' .
												'<td>' . $row['discount'] . '</td>' .
												'<td>' . $row['quantity'] . '</td>' .
												'<td>' . $row['unitPrice'] . '</td>' .
												'<td>' . $totalPrice . '</td>' .
											'</tr>';
							}
							
							$saleDetailsSearchStatement->closeCursor();
							
							$output .= '</tbody>
											<tfoot>
												<tr>
													<th>Sale ID</th>
													
													<th>Customer ID</th>
													<th>Customer Name</th>
													<th>Item Name</th>
													<th>Sale Date</th>
													<th>Discount %</th>
													<th>Quantity</th>
													<th> &#8358; Unit Price</th>
													<th> &#8358; Total Price</th>
												</tr>
											</tfoot>
										</table>';
							echo $output; 	
								?>
						</div>
						</div>
						<div id="purchaseSearchTab" class="container-fluid tab-pane fade">
							<br>
							<p>Use the grid below to search purchase details</p>
							<div class="table-responsive" id="purchaseDetailsTableDiv"></div>
						</div>
						<div id="vendorSearchTab" class="container-fluid tab-pane fade">
							<br>
							<p>Use the grid below to search vendor details</p>
							<div class="table-responsive" id="vendorDetailsTableDiv"></div>
						</div>
					</div>
				  </div> 
				</div>
			  </div>
			  
			  <div class="tab-pane fade" id="v-pills-reports" role="tabpanel" aria-labelledby="v-pills-reports-tab">
				<div class="card card-outline-secondary my-4">
				<h4>Mouka Shop</h4>
				  <div class="card-header">Reports</div>
				  <a href="index.php">Refresh</a>
				 
				  <div class="card-body">										
					<ul class="nav nav-tabs" role="tablist">
						<li class="nav-item">
							<a class="nav-link active" data-toggle="tab" href="#itemReportsTab">Item</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#customerReportsTab">Customer</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#saleReportsTab">Sale</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#purchaseReportsTab">Expenses</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#vendorReportsTab"></a>
						</li>
					</ul>
  
					<!-- Tab panes for reports sections -->
					<div class="tab-content">
						<div id="itemReportsTab" class="container-fluid tab-pane active">
							<br>
							<p>Use the grid below to get reports for items</p>
							<div class="table-responsive" id="itemReportsTableDiv2">
							<?php 
							$itemDetailsSearchSql = 'SELECT * FROM item';
							$itemDetailsSearchStatement = $conn->prepare($itemDetailsSearchSql);
							$itemDetailsSearchStatement->execute();
						
							$output = '<table id="itemReportsTable" class="table table-sm table-striped table-bordered table-hover" style="width:100%">
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
								                '<td>' . $row['itemNumber'] . '</td>' .
												'<td>' . $row['itemName'] . '</td>' .
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




							</div>
						</div>
						<div id="customerReportsTab" class="container-fluid tab-pane fade">
							<br>
							<p>Use the grid below to get reports for customers</p>
							<div class="table-responsive" id="customerReportsTableDiv2">
							<?php 
							$customerDetailsSearchSql = 'SELECT * FROM customer';
							$customerDetailsSearchStatement = $conn->prepare($customerDetailsSearchSql);
							$customerDetailsSearchStatement->execute();
						
							$output = '<table id="customerReportsTable" class="table table-sm table-striped table-bordered table-hover" style="width:100%">
										<thead>
											<tr>
											    <th>Customer ID</th>
												<th>Full Name</th>
												<th>Email</th>
												<th>Mobile</th>
												<th>Address</th>
												<th>Edit</th>
												<th>Delete</th>
												
											</tr>
										</thead>
										<tbody>';
							
							// Create table rows from the selected data
							while($row = $customerDetailsSearchStatement->fetch(PDO::FETCH_ASSOC)){
								$output .= '<tr>' .
												'<td>' . $row['customerID'] . '</td>' .
												'<td>' . $row['fullName'] . '</td>' .
												'<td>' . $row['email'] . '</td>' .
												'<td>' . $row['mobile'] . '</td>' .
												'<td>' . $row['address'] . '</td>' .
												'<td>  <a href="editCustomer1.php" class="btn btn-primary">Edit Customer Details</a></td>' .
						                         '<td> <a href="deleteCustomer1.php" class="btn btn-danger">Delete Customer</a></td>' .
											'</tr>';
							}
							
							$customerDetailsSearchStatement->closeCursor();
							
							$output .= '</tbody>
											<tfoot>
												<tr>
												<th>Customer ID</th>
												<th>Full Name</th>
												<th>Email</th>
												<th>Mobile</th>
												<th>Address</th>
												<th>Edit</th>
												<th>Delete</th>

												</tr>
											</tfoot>
										</table>';
							echo $output;
								
								
								?>
							</div>
						</div>
						<div id="saleReportsTab" class="container-fluid tab-pane fade">
							<br>
							<!-- <p>Use the grid below to get reports for sales</p> -->
							<form> 
							  <div class="form-row">
								  <div class="form-group col-md-3">
									<label for="saleReportStartDate">Start Date</label>
									<input type="text" class="form-control datepicker" id="saleReportStartDate" value="" name="saleReportStartDate" readonly>
								  </div>
								  <div class="form-group col-md-3">
									<label for="saleReportEndDate">End Date</label>
									<input type="text" class="form-control datepicker" id="saleReportEndDate" value="" name="saleReportEndDate" readonly>
								  </div>
							  </div>
							  <button type="button" id="showSaleReport" class="btn btn-dark">Show Report</button>
							  <button type="reset" id="saleFilterClear" class="btn">Clear</button>
							</form>
							<br><br>
							<div class="table-responsive" id="saleReportsTableDiv2">
							<?php 
							$uPrice = 0;
							$qty = 0;
							$totalPrice = 0;
							
							$saleDetailsSearchSql = 'SELECT * FROM sale';
							$saleDetailsSearchStatement = $conn->prepare($saleDetailsSearchSql);
							$saleDetailsSearchStatement->execute();
						
							$output = '<table id="saleReportsTable" class="table table-sm table-striped table-bordered table-hover" style="width:100%">
										<thead>
											<tr>
												<th>Sale ID</th>
												<th>Customer Name</th>
												<th>Item Name</th>
												<th>Sale Date</th>
												<th>Quantity</th>
												<th>&#8358; Unit Price</th>
												<th>&#8358; Total Price</th>
												<th>Edit</th>
												<th>Delete</th>	

											</tr>
										</thead>
										<tbody>';
							
							// Create table rows from the selected data
							while($row = $saleDetailsSearchStatement->fetch(PDO::FETCH_ASSOC)){
								$uPrice = $row['unitPrice'];
								$qty = $row['quantity'];
								$discount = $row['discount'];
								$totalPrice = $uPrice * $qty * ((100 - $discount)/100);
								
								 $output .= '<tr>' .
								                '<td>' . $row['saleID'] . '</td>' .
												'<td>' . $row['customerName'] . '</td>' .
												'<td>' . $row['itemName'] . '</td>' .
												'<td>' . $row['saleDate'] . '</td>' .
												'<td>' . $row['quantity'] . '</td>' .
												'<td>' . $row['unitPrice'] . '</td>' .
												'<td>' . $totalPrice . '</td>' .
												'<td> <a href="editSale1.php" class="btn btn-primary">Edit Sale Details</a></td>' .
						                        '<td> <a href="deleteSale1.php" class="btn btn-danger">Delete</a></td>' .
											'</tr>';
							}
							
							$saleDetailsSearchStatement->closeCursor();
							
							$output .= '</tbody>
											<tfoot>
												<tr>
												<th>Sale ID</th>
												<th>Customer Name</th>
												<th>Item Name</th>
												<th>Sale Date</th>
												<th>Quantity</th>
												<th>&#8358; Unit Price</th>
												<th>&#8358; Total Price</th>
												<th>Edit</th>
												<th>Delete</th>	
												</tr>
											</tfoot>
										</table>';
							echo $output;	
								?>
							</div>
						</div>
						<div id="purchaseReportsTab" class="container-fluid tab-pane fade">
							<br>
							<!-- <p>Use the grid below to get reports for sales</p> -->
							<!-- <form> 
							  <div class="form-row">
								  <div class="form-group col-md-3">
									<label for="purchaseReportStartDate">Start Date</label>
									<input type="text" class="form-control datepicker" id="purchaseReportStartDate" value="" name="purchaseReportStartDate" readonly>
								  </div>
								  <div class="form-group col-md-3">
									<label for="purchaseReportEndDate">End Date</label>
									<input type="text" class="form-control datepicker" id="purchaseReportEndDate" value="" name="purchaseReportEndDate" readonly>
								  </div>
							  </div>
							  <button type="button" id="showpurchaseReport" class="btn btn-dark">Show Report</button>
							  <button type="reset" id="purchaseFilterClear" class="btn">Clear</button>
							</form> -->
							<br><br>
							
							<div class="table-responsive" id="saleReportsTableDiv2">
							<?php

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
												'<td> <a href="editExpense1.php" class="btn btn-primary">Edit</a></td>' .
						                        '<td> <a href="deleteExpense1.php" class="btn btn-danger">Delete</a></td>' .
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
</div>
</div>
					
						<div id="vendorReportsTab" class="container-fluid tab-pane fade">
							<br>
							<p>Use the grid below to get reports for vendors</p>
							<div class="table-responsive" id="vendorReportsTableDiv"></div>		
					</div>
					</div>
				  </div> 
				</div>
			  </div>

			  <!-- WARE HOUSE PAGE -->
			  <div class="col-lg-10">
			<div class="tab-content" id="v-pills-tabContent">
			  <div class="tab-pane fade show active" id="v-pills-item2" role="tabpanel" aria-labelledby="v-pills-item-tab">
				<div class="card card-outline-secondary my-4">
					<h4>Ware House</h4>
				  <div class="card-header">Add Item</div>
				  <div class="card-body">
					<ul class="nav nav-tabs" role="tablist">
						<li class="nav-item">
							<a class="nav-link active" data-toggle="tab" href="#itemDetailsTab">Item</a>
						</li>
					</ul>
					
					<!-- Tab panes for item details and image sections -->
					<div class="tab-content">
						<div id="itemDetailsTab" class="container-fluid tab-pane active">
							<br>
							<!-- Div to show the ajax message from validations/db submission -->
							<div id="itemDetailsMessage2"></div>
							<form>
							  <div class="form-row">
								<div class="form-group col-md-3" style="display:inline-block">
								<label for="itemDetailsItemNumber2">Item Number<span class="requiredIcon">*</span></label>
									<input type="text" class="form-control" name="itemDetailsItemNumber2" id="itemDetailsItemNumber2" autocomplete="off">
								  <div id="itemDetailsItemNumberSuggestionsDiv2" class="customListDivWidth"></div>
								</div>
								<div class="form-group col-md-3">
								
								</div>
							  </div>
							  <div class="form-row">
								  <div class="form-group col-md-6">
									<label for="itemDetailsItemName2">Item Name<span class="requiredIcon">*</span></label>
									<input type="text" class="form-control" name="itemDetailsItemName2" id="itemDetailsItemName2" autocomplete="off">
									<div id="itemDetailsItemNameSuggestionsDiv2" class="customListDivWidth"></div>
								  </div>
								  
							  </div>
							  <div class="form-row">
								<div class="form-group col-md-6" style="display:inline-block">
								  <!-- <label for="itemDetailsDescription">Description</label> -->
								  <textarea rows="4" class="form-control" placeholder="Description" name="itemDetailsDescription2" id="itemDetailsDescription2"></textarea>
								</div>
							  </div>
							  <div class="form-row">
								
								<div class="form-group col-md-3">
								  <label for="itemDetailsQuantity2">Quantity<span class="requiredIcon">*</span></label>
								  <input type="number" class="form-control" value="" name="itemDetailsQuantity2" id="itemDetailsQuantity2">
								</div>
								<div class="form-group col-md-3">
								  <label for="itemDetailsUnitPrice2"> &#8358; Unit Price<span class="requiredIcon">*</span></label>
								  <input type="text" class="form-control" value="" name="itemDetailsUnitPrice2" id="itemDetailsUnitPrice2">
								</div>
								<div class="form-group col-md-3">
								  <label for="itemDetailsTotalStock2">Total Stock</label>
								  <input type="text" class="form-control" name="itemDetailsTotalStock2" id="itemDetailsTotalStock2" readonly>
								</div>
								<div class="form-group col-md-3">
									<div id="imageContainer"></div>
								</div>
							  </div>
							  <button type="button" id="addItem2" class="btn btn-success">Add Item</button>
							  <button type="reset" class="btn" id="itemClear">Clear</button>
							</form>
						</div>
					</div>
				  </div> 
				</div>
			  </div>
			  <div class="tab-pane fade" id="v-pills-purchase" role="tabpanel" aria-labelledby="v-pills-purchase-tab">
				<div class="card card-outline-secondary my-4">
				  <div class="card-header">Purchase Details</div>
				  <div class="card-body">
					<div id="purchaseDetailsMessage"></div>
					<form>
					  <div class="form-row">
						<div class="form-group col-md-3">
						  <label for="purchaseDetailsItemNumber">Item Number<span class="requiredIcon">*</span></label>
						  <input type="text" class="form-control" id="purchaseDetailsItemNumber" name="purchaseDetailsItemNumber" autocomplete="off">
						  <div id="purchaseDetailsItemNumberSuggestionsDiv" class="customListDivWidth"></div>
						</div>
						<div class="form-group col-md-3">
						  <label for="purchaseDetailsPurchaseDate">Purchase Date<span class="requiredIcon">*</span></label>
						  <input type="text" class="form-control datepicker" id="purchaseDetailsPurchaseDate" name="purchaseDetailsPurchaseDate" readonly value="2018-05-24">
						</div>
						<div class="form-group col-md-2">
						  <label for="purchaseDetailsPurchaseID">Purchase ID</label>
						  <input type="text" class="form-control invTooltip" id="purchaseDetailsPurchaseID" name="purchaseDetailsPurchaseID" title="This will be auto-generated when you add a new record" autocomplete="off">
						  <div id="purchaseDetailsPurchaseIDSuggestionsDiv" class="customListDivWidth"></div>
						</div>
					  </div>
					  <div class="form-row"> 
						  <div class="form-group col-md-4">
							<label for="purchaseDetailsItemName">Item Name<span class="requiredIcon">*</span></label>
							<input type="text" class="form-control invTooltip" id="purchaseDetailsItemName" name="purchaseDetailsItemName" readonly title="This will be auto-filled when you enter the item number above">
						  </div>
						  <div class="form-group col-md-2">
							  <label for="purchaseDetailsCurrentStock">Current Stock</label>
							  <input type="text" class="form-control" id="purchaseDetailsCurrentStock" name="purchaseDetailsCurrentStock" readonly>
						  </div>
						  <div class="form-group col-md-4">
							<label for="purchaseDetailsVendorName">Vendor Name<span class="requiredIcon">*</span></label>
							<select id="purchaseDetailsVendorName" name="purchaseDetailsVendorName" class="form-control chosenSelect">
								<?php 
									require('model/vendor/getVendorNames.php');
								?>
							</select>
						  </div>
					  </div>
					  <div class="form-row">
						<div class="form-group col-md-2">
						  <label for="purchaseDetailsQuantity">Quantity<span class="requiredIcon">*</span></label>
						  <input type="number" class="form-control" id="purchaseDetailsQuantity" name="purchaseDetailsQuantity" value="0">
						</div>
						<div class="form-group col-md-2">
						  <label for="purchaseDetailsUnitPrice">Unit Price<span class="requiredIcon">*</span></label>
						  <input type="text" class="form-control" id="purchaseDetailsUnitPrice" name="purchaseDetailsUnitPrice" value="0">
						  
						</div>
						<div class="form-group col-md-2">
						  <label for="purchaseDetailsTotal">Total Cost</label>
						  <input type="text" class="form-control" id="purchaseDetailsTotal" name="purchaseDetailsTotal" readonly>
						</div>
					  </div>
					  <!-- <button type="button" id="addPurchase" class="btn btn-success">Add Purchase</button> -->
					  <button type="button" id="updatePurchaseDetailsButton" class="btn btn-primary">Update</button>
					  <button type="reset" class="btn">Clear</button>
					</form>
				  </div> 
				</div>
			  </div>
			  
			  <div class="tab-pane fade" id="v-pills-vendor" role="tabpanel" aria-labelledby="v-pills-vendor-tab">
				<div class="card card-outline-secondary my-4">
				  <div class="card-header">Vendor Details</div>
				  <div class="card-body">
				  <!-- Div to show the ajax message from validations/db submission -->
				  <div id="vendorDetailsMessage"></div>
					 <form> 
					  <div class="form-row">
						<div class="form-group col-md-6">
						  <label for="vendorDetailsVendorFullName">Full Name<span class="requiredIcon">*</span></label>
						  <input type="text" class="form-control" id="vendorDetailsVendorFullName" name="vendorDetailsVendorFullName" placeholder="">
						</div>
						<div class="form-group col-md-2">
							<label for="vendorDetailsStatus">Status</label>
							<select id="vendorDetailsStatus" name="vendorDetailsStatus" class="form-control chosenSelect">
								<?php include('inc/statusList.html'); ?>
							</select>
						</div>
						 <div class="form-group col-md-3">
							<label for="vendorDetailsVendorID">Vendor ID</label>
							<input type="text" class="form-control invTooltip" id="vendorDetailsVendorID" name="vendorDetailsVendorID" title="This will be auto-generated when you add a new vendor" autocomplete="off">
							<div id="vendorDetailsVendorIDSuggestionsDiv" class="customListDivWidth"></div>
						</div>
					  </div>
					  <div class="form-row">
						  <div class="form-group col-md-3">
							<label for="vendorDetailsVendorMobile">Phone (mobile)<span class="requiredIcon">*</span></label>
							<input type="text" class="form-control invTooltip" id="vendorDetailsVendorMobile" name="vendorDetailsVendorMobile" title="Do not enter leading 0">
						  </div>
						  <div class="form-group col-md-3">
							<label for="vendorDetailsVendorPhone2">Phone 2</label>
							<input type="text" class="form-control invTooltip" id="vendorDetailsVendorPhone2" name="vendorDetailsVendorPhone2" title="Do not enter leading 0">
						  </div>
						  <div class="form-group col-md-6">
							<label for="vendorDetailsVendorEmail">Email</label>
							<input type="email" class="form-control" id="vendorDetailsVendorEmail" name="vendorDetailsVendorEmail">
						</div>
					  </div>
					  <div class="form-group">
						<label for="vendorDetailsVendorAddress">Address<span class="requiredIcon">*</span></label>
						<input type="text" class="form-control" id="vendorDetailsVendorAddress" name="vendorDetailsVendorAddress">
					  </div>
					  <div class="form-group">
						<label for="vendorDetailsVendorAddress2">Address 2</label>
						<input type="text" class="form-control" id="vendorDetailsVendorAddress2" name="vendorDetailsVendorAddress2">
					  </div>
					  <div class="form-row">
						<div class="form-group col-md-6">
						  <label for="vendorDetailsVendorCity">City</label>
						  <input type="text" class="form-control" id="vendorDetailsVendorCity" name="vendorDetailsVendorCity">
						</div>
						<div class="form-group col-md-4">
						  <label for="vendorDetailsVendorDistrict">District</label>
						  <select id="vendorDetailsVendorDistrict" name="vendorDetailsVendorDistrict" class="form-control chosenSelect">
							<?php include('inc/districtList.html'); ?>
						  </select>
						</div>
					  </div>					  
					  <button type="button" id="addVendor" name="addVendor" class="btn btn-success">Add Vendor</button>
					  <button type="button" id="updateVendorDetailsButton" class="btn btn-primary">Update</button>
					  <button type="button" id="deleteVendorButton" class="btn btn-danger">Delete</button>
					  <button type="reset" class="btn">Clear</button>
					 </form>
				  </div> 
				</div>
			  </div>
			  <div class="tab-pane fade" id="v-pills-sale" role="tabpanel" aria-labelledby="v-pills-sale-tab">
				<div class="card card-outline-secondary my-4">
				<div id="toprint">
				  <div class="card-header">Sale Details <br>
					 <h6 id="reg">(Register Customer before making Sale)</h6></div>
				  <div class="card-body">
					<form>
						<h3 id="cblack">Customer Details</h3>
						<!-- HERE -->
						<div class="form-row">
						<div class="form-group col-md-6">
						<div id="customerDetailsMessage"></div>
						  <label for="customerDetailsCustomerFullName">Full Name<span class="requiredIcon">*</span></label>
						  <input type="text" class="form-control" id="customerDetailsCustomerFullName" name="customerDetailsCustomerFullName">
						</div>
						<div class="form-group col-md-2">
						</div>
						 <div class="form-group col-md-3">
							<label for="customerDetailsCustomerID"id="reg">Customer ID</label>
							<input type="text" class="form-control invTooltip" id="customerDetailsCustomerID" name="customerDetailsCustomerID" title="This will be auto-generated when you add a new customer" autocomplete="off" readonly>
							<div id="customerDetailsCustomerIDSuggestionsDiv" class="customListDivWidth"></div>
						</div>
					  </div>
					  <div class="form-row">
						<!-- RETRUN TO FIX ERROR -->
						  <div class="form-group col-md-3">
							<label for="customerDetailsCustomerMobile">Phone (mobile)<span class="requiredIcon">*</span></label>
							<input type="text" class="form-control invTooltip" id="customerDetailsCustomerMobile" name="customerDetailsCustomerMobile">
						  </div>
						  <div class="form-group col-md-3">
						  </div>
						  <div class="form-group col-md-6">
						</div>
					  </div>
					  <div class="form-group">
						<label for="customerDetailsCustomerAddress" id="reg">Address<span class="requiredIcon">*</span></label>
						<input type="text" class="form-control" id="customerDetailsCustomerAddress" name="customerDetailsCustomerAddress" style="width:30%">
					  </div>
					  <div class="form-group">
					  </div>
					  <div class="form-row">
						<div class="form-group col-md-6">
						</div>
						<div class="form-group col-md-4">
						</div>
					  </div>					  
					  <button type="button" id="addCustomer" name="addCustomer" class="btn btn-success">Register Customer</button>
					  <button type="reset" class="btn" id="reg">Clear</button>
                         <br><br>
						 <h3 id="cblack">Sale</h3>

						 <div id="saleDetailsMessage"></div>
					  <div class="form-row">
						<div class="form-group col-md-3">
						
						  <label for="saleDetailsItemNumber"id="reg">Item Number<span class="requiredIcon">*</span></label>
						  <input type="text" class="form-control" id="saleDetailsItemNumber" name="saleDetailsItemNumber" autocomplete="off">
						  <div id="saleDetailsItemNumberSuggestionsDiv" class="customListDivWidth"></div>
						</div>
						<div class="form-group col-md-3">
							<label for="saleDetailsCustomerID" id="reg">Customer ID<span class="requiredIcon">*</span></label>
							<input type="text" class="form-control" id="saleDetailsCustomerID" name="saleDetailsCustomerID" autocomplete="off">
							<div id="saleDetailsCustomerIDSuggestionsDiv" class="customListDivWidth"></div>
						</div>
						<div class="form-group col-md-4">
						  <label for="saleDetailsCustomerName" id="reg">Customer Name</label>
						  <input type="text" class="form-control" id="saleDetailsCustomerName" name="saleDetailsCustomerName" readonly>
						</div>
						<div class="form-group col-md-2">
						  <label for="saleDetailsSaleID" id="reg">Sale ID</label>
						  <input type="text" class="form-control invTooltip" id="saleDetailsSaleID" name="saleDetailsSaleID" title="This will be auto-generated when you add a new record" autocomplete="off">
						  <div id="saleDetailsSaleIDSuggestionsDiv" class="customListDivWidth"></div>
						</div>
					  </div>
					  <div class="form-row">
						  <div class="form-group col-md-5">
							<label for="saleDetailsItemName">Item Name</label>
							<!--<select id="saleDetailsItemNames" name="saleDetailsItemNames" class="form-control chosenSelect"> -->
								<?php 
									//require('model/item/getItemDetails.php');
								?>
							<!-- </select> -->
							<input type="text" class="form-control invTooltip" id="saleDetailsItemName" name="saleDetailsItemName" readonly title="This will be auto-filled when you enter the item number above">
						  </div>
						  <div class="form-group col-md-3">
							  <label for="saleDetailsSaleDate">Sale Date<span class="requiredIcon">*</span></label>
							  <input type="text" class="form-control datepicker" id="saleDetailsSaleDate" value="" name="saleDetailsSaleDate" readonly>
						  </div>
					  </div>
					  <div class="form-row">
						<div class="form-group col-md-2">
								  <label for="saleDetailsTotalStock" id="reg">Total Stock</label>
								  <input type="text" class="form-control" name="saleDetailsTotalStock" id="saleDetailsTotalStock" readonly>
								</div>
						<div class="form-group col-md-2">
						  <label for="saleDetailsDiscount">Discount %</label>
						  <input type="text" class="form-control" id="saleDetailsDiscount" name="saleDetailsDiscount" value="">
						</div>
						<div class="form-group col-md-2">
						  <label for="saleDetailsQuantity">Quantity<span class="requiredIcon">*</span></label>
						  <input type="number" class="form-control" id="saleDetailsQuantity" name="saleDetailsQuantity" value="">
						</div>
						<div class="form-group col-md-2">
						  <label for="saleDetailsUnitPrice"> &#8358; Unit Price<span class="requiredIcon">*</span></label>
						  <input type="text" class="form-control" id="saleDetailsUnitPrice" name="saleDetailsUnitPrice" value="">
						</div>
						<div class="form-group col-md-3">
						  <label for="saleDetailsTotal"> &#8358; Total</label>
						  <input type="text" class="form-control" id="saleDetailsTotal" name="saleDetailsTotal">
						</div>
					  </div>
					  <div class="form-row">
						  <div class="form-group col-md-3">
							<div></div>
						  </div>
						  
					 </div>
					 <h6 id="reg">Dont forget to print sale receipt</h6>
					  <button type="button" id="addSaleButton" class="btn btn-success">Add Sale</button>
					  <button type="reset" id="saleClear" class="btn">Clear</button>
					  <button id="btnPrint" class="hidden-print">Print Receipt <ion-icon name="print-outline"></ion-icon></button>
				       <!-- Javascript for print receipt falls here -->
                       <script>
                       const $btnPrint = document.querySelector("#btnPrint");
                       $btnPrint.addEventListener("click", () => {
                       window.print();
                              });
							</script>
			         
						   
					  <!-- Custom Style for receipt(css) -->
					  <style>
						#saleClear{
							color:white;
							background-color:gray;
						}
						#btnPrint{
							font-size:20px;
							background-color:blue;
							color:white; 
							padding-inline-start: 20px;
                            padding-inline-end: 20px;
							padding: 7px;
						}
						@media Print{
							#customerDetailsMessage{
								display:none;
							}
						}
						
						@media Print{
							#btnPrint{
								display:none;
							}
						}
						@media Print{
							#cblack{
								color:black;
							}
						}
						@media Print{
							#saleDetailsCustomerName{
								display:none;
							}
						}
						 @media Print 
						   {
							#v-pills-item-tab{
								display:none;
							}
						   } 
						   @media Print 
						   {
							#v-pills-sale-tab{
								display:none;
							}
						   } 
						   @media Print 
						   {
							#v-pills-customer-tab{
								display:none;
							}
						   } 
						   @media Print 
						   {
							#v-pills-search-tab{
								display:none;
							}
						   } 
						   @media Print 
						   {
							#v-pills-reports-tab{
								display:none;
							}
						   } 
						 @media Print 
						   {
							#addSaleButton{
								display:none;
							}
						   } 
						   @media Print 
						   {
							#saleClear{
								display:none;
							}
						   } 
                           @media Print 
						   {
							input{
								width:90%;
								border-radius:50px;
							}
						   }
						   @media Print 
						   {
							#reg{
								display:none;
							}
						   } 
						   @media Print 
						   {
							#customerDetailsCustomerID{
								display:none;
							}
						   } 
						   @media Print 
						   {
							#customerDetailsCustomerAddress{
								display:none;
							}
						   } 
						   @media Print 
						   {
							#addCustomer{
								display:none;
							}
						   } 
						   @media Print 
						   {
							#saleDetailsMessage{
								display:none;
							}
						   } 
						   @media Print 
						   {
							#saleDetailsItemNumber{
								display:none;
							}
						   } 
						   @media Print 
						   {
							#saleDetailsCustomerID{
								display:none;
							}
						   } 
						   @media Print 
						   {
							#saleDetailsSaleID{
								display:none;
							}
						   }  
						   @media Print{
							#saleDetailsTotalStock{
								display:none;
							}
						   }
						   </style>
					</form>
				  </div> 
				</div>
			  </div>
			  </div>
			  <div class="tab-pane fade" id="v-pills-customer2" role="tabpanel" aria-labelledby="v-pills-customer-tab">
				<div class="card card-outline-secondary my-4">
					<h4>Ware House</h4>
				  <div class="card-header">Customer Details</div>
				  <br>
				  <a href="addCustomer2.php" style="font-size:20px;border-style:solid;border-radius:20px;width: 17%;"><ion-icon name="person-add-outline"></ion-icon>Add Customer</a>
				  <div class="card-body">
					
					 <!-- Div to show the ajax message from validations/db submission -->
					 <?php
	$customerDetailsSearchSql = 'SELECT * FROM customer2';
	$customerDetailsSearchStatement = $conn->prepare($customerDetailsSearchSql);
	$customerDetailsSearchStatement->execute();

	$output = '<table id="customerDetailsTable" class="table table-sm table-striped table-bordered table-hover" style="width:100%">
				<thead>
					<tr>
					    <th>Customer ID</th>
						<th>Full Name</th>
						<th>Email</th>
						<th>Mobile</th>
						<th>Address</th>
						<th>Edit</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>';
	
	// Create table rows from the selected data
	while($row = $customerDetailsSearchStatement->fetch(PDO::FETCH_ASSOC)){
		$output .= '<tr>' .
		                '<td>' . $row['customerID'] . '</td>' .
						'<td>' . $row['fullName'] . '</td>' .
						'<td>' . $row['email'] . '</td>' .
						'<td>' . $row['mobile'] . '</td>' .
						'<td>' . $row['address'] . '</td>' .
						'<td> <a href="editCustomer2.php" class="btn btn-primary">Edit Customer Details</a></td>' .
						'<td> <a href="deleteCustomer2.php" class="btn btn-danger">Delete Customer</a></td>' .
						
					'</tr>';
	}
	$customerDetailsSearchStatement->closeCursor();
	
	$output .= '</tbody>
					<tfoot>
						<tr>
						    <th>Customer ID</th>
							<th>Full Name</th>
							<th>Email</th>
							<th>Mobile</th>
							<th>Address</th>
							<th>Edit</th>
						    <th>Delete</th>
						</tr>
					</tfoot>
				</table>';
	echo $output;
?>

				  
				  </div> 
				</div>
			  </div>
			  <div class="tab-pane fade" id="v-pills-search2" role="tabpanel" aria-labelledby="v-pills-search-tab">
				<div class="card card-outline-secondary my-4">
				<h4>Ware House</h4>
				  <div class="card-header">Search Inventory</div>
				  <a href="index.php">Refresh</a>
				  <div class="card-body">										
					<ul class="nav nav-tabs" role="tablist">
						<li class="nav-item">
							<a class="nav-link active" data-toggle="tab" href="#itemSearchTab2">Item</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#customerSearchTab2">Customer</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#saleSearchTab2">Sale</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#purchaseSearchTab"></a>
						</li>
						<li class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#vendorSearchTab"></a>
						</li>
					</ul>
  
					<!-- Tab panes -->
					<div class="tab-content">
						<div id="itemSearchTab2" class="container-fluid tab-pane active">
						  <br>
						  <p>Use the grid below to search all details of items</p>
						  <!-- <a href="#" class="itemDetailsHover" data-toggle="popover" id="10">wwwee</a> -->
							<div class="table-responsive" id="itemDetailsTableDiv2">
								<?php
								$itemDetailsSearchSql = 'SELECT * FROM item2';
								$itemDetailsSearchStatement = $conn->prepare($itemDetailsSearchSql);
								$itemDetailsSearchStatement->execute();
								
								$output = '<table id="itemDetailsTable2" class="table table-sm table-striped table-bordered table-hover" style="width:100%">
											<thead>
												<tr>
												<th>Item Name</th>
                                                 <th>Stock</th>
												 <th>&#8358; Unit Price</th>
												<th>Description</th>
												<th>Item Total Amount</th>
												<th>Edit</th>
												<th>Delete</th>	
													
												</tr>
											</thead>
											<tbody>';
								
								// Create table rows from the selected data
								while($row = $itemDetailsSearchStatement->fetch(PDO::FETCH_ASSOC)){
									
									$output .= '<tr>' .
													'<td>' . $row['productID'] . '</td>' .
													
													'<td><a href="#" class="itemDetailsHover" data-toggle="popover" id="' . $row['productID'] . '">' . $row['itemName'] . '</a></td>' .
													'<td>' . $row['discount'] . '</td>' .
													'<td>' . $row['stock'] . '</td>' .
													'<td>' . $row['unitPrice'] . '</td>' .
													'<td>' . $row['status'] . '</td>' .
													'<td>' . $row['description'] . '</td>' .
												'</tr>';
								}
								
								$itemDetailsSearchStatement->closeCursor();
								
								$output .= '</tbody>
												<tfoot>
													<tr>
														<th>Product ID</th>
														<th>Item Name</th>
														<th>Discount %</th>
														<th>Stock</th>
														<th>&#8358; Unit Price</th>
														<th>Status</th>
														<th>Description</th>
													</tr>
												</tfoot>
											</table>';
								echo $output;							
								?>
							</div>
						</div>
						<div id="customerSearchTab2" class="container-fluid tab-pane fade">
						  <br>
						  <p>Use the grid below to search all details of customers</p>
							<div class="table-responsive" id="customerDetailsTableDiv2">
								<?php
								$customerDetailsSearchSql = 'SELECT * FROM customer2';
								$customerDetailsSearchStatement = $conn->prepare($customerDetailsSearchSql);
								$customerDetailsSearchStatement->execute();
							
								$output = '<table id="customerDetailsTable2" class="table table-sm table-striped table-bordered table-hover" style="width:100%">
											<thead>
												<tr>
													<th>Customer ID</th>
													<th>Full Name</th>
													<th>Email</th>
													<th>Mobile</th>
													<th>Phone 2</th>
													<th>Address</th>
													<th>Address 2</th>
													<th>City</th>
													<th>District</th>
													<th>Status</th>
												</tr>
											</thead>
											<tbody>';
								
								// Create table rows from the selected data
								while($row = $customerDetailsSearchStatement->fetch(PDO::FETCH_ASSOC)){
									$output .= '<tr>' .
													'<td>' . $row['customerID'] . '</td>' .
													'<td>' . $row['fullName'] . '</td>' .
													'<td>' . $row['email'] . '</td>' .
													'<td>' . $row['mobile'] . '</td>' .
													'<td>' . $row['phone2'] . '</td>' .
													'<td>' . $row['address'] . '</td>' .
													'<td>' . $row['address2'] . '</td>' .
													'<td>' . $row['city'] . '</td>' .
													'<td>' . $row['district'] . '</td>' .
													'<td>' . $row['status'] . '</td>' .
												'</tr>';
								}
								
								$customerDetailsSearchStatement->closeCursor();
								
								$output .= '</tbody>
												<tfoot>
													<tr>
														<th>Customer ID</th>
														<th>Full Name</th>
														<th>Email</th>
														<th>Mobile</th>
														<th>Phone 2</th>
														<th>Address</th>
														<th>Address 2</th>
														<th>City</th>
														<th>District</th>
														<th>Status</th>
													</tr>
												</tfoot>
											</table>';
								echo $output;									
							?>
						</div>
						</div>
						<div id="saleSearchTab2" class="container-fluid tab-pane fade">
							<br>
							<p>Use the grid below to search sale details</p>
							<div class="table-responsive" id="saleDetailsTableDiv2">
								<?php
								$uPrice = 0;
								$qty = 0;
								$totalPrice = 0;
								
								$saleDetailsSearchSql = 'SELECT * FROM sale2';
								$saleDetailsSearchStatement = $conn->prepare($saleDetailsSearchSql);
								$saleDetailsSearchStatement->execute();
							
								$output = '<table id="saleDetailsTable2" class="table table-sm table-striped table-bordered table-hover" style="width:100%">
											<thead>
												<tr>
													<th>Sale ID</th>													
													<th>Customer ID</th>
													<th>Customer Name</th>
													<th>Item Name</th>
													<th>Sale Date</th>
													<th>Discount %</th>
													<th>Quantity</th>
													<th> &#8358; Unit Price</th>
													<th> &#8358; Total Price</th>
												</tr>
											</thead>
											<tbody>';
								
								// Create table rows from the selected data
								while($row = $saleDetailsSearchStatement->fetch(PDO::FETCH_ASSOC)){
									$uPrice = $row['unitPrice'];
									$qty = $row['quantity'];
									$discount = $row['discount'];
									$totalPrice = $uPrice * $qty * ((100 - $discount)/100);
										
									$output .= '<tr>' .
													'<td>' . $row['saleID'] . '</td>' .
													'<td>' . $row['customerID'] . '</td>' .
													'<td>' . $row['customerName'] . '</td>' .
													'<td>' . $row['itemName'] . '</td>' .
													'<td>' . $row['saleDate'] . '</td>' .
													'<td>' . $row['discount'] . '</td>' .
													'<td>' . $row['quantity'] . '</td>' .
													'<td>' . $row['unitPrice'] . '</td>' .
													'<td>' . $totalPrice . '</td>' .
												'</tr>';
								}
								
								$saleDetailsSearchStatement->closeCursor();
								
								$output .= '</tbody>
												<tfoot>
													<tr>
														<th>Sale ID</th>
														<th>Customer ID</th>
														<th>Customer Name</th>
														<th>Item Name</th>
														<th>Sale Date</th>
														<th>Discount %</th>
														<th>Quantity</th>
														<th> &#8358; Unit Price</th>
														<th> &#8358; Total Price</th>
													</tr>
												</tfoot>
											</table>';
								echo $output;						
								?>



							</div>
						</div>
						<div id="purchaseSearchTab" class="container-fluid tab-pane fade">
							<br>
							<p>Use the grid below to search purchase details</p>
							<div class="table-responsive" id="purchaseDetailsTableDiv"></div>
						</div>
						<div id="vendorSearchTab" class="container-fluid tab-pane fade">
							<br>
							<p>Use the grid below to search vendor details</p>
							<div class="table-responsive" id="vendorDetailsTableDiv"></div>
						</div>
					</div>
				  </div> 
				</div>
			  </div>  
			  <div class="tab-pane fade" id="v-pills-reports2" role="tabpanel" aria-labelledby="v-pills-reports-tab">
				<div class="card card-outline-secondary my-4">
				<h4>Ware House</h4>
				  <div class="card-header">Reports</div>
				  <a href="index.php">Refresh</a>
				 
				  <div class="card-body">										
					<ul class="nav nav-tabs" role="tablist">
						<li class="nav-item">
							<a class="nav-link active" data-toggle="tab" href="#itemReportsTab2">Item</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#customerReportsTab2">Customer</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#saleReportsTab2">Sale</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#purchaseReportsTab"></a>
						</li>
						<li class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#vendorReportsTab"></a>
						</li>
					</ul>
  
					<!-- Tab panes for reports sections -->
					<div class="tab-content">
						<div id="itemReportsTab2" class="container-fluid tab-pane active">
							<br>
							<p>Use the grid below to get reports for items</p>
							<div class="table-responsive" id="itemReportsTableDiv2">
							<?php 
							$itemDetailsSearchSql = 'SELECT * FROM item2';
							$itemDetailsSearchStatement = $conn->prepare($itemDetailsSearchSql);
							$itemDetailsSearchStatement->execute();
						
							$output = '<table id="itemReportsTable2" class="table table-sm table-striped table-bordered table-hover" style="width:100%">
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
								                '<td>' . $row['itemNumber'] . '</td>' .
												'<td>' . $row['itemName'] . '</td>' .
												'<td>' . $row['stock'] . '</td>' .
												'<td>' . $row['unitPrice'] . '</td>' .
												'<td>' . $row['description'] . '</td>' .
												'<td>' . $row['stock'] *  $row['unitPrice'] . '</td>' .
												'<td> <a href="updateItem2.php" class="btn btn-warning">Update Item</a></td>' .
												'<td> <a href="editItem2.php" class="btn btn-primary">Edit Item</a></td>' .
						                        '<td> <a href="deleteItem2.php" class="btn btn-danger">Delete</a></td>' .
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




							</div>
						</div>
						<div id="customerReportsTab2" class="container-fluid tab-pane fade">
							<br>
							<p>Use the grid below to get reports for customers</p>
							<div class="table-responsive" id="customerReportsTableDiv2">
							<?php 
							$customerDetailsSearchSql = 'SELECT * FROM customer2';
							$customerDetailsSearchStatement = $conn->prepare($customerDetailsSearchSql);
							$customerDetailsSearchStatement->execute();
						
							$output = '<table id="customerReportsTable2" class="table table-sm table-striped table-bordered table-hover" style="width:100%">
										<thead>
											<tr>
											    <th>Customer ID</th>
												<th>Full Name</th>
												<th>Email</th>
												<th>Mobile</th>
												<th>Address</th>
												<th>Edit</th>
												<th>Delete</th>
												
											</tr>
										</thead>
										<tbody>';
							
							// Create table rows from the selected data
							while($row = $customerDetailsSearchStatement->fetch(PDO::FETCH_ASSOC)){
								$output .= '<tr>' .
												'<td>' . $row['customerID'] . '</td>' .
												'<td>' . $row['fullName'] . '</td>' .
												'<td>' . $row['email'] . '</td>' .
												'<td>' . $row['mobile'] . '</td>' .
												'<td>' . $row['address'] . '</td>' .
												'<td>  <a href="editCustomer2.php" class="btn btn-primary">Edit Customer Details</a></td>' .
						                         '<td> <a href="deleteCustomer2.php" class="btn btn-danger">Delete Customer</a></td>' .
											'</tr>';
							}
							
							$customerDetailsSearchStatement->closeCursor();
							
							$output .= '</tbody>
											<tfoot>
												<tr>
												<th>Customer ID</th>
												<th>Full Name</th>
												<th>Email</th>
												<th>Mobile</th>
												<th>Address</th>
												<th>Edit</th>
												<th>Delete</th>

												</tr>
											</tfoot>
										</table>';
							echo $output;
								
								
								?>
							</div>
						</div>
						<div id="saleReportsTab2" class="container-fluid tab-pane fade">
							<br>
							<!-- <p>Use the grid below to get reports for sales</p> -->
							<form> 
							  <div class="form-row">
								  <div class="form-group col-md-3">
									<label for="saleReportStartDate2">Start Date</label>
									<input type="text" class="form-control datepicker" id="saleReportStartDate2" value="" name="saleReportStartDate2" readonly>
								  </div>
								  <div class="form-group col-md-3">
									<label for="saleReportEndDate2">End Date</label>
									<input type="text" class="form-control datepicker" id="saleReportEndDate2" value="" name="saleReportEndDate2" readonly>
								  </div>
							  </div>
							  <button type="button" id="showSaleReport2" class="btn btn-dark">Show Report</button>
							  <button type="reset" id="saleFilterClear2" class="btn">Clear</button>
							</form>
							<br><br>
							<div class="table-responsive" id="saleReportsTableDiv2">
							<?php 
							$uPrice = 0;
							$qty = 0;
							$totalPrice = 0;
							
							$saleDetailsSearchSql = 'SELECT * FROM sale2';
							$saleDetailsSearchStatement = $conn->prepare($saleDetailsSearchSql);
							$saleDetailsSearchStatement->execute();
						
							$output = '<table id="saleReportsTable2" class="table table-sm table-striped table-bordered table-hover" style="width:100%">
										<thead>
											<tr>
												<th>Sale ID</th>
												<th>Customer Name</th>
												<th>Item Name</th>
												<th>Sale Date</th>
												<th>Quantity</th>
												<th>&#8358; Unit Price</th>
												<th>&#8358; Total Price</th>
												<th>Edit</th>
												<th>Delete</th>	

											</tr>
										</thead>
										<tbody>';
							
							// Create table rows from the selected data
							while($row = $saleDetailsSearchStatement->fetch(PDO::FETCH_ASSOC)){
								$uPrice = $row['unitPrice'];
								$qty = $row['quantity'];
								$discount = $row['discount'];
								$totalPrice = $uPrice * $qty * ((100 - $discount)/100);
								
								 $output .= '<tr>' .
								                '<td>' . $row['saleID'] . '</td>' .
												'<td>' . $row['customerName'] . '</td>' .
												'<td>' . $row['itemName'] . '</td>' .
												'<td>' . $row['saleDate'] . '</td>' .
												'<td>' . $row['quantity'] . '</td>' .
												'<td>' . $row['unitPrice'] . '</td>' .
												'<td>' . $totalPrice . '</td>' .
												'<td> <a href="editSale2.php" class="btn btn-primary">Edit Sale Details</a></td>' .
						                        '<td> <a href="deleteSale2.php" class="btn btn-danger">Delete</a></td>' .
											'</tr>';
							}
							
							$saleDetailsSearchStatement->closeCursor();
							
							$output .= '</tbody>
											<tfoot>
												<tr>
												<th>Sale ID</th>
												<th>Customer Name</th>
												<th>Item Name</th>
												<th>Sale Date</th>
												<th>Quantity</th>
												<th>&#8358; Unit Price</th>
												<th>&#8358; Total Price</th>
												<th>Edit</th>
												<th>Delete</th>	
												</tr>
											</tfoot>
										</table>';
							echo $output;
									
								?>
							</div>
						</div>
						<div id="purchaseReportsTab" class="container-fluid tab-pane fade">
							<br>
							<!-- <p>Use the grid below to get reports for purchases</p> -->
							<form> 
							  <div class="form-row">
								  <div class="form-group col-md-3">
									<label for="purchaseReportStartDate">Start Date</label>
									<input type="text" class="form-control datepicker" id="purchaseReportStartDate" value="" name="purchaseReportStartDate" readonly>
								  </div>
								  <div class="form-group col-md-3">
									<label for="purchaseReportEndDate">End Date</label>
									<input type="text" class="form-control datepicker" id="purchaseReportEndDate" value="" name="purchaseReportEndDate" readonly>
								  </div>
							  </div>
							  <button type="button" id="showPurchaseReport" class="btn btn-dark">Show Report</button>
							  <button type="reset" id="purchaseFilterClear" class="btn">Clear</button>
							</form>
							<br><br>
							<div class="table-responsive" id="purchaseReportsTableDiv"></div>
						</div>
						<div id="vendorReportsTab" class="container-fluid tab-pane fade">
							<br>
							<p>Use the grid below to get reports for vendors</p>
							<div class="table-responsive" id="vendorReportsTableDiv"></div>		
					</div>
					</div>
				  </div> 
				</div>
			  </div>
			  
			  <!-- WINNER'S FOAM PAGE CONTENT-->
		 <div class="col-lg-10">
			<div class="tab-content" id="v-pills-tabContent">
			  <div class="tab-pane fade show active" id="v-pills-item3" role="tabpanel" aria-labelledby="v-pills-item-tab">
				<div class="card card-outline-secondary my-4">
					<h4>Winner's Foam</h4>
				  <div class="card-header">Add Item</div>
				  <div class="card-body">
					<ul class="nav nav-tabs" role="tablist">
						<li class="nav-item">
							<a class="nav-link active" data-toggle="tab" href="#itemDetailsTab3">Item</a>
						</li>
					</ul>					
	            	<!-- Tab panes for item details and image sections -->
					<div class="tab-content">
						<div id="itemDetailsTab3" class="container-fluid tab-pane active">
							<br>
							<!-- Div to show the ajax message from validations/db submission -->
							<div id="itemDetailsMessage3"></div>
							<form>
							  <div class="form-row">
								<div class="form-group col-md-3" style="display:inline-block">
								<label for="itemDetailsItemNumber3">Item Number<span class="requiredIcon">*</span></label>
									<input type="text" class="form-control" name="itemDetailsItemNumber3" id="itemDetailsItemNumber3" autocomplete="off">
								  <div id="itemDetailsItemNumberSuggestionsDiv3" class="customListDivWidth"></div>
								</div>
								<div class="form-group col-md-3">
								  
								</div>
							  </div>
							  <div class="form-row">
								  <div class="form-group col-md-6">
									<label for="itemDetailsItemName3">Item Name<span class="requiredIcon">*</span></label>
									<input type="text" class="form-control" name="itemDetailsItemName3" id="itemDetailsItemName3" autocomplete="off">
									<div id="itemDetailsItemNameSuggestionsDiv3" class="customListDivWidth"></div>
								  </div>
								  
							  </div>
							  <div class="form-row">
								<div class="form-group col-md-6" style="display:inline-block">
								  <!-- <label for="itemDetailsDescription">Description</label> -->
								  <textarea rows="4" class="form-control" placeholder="Description" name="itemDetailsDescription3" id="itemDetailsDescription3"></textarea>
								</div>
							  </div>
							  <div class="form-row">
								
								<div class="form-group col-md-3">
								  <label for="itemDetailsQuantity3">Quantity<span class="requiredIcon">*</span></label>
								  <input type="number" class="form-control" value="" name="itemDetailsQuantity3" id="itemDetailsQuantity3">
								</div>
								<div class="form-group col-md-3">
								  <label for="itemDetailsUnitPrice3"> &#8358; Unit Price<span class="requiredIcon">*</span></label>
								  <input type="text" class="form-control" value="" name="itemDetailsUnitPrice3" id="itemDetailsUnitPrice3">
								</div>
								<div class="form-group col-md-3">
								  <label for="itemDetailsTotalStock3">Total Stock</label>
								  <input type="text" class="form-control" name="itemDetailsTotalStock3" id="itemDetailsTotalStock3" readonly>
								</div>
								<div class="form-group col-md-3">
									<div id="imageContainer"></div>
								</div>
							  </div>
							  <button type="button" id="addItem3" class="btn btn-success">Add Item</button>
							  <button type="reset" class="btn" id="itemClear3">Clear</button>
							</form>
						</div>
					</div>
				  </div> 
				</div>
			  </div>
			  <div class="tab-pane fade" id="v-pills-purchase" role="tabpanel" aria-labelledby="v-pills-purchase-tab">
				<div class="card card-outline-secondary my-4">
				  <div class="card-header">Purchase Details</div>
				  <div class="card-body">
					<div id="purchaseDetailsMessage"></div>
					<form>
					  <div class="form-row">
						<div class="form-group col-md-3">
						  <label for="purchaseDetailsItemNumber">Item Number<span class="requiredIcon">*</span></label>
						  <input type="text" class="form-control" id="purchaseDetailsItemNumber" name="purchaseDetailsItemNumber" autocomplete="off">
						  <div id="purchaseDetailsItemNumberSuggestionsDiv" class="customListDivWidth"></div>
						</div>
						<div class="form-group col-md-3">
						  <label for="purchaseDetailsPurchaseDate">Purchase Date<span class="requiredIcon">*</span></label>
						  <input type="text" class="form-control datepicker" id="purchaseDetailsPurchaseDate" name="purchaseDetailsPurchaseDate" readonly value="2018-05-24">
						</div>
						<div class="form-group col-md-2">
						  <label for="purchaseDetailsPurchaseID">Purchase ID</label>
						  <input type="text" class="form-control invTooltip" id="purchaseDetailsPurchaseID" name="purchaseDetailsPurchaseID" title="This will be auto-generated when you add a new record" autocomplete="off">
						  <div id="purchaseDetailsPurchaseIDSuggestionsDiv" class="customListDivWidth"></div>
						</div>
					  </div>
					  <div class="form-row"> 
						  <div class="form-group col-md-4">
							<label for="purchaseDetailsItemName">Item Name<span class="requiredIcon">*</span></label>
							<input type="text" class="form-control invTooltip" id="purchaseDetailsItemName" name="purchaseDetailsItemName" readonly title="This will be auto-filled when you enter the item number above">
						  </div>
						  <div class="form-group col-md-2">
							  <label for="purchaseDetailsCurrentStock">Current Stock</label>
							  <input type="text" class="form-control" id="purchaseDetailsCurrentStock" name="purchaseDetailsCurrentStock" readonly>
						  </div>
						  <div class="form-group col-md-4">
							<label for="purchaseDetailsVendorName">Vendor Name<span class="requiredIcon">*</span></label>
							<select id="purchaseDetailsVendorName" name="purchaseDetailsVendorName" class="form-control chosenSelect">
								<?php 
									require('model/vendor/getVendorNames.php');
								?>
							</select>
						  </div>
					  </div>
					  <div class="form-row">
						<div class="form-group col-md-2">
						  <label for="purchaseDetailsQuantity">Quantity<span class="requiredIcon">*</span></label>
						  <input type="number" class="form-control" id="purchaseDetailsQuantity" name="purchaseDetailsQuantity" value="0">
						</div>
						<div class="form-group col-md-2">
						  <label for="purchaseDetailsUnitPrice">Unit Price<span class="requiredIcon">*</span></label>
						  <input type="text" class="form-control" id="purchaseDetailsUnitPrice" name="purchaseDetailsUnitPrice" value="0">
						  
						</div>
						<div class="form-group col-md-2">
						  <label for="purchaseDetailsTotal">Total Cost</label>
						  <input type="text" class="form-control" id="purchaseDetailsTotal" name="purchaseDetailsTotal" readonly>
						</div>
					  </div>
					  <!-- <button type="button" id="addPurchase" class="btn btn-success">Add Purchase</button> -->
					  <button type="button" id="updatePurchaseDetailsButton" class="btn btn-primary">Update</button>
					  <button type="reset" class="btn">Clear</button>
					</form>
				  </div> 
				</div>
			  </div>
			  
			  <div class="tab-pane fade" id="v-pills-vendor" role="tabpanel" aria-labelledby="v-pills-vendor-tab">
				<div class="card card-outline-secondary my-4">
				  <div class="card-header">Vendor Details</div>
				  <div class="card-body">
				  <!-- Div to show the ajax message from validations/db submission -->
				  <div id="vendorDetailsMessage"></div>
					 <form> 
					  <div class="form-row">
						<div class="form-group col-md-6">
						  <label for="vendorDetailsVendorFullName">Full Name<span class="requiredIcon">*</span></label>
						  <input type="text" class="form-control" id="vendorDetailsVendorFullName" name="vendorDetailsVendorFullName" placeholder="">
						</div>
						<div class="form-group col-md-2">
							<label for="vendorDetailsStatus">Status</label>
							<select id="vendorDetailsStatus" name="vendorDetailsStatus" class="form-control chosenSelect">
								<?php include('inc/statusList.html'); ?>
							</select>
						</div>
						 <div class="form-group col-md-3">
							<label for="vendorDetailsVendorID">Vendor ID</label>
							<input type="text" class="form-control invTooltip" id="vendorDetailsVendorID" name="vendorDetailsVendorID" title="This will be auto-generated when you add a new vendor" autocomplete="off">
							<div id="vendorDetailsVendorIDSuggestionsDiv" class="customListDivWidth"></div>
						</div>
					  </div>
					  <div class="form-row">
						  <div class="form-group col-md-3">
							<label for="vendorDetailsVendorMobile">Phone (mobile)<span class="requiredIcon">*</span></label>
							<input type="text" class="form-control invTooltip" id="vendorDetailsVendorMobile" name="vendorDetailsVendorMobile" title="Do not enter leading 0">
						  </div>
						  <div class="form-group col-md-3">
							<label for="vendorDetailsVendorPhone2">Phone 2</label>
							<input type="text" class="form-control invTooltip" id="vendorDetailsVendorPhone2" name="vendorDetailsVendorPhone2" title="Do not enter leading 0">
						  </div>
						  <div class="form-group col-md-6">
							<label for="vendorDetailsVendorEmail">Email</label>
							<input type="email" class="form-control" id="vendorDetailsVendorEmail" name="vendorDetailsVendorEmail">
						</div>
					  </div>
					  <div class="form-group">
						<label for="vendorDetailsVendorAddress">Address<span class="requiredIcon">*</span></label>
						<input type="text" class="form-control" id="vendorDetailsVendorAddress" name="vendorDetailsVendorAddress">
					  </div>
					  <div class="form-group">
						<label for="vendorDetailsVendorAddress2">Address 2</label>
						<input type="text" class="form-control" id="vendorDetailsVendorAddress2" name="vendorDetailsVendorAddress2">
					  </div>
					  <div class="form-row">
						<div class="form-group col-md-6">
						  <label for="vendorDetailsVendorCity">City</label>
						  <input type="text" class="form-control" id="vendorDetailsVendorCity" name="vendorDetailsVendorCity">
						</div>
						<div class="form-group col-md-4">
						  <label for="vendorDetailsVendorDistrict">District</label>
						  <select id="vendorDetailsVendorDistrict" name="vendorDetailsVendorDistrict" class="form-control chosenSelect">
							<?php include('inc/districtList.html'); ?>
						  </select>
						</div>
					  </div>					  
					  <button type="button" id="addVendor" name="addVendor" class="btn btn-success">Add Vendor</button>
					  <button type="button" id="updateVendorDetailsButton" class="btn btn-primary">Update</button>
					  <button type="button" id="deleteVendorButton" class="btn btn-danger">Delete</button>
					  <button type="reset" class="btn">Clear</button>
					 </form>
				  </div> 
				</div>
			  </div>
			  <div class="tab-pane fade" id="v-pills-sale" role="tabpanel" aria-labelledby="v-pills-sale-tab">
				<div class="card card-outline-secondary my-4">
				<div id="toprint">
				  <div class="card-header">Sale Details <br>
					 <h6 id="reg">(Register Customer before making Sale)</h6></div>
				  <div class="card-body">
					<form>
						<h3 id="cblack">Customer Details</h3>
						<!-- HERE -->
						<div class="form-row">
						<div class="form-group col-md-6">
						<div id="customerDetailsMessage"></div>
						  <label for="customerDetailsCustomerFullName">Full Name<span class="requiredIcon">*</span></label>
						  <input type="text" class="form-control" id="customerDetailsCustomerFullName" name="customerDetailsCustomerFullName">
						</div>
						<div class="form-group col-md-2">
						</div>
						 <div class="form-group col-md-3">
							<label for="customerDetailsCustomerID"id="reg">Customer ID</label>
							<input type="text" class="form-control invTooltip" id="customerDetailsCustomerID" name="customerDetailsCustomerID" title="This will be auto-generated when you add a new customer" autocomplete="off" readonly>
							<div id="customerDetailsCustomerIDSuggestionsDiv" class="customListDivWidth"></div>
						</div>
					  </div>
					  <div class="form-row">
						<!-- RETRUN TO FIX ERROR -->
						  <div class="form-group col-md-3">
							<label for="customerDetailsCustomerMobile">Phone (mobile)<span class="requiredIcon">*</span></label>
							<input type="text" class="form-control invTooltip" id="customerDetailsCustomerMobile" name="customerDetailsCustomerMobile">
						  </div>
						  <div class="form-group col-md-3">
						  </div>
						  <div class="form-group col-md-6">
						</div>
					  </div>
					  <div class="form-group">
						<label for="customerDetailsCustomerAddress" id="reg">Address<span class="requiredIcon">*</span></label>
						<input type="text" class="form-control" id="customerDetailsCustomerAddress" name="customerDetailsCustomerAddress" style="width:30%">
					  </div>
					  <div class="form-group">
					  </div>
					  <div class="form-row">
						<div class="form-group col-md-6">
						</div>
						<div class="form-group col-md-4">
						</div>
					  </div>					  
					  <button type="button" id="addCustomer" name="addCustomer" class="btn btn-success">Register Customer</button>
					  <button type="reset" class="btn" id="reg">Clear</button>
                         <br><br>
						 <h3 id="cblack">Sale</h3>

						 <div id="saleDetailsMessage"></div>
					  <div class="form-row">
						<div class="form-group col-md-3">
						
						  <label for="saleDetailsItemNumber"id="reg">Item Number<span class="requiredIcon">*</span></label>
						  <input type="text" class="form-control" id="saleDetailsItemNumber" name="saleDetailsItemNumber" autocompete="off">
						  <div id="saleDetailsItemNumberSuggestionsDiv" class="customListDivWidth"></div>
						</div>
						<div class="form-group col-md-3">
							<label for="saleDetailsCustomerID" id="reg">Customer ID<span class="requiredIcon">*</span></label>
							<input type="text" class="form-control" id="saleDetailsCustomerID" name="saleDetailsCustomerID" autocomplete="off">
							<div id="saleDetailsCustomerIDSuggestionsDiv" class="customListDivWidth"></div>
						</div>
						<div class="form-group col-md-4">
						  <label for="saleDetailsCustomerName" id="reg">Customer Name</label>
						  <input type="text" class="form-control" id="saleDetailsCustomerName" name="saleDetailsCustomerName" readonly>
						</div>
						<div class="form-group col-md-2">
						  <label for="saleDetailsSaleID" id="reg">Sale ID</label>
						  <input type="text" class="form-control invTooltip" id="saleDetailsSaleID" name="saleDetailsSaleID" title="This will be auto-generated when you add a new record" autocomplete="off">
						  <div id="saleDetailsSaleIDSuggestionsDiv" class="customListDivWidth"></div>
						</div>
					  </div>
					  <div class="form-row">
						  <div class="form-group col-md-5">
							<label for="saleDetailsItemName">Item Name</label>
							<!--<select id="saleDetailsItemNames" name="saleDetailsItemNames" class="form-control chosenSelect"> -->
								<?php 
									//require('model/item/getItemDetails.php');
								?>
							<!-- </select> -->
							<input type="text" class="form-control invTooltip" id="saleDetailsItemName" name="saleDetailsItemName" readonly title="This will be auto-filled when you enter the item number above">
						  </div>
						  <div class="form-group col-md-3">
							  <label for="saleDetailsSaleDate">Sale Date<span class="requiredIcon">*</span></label>
							  <input type="text" class="form-control datepicker" id="saleDetailsSaleDate" value="" name="saleDetailsSaleDate" readonly>
						  </div>
					  </div>
					  <div class="form-row">
						<div class="form-group col-md-2">
								  <label for="saleDetailsTotalStock" id="reg">Total Stock</label>
								  <input type="text" class="form-control" name="saleDetailsTotalStock" id="saleDetailsTotalStock" readonly>
								</div>
						<div class="form-group col-md-2">
						  <label for="saleDetailsDiscount">Discount %</label>
						  <input type="text" class="form-control" id="saleDetailsDiscount" name="saleDetailsDiscount" value="">
						</div>
						<div class="form-group col-md-2">
						  <label for="saleDetailsQuantity">Quantity<span class="requiredIcon">*</span></label>
						  <input type="number" class="form-control" id="saleDetailsQuantity" name="saleDetailsQuantity" value="">
						</div>
						<div class="form-group col-md-2">
						  <label for="saleDetailsUnitPrice"> &#8358; Unit Price<span class="requiredIcon">*</span></label>
						  <input type="text" class="form-control" id="saleDetailsUnitPrice" name="saleDetailsUnitPrice" value="">
						</div>
						<div class="form-group col-md-3">
						  <label for="saleDetailsTotal"> &#8358; Total</label>
						  <input type="text" class="form-control" id="saleDetailsTotal" name="saleDetailsTotal">
						</div>
					  </div>
					  <div class="form-row">
						  <div class="form-group col-md-3">
							<div></div>
						  </div>
						  
					 </div>
					 <h6 id="reg">Dont forget to print sale receipt</h6>
					  <button type="button" id="addSaleButton" class="btn btn-success">Add Sale</button>
					  <button type="reset" id="saleClear" class="btn">Clear</button>
					  <button id="btnPrint" class="hidden-print">Print Receipt <ion-icon name="print-outline"></ion-icon></button>
				       <!-- Javascript for print receipt falls here -->
                       <script>
                       const $btnPrint = document.querySelector("#btnPrint");
                       $btnPrint.addEventListener("click", () => {
                       window.print();
                              });
							</script>
					  <!-- Custom Style for receipt(css) -->
					  <style>
						#saleClear{
							color:white;
							background-color:gray;
						}
						#btnPrint{
							font-size:20px;
							background-color:blue;
							color:white; 
							padding-inline-start: 20px;
                            padding-inline-end: 20px;
							padding: 7px;
						}
						@media Print{
							#customerDetailsMessage{
								display:none;
							}
						}
						
						@media Print{
							#btnPrint{
								display:none;
							}
						}
						@media Print{
							#cblack{
								color:black;
							}
						}
						@media Print{
							#saleDetailsCustomerName{
								display:none;
							}
						}
						 @media Print 
						   {
							#v-pills-item-tab{
								display:none;
							}
						   } 
						   @media Print 
						   {
							#v-pills-sale-tab{
								display:none;
							}
						   } 
						   @media Print 
						   {
							#v-pills-customer-tab{
								display:none;
							}
						   } 
						   @media Print 
						   {
							#v-pills-search-tab{
								display:none;
							}
						   } 
						   @media Print 
						   {
							#v-pills-reports-tab{
								display:none;
							}
						   } 
						 @media Print 
						   {
							#addSaleButton{
								display:none;
							}
						   } 
						   @media Print 
						   {
							#saleClear{
								display:none;
							}
						   } 
                           @media Print 
						   {
							input{
								width:90%;
								border-radius:50px;
							}
						   }
						   @media Print 
						   {
							#reg{
								display:none;
							}
						   } 
						   @media Print 
						   {
							#customerDetailsCustomerID{
								display:none;
							}
						   } 
						   @media Print 
						   {
							#customerDetailsCustomerAddress{
								display:none;
							}
						   } 
						   @media Print 
						   {
							#addCustomer{
								display:none;
							}
						   } 
						   @media Print 
						   {
							#saleDetailsMessage{
								display:none;
							}
						   } 
						   @media Print 
						   {
							#saleDetailsItemNumber{
								display:none;
							}
						   } 
						   @media Print 
						   {
							#saleDetailsCustomerID{
								display:none;
							}
						   } 
						   @media Print 
						   {
							#saleDetailsSaleID{
								display:none;
							}
						   }  
						   @media Print{
							#saleDetailsTotalStock{
								display:none;
							}
						   }
						   </style>
					</form>
				  </div> 
				</div>
			  </div>
			  </div>
			  <div class="tab-pane fade" id="v-pills-customer3" role="tabpanel" aria-labelledby="v-pills-customer-tab">
				<div class="card card-outline-secondary my-4">
					<h4>Winner's Foam</h4>
				  <div class="card-header">Customer Details</div>
				  <br>
				  <a href="addCustomer3.php" style="font-size:20px;border-style:solid;border-radius:20px;width: 21%;"><ion-icon name="person-add-outline"></ion-icon>Add Customer</a>
				  <div class="card-body">
				  <!-- Div to show the ajax message from validations/db submission -->
				   <!-- Div to show the ajax message from validations/db submission -->
				   <?php
	$customerDetailsSearchSql = 'SELECT * FROM customer3';
	$customerDetailsSearchStatement = $conn->prepare($customerDetailsSearchSql);
	$customerDetailsSearchStatement->execute();

	$output = '<table id="customerDetailsTable" class="table table-sm table-striped table-bordered table-hover" style="width:100%">
				<thead>
					<tr>
						<th>Customer ID</th>
						<th>Full Name</th>
						<th>Email</th>
						<th>Mobile</th>
						<th>Address</th>
						<th>Edit</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>';
	
	// Create table rows from the selected data
	while($row = $customerDetailsSearchStatement->fetch(PDO::FETCH_ASSOC)){
		$output .= '<tr>' .
		                '<td>' . $row['customerID'] . '</td>' .
						'<td>' . $row['fullName'] . '</td>' .
						'<td>' . $row['email'] . '</td>' .
						'<td>' . $row['mobile'] . '</td>' .
						'<td>' . $row['address'] . '</td>' .
						'<td>' . $row['city'] . '</td>' .
						'<td> <a href="editCustomer3.php" class="btn btn-primary">Edit Customer Details</a></td>' .
                        '<td> <a href="deleteCustomer3.php?action=delete" class="btn btn-danger">Delete Customer</a></td>' .
					'</tr>';
	}
	$customerDetailsSearchStatement->closeCursor();
	
	$output .= '</tbody>
					<tfoot>
						<tr>
						    <th>Customer ID</th>
							<th>Full Name</th>
							<th>Email</th>
							<th>Mobile</th>
							<th>Address</th>
							<th>Edit</th>
							<th>Delete</th>
						</tr>
					</tfoot>
				</table>';
	echo $output;
?>
				  
				  </div> 
				</div>
			  </div>
			  
			  <div class="tab-pane fade" id="v-pills-search3" role="tabpanel" aria-labelledby="v-pills-search-tab">
				<div class="card card-outline-secondary my-4">
				<h4>Winner's Foam</h4>
				  <div class="card-header">Search Inventory</div>
				  <a href="index.php">Refresh</a>
				  <div class="card-body">										
					<ul class="nav nav-tabs" role="tablist">
						<li class="nav-item">
							<a class="nav-link active" data-toggle="tab" href="#itemSearchTab3">Item</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#customerSearchTab3">Customer</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#saleSearchTab3">Sale</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#purchaseSearchTab"></a>
						</li>
						<li class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#vendorSearchTab"></a>
						</li>
					</ul>
  
					<!-- Tab panes -->
					<div class="tab-content">
						<div id="itemSearchTab3" class="container-fluid tab-pane active">
						  <br>
						  <p>Use the grid below to search all details of items</p>
						  <!-- <a href="#" class="itemDetailsHover" data-toggle="popover" id="10">wwwee</a> -->
							<div class="table-responsive" id="itemDetailsTableDiv2">
								<?php 
								$itemDetailsSearchSql = 'SELECT * FROM item3';
								$itemDetailsSearchStatement = $conn->prepare($itemDetailsSearchSql);
								$itemDetailsSearchStatement->execute();
								
								$output = '<table id="itemDetailsTable3" class="table table-sm table-striped table-bordered table-hover" style="width:100%">
											<thead>
												<tr>
													<th>Product ID</th>
													<th>Item Number</th>
													<th>Item Name</th>
													<th>Discount %</th>
													<th>Stock</th>
													<th>&#8358; Unit Price</th>
													<th>Status</th>
													<th>Description</th>
												</tr>
											</thead>
											<tbody>';
								
								// Create table rows from the selected data
								while($row = $itemDetailsSearchStatement->fetch(PDO::FETCH_ASSOC)){
									
									$output .= '<tr>' .
													'<td>' . $row['productID'] . '</td>' .
													'<td>' . $row['itemNumber'] . '</td>' .
													'<td><a href="#" class="itemDetailsHover" data-toggle="popover" id="' . $row['productID'] . '">' . $row['itemName'] . '</a></td>' .
													'<td>' . $row['discount'] . '</td>' .
													'<td>' . $row['stock'] . '</td>' .
													'<td>' . $row['unitPrice'] . '</td>' .
													'<td>' . $row['status'] . '</td>' .
													'<td>' . $row['description'] . '</td>' .
												'</tr>';
								}
								
								$itemDetailsSearchStatement->closeCursor();
								
								$output .= '</tbody>
												<tfoot>
													<tr>
														<th>Product ID</th>
														<th>Item Number</th>
														<th>Item Name</th>
														<th>Discount %</th>
														<th>Stock</th>
														<th>&#8358; Unit Price</th>
														<th>Status</th>
														<th>Description</th>
													</tr>
												</tfoot>
											</table>';
								echo $output;
								
								?>


							</div>
						</div>
						<div id="customerSearchTab3" class="container-fluid tab-pane fade">
						  <br>
						  <p>Use the grid below to search all details of customers</p>
							<div class="table-responsive" id="customerDetailsTableDiv2">
							<?php 
							$customerDetailsSearchSql = 'SELECT * FROM customer3';
							$customerDetailsSearchStatement = $conn->prepare($customerDetailsSearchSql);
							$customerDetailsSearchStatement->execute();
						
							$output = '<table id="customerReportsTable3" class="table table-sm table-striped table-bordered table-hover" style="width:100%">
										<thead>
											<tr>
												<th>Customer ID</th>
												<th>Full Name</th>
												<th>Email</th>
												<th>Mobile</th>
												<th>Phone 2</th>
												<th>Address</th>
												<th>Address 2</th>
												<th>City</th>
												<th>District</th>
												<th>Status</th>
											</tr>
										</thead>
										<tbody>';
							
							// Create table rows from the selected data
							while($row = $customerDetailsSearchStatement->fetch(PDO::FETCH_ASSOC)){
								$output .= '<tr>' .
												'<td>' . $row['customerID'] . '</td>' .
												'<td>' . $row['fullName'] . '</td>' .
												'<td>' . $row['email'] . '</td>' .
												'<td>' . $row['mobile'] . '</td>' .
												'<td>' . $row['phone2'] . '</td>' .
												'<td>' . $row['address'] . '</td>' .
												'<td>' . $row['address2'] . '</td>' .
												'<td>' . $row['city'] . '</td>' .
												'<td>' . $row['district'] . '</td>' .
												'<td>' . $row['status'] . '</td>' .
											'</tr>';
							}
							
							$customerDetailsSearchStatement->closeCursor();
							
							$output .= '</tbody>
											<tfoot>
												<tr>
													<th>Customer ID</th>
													<th>Full Name</th>
													<th>Email</th>
													<th>Mobile</th>
													<th>Phone 2</th>
													<th>Address</th>
													<th>Address 2</th>
													<th>City</th>
													<th>District</th>
													<th>Status</th>
												</tr>
											</tfoot>
										</table>';
							echo $output;	
								?>
							</div>
						</div>
						<div id="saleSearchTab3" class="container-fluid tab-pane fade">
							<br>
							<p>Use the grid below to search sale details</p>
							<div class="table-responsive" id="saleDetailsTableDiv2">
							<?php
							$uPrice = 0;
							$qty = 0;
							$totalPrice = 0;
							
							$saleDetailsSearchSql = 'SELECT * FROM sale3';
							$saleDetailsSearchStatement = $conn->prepare($saleDetailsSearchSql);
							$saleDetailsSearchStatement->execute();
						
							$output = '<table id="saleDetailsTable3" class="table table-sm table-striped table-bordered table-hover" style="width:100%">
										<thead>
											<tr>
												<th>Sale ID</th>
												<th>Customer ID</th>
												<th>Customer Name</th>
												<th>Item Name</th>
												<th>Sale Date</th>
												<th>Discount %</th>
												<th>Quantity</th>
												<th> &#8358; Unit Price</th>
												<th> &#8358; Total Price</th>
											</tr>
										</thead>
										<tbody>';
							
							// Create table rows from the selected data
							while($row = $saleDetailsSearchStatement->fetch(PDO::FETCH_ASSOC)){
								$uPrice = $row['unitPrice'];
								$qty = $row['quantity'];
								$discount = $row['discount'];
								$totalPrice = $uPrice * $qty * ((100 - $discount)/100);
									
								$output .= '<tr>' .
												'<td>' . $row['saleID'] . '</td>' .
												
												'<td>' . $row['customerID'] . '</td>' .
												'<td>' . $row['customerName'] . '</td>' .
												'<td>' . $row['itemName'] . '</td>' .
												'<td>' . $row['saleDate'] . '</td>' .
												'<td>' . $row['discount'] . '</td>' .
												'<td>' . $row['quantity'] . '</td>' .
												'<td>' . $row['unitPrice'] . '</td>' .
												'<td>' . $totalPrice . '</td>' .
											'</tr>';
							}
							
							$saleDetailsSearchStatement->closeCursor();
							
							$output .= '</tbody>
											<tfoot>
												<tr>
													<th>Sale ID</th>
													
													<th>Customer ID</th>
													<th>Customer Name</th>
													<th>Item Name</th>
													<th>Sale Date</th>
													<th>Discount %</th>
													<th>Quantity</th>
													<th> &#8358; Unit Price</th>
													<th> &#8358; Total Price</th>
												</tr>
											</tfoot>
										</table>';
							echo $output; 	
								?>
						</div>
						</div>
						<div id="purchaseSearchTab" class="container-fluid tab-pane fade">
							<br>
							<p>Use the grid below to search purchase details</p>
							<div class="table-responsive" id="purchaseDetailsTableDiv"></div>
						</div>
						<div id="vendorSearchTab" class="container-fluid tab-pane fade">
							<br>
							<p>Use the grid below to search vendor details</p>
							<div class="table-responsive" id="vendorDetailsTableDiv"></div>
						</div>
					</div>
				  </div> 
				</div>
			  </div>
			  
			  <div class="tab-pane fade" id="v-pills-reports3" role="tabpanel" aria-labelledby="v-pills-reports-tab">
				<div class="card card-outline-secondary my-4">
				<h4>Winner's Foam</h4>
				  <div class="card-header">Reports</div>
				  <a href="index.php">Refresh</a>
				 
				  <div class="card-body">										
					<ul class="nav nav-tabs" role="tablist">
						<li class="nav-item">
							<a class="nav-link active" data-toggle="tab" href="#itemReportsTab3">Item</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#customerReportsTab3">Customer</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#saleReportsTab3">Sale</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#purchaseReportsTab"></a>
						</li>
						<li class="nav-item">
							<a class="nav-link" data-toggle="tab" href="#vendorReportsTab"></a>
						</li>
					</ul>
  
					<!-- Tab panes for reports sections -->
					<div class="tab-content">
						<div id="itemReportsTab3" class="container-fluid tab-pane active">
							<br>
							<p>Use the grid below to get reports for items</p>
							<div class="table-responsive" id="itemReportsTableDiv2">
							<?php 
							$itemDetailsSearchSql = 'SELECT * FROM item3';
							$itemDetailsSearchStatement = $conn->prepare($itemDetailsSearchSql);
							$itemDetailsSearchStatement->execute();
						
							$output = '<table id="itemReportsTable3" class="table table-sm table-striped table-bordered table-hover" style="width:100%">
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
								                '<td>' . $row['itemNumber'] . '</td>' .
												'<td>' . $row['itemName'] . '</td>' .
												'<td>' . $row['stock'] . '</td>' .
												'<td>' . $row['unitPrice'] . '</td>' .
												'<td>' . $row['description'] . '</td>' .
												'<td>' . $row['stock'] *  $row['unitPrice'] . '</td>' .
												'<td> <a href="updateItem3.php" class="btn btn-warning">Update Item</a></td>' .
												'<td> <a href="editItem3.php" class="btn btn-primary">Edit Item</a></td>' .
						                        '<td> <a href="deleteItem3.php" class="btn btn-danger">Delete</a></td>' .
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




							</div>
						</div>
						<div id="customerReportsTab3" class="container-fluid tab-pane fade">
							<br>
							<p>Use the grid below to get reports for customers</p>
							<div class="table-responsive" id="customerReportsTableDiv2">
							<?php 
							$customerDetailsSearchSql = 'SELECT * FROM customer3';
							$customerDetailsSearchStatement = $conn->prepare($customerDetailsSearchSql);
							$customerDetailsSearchStatement->execute();
						
							$output = '<table id="customerReportsTable3" class="table table-sm table-striped table-bordered table-hover" style="width:100%">
										<thead>
											<tr>
											    <th>Customer ID</th>
												<th>Full Name</th>
												<th>Email</th>
												<th>Mobile</th>
												<th>Address</th>
												<th>Edit</th>
												<th>Delete</th>
												
											</tr>
										</thead>
										<tbody>';
							
							// Create table rows from the selected data
							while($row = $customerDetailsSearchStatement->fetch(PDO::FETCH_ASSOC)){
								$output .= '<tr>' .
												'<td>' . $row['customerID'] . '</td>' .
												'<td>' . $row['fullName'] . '</td>' .
												'<td>' . $row['email'] . '</td>' .
												'<td>' . $row['mobile'] . '</td>' .
												'<td>' . $row['address'] . '</td>' .
												'<td>  <a href="editCustomer3.php" class="btn btn-primary">Edit Customer Details</a></td>' .
						                         '<td> <a href="deleteCustomer3.php" class="btn btn-danger">Delete Customer</a></td>' .
											'</tr>';
							}
							
							$customerDetailsSearchStatement->closeCursor();
							
							$output .= '</tbody>
											<tfoot>
												<tr>
												<th>Customer ID</th>
												<th>Full Name</th>
												<th>Email</th>
												<th>Mobile</th>
												<th>Address</th>
												<th>Edit</th>
												<th>Delete</th>

												</tr>
											</tfoot>
										</table>';
							echo $output;
								
								
								?>
							</div>
						</div>
						<div id="saleReportsTab3" class="container-fluid tab-pane fade">
							<br>
							<!-- <p>Use the grid below to get reports for sales</p> -->
							<form> 
							  <div class="form-row">
								  <div class="form-group col-md-3">
									<label for="saleReportStartDate3">Start Date</label>
									<input type="text" class="form-control datepicker" id="saleReportStartDate3" value="" name="saleReportStartDate3" readonly>
								  </div>
								  <div class="form-group col-md-3">
									<label for="saleReportEndDate3">End Date</label>
									<input type="text" class="form-control datepicker" id="saleReportEndDate3" value="" name="saleReportEndDate3" readonly>
								  </div>
							  </div>
							  <button type="button" id="showSaleReport3" class="btn btn-dark">Show Report</button>
							  <button type="reset" id="saleFilterClear3" class="btn">Clear</button>
							</form>
							<br><br>
							<div class="table-responsive" id="saleReportsTableDiv2">
							<?php 
							$uPrice = 0;
							$qty = 0;
							$totalPrice = 0;
							
							$saleDetailsSearchSql = 'SELECT * FROM sale3';
							$saleDetailsSearchStatement = $conn->prepare($saleDetailsSearchSql);
							$saleDetailsSearchStatement->execute();
						
							$output = '<table id="saleReportsTable3" class="table table-sm table-striped table-bordered table-hover" style="width:100%">
										<thead>
											<tr>
												<th>Sale ID</th>
												<th>Customer Name</th>
												<th>Item Name</th>
												<th>Sale Date</th>
												<th>Quantity</th>
												<th>&#8358; Unit Price</th>
												<th>&#8358; Total Price</th>
												<th>Edit</th>
												<th>Delete</th>	

											</tr>
										</thead>
										<tbody>';
							
							// Create table rows from the selected data
							while($row = $saleDetailsSearchStatement->fetch(PDO::FETCH_ASSOC)){
								$uPrice = $row['unitPrice'];
								$qty = $row['quantity'];
								$discount = $row['discount'];
								$totalPrice = $uPrice * $qty * ((100 - $discount)/100);
								
								 $output .= '<tr>' .
								                '<td>' . $row['saleID'] . '</td>' .
												'<td>' . $row['customerName'] . '</td>' .
												'<td>' . $row['itemName'] . '</td>' .
												'<td>' . $row['saleDate'] . '</td>' .
												'<td>' . $row['quantity'] . '</td>' .
												'<td>' . $row['unitPrice'] . '</td>' .
												'<td>' . $totalPrice . '</td>' .
												'<td> <a href="editSale3.php" class="btn btn-primary">Edit Sale Details</a></td>' .
						                        '<td> <a href="deleteSale3.php" class="btn btn-danger">Delete</a></td>' .
											'</tr>';
							}
							
							$saleDetailsSearchStatement->closeCursor();
							
							$output .= '</tbody>
											<tfoot>
												<tr>
												<th>Sale ID</th>
												<th>Customer Name</th>
												<th>Item Name</th>
												<th>Sale Date</th>
												<th>Quantity</th>
												<th>&#8358; Unit Price</th>
												<th>&#8358; Total Price</th>
												<th>Edit</th>
												<th>Delete</th>	
												</tr>
											</tfoot>
										</table>';
							echo $output;
								
								
								?>



							</div>
						</div>
						<div id="purchaseReportsTab" class="container-fluid tab-pane fade">
							<br>
							<!-- <p>Use the grid below to get reports for purchases</p> -->
							<form> 
							  <div class="form-row">
								  <div class="form-group col-md-3">
									<label for="purchaseReportStartDate">Start Date</label>
									<input type="text" class="form-control datepicker" id="purchaseReportStartDate" value="" name="purchaseReportStartDate" readonly>
								  </div>
								  <div class="form-group col-md-3">
									<label for="purchaseReportEndDate">End Date</label>
									<input type="text" class="form-control datepicker" id="purchaseReportEndDate" value="" name="purchaseReportEndDate" readonly>
								  </div>
							  </div>
							  <button type="button" id="showPurchaseReport" class="btn btn-dark">Show Report</button>
							  <button type="reset" id="purchaseFilterClear" class="btn">Clear</button>
							</form>
							<br><br>
							<div class="table-responsive" id="purchaseReportsTableDiv"></div>
						</div>
						<div id="vendorReportsTab" class="container-fluid tab-pane fade">
							<br>
							<p>Use the grid below to get reports for vendors</p>
							<div class="table-responsive" id="vendorReportsTableDiv"></div>		
					</div>
					</div>
				  </div> 
				</div>
			  </div>
  </body>
<?php
include 'inc/footer.php';
?>

  </html>	
