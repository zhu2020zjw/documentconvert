<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DCS在线预览</title>
<style type="text/css">
	a {font-size:16px}   
	a:link {color: blue; text-decoration:none;} //未访问：蓝色、无下划线   
	a:active:{color: red; } //激活：红色   
	a:visited {color:purple;text-decoration:none;} //已访问：purple、无下划线   
	a:hover {color: red; text-decoration:underline;} //鼠标移近：红色、下划线 
</style>
<script type="text/javascript" src="script/jquery-1.7.2.js"></script>
<script type="text/javascript">
	
	$(function(){
		
		$(".first").click(function() {
			$(".second_body").hide();
			$(".first_body").show();
		});
		
		$(".second").click(function() {
			$(".first_body").hide();
			$(".second_body").show();
		});
	})
	
</script>
</head>
<body>
	
	<header>
		<h2>在线文档预览</h2>
	</header>
	<br>
	<div class="type_button">
		<a class="first" href="#">文件预览</a>
		&nbsp;&nbsp;
		<a class="second" href="#">文档转换</a>
	</div>
	<br>
	<div class="first_body">
		<fieldset>
			<legend>
				<span>输入文件</span>
			</legend>
			<form action="upLoadServlet" method="post" enctype="multipart/form-data">
				<input type="file" name="file" size="50"/>
				<input type="submit" value="上传本地文件"/>
			</form>
		</fieldset>
		<br>
		<fieldset>
			<legend>
				<span>输出文件</span>
			</legend>
			<a href="convertServlet?method=previewOfSD">标准预览</a>
			&nbsp;&nbsp;
			<a href="${requestScope.targetFileName }">${requestScope.targetFileName }</a>
			<br><br>
			<a href="convertServlet?method=previewOfHD">高清预览</a>
			&nbsp;&nbsp;
			<a href="${requestScope.targetFileNameOfHD }">${requestScope.targetFileNameOfHD }</a>
		</fieldset>
	</div>
	
	<div class="second_body" style="display: none;">
		<fieldset>
			<legend>
				<span>添加结果</span>
			</legend>
			<form action="convertServlet" method="post">
				<input type="file" name="file" size="50"/>
				<input type="submit" value="上传本地文件"/>
			</form>
		</fieldset>
		<br>
		<fieldset>
			<legend>
				<span>选择类型</span>
			</legend>
			<div>
				<select>
					<option selected="selected" value="-1">请选择支持转换的格式</option>
					<option value="html">html</option>
					<option value="pdf">pdf</option>
					<option value="doc">doc</option>
					<option value="docx">docx</option>
					<option value="txt">txt</option>
				</select>
			</div>
		</fieldset>
		<br>
		<fieldset>
			<legend>
				<span>转换结果</span>
			</legend>
			<input type="text" value="" size="50"/>
			<button class="download">下载</button>&nbsp;
			<button class="look">预览</button>
		</fieldset>
	</div>
	
</body>
</html>