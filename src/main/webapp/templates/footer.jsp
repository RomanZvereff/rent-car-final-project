<%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 11.09.2021
  Time: 17:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; image/svg+xml; charset=UTF-8" pageEncoding="UTF-8"  language="java" %>

<html>
<head>
    <title>Rent car</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>

    <footer class="footer">
        <div class="container-fluid">
            <div class="container footer-container">
                <div class="row">
                    <div class="footer-bl col-lg-3 col-md-6 col-sm-12 text-center">
                        <div class="system-item">
                            <img src="<%=request.getContextPath()%>/templates/images/footer/visa.png" alt="visa">
                        </div>
                        <div class="system-item">
                            <img src="<%=request.getContextPath()%>/templates/images/footer/Mastercard-logo.png" alt="master">
                        </div>
                        <div class="system-item">
                            <img src="<%=request.getContextPath()%>/templates/images/footer/apple_pay.png" alt="apple_pay">
                        </div>
                        <div class="system-item">
                            <img src="<%=request.getContextPath()%>/templates/images/footer/Google_Pay_2.png" alt="google_pay">
                        </div>
                    </div>
                    <div class="footer-bl col-lg-3 col-md-6 col-sm-12  text-center text-sm-center text-md-left">
                        <ul class="footer-list">
                            <li class="footer-list-item">News</li>
                            <li class="footer-list-item">Company's history</li>
                            <li class="footer-list-item">Promos</li>
                            <li class="footer-list-item">Damage Management Policy</li>
                            <li class="footer-list-item">Delivery and payment</li>
                            <li class="footer-list-item">FAQ</li>
                        </ul>
                    </div>
                    <div class="footer-bl col-lg-3 col-md-6 col-sm-12 text-center text-sm-center text-md-left order-md-2 order-lg-3">
                        <ul class="footer-list">
                            <li class="footer-list-item">Our team</li>
                            <li class="footer-list-item">Terms & Conditions</li>
                            <li class="footer-list-item">Contacts</li>
                            <li class="footer-list-item">Partners</li>
                            <li class="footer-list-item">Media</li>
                        </ul>
                    </div>
                    <div class="footer-bl col-lg-3 col-md-6 col-sm-12  text-center text-sm-center text-md-left order-md-1 order-lg-4">
                        <div class="footer-address">
                            <p>Ukraine, Dnipro, Barykadna, 16, 5 floor, 49044</p>
                            <p>+380-50-629-2724</p>
                        </div>
                        <div class="social-link d-flex justify-content-center">
                            <div class="link-facebook">
                                <a href="#">
                                    <img class="facebook-icon" src="<%=request.getContextPath()%>/templates/images/social/facebook.png" alt="facebook">
                                </a>
                            </div>
                            <div class="link-instagram">
                                <a href="#">
                                    <img class="instagram-icon" src="<%=request.getContextPath()%>/templates/images/social/instagram.png" alt="instagram">
                                </a>
                            </div>
                            <div class="link-youtube">
                                <a href="#">
                                    <img class="youtube-icon" src="<%=request.getContextPath()%>/templates/images/social/youtube.png" alt="youtube">
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </footer>

</body>
</html>
