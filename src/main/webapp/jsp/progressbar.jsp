<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>导出进度</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.9.1.js"></script>
  <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
  <link rel="stylesheet" href="http://jqueryui.com/resources/demos/style.css">
  <style>
  .ui-progressbar {
    position: relative;
  }
  .progress-label {
    position: absolute;
    left: 50%;
    top: 4px;
    font-weight: bold;
    text-shadow: 1px 1px 0 #fff;
  }
  </style>
  <script>
  $(function() {
    var progressbar = $( "#progressbar" ),
      progressLabel = $( ".progress-label" );
 
    progressbar.progressbar({
      value: false,
      change: function() {
        progressLabel.text( progressbar.progressbar( "value" ) + "%" );
      },
      complete: function() {
        progressLabel.text( "完成！" );
      }
    });
 
    function progress() {
    	var val = progressbar.progressbar( "value" ) || 0;
    	$.ajax({
    		url : "${marathon}/admin/system/orderMng/getGrogressbar.shtml",
    		data:{},
    		dataType:"json",
    		type:"post",
    		success:function(data){
    			for (key in data.obj){
    				var currentNum = data.obj[key].currentNum;
    				var totalNum = data.obj[key].total;
    				val = Math.round(currentNum/totalNum*100);
    				progressbar.progressbar( "value", val);
    			}
    		}
    	});
 
      if ( val < 99 ) {
        setTimeout( progress, 100 );
      }
    }
 
    setTimeout( progress, 1000 );
  });
  </script>
</head>
<body>
 
<div id="progressbar"><div class="progress-label">加载...</div></div>
 
 
</body>
</html>