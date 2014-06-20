var highlightindex = -1;
var timeDelay;

// 设置文本框的内容
function setContent(list, index) {
	var context = list.eq(index).text();
	$("#siteSug").val(context);
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
					'<style type="text/css">.mysite-suggest{position:absolute;border:1px solid #707a86;background-color:#fff}.mysite-suggest li{overflow:hidden;height:30px;cursor:pointer}.mysite-suggest .sug-url,.mysite-suggest .sug-name{display:inline;overflow:hidden;float:left;padding-left:6px;font-size:14px;height:30px;line-height:30px;text-overflow:ellipsis;white-space:nowrap}.mysite-suggest .sug-url{margin-right:12px;width:206px}.mysite-suggest .sug-name{margin-right:80px;width:188px}.mysite-suggest .hover{background-color:#f2f8ff}</style>');
	$(document).on("mouseover",".mysite-suggest li", function() {
		changeHoverClass(this);
	});
}
/**
 * // 鼠标离开 newNode .mouseout(function() { $( this) .css( "background-color",
 * "white"); }); // 鼠标点击 newNode .click(function() { setContent( con,
 * highlightindex); highlightindex = -1; sugContainer .hide(); });
 * 
 * @param li
 */
function changeHoverClass(li) {
	$(".mysite-suggest .hover").removeClass();
	$(li).addClass("hover");
}

function getSite(word,sug_api_url){
	// 将文本框的内容发到服务器
	$
			.ajax({
				type : "get",
				url : sug_api_url
						+ word,
				dataType : "jsonp",// 数据类型为jsonp
				jsonp : "cb",// 服务端用于接收callback调用的function名的参数
				jsonpCallback : "callback",
				success : function(
						data) {
					// 清除上一次的列表
					var sugContainer = $(".mysite-suggest");
					sugContainer
							.html(" ");
					$(
							data)
							.each(
									
									function() {
										sugContainer
												.append(generateSiteList(this));
									});
					// 当返回的数据长度大于0才显示
					if ($(
							sugContainer)
							.children(
									"li").length > 0) {
						sugContainer
								.show();
					} else {
						sugContainer
								.hide();
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
												sugContainer.hide();
												highlightindex = -1;
												return;
											}

											// 若上次间隔时间未到，先取消上次提交
											
											window.clearTimeout(timeDelay);
											timeDelay = window
													.setTimeout(
														'getSite("'+word+'","'+sug_api_url+'")', 400); // settimeout
										} else {
											// 获得返回框中的值
											var sitelis = $(sugContainer)
													.children("li");

											// 向上
											if (keyCode == 38) {
												if (highlightindex > 0) {
													highlightindex--;
												} else {
													highlightindex = sitelis.length - 1;
												}
											}
											// 向下
											if (keyCode == 40) {
												if (highlightindex < sitelis.length) {
													highlightindex++;
												} else {
													highlightindex = 0;
												}
												// setContent(rvalue,
												// highlightindex);
											}

											changeHoverClass($(sitelis)[highlightindex]);
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
