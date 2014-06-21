var hoverIndex = -1;// 提示项高亮索引
var ajaxDelay;// 超时查询的ajax请求

// 设置网站名、网址文本框的内容
function setContent(li) {
	$("#mysite-name-add").val($(li).children(":first-child").text());
	$("#mysite-url-add").val($(li).children(":last-child").text());
}

// 添加提示框
function addSug() {
	var name_container = $(".site-name");
	var url_container = $(".site-url");
	var name_container_offset = name_container.offset();
	var url_container_offset = url_container.offset();
	$(document.body).append('<ul class="mysite-suggest"></ul>');
	$(".mysite-suggest").css("top",
			(name_container_offset.top + name_container.height())).css("left",
			name_container_offset.left).width(
			url_container_offset.left - name_container_offset.left
					+ url_container.width());
}

// 产生提示框列表项的html代码
function generateSiteList(data) {
	var name = data.split(',')[0];
	var url = data.split(',')[1];
	return '<li><div class="sug-name">' + name + '</div>	<div class="sug-url">'
			+ url + '</div></li>';
}

// 加载样式并绑定事件
function applyCSSAndEvent() {
	$(document.body)
			.append(
					'<style type="text/css">.mysite-suggest{display:none;position:absolute;border:1px solid #707a86;background-color:#fff}.mysite-suggest li{overflow:hidden;height:30px;cursor:pointer}.mysite-suggest .sug-url,.mysite-suggest .sug-name{display:inline;overflow:hidden;float:left;padding-left:6px;font-size:14px;height:30px;line-height:30px;text-overflow:ellipsis;white-space:nowrap}.mysite-suggest .sug-url{width:210px}.mysite-suggest .sug-name{margin-right:80px;width:188px}.mysite-suggest .hover{background-color:#f2f8ff}</style>');

	$(document).on("mouseover", ".mysite-suggest li", function() {
		changeHoverClass(this);
	});

	$(document).on("mousedown", ".mysite-suggest li", function() {
		setContent(this);
	});

	$(document).on("keydown", "#mysite-name-add,#mysite-url-add", function(e) {
		if (e.keyCode == 38)// 将文本框向上键的响应取消
			return false;
	});

	// 当文本框失去焦点时隐藏提示框
	$(document).on("blur", "#mysite-name-add,#mysite-url-add", function() {
		clearSug();
	});

}

// 清空提示框
function clearSug() {
	$(".mysite-suggest").html(" ");
	$(".mysite-suggest").hide();
}

// 改变提示项的高亮属性
function changeHoverClass(li) {
	$(".mysite-suggest .hover").removeClass();
	$(li).addClass("hover");
}

// 异步请求网站提示API
function getSite(word, sugApiUrl) {
	$.ajax({
		type : "get",
		url : sugApiUrl + word,
		dataType : "jsonp",// 数据类型为jsonp
		jsonp : "cb",// 服务端用于接收callback调用的function名的参数，360导航必须是cb
		jsonpCallback : "callback",// 不能采用默认的函数名，360导航对回调名称有限制
		success : function(data) {
			clearSug();// 先清除上一次的列表

			var sugContainer = $(".mysite-suggest");
			$(data).each(function() {
				sugContainer.append(generateSiteList(this));
			});

			// 当返回的数据长度大于0才显示
			if ($(sugContainer).children("li").length > 0) {
				sugContainer.show();
				hoverIndex = -1;
			}
		}
	});
}

$(document)
		.ready(
				function() {
					applyCSSAndEvent();
					addSug();
					var sugContainer = $(".mysite-suggest");

					// 监听文本框的按键抬起事件
					$("#mysite-name-add,#mysite-url-add")
							.keyup(
									function(event) {
										// 若上次间隔时间未到，先取消上次提交
										window.clearTimeout(ajaxDelay);

										var e = event || window.event;
										var keyCode = e.keyCode;
										// 数字字母退格删除空格
										if (keyCode >= 48
												&& keyCode <= 57
												|| (keyCode >= 65 && keyCode <= 90)
												|| keyCode == 8
												|| keyCode == 46
												|| keyCode == 32) {

											// 获得搜索地址和搜索内容
											var word;
											var sugApiUrl;
											if ("mysite-name-add" == document.activeElement.id) {
												word = $("#mysite-name-add")
														.val();
												sugApiUrl = "http://suggest.h.qhimg.com/index.php?biz=websitename&fmt=jsonp&word=";
											} else {
												word = $("#mysite-url-add")
														.val();
												sugApiUrl = "http://suggest.h.qhimg.com/index.php?biz=websiteurl&fmt=jsonp&word=";
											}

											// 没有内容就没必要搜索了
											if (word.trim() == "") {
												clearSug();
												return;
											}

											// 400毫秒后进行搜索，400毫秒未到又触发了下次，本次搜索会被取消
											ajaxDelay = window.setTimeout(
													'getSite("' + word + '","'
															+ sugApiUrl + '")',
													400);
											return;
										}

										// 获得提示框中的项
										var sitelis = $(sugContainer).children(
												"li");
										// 响应向上键
										if (keyCode == 38 && sitelis.length > 0) {
											if (hoverIndex > 0) {
												hoverIndex--;
											} else {
												hoverIndex = sitelis.length - 1;
											}
											setContent($(sitelis)[hoverIndex]);
											changeHoverClass($(sitelis)[hoverIndex]);
										}
										// 响应向下键
										if (keyCode == 40 && sitelis.length > 0) {
											if (hoverIndex < sitelis.length - 1) {
												hoverIndex++;
											} else {
												hoverIndex = 0;
											}
											setContent($(sitelis)[hoverIndex]);
											changeHoverClass($(sitelis)[hoverIndex]);
										}
									});
				});
