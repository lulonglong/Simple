//高亮索引
var highlightindex = -1;

// 设置文本框的内容
function setContent(list, index) {
	var context = list.eq(index).text();
	$("#siteSug").val(context);
}

// 设置背景颜色
function setBackgroundColor(con, index, color) {
	con.eq(index).css("background-color", color);
}

function addSugList() {
	// add ul list
	var name_container = $(".site-name");
	var url_container = $(".site-url");
	var name_container_offset = name_container.offset();
	var url_container_offset = url_container.offset();
	$(document.body).append(
			'<ul class="mysite-suggest" id="mysite-name-suggest" ></ul>');
	$(".mysite-suggest").css("position", "absolute").css("border",
			"1px solid #707a86").css("background-color", "#fff").css("top",
			(name_container_offset.top + name_container.height())).css("left",
			name_container_offset.left).width(
			url_container_offset.left - name_container_offset.left
					+ url_container.width());
}

$(document)
		.ready(
				function() {
					addSugList();
					var sugContainer = $(".mysite-suggest");
					$("#mysite-name-add,#mysite-url-add")
							.keyup(
									function(event) {
										var myevent = event || window.event;
										var keyCode = myevent.keyCode;
										// 字母，退格，删除，空格
										if (keyCode >= 65 && keyCode <= 90
												|| keyCode == 8
												|| keyCode == 46
												|| keyCode == 32) {

											// 清除上一次的列表
											sugContainer.html(" ");

											// 获得文本框内容
											var word;
											var sug_api_url;
											if ("mysite-name-add" == document.activeElement.id) {
												word = $("#mysite-name-add")
														.val();
												sug_api_url = "http://suggest.h.qhimg.com/index.php?biz=websitename&fmt=jsonp&word=";
											} else {
												word = $("#mysite-url-add")
														.val();
												sug_api_url = "http://suggest.h.qhimg.com/index.php?biz=websiteurl&fmt=jsonp&word=";
											}

											var timeDelay;
											if (word.trim() == "") {
												sugContainer.hide();
												highlightindex = -1;
												return;
											}

											// 取消上次提交
											window.clearTimeout(timeDelay);
											// 延迟提交，这边设置的为400ms
											timeDelay = window.setTimeout(
											// 将文本框的内容发到服务器
											 $.ajax({  
											        type : "get",  
											        async:false,  
											        url : sug_api_url + word,  
											        dataType : "jsonp",//数据类型为jsonp  
											        jsonp: "cb",//服务端用于接收callback调用的function名的参数  
											        jsonpCallback:"callback",
											        success : function(data){  
											        	alert(data); 
														// var jqObj = $(data);
														// // 找到所有的word节点
														// var wordNodes = jqObj
														// .find("word");
														// wordNodes
														// .each(function(
														// i) {
														// // 获得返回的单词内容
														// var wordNode =
														// $(this);
														// var newNode = $(
														// "<div>")
														// .html(
														// wordNode
														// .text())
														// .attr(
														// "id",
														// i)
														// .addClass(
														// "pro");
														// // 将返回内容附加到页面
														// newNode
														// .appendTo(sugContainer);
														// // 处理鼠标事件
														// var con = $(
														// "#sugContainer")
														// .children(
														// "div");
														// // 鼠标经过
														// newNode
														// .mouseover(function()
														// {
														// if (highlightindex !=
														// -1) {
														// setBackgroundColor(
														// con,
														// highlightindex,
														// "white");
														// }
														// highlightindex = $(
														// this)
														// .attr(
														// "id");
														// $(
														// this)
														// .css(
														// "background-color",
														// "#ADD8E6");
														// setContent(
														// con,
														// highlightindex);
														// });
														// // 鼠标离开
														// newNode
														// .mouseout(function()
														// {
														// $(
														// this)
														// .css(
														// "background-color",
														// "white");
														// });
														// // 鼠标点击
														// newNode
														// .click(function() {
														// setContent(
														// con,
														// highlightindex);
														// highlightindex = -1;
														// sugContainer
														// .hide();
														// });
														// }); // each
														//
														// // 当返回的数据长度大于0才显示
														// if (wordNodes.length
														// > 0) {
														// sugContainer
														// .show();
														// } else {
														// sugContainer
														// .hide();
														// }
													}}), 400); // settimeout
										} else {
											// 获得返回框中的值
											var rvalue = $("#sugContainer")
													.children("div");
											// 上下键
											if (keyCode == 38 || keyCode == 40) {
												// 向上
												if (keyCode == 38) {
													if (highlightindex != -1) {
														setBackgroundColor(
																rvalue,
																highlightindex,
																"white");
														highlightindex--;
													}
													if (highlightindex == -1) {
														setBackgroundColor(
																rvalue,
																highlightindex,
																"white");
														highlightindex = rvalue.length - 1;
													}
													setBackgroundColor(rvalue,
															highlightindex,
															"#ADD8E6");
													setContent(rvalue,
															highlightindex);
												}
												// 向下
												if (keyCode == 40) {
													if (highlightindex != rvalue.length) {
														setBackgroundColor(
																rvalue,
																highlightindex,
																"white");
														highlightindex++;
													}
													if (highlightindex == rvalue.length) {
														setBackgroundColor(
																rvalue,
																highlightindex,
																"white");
														highlightindex = 0;
													}
													setBackgroundColor(rvalue,
															highlightindex,
															"#ADD8E6");
													setContent(rvalue,
															highlightindex);
												}
											}
											// 回车键
											if (keyCode == 13) {
												if (highlightindex != -1) {
													setContent(rvalue,
															highlightindex);
													highlightindex = -1;
													sugContainer.hide();
												} else {
													alert($("#siteSug").val());
												}
											}
										}
									});// 键盘抬起

					// 当文本框失去焦点时隐藏提示框
					$("#mysite-name-add,#mysite-url-add").focusout(function() {
						sugContainer.hide();
					});
				});
