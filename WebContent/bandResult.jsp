<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>绑定结果</title>

<!-- Bootstrap -->
<link href="bootstrap-3.1.1-dist/css/bootstrap.min.css" rel="stylesheet" />
<script src="bootstrap-3.1.1-dist/js/jquery-1.8.3.min.js"></script>


<script type="text/javascript">
	
</script>
</head>
<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">
				<h3>
					<s:property value="#request.myResult" />
				</h3>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<p>
					<s:property value="#request.myReason" />
				</p>
			</div>
		</div>
	</div>



	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="bootstrap-3.1.1-dist/js/bootstrap.min.js"></script>
</body>
</html>