var highlightindex = -1;
var timeDelay;

// 设置文本框的内容
function setContent(li) {
	$("#mysite-name-add").val($(li).children(":first-child").text());
	$("#mysite-url-add").val($(li).children(":last-child").text());
}

// add ul list
function addSugList() {
	var name_container = $(".site-name");
	var url_container = $(".site-url");
	var name_container_offset = name_container.offset();
	var url_container_offset = url_container.offset();
	$(document.body).append(
			'<ul class="mysite-suggest" id="mysite-name-suggest" ></ul>');
	$(".mysite-suggest").css("top",
			(name_container_offset.top + name_container.height())).css("left",
			name_container_offset.left).width(
			url_container_offset.left - name_container_offset.left
					+ url_container.width());
}

function generateSiteList(data) {
	var name = data.split(',')[0];
	var url = data.split(',')[1];
	return '<li><div class="sug-name">' + name + '</div>	<div class="sug-url">'
			+ url + '</div></li>';
}

function applyCSS() {
	$(document.body)
			.append(
					'<style type="text/css">.mysite-suggest{display:none;position:absolute;border:1px solid #707a86;background-color:#fff}.mysite-suggest li{overflow:hidden;height:30px;cursor:pointer}.mysite-suggest .sug-url,.mysite-suggest .sug-name{display:inline;overflow:hidden;float:left;padding-left:6px;font-size:14px;height:30px;line-height:30px;text-overflow:ellipsis;white-space:nowrap}.mysite-suggest .sug-url{margin-right:12px;width:206px}.mysite-suggest .sug-name{margin-right:80px;width:188px}.mysite-suggest .hover{background-color:#f2f8ff}</style>');
	$(document).on("mouseover", ".mysite-suggest li", function() {
		changeHoverClass(this);
	});

	$(document).on("mousedown", ".mysite-suggest li", function() {
		setContent(this);
	});

	$(document).on("keydown", "#mysite-name-add,#mysite-url-add", function(e) {
		if (e.keyCode == 38)
			return false;
	});
}

function changeHoverClass(li) {
	$(".mysite-suggest .hover").removeClass();
	$(li).addClass("hover");
}

function getSite(word, sug_api_url) {
	// 将文本框的内容发到服务器
	$.ajax({
		type : "get",
		url : sug_api_url + word,
		dataType : "jsonp",// 数据类型为jsonp
		jsonp : "cb",// 服务端用于接收callback调用的function名的参数
		jsonpCallback : "callback",
		success : function(data) {
			// 清除上一次的列表
			var sugContainer = $(".mysite-suggest");
			sugContainer.html(" ");
			$(data).each(

			function() {
				sugContainer.append(generateSiteList(this));
			});
			// 当返回的数据长度大于0才显示
			if ($(sugContainer).children("li").length > 0) {
				sugContainer.show();
				highlightindex = -1;
			} else {
				sugContainer.html(" ");
				sugContainer.hide();
			}
		}
	});
}

$(document)
		.ready(
				function() {
					applyCSS();
					addSugList();
					var sugContainer = $(".mysite-suggest");

					// 当文本框失去焦点时隐藏提示框
					$("#mysite-name-add,#mysite-url-add").focusout(function() {
						sugContainer.html(" ");
						sugContainer.hide();
					});

					$("#mysite-name-add,#mysite-url-add")
							.keyup(
									function(event) {
										// 若上次间隔时间未到，先取消上次提交
										window.clearTimeout(timeDelay);

										var myevent = event || window.event;
										var keyCode = myevent.keyCode;
										// 字母，退格，删除，空格
										if (keyCode >= 65 && keyCode <= 90
												|| keyCode == 8
												|| keyCode == 46
												|| keyCode == 32) {

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

											if (word.trim() == "") {
												sugContainer.html(" ");
												sugContainer.hide();
												return;
											}

											timeDelay = window.setTimeout(
													'getSite("' + word + '","'
															+ sug_api_url
															+ '")', 400); // settimeout
										}

										// 获得返回框中的值
										var sitelis = $(sugContainer).children(
												"li");
										// 向上
										if (keyCode == 38&&sitelis.length>0) {
											if (highlightindex > 0) {
												highlightindex--;
											} else {
												highlightindex = sitelis.length - 1;
											}
											setContent($(sitelis)[highlightindex]);
											changeHoverClass($(sitelis)[highlightindex]);
										}
										// 向下
										if (keyCode == 40&&sitelis.length>0) {
											if (highlightindex < sitelis.length - 1) {
												highlightindex++;
											} else {
												highlightindex = 0;
											}
											setContent($(sitelis)[highlightindex]);
											changeHoverClass($(sitelis)[highlightindex]);
										}
									});
				});
