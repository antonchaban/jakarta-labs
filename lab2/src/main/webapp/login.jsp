<%--
  Created by IntelliJ IDEA.
  User: zwyntarsuimin
  Date: 20.05.2025
  Time: 16:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Login Page</title>
  <style>
    @import url("https://rsms.me/inter/inter-ui.css");
    ::selection {
      background: #2d2f36;
    }
    /*::-webkit-selection {*/
    /*  background: #2d2f36;*/
    /*}*/
    ::-moz-selection {
      background: #2d2f36;
    }
    body {
      background-image: url("https://i.pinimg.com/originals/46/1d/25/461d254f8563f8945ce3a385f289df17.jpg");
      font-family: "Inter UI", sans-serif;
      margin: 0;
    }
    .page {
      background-image: url("https://i.pinimg.com/originals/46/1d/25/461d254f8563f8945ce3a385f289df17.jpg");
      display: flex;
      flex-direction: column;
      height: 100vh;
      width: 100vw;
      align-content: center;
      justify-content: center;
    }
    @media (max-width: 767px) {
      .page {
        height: auto;
        margin-bottom: 20px;
        padding-bottom: 20px;
      }
    }
    .container {
      display: flex;
      height: 320px;
      margin: 0 auto;
      width: 700px;
      align-items: center;
      justify-content: center;
    }
    @media (max-width: 767px) {
      .container {
        flex-direction: column;
        height: 630px;
        width: 400px;
      }
    }
    .left {
      background: rgba(255, 255, 255, 0);
      height: 100%;
      top: 20px;
      position: relative;
      width: 50%;
    }
    @media (max-width: 767px) {
      .left {
        height: 100%;
        left: 20px;
        width: calc(100% - 40px);
        max-height: 270px;
      }
    }
    .login {
      font-size: 50px;
      font-weight: 900;
      margin: 50px 40px 40px;
    }
    .eula {
      color: #999;
      font-size: 14px;
      line-height: 1.5;
      margin: 40px;
    }
    .right {
      background: #474a59;
      box-shadow: 0px 0px 40px 16px rgba(0, 0, 0, 0.22);
      color: #f1f1f2;
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 2rem;
      position: relative;
      min-height: 250px;
      width: 50%;
    }
    @media (max-width: 767px) {
      .right {
        flex-shrink: 0;
        height: 100%;
        width: 100%;
        padding: 2rem;
      }
    }
    svg {
      position: absolute;
      width: 320px;
    }
    path {
      fill: none;
      stroke: url(#linearGradient);
      stroke-width: 4;
      stroke-dasharray: 240 1386;
    }
    .form {
      position: absolute;
    }
    label {
      color: #c2c2c5;
      display: block;
      font-size: 14px;
      height: 16px;
      margin-top: 20px;
      margin-bottom: 5px;
    }
    input {
      background: transparent;
      border-radius: 10px;
      border: 0;
      color: #f2f2f2;
      font-size: 20px;
      height: 30px;
      line-height: 30px;
      outline: 1px solid white;
      width: 100%;
    }
    input::-moz-focus-inner {
      border: 0;
    }
    #submit {
      background-color: #2196F3;
      border-radius: 10px;
      color: white;
      margin-top: 40px;
      transition: color 300ms;
    }
    #submit:focus {
      color: #f2f2f2;
    }
    #submit:active {
      color: #d0d0d2;
    }

    .form {
      display: flex;
      justify-content: space-between;
      flex-direction: column;
    }

  </style>
</head>
<body>
<div class="page">
  <div class="container">
    <div class="left">
      <div class="login">Login</div>
      <div class="eula">By logging in you agree to check out our awesome dating app</div>
    </div>
    <div class="right">
      <form method="post" action="/date-app/login" class="form" >
        <label for="email">Email</label>
        <input type="email" id="email" name="login">
        <label for="password">Password</label>
        <input type="password" id="password" name="password">
        <input type="submit" id="submit" value="Login">
      </form>
    </div>
  </div>
</div>
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/animejs/2.0.2/anime.js"></script>--%>
<%--<script src="backend.js" charset="utf-8"></script>--%>
<%--<script src="/javascript/animation.js"></script>--%>
</body>
</html>