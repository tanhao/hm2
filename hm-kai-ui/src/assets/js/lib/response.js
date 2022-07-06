$(function() {
	$(".inputbody").on("touchstart", ".submit", function() {
		method.submitData();
	});
	$("#backbnt").on("touchstart", function() {
		window.history.go(-1);
	});
});
var method = {};
method.submitData = function() {
	//禁止重复提交
	if($("#submit").text() != "提交") {
		return;
	}
	var flag = false;
	$("input,textarea[class=reqired]").each(function() {
		if($(this).val() == "") {
			$(this).css({ border: "1px solid red" });
			$(this).attr("placeholder", "该项不能为空");
			flag = true;
		} else {
			$(this).css({ border: "1px solid #dbdbdb" });
			if($(this).attr("id")=="linkNumber") {
				if(!tools.noChinesFont($(this).val())) {
					$(this).css({ border: "1px solid red" });
					$(this).val("");
					$(this).attr("placeholder", "该项不能有中文");
					flag = true;
				}
			}
		}
	});
	//表单验证没有通过就返回
	if(flag) {
		return;
	}
	var nickName = $("#nickName").val();
	var linkNumber = $("#linkNumber").val();
	var fedBack = $("#fedBack").val();
	var data = {
		"nickName": nickName,
		"linkType": "0",
		"linkNumber": linkNumber,
		"fedBack": fedBack
	};
	$.ajax({
		url: config.publicUrl + "fedBack/saveFedBack.do",
		type: "post",
		data: data,
		beforeSend: function() {
			$("#submit").text("正在提交...");
		},
		success: function(data) {
			//执行数据请求
			method.createList(data);
		},
		error: function(data) {
			alert("提交失败，请稍后再试！")
			$("#submit").text("提交");
		}
	});
}
method.createList = function(data) {
	var data = tools.parseObj(data);
	if(data.result.businessCode == 0) {
		$("#inputbox").hide("200");
		$("#success").show("200");
	} else {
		alert("提交失败，请稍后再试！")
		$("#submit").text("提交");
	}
}