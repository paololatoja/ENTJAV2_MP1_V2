<%@page import="model.Order"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Product"%>
<%@ page language="java" 
	contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
%>

<% 
	// Get products and cart from application
	ArrayList<Product> products = (ArrayList<Product>) application.getAttribute("INITIAL");
	ArrayList<Product> cart = Order.getCart();
%>
    
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="shortcut icon" href="./assets/fireship.png" />

    <title>Fireship | Home</title>

    <!-- CSS Reset -->
    <link rel="stylesheet" href="./styles/reset.css" lang="text/css">
    <!-- Page Style -->
    <link rel="stylesheet" href="./styles/home.css">
    <!-- Font Awesome Kit -->
    <script src="https://kit.fontawesome.com/24fa509132.js"></script>

</head>
<body>

    <!-- Navigation bar -->
    <nav>
        <a href="./home.jsp">
            <img class="fireship_logo" src="./assets/Fireship.svg" alt="Fireship">
        </a>
        
        <a class="cart_icon" href="cart.jsp">
        
        	<%-- SHOPPING CART STATE RENDERING | (Gray : Empty Cart) & (Yellow : Items present) ---------------------------------- --%>
	        <% if(cart.size() > 0) { %>
	        	<i class="fas fa-shopping-cart cart_icon" style="color: #ffcd43"></i>
	        <% } else { %>
	            <i class="fas fa-shopping-cart cart_icon"></i>        
	        <% } %>
	        <%-- ----------------------------------------------------------------------------------------------------------------- --%>
	        
        </a>
    </nav>

    <!-- Wrapper Container-->
    <div class="wrapper_container">
        <img id="Banner" src="./assets/Banner.svg" alt="Free Shipping! Banner">

        <div class="item_list_container">

			<%-- RENDER ALL PRODUCTS --------------------------------------------------------------------------------------------- --%>
			<% for(Product p : products) { %>
	            <!-- Loop -->
	            <a href="ViewItem?id=<%= p.getId() %>">
	                <div class="item"> <!-- ITEM CARD -->
	                    <div class="product_image_container">
	                        <img class="product_image" src="./assets/item_images/<%= p.getImagePath() %>" alt="">
	                    </div>
	                    <div class="product_info">
	                        <p class="product_name"><%= p.getName() %></p>
	                        <p class="product_price">&#8369;<%= String.format("%,.2f", p.getPrice()) %></p>
	                        <div class="rating">
	                        <% for(int j=0; j < p.getRating() ; ++j) { %>
	                           	<img class="star" src="assets/Star.svg" alt="rating">
	                        <% } %>
	                        </div>
	                        
	                    </div>
	                </div> <!-- ITEM CARD -->
	            </a>
	        <% } %>
	        <%-- ---------------------------------------------------------------------------------------------------------------- --%>
        
        </div> <!-- ITEM LIST CONTAINER -->
        <p class="end">- End of Product Feed -</p>
    </div>
</body>
</html>