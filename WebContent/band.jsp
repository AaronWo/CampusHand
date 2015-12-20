<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>绑定账号</title>

<!-- Bootstrap -->
<link href="bootstrap-3.1.1-dist/css/bootstrap.min.css" rel="stylesheet" />
<script src="bootstrap-3.1.1-dist/js/jquery-1.8.3.min.js"></script>


<script type="text/javascript">
	var university = "";

	$(document).ready(function() {
		$("li").click(function() {
			university = $(this).attr("class");
			$("#university_input").val(university);
			$("#university_input1").val(university);
		});
	});

	function submit_form() {
		if ($("#university_input").val() == ""
				|| $("#username_input").val() == ""
				|| $("#password_input").val() == "") {
			alert("请先填写表单信息");
			return;
		} else {
			document.forms[0].action = "wechatAction!band.action";
			alert($("#university_input").val());
			document.forms[0].submit();
		}
	}
</script>
</head>
<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column" style="margin-top: 20px;">

				<div id="university" class="dropdown">
					<button class="btn btn-primary dropdown-toggle"
						data-toggle="dropdown">
						请选择您的学校<span class="caret"></span>
					</button>
					<span class="dropdown-arrow dropdown-arrow-inverse"
						style="margin-left: 30px; float: left;"></span>
					<ul class="dropdown-menu dropdown-inverse">
						<li class="厦门大学"><a href="#fakelink">厦门大学</a></li>
						<li class="燕京理工"><a href="#fakelink">燕京理工 </a></li>
					</ul>
				</div>


				<div class="row clearfix">
					<div class="col-md-12 column">
						<p id="university_p"></p>
					</div>
				</div>

				<div class="row clearfix form-horizontal"
					style="margin-left: 1px; margin-top: 20px;">
					<form method="post" action="" role="form" id="fromId">
						<fieldset>
							<div id="legend" class="">
								<legend class="">绑定信息</legend>
							</div>
							<div class="control-group">

								<label class="control-label" for="input01">学校</label>
								<div class="controls">
									<input id="university_input1" type="text" placeholder="请选择学校"
										class="input-xlarge" disabled="disabled" value="" />
									<p class="help-block"></p>
								</div>
							</div>
							<div class="control-group">

								<label class="control-label" for="input01">用户名</label>
								<div class="controls">
									<input id="username_input" name="username" type="text"
										placeholder="User Name" class="input-xlarge" value="" />
									<p class="help-block"></p>
								</div>
							</div>

							<div class="control-group">

								<label class="control-label" for="input01">密码</label>
								<div class="controls">
									<input id="password_input" name="password" type="password"
										placeholder="Password" class="input-xlarge" value="" />
									<p class="help-block"></p>
								</div>

							</div>
							<div class="control-group" style="margin-top: 15px;">
								<div class="controls">
									<input id="university_input" name="univer" value=""
										type="hidden" />
									<button class="btn btn-primary" onClick="submit_form()">提交</button>
								</div>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>

	</div>

	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="bootstrap-3.1.1-dist/js/bootstrap.min.js"></script>
</body>
</html>