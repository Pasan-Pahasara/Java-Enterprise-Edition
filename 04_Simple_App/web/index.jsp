<%@ page import="dto.CustomerDTO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %><%--
Created by IntelliJ IDEA.
User: ShEnUx
Date: 12/3/2022
Time: 11:25 PM
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>POS System</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css" rel="stylesheet">
    <!-- Bootstrap for Style -->
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <!-- Custom Style Sheet -->
    <link rel="stylesheet" href="assets/css/styles.css">
</head>
<body class="container-fluid">

<%
    ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
//    allCustomers.add(new CustomerDTO("C001", "Pahasara", "Galle", 10000));
//    allCustomers.add(new CustomerDTO("C002", "Sadun", "Panadura", 30000));
//    allCustomers.add(new CustomerDTO("C003", "Nimesh", "Kaluthara", 40000));
//    allCustomers.add(new CustomerDTO("C004", "Maneesha", "Hikkaduwa", 51000));

    //    initialize database connection
    Class.forName("com.mysql.jdbc.Driver");
    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/JEPOS", "root", "1234");
    PreparedStatement pstm = connection.prepareStatement("select * from Customer");
    ResultSet rst = pstm.executeQuery();
    while (rst.next()) {
        String id = rst.getString("id");
        String name = rst.getString("name");
        String address = rst.getString("address");
        double salary = rst.getDouble("salary");
        allCustomers.add(new CustomerDTO(id,name,address,salary));
    }
%>

<header id="headerBar" class="row">

    <nav class="navbar navbar-expand-lg border-bottom shadow-lg sticky-top navbar-dark"
         style="background-color: #232121">
        <div class="container-fluid">
            <a class="navbar-brand" href="#" style="font-size: 18px; color: #A87E5A;">
                <!-- navbar image setup -->
                <img alt="" src="assets/img/logo.png"
                     style="width: 70px;height: 58px;position: absolute;left: 0;top: 0;"><span
                    style="font-size: 20px; font-weight: bolder; color: #f0f0f0; margin-left: 46px;">YᑌᗰᗰY</span> treats</a>
            <!--Toggle button-->
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse " id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="index.jsp"><i class="bi bi-house-fill"></i>
                            Home</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="CustomerForm.html"><i
                                class="bi bi-people-fill"></i> Customers</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="itemForm.html"><i
                                class="bi bi-handbag-fill"></i> Items</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="oderForm.html"><i
                                class="bi bi-cart-check-fill"></i> Orders</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="#"><i class="bi bi-card-checklist"></i> Orders
                            Details</a>
                    </li>

                </ul>
                <ul class="navbar-nav me-6 mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="#"><i class="bi bi-person-plus-fill"></i>
                            Sign Up</a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="#"><i class="bi bi-box-arrow-right"></i>
                            Log Out</a>
                    </li>
                </ul>
                </ul>
            </div>
        </div>
    </nav>

</header>

