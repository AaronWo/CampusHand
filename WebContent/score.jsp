<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html lang="en">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>查询成绩</title>

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
				<h3>查询结果</h3>
			</div>
		</div>

		<div class="row clearfix">
			<div class="col-md-12 column">
				<table class="table">
					<thead>
						<tr>
							<th></th>
							<th>科目</th>
							<th>类型</th>
							<th>成绩</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="#request.scoreList" status="st" var="entry">
							<s:if test="#st.index%4==0">
								<tr>
									<td><s:property value="#st.index+1" /></td>
									<td><s:property value="#entry.name" /></td>
									<td><s:property value="#entry.type" /></td>
									<td><s:property value="#entry.score" /></td>
								</tr>
							</s:if>
							<s:elseif test="#st.index%4==1">
								<tr class="success">
									<td><s:property value="#st.index+1" /></td>
									<td><s:property value="#entry.name" /></td>
									<td><s:property value="#entry.type" /></td>
									<td><s:property value="#entry.score" /></td>
								</tr>
							</s:elseif>
							<s:elseif test="#st.index%4==2">
								<tr class="warning">
									<td><s:property value="#st.index+1" /></td>
									<td><s:property value="#entry.name" /></td>
									<td><s:property value="#entry.type" /></td>
									<td><s:property value="#entry.score" /></td>
								</tr>
							</s:elseif>
							<s:else>
								<tr class="danger">
									<td><s:property value="#st.index+1" /></td>
									<td><s:property value="#entry.name" /></td>
									<td><s:property value="#entry.type" /></td>
									<td><s:property value="#entry.score" /></td>
								</tr>
							</s:else>
						</s:iterator>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="bootstrap-3.1.1-dist/js/bootstrap.min.js"></script>
</body>
</html>