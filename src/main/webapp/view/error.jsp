<%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 24.09.2021
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Rent car</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            font-family: "Fira Code", monospace;
        }
        body {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #ecf0f1;
        }

        .container {
            text-align: center;
            margin: auto;
            padding: 4em;
        }

        img {
            width: 256px;
            height: 225px;
        }

        h1 {
            margin-top: 1rem;
            font-size: 35px;
            text-align: center;
        }

        span {
            font-size: 60px;
        }

        p {
            margin-top: 1rem;
        }

        p.info {
            margin-top: 4em;
            font-size: 12px;
        }

        a {
            font-family: 'Cabin', sans-serif;
            display: inline-block;
            padding: 10px 25px;
            background-color: #8f8f8f;
            border: none;
            border-radius: 40px;
            color: #fff;
            font-size: 14px;
            font-weight: 700;
            text-transform: uppercase;
            text-decoration: none;
            -webkit-transition: 0.2s all;
            transition: 0.2s all;
        }

        a:hover {
            background-color: #2c2c2c;
        }


    </style>
</head>
<body>

<div class="container">
    <img src="https://i.imgur.com/qIufhof.png" />

    <h1>
        <span>500</span> <br />
        Internal server error
    </h1>
    <p>We are currently trying to fix the problem.</p>
    <p class="info">
        <a href="<%=request.getContextPath()%>/view/main.jsp">home page</a>
    </p>
</div>

</body>
</html>