<main class="row mx-3">
    <section class="col-12 mb-4">
        <div class="row">
            <div style="background: linear-gradient(to right, #000000, #b88962);box-shadow: rgb(0 0 0 / 25%) 0 14px 28px, rgb(0 0 0 / 22%) 0 10px 10px!important;"
                 class="p-1 px-3 col rounded-3 fw-bolder bg-secondary text-light fs-3 mt-4 shadow-sm"><i
                    class="fas fa-user-edit me-2"></i>Manage Customer
            </div>
        </div>
    </section>

    <section class="col-12 row">
        <section class="col-xl-7 ps-4">
            <section class="col-12 mt-4 mb-5">
                <form class="row g-3">
                    <div class="col-xl-6 col-lg-12">
                        <input type="search" class="form-control rounded-pill" id="txtSearchCustomer"
                               placeholder="Search Customer">
                    </div>

                    <div class="col-1" style="width: max-content">
                        <button id="btnAllCustomer" type="button" class="btn-modern">Get All Customers</button>
                    </div>

                    <div class="col-1" style="width: max-content">
                        <button id="btnSearchCustomer" type="button" class="btn-modern">Search</button>
                    </div>

                    <div class="col-1" style="width: max-content">
                        <button id="btnSearchCustomerClear" type="button" class="btn-modern">Clear</button>
                    </div>
                </form>
            </section>
            <!--            style="height: 25.5rem; overflow: auto"-->
            <section>
                <div class="col-12 d-flex rounded-2 shadow overflow-auto">
                    <!--Customer Table-->
                    <table class="table table-hover">
                        <!--Table head-->
                        <thead class="text-white card-header-tabs table-responsive sticky-top">
                        <!--Table head row-->
                        <tr>
                            <th>Customer ID</th> <!--Table heading-->
                            <th>Customer Name</th>
                            <th>Address</th>
                            <th>Salary</th>
                        </tr>
                        </thead>
                        <!--Table body-->
                        <tbody id="tblCustomer">
                        <!--Table data row-->
                        <%
                            for (CustomerDTO customer : allCustomers) {
                        %>
                        <tr>
                            <td><%=customer.getId()%>
                            </td>
                            <td><%=customer.getName()%>
                            </td>
                            <td><%=customer.getAddress()%>
                            </td>
                            <td><%=customer.getSalary()%>
                            </td>
                        </tr>
                        <%
                            }
                        %>
                        </tbody>
                    </table>
                </div>
            </section>
        </section>

        <!--Customer Manage Form-->
        <section class="col-xl-5 row justify-content-xl-end d-grid gap-2 d-md-flex justify-content-sm-center">
            <section class="col-8 border customer-container mt-3 mb-3">
                <form id="customerForm">
                    <div style="display: inline-block" class="col d-grid gap-2 d-md-flex justify-content-md-end">
                        <button id="btnClear" type="button" class="btn-modern">Clear
                        </button>
                    </div>
                    <div style="display: inline-block" class="col d-grid gap-2 d-md-flex justify-content-md-end">
                        <button style="margin-bottom: 8px;margin-top: 8px;" id="btnDeleteCustomer" type="button"
                                class="btn-modern">
                            Delete
                        </button>
                    </div>
                    <div class="mb-3">
                        <label for="txtCustomerID" class="form-label">Customer ID</label>
                        <input type="text" class="form-control prevent_tab_key_focus" id="txtCustomerID"
                               aria-describedby="idHelp" placeholder="Eg :- C00-001" name="id">
                        <div id="idHelp" class="form-text"></div>
                    </div>
                    <div class="mb-3">
                        <label for="txtCustomerName" class="form-label">Customer Name</label>
                        <input type="text" class="form-control prevent_tab_key_focus" id="txtCustomerName"
                               aria-describedby="nameHelp" placeholder="Eg :- Pahasara" name="name">
                        <div id="nameHelp" class="form-text"></div>
                    </div>
                    <div class="mb-3">
                        <label for="txtCustomerAddress" class="form-label">Address</label>
                        <input type="text" class="form-control prevent_tab_key_focus" id="txtCustomerAddress"
                               aria-describedby="addressHelp" placeholder="Eg :- N0/02, Godahena Watta, Galle"
                               name="address">
                        <div id="addressHelp" class="form-text"></div>
                    </div>
                    <div class="mb-3">
                        <label for="txtCustomerSalary" class="form-label">Salary</label>
                        <input type="text" class="form-control prevent_tab_key_focus" id="txtCustomerSalary"
                               aria-describedby="salaryHelp"
                               placeholder="Eg :- 200 or 250.00" name="salary">
                        <div id="salaryHelp" class="form-text"></div>
                    </div>
                    <div style="display: inline-block" class="me-2">
                        <button style="margin-bottom: 8px;margin-top: 8px;" id="btnCustomer" class="btn-modern"
                                form="customerForm" formaction="customer" formmethod="post">+New Customer
                        </button>
                    </div>
                    <div style="display: inline-block" class="me-2">
                        <button style="margin-bottom: 8px;margin-top: 8px;" id="btnUpdateCustomer" type="button"
                                class="btn-modern">
                            Update
                        </button>
                    </div>
                </form>
            </section>
        </section>
    </section>
</main>

<!-- Bootstrap for JS -->
<script src="assets/js/bootstrap.min.js"></script>

<!-- jQuery for JS -->
<script src="assets/jQuery/jquery-3.6.1.min.js"></script>

<!-- Swal CDN link -->
<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<!-- Customer JS -->
<script src="assets/js/customer.js"></script>
</body>
</html>


