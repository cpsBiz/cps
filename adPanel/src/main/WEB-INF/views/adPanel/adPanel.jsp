<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta name="description" content="" />
  <title>Panel</title>

  <script type="text/javascript" src="/js/lib/jquery-2.2.2.min.js"></script>
  <script type="text/javascript" src="/js/main/coupang.js"></script>


  <link href="https://partners.coupangcdn.com/widget/carousel/default/09aab9e20807268b96f5.css" rel="stylesheet">
  <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css" />
  <script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
</head>

<script type="text/javascript">
  var adPanelList = JSON.parse('${adPanelList}');
</script>

<input type="text" id="adid" name="adid" value="${listSize}">
<body>
<p th:text="'this is Mobcoms\'s' + ${adPanelList} + ' page! GoodLuck... '">Hello, Spring Boot!</p>
</body>
</html>