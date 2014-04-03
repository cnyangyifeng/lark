/* JavaScript Document
 *
 * 模板详情 加载各子页面对象
 *
**/
	(function(){
		"use strict";
		
		var rightCont = {},global,data = {};
		
		global = (function(){ return this || (0,eval)('this'); }());
		global.rightCont = rightCont;
		
		var def = {
			pageHeader:document.getElementById("contHeaderTemplate").text		
		}	
		// 基本信息 模板函数
		var basicInfoFn = doT.template(document.getElementById("basicInfoTemplate").text, undefined, def);
		// 基本信息 添加关键词表单 模板函数
		var addKeywordFn = doT.template(document.getElementById("addKeywordInfoTemplate").text);
		// 基本信息 关键词标签 模板函数
		var tagKeywordFn = doT.template(document.getElementById("tagKeywordInfoTemplate").text);
		
		// 详细信息 模板函数
		var detailInfoFn = doT.template(document.getElementById("detailInfoTemplate").text, undefined, def);
		// 详细信息 列表项 模板函数
		var itemDetailInfoFn = doT.template(document.getElementById("itemDetailInfoTemplate").text);
		
		// 课程推广 模板函数
		var promotionFn = doT.template(document.getElementById("promotionTemplate").text, undefined, def);
		
		// 访问权限 模板函数
		var accessRightFn = doT.template(document.getElementById("accessRightTemplate").text, undefined, def);
		
		// 授权管理 模板函数
		var kinguserFn = doT.template(document.getElementById("kinguserTemplate").text, undefined, def);
		// 授权管理 用户列表 模板函数
		var listUserKinguserFn = doT.template(document.getElementById("listUserKinguserTemplate").text);
		
		// 授权管理 群组列表 模板
		var listGroupFn = doT.template(document.getElementById("listCourseGroupTemplate").text);
		
		// 删除课程 模板函数
		var deleteCourseFn = doT.template(document.getElementById("deleteCourseTemplate").text, undefined, def);

        // 各媒体页 模板函数
        var mediaPageFn = doT.template(document.getElementById("mediaPageTemplate").text);
        // 各媒体页 列表项 模板函数
        var mediaListFn = doT.template(document.getElementById("mediaListTemplate").text);
        // 编辑章节标题模板函数
        var editMediaTitleFn = doT.template(document.getElementById("formEditMediaTitle").text);

		// 编辑章节标题模板函数
		var editTitleFn = doT.template(document.getElementById("formEditSectionTitle").text);
		// 添加章节模板函数
		var addSectionsFn = doT.template(document.getElementById("addSectionsTemplate").text, undefined, {
			edittitle:document.getElementById("formEditSectionTitle").text		
		});
		// 展示章节模板函数
		var sectionsFn = doT.template(document.getElementById("sectionsTemplate").text + document.getElementById("sectionBarTemplate").text + document.getElementById("lectureContentTemplate").text);
		// load章节目录模板函数
		var loadsectionsDirectoryFn = doT.template(document.getElementById("sectionDirectoryTemplate").text + document.getElementById("sectionBarTemplate").text + document.getElementById("lectureContentTemplate").text, undefined, def);

        // 加载视频页
        rightCont.loadVideoPage = function (opt,type){
            /*
			 * ============================================ ajax 加载JSON数据
			 * ================================================
			 */
            /*
			 * $.getJSON("url?load",{id:opt.id},function(rsult){ data = rsult;
			 * data.pageTitle = opt.title; data.lectureIndex =
			 * numParseCN(opt.index); data.typeTxt = "视频"; data.uploadIntro =
			 * "上传视频（支持MP4、AVI、WMV格式的视频，建议小于10G）：成功上传的视频将会显示在下面的视频列表中。";
			 * $("#rightCont").html(mediaPageFn(data)); });
			 */
        	var data_type;
        	var data_typeTxt;
        	var data_uploadIntro;
        	var uptype;
        	switch(type){
          	case "01":
          		data_type = "video";
          		data_typeTxt = "视频";
          		data_uploadIntro = "您可以直接上传视频（支持绝大多数主流视频格式，建议小于10G）：成功上传的视频将会显示在上面视频列表中。";
          		uptype='*.wmv;*.wm;*.asf;*.asx;*.rm;*.rmvb;*.ra;*.ram;*.mpg;*.mpeg;*.mpe;*.vob;*.dat;*.mov;*.3gp;*.mp4;*.mp4v;*.m4v;*.mkv;*.avi;*.flv;*.f4v;*.mts;';
          		break;
            case "02":
            	data_type = "audio";
            	data_typeTxt = "音频";
            	data_uploadIntro = "上传音频（支持MP3、MV格式的音频，建议小于10G）：成功上传的音频将会显示在上面的音频列表中。";
            	uptype='*.mp3;*.mv;';
	            break;
            case "04":
            	data_type = "doc";
            	data_typeTxt = "文档";
            	data_uploadIntro = "上传文档（支持DOC、EXCEL、PDF、PPT格式的文档，建议小于10G）：成功上传的文档将会显示在上面的文档列表中。";
            	uptype='*.doc;*.docx;*.xls;*.xlsx;*.pdf;*.ppt;*.pptx;*.pps;*.ppsx;';
	            break;
            case "05":
            	data_type = "ppt";
            	data_typeTxt = "幻灯片";
            	data_uploadIntro = "上传幻灯片（建议小于10G）：成功上传的视频将会显示在上面的幻灯片列表中。";
            	uptype='*.ppt;*.pptx;*.pps;*.ppsx;';
	            break;
            case "03":
            	data_type = "img";
            	data_typeTxt = "图片";
            	data_uploadIntro = "上传图片（支持JPG、PNG、BMP格式的图片，建议小于10G）：成功上传的图片将会显示在上面的图片列表中。";
            	uptype='*.jpg;*.png;';
	            break;
            case "07":
            	data_type = "txt";
            	data_typeTxt = "在线创作";
            	data_uploadIntro = "";
            	uptype='';
	            break;
            case "08":
            	data_type = "exam";
            	data_typeTxt = "测试";
            	data_uploadIntro = "";
            	uptype='';
	            break;
            case "10":
            	data_type = "task";
            	data_typeTxt = "作业";
            	data_uploadIntro = "";
            	uptype='';
	            break;
          }
        	$.ajax({
				  url: $('#ctx').val()+"/ajax/courseContent/getMaterialsByCategoryId",
				  async:false,
				  data:{catalogId:opt.id},
				  dataType:'json',
				  success: function(rsult){
					  data = rsult;
		              data.pageTitle = opt.title;
		              data.lectureIndex = numParseCN(opt.index);
		              data.type = data_type;
		              data.typeTxt = data_typeTxt;
		              data.uploadIntro = data_uploadIntro;
		              $("#rightCont").html(mediaPageFn(data));
		              //学习时长默认值
		              if($("#learnTime").val()==""){
		            	  $("#learnTime").val("不详");
		              }
				  },
			});
        	
        	 var $txt = $("#upMaterial").prev(".txt"), 
             $progress = $txt.prev(".progress").children(".bar"),$pct = $txt.children(".pct"),
             $countdown = $txt.children(".countdown"),flag = true,
         	  pct,interval,countdown = 0,byteUped = 0;
                jQuery("#upMaterial").uploadify({
                	'height' : 40,
                    'width' : 68,
                    'multi' : false,
                    'simUploadLimit' : 1,
                    'swf' : $('#ctx').val()+'/resources/uploadify/uploadify.swf',
                    "buttonClass": "btn btn-primary btn-large",
                    'buttonText' : '上传',
                    'uploader' : $('#ctx').val()+'/common/file/o_upload',
                    'auto' : true,
                    'fileTypeExts' : uptype,
                    'onInit' : function(){
                    	$("#upMaterial").next(".uploadify-queue").remove();
                    },
                    'onUploadStart' : function (file) {},
                    'onUploadSuccess' : function (file, data, Response) {
                        if (Response) {
                        	$countdown.text("00:00:00");
                        	$progress.width("0");
                        	$pct.text("0%");
                            var objvalue = eval("(" + data + ")");
                            jQuery("#attId").val(objvalue.attId);
                            jQuery("#upMaterial").val(objvalue.attId);
                            //上传成功后把该素材放到节的素材列表中
                        }
                        $.ajax({
            				  url: $("#ctx").val()+"/ajax/material/saveMaterial",
            				  async:false,
            				  data:{
            					  type : type,
            					  fileName : file.name,
            					  attId : objvalue.attId
            				  },
            				  dataType : 'json',
            				  success: function(rsult){
            					  rsult.typeTxt = data_typeTxt;
            					  rsult.index = $listMedia.children("li").length + 1;
                                $listMedia.append(mediaListFn(rsult)).sortable();
                                $("#mediaCount").text($listMedia.children("li").length);
            				  }
                          });
                    },
                    'onUploadProgress' : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
                    	pct = Math.round((bytesUploaded/bytesTotal)*100)+'%';
                    	byteUped = bytesUploaded;
                    	if(flag){
                    		interval = setInterval(uploadSpeed,100);
                    		flag = false;
                    	}
                    	if(bytesUploaded == bytesTotal){
                    		clearInterval(interval);
                    	}
                    	
                    	$progress.width(pct);
                    	$pct.text(pct);
                    	countdown>0 && $countdown.text(secTransform((bytesTotal-bytesUploaded)/countdown*10));
                    }
                });
                function uploadSpeed(){
            		countdown = byteUped - countdown;
            	}
            	function secTransform(s){
            		if( typeof s == "number"){
            			s = Math.ceil(s);
            			var t = "";
            			if(s>3600){
            				t= completeZero(Math.ceil(s/3600)) + ":" + completeZero(Math.ceil(s%3600/60)) + ":" + completeZero(s%3600%60) ;
            			} else if(s>60){
            				t= "00:" + completeZero(Math.ceil(s/60)) + ":" + completeZero(s%60) ;
            			} else {
            				t= "00:00:" + completeZero(s);
            			}
            			return t;
            		}else{
            			return null;
            		}		
            	}
            	function completeZero(n){
            		return n<10 ? "0"+n : n;
            	}

        	/*
			 * data = { learnTime: "", sectionsIntro: "", mediaList: [ { id:
			 * "fdid4258924985", index: 2, title: "俞老师对国外考试项目新教师致辞" }, { id:
			 * "fdid4255", index: 1, title: "企业2013校园招聘" }, { id:
			 * "fdid425511111", index: 4, title: "国外考试新教师学习致辞" }, { id:
			 * "fdid425522222", index: 3, title: "国外部项目学习致辞" }, { id:
			 * "fdid425500", index: 5, title: "BC官员雅思考试解读" }, { id:
			 * "fdid43454545222", index: 6, title: "国外考试新教师在线学习平台介绍" } ] }
			 * data.pageTitle = opt.title; data.lectureIndex =
			 * numParseCN(opt.index); data.typeTxt = "视频"; data.uploadIntro =
			 * "上传视频（支持MP4、AVI、WMV格式的视频，建议小于10G）：成功上传的视频将会显示在下面的视频列表中。";
			 * $("#rightCont").html(mediaPageFn(data));
			 */
            $("#listMedia").sortable()
                .bind("sortupdate",function(){// 绑定拖放元素改变次序事件
                    $(this).children().each(function(i){
                        $(this).find(".title>.index").text(i+1);
                    });
                })
                .delegate(".btn-ctrls","click",function(e){
                    e.preventDefault();
                    if($(this).hasClass("icon-remove")){// 删除项
                        $(this).parent("li").remove();
                        $("#mediaCount").text($("#listMedia").children("li").each(function(i){
                            $(this).find(".title>.index").text(i+1);
                        }).length);
                    }else if($(this).hasClass("icon-pencil2")){// 编辑项
                    	var fdId = $(this).parent("li").attr("data-fdid");
                    	window.open($('#ctx').val()+"/material/materialFoward?fdId="+fdId+"&fdType="+type);
//                        var $tit = $(this).prev(".title");
//                        $(this).parent("li").hide().after(editMediaTitleFn({
//                            typeTxt: data.typeTxt,
//                            index: $tit.children(".index").text(),
//                            name: $tit.children(".name").text()
//                        }));
                    }
                })
                // 绑定保存,取消标题按钮事件
                .delegate(".form-edit-title .btn","click",function(e){
                    e.preventDefault();
                    if($(this).hasClass("btn-primary")){// 保存
                        var val = $(this).parent().prev(".control-group").find(":text").val();
                        if(val){
                            $(this).closest(".form-edit-title").prev("li").show()
                                .find(".title>.name").text(val);
                        }else {                          
							$(this).closest(".form-edit-title").find(".control-group:first").addClass("warning").find(":text").after('<span class="help-block">请填写名称！</span>');;
							return ;
						}
                    } else{
                        $(this).closest(".form-edit-title").prev("li").show();
                    }
                    $(this).closest(".form-edit-title").remove();
                });
            $("#formMedia").validate({
                submitHandler:function(form){
                    var listArr = [];
                    $("#listMedia>li").each(function(i){
                        listArr.push({
                            id: $(this).attr("data-fdid"),
                            index: $(this).find(".title>.index").text(),
                            title: $(this).find(".title>.name").text()
                        });
                    });
                    if(listArr.length==0){
                    	$("#materErr").html("请选择"+data_typeTxt);
						$("#materErr").css("display", "block");
                    	return false;
                    }else{
                    	$.post($('#ctx').val()+"/ajax/courseContent/saveCourseContent",{
                        	catalogId:opt.id,
                        	type:type,
                        	isElective: $("#isElective").val(),
                            pageTitle: $("#lectureName").val(),
                            learnTime:  $("#learnTime").val(),
                            sectionsIntro: $("#sectionsIntro").val()==""?"作者很懒，没有写章节说明。":$("#sectionsIntro").val(),
                            mediaList:JSON.stringify(listArr)
                        }).success(function(){
                        	$("#goTop").trigger("click");
                                // 提交成功
    	                        if ($('#upMaterial').length > 0) { //注意jquery下检查一个元素是否存在必须使用 .length >0 来判断
    	                   		     $('#upMaterial').uploadify('destroy'); 
    	                   		}
                            	urlRouter("sectionsDirectory");
                            });
                    }
                    
                }
            });
            $("#formMedia .btns-radio>.btn").click(function(){
            	if(this.id == "elective"){
            		$("#isElective").val("0");
            	}else{
            		$("#isElective").val("1");
            	}
          });
            var itemHtml,
                $listMedia = $("#listMedia"),
                addFlag = false,
                mediaData ;
            
            /*
			 * $("#addMedia").autocomplete("url.jsp",{ dataType: "json", parse:
			 * function(data) { return $.map(data, function(row) { return {
			 * data: row, value: row.name, result: } }); },
			 */
            $("#addMedia").autocomplete($("#ctx").val()+"/ajax/material/getMaterialBykey",{
                formatItem: function(item) {
                	$("#addMedia").next(".help-block").remove();
                	var fdName = item.name;
                	if(fdName.length>42){
                		fdName = fdName.substr(0,42)+"...";
                	}
                    return fdName+"（发布者："+item.creator+"，发布时间："+item.createtime+"）";
                },
                extraParams : {
					type : type
				},
                parse : function(data) {
                	$("#addMedia").next(".help-block").remove();
					var rows = [];
					for ( var i = 0; i < data.length; i++) {
						rows[rows.length] = {
							data : data[i],
							value : data[i].name,
							result : data[i].name
						// 显示在输入文本框里的内容 ,
						};
					}
					return rows;
				},
				dataType : 'json',
                matchContains:true ,
                max: 10,
                scroll: false,
                width:736
            }).result(function(e,item){
            	    var flag = true;
                    item.typeTxt = data.typeTxt;
                    item.index = $listMedia.children("li").length + 1;
                    itemHtml = mediaListFn(item);
                    // addFlag = true;
                    $("#addMedia").next(".help-block").remove();
                    $("#listMedia>li").each(function(i){
                        if($(this).attr("data-fdid")==item.id){
                        	$("#addMedia").after('<label class="error help-block" for="addMedia">不能添加重复的素材！</label>');;
                        	// addFlag = false;
                        	$("#addMedia").val("");
                        	flag = false;
                        }
                   });
                    if(flag){
                    	$("#materErr").css("display","none");
                    	$listMedia.append(itemHtml)
                        .sortable();
                        $("#mediaCount").text($listMedia.children("li").length);
                        $("#addMedia").val("");
                    }
                });
// .next(".btn-primary").bind("click", function(){
// if(addFlag){
// $listMedia.append(itemHtml)
// .sortable();
// $("#mediaCount").text($listMedia.children("li").length);
// addFlag = false;
// }
// });
            $("#gotoMaterial").bind("click",function(){
            	window.open($('#ctx').val()+"/material/findList?fdType="+type+"&order=FDCREATETIME",'_blank');
            });
        }

        function numParseCN(n){
            var num = ["零","一","二","三","四","五","六","七","八","九","十"];
            n = typeof n == "number" ? n : parseInt(n) ;
            return (n/10)>1 ? (num[n/10] + num[10] + num[n%10]) : num[n];
        }

		// 加载删除课程
		rightCont.loadDeleteCoursePage = function (title, fdid){	
			data.pageTitle = title;	
			$("#rightCont").html(deleteCourseFn(data));	
			
			$("#deleteCourse").click(
					function(){
						$("#goTop").trigger("click");
						confirmDel();
			});
			function confirmDel(){
				jalert("您确认要删除当前课程？",deleteCourse)
			}
			// 调用ajax删除当前课程
			function deleteCourse(){
				/*
				 * ============================================ ajax 删除当前课程
				 * ================================================
				 */
				 $.post($('#ctx').val()+"/ajax/course/deleteCourse",{
					 courseId:fdid
		         })
		             .success(function(){
		                 // 提交成功
		            	 window.location.href=$('#ctx').val()+"/course/findcourseInfos?fdType=12&order=fdcreatetime";
		             });
			}
		}
		
		// 加载授权管理
		rightCont.loadKinguserPage = function (title){			
			/*
			 * ============================================ ajax 加载JSON数据
			 * ================================================
			 */
			/*
			 * $.getJSON("url?load",function(rsult){ data = rsult;
			 * data.pageTitle = title; $("#rightCont").html(kinguserFn(data));
			 * 
			 * });
			 */
			$.ajax({
				  url: $("#ctx").val()+"/ajax/course/getAuthInfoByCourseId?courseId="+$("#courseId").val(),
				  async:false,
				  dataType : 'json',
				  success: function(rsult){
					  data = rsult;
				  }
			});
			/*
			 * data = {//ajax 成功后删除 user: [ { id: "fdid323", index: 2, imgUrl:
			 * "http://img.staff.xdf.cn/Photo/06/3A/a911e1178bf3725acd75ddbb9c7e3a06_9494.jpg",
			 * name: "杨义锋", mail: "yangyifeng@xdf.cn", org: "集团总公司", department:
			 * "知识管理中心", tissuePreparation: true, editingCourse: true }, { id:
			 * "fdid324", index: 1, imgUrl: "", name: "刘鹍", mail:
			 * "liukun@xdf.cn", org: "集团总公司", department: "知识管理中心",
			 * tissuePreparation: false, editingCourse: true }, { id: "fdid325",
			 * index: 0, imgUrl: "", name: "魏紫", mail: "weizi5@xdf.cn", org:
			 * "广州学校", department: "国外考试部", tissuePreparation: true,
			 * editingCourse: false } ] }
			 */
			data.pageTitle = title;	// ajax 成功后删除
			$("#rightCont").html(kinguserFn(data));// ajax 成功后删除
			
			$("#list_user").sortable({
				handle: '.state-dragable',
				forcePlaceholderSize: false
			})
			.find("a.icon-remove-blue").bind("click",function(e){
				e.preventDefault();
				$(this).closest("tr").remove();
			});
			var allUserData ;
			
			/*
			 * $.getJSON("${ctx}/ajax/user/findByName?q="+$('#addUser').val(),function(rsult){
			 * allUserData = rsult; });
			 */
			/*
			 * allUserData = [ { id: "fdid3232323", imgUrl:
			 * "http://img.staff.xdf.cn/Photo/06/3A/a911e1178bf3725acd75ddbb9c7e3a06_9494.jpg",
			 * name: "杨小锋", mail: "yangyifeng@xdf.cn", org: "集团总公司", department:
			 * "知识管理中心" }, { id: "fdid32234", imgUrl: "", name: "刘小鹍", mail:
			 * "liukun@xdf.cn", org: "集团总公司", department: "知识管理中心" }, { id:
			 * "fdid328", imgUrl: "", name: "魏小紫", mail: "weizi5@xdf.cn", org:
			 * "广州学校", department: "国外考试部" } ]
			 */
			/*
			 * $("#addUser").autocomplete("url.jsp",{ dataType: "json", parse:
			 * function(data) { return $.map(data, function(row) { return {
			 * data: row, value: row.name, result: row.name + " <" + row.mail +
			 * ">" } }); },
			 */
			$("#addUser").autocomplete($("#ctx").val()+"/ajax/user/findByName"+$('#addUser').val(),{
				formatMatch: function(item) { 
					return item.name + item.mail + item.org + item.department; 
				},
				formatItem: function(item) { 
					var photo;
					if(item.imgUrl.indexOf("http")>-1){
						photo=item.imgUrl;
					}else{
						photo=$("#ctx").val()+"/"+item.imgUrl;
					}
					$("#addUser").next(".help-block").remove();
					return '<img src="' 
						+ (photo) + '" alt="">' 
						+ item.name + '（' + item.mail + '），' 
						+ item.org + '  ' + item.department; 
				},
				parse : function(data) {
					$("#addUser").next(".help-block").remove();
					var rows = [];
					for ( var i = 0; i < data.length; i++) {
						rows[rows.length] = {
							data : data[i],
							value : data[i].name,
							result : data[i].name
						// 显示在输入文本框里的内容 ,
						};
					}
					return rows;
				},
			
				dataType : 'json',
				matchContains:true ,
				max: 10,
				scroll: false,
				width:748
			}).result(function(e,item){
				var flag = true;
				$("#addUser").next(".help-block").remove();
				$("#list_user>tr").each(function(){
					if($(this).attr("data-fdid")==item.id){
						$("#addUser").after('<label class="error help-block">请勿重复添加用户</label>');;
						$("#addUser").val("");
						flag = false;
					}
				});
				if(flag){
					$(this).val(item.name);
					$("#list_user").append(listUserKinguserFn(item))
					.sortable({
						handle: '.state-dragable',
						forcePlaceholderSize: true
					})
					.find("a.icon-remove-blue").bind("click",function(e){
						e.preventDefault();
						$(this).closest("tr").remove();
					});
					$("#addUser").val("");
				}
			});
			
			// 提交人员授权数据
			$("#submitUser").click(function(){
				var data = [];
				$("#list_user>tr").each(function(){
					if($(this).attr("data-creater")=="uncreater"){ 
						data.push({
							id: $(this).attr("data-fdid"),
							index: $(this).index(),
							tissuePreparation: $(this).find(".tissuePreparation").is(":checked"),
							editingCourse: $(this).find(".editingCourse").is(":checked")
						});
					}
				});
				// alert(JSON.stringify(data));
				$.ajax({
					  url: $('#ctx').val()+"/ajax/course/updateCourseAuth?courseId="+$('#courseId').val(),
					  async:false,
					  data:{data:JSON.stringify(data)},
					  dataType:'json',
					  success: function(rsult){
						  jalert_tips("保存成功");
						  $("#goTop").trigger("click");
					  },
				});
			});
		}
		
		// 加载访问权限
		rightCont.loadAccessRightPage = function (title){
			/*
			 * ============================================ ajax 加载JSON数据
			 * ================================================
			 */
			$.ajax({
				  url: $('#ctx').val()+"/ajax/course/getIsPublishInfo?courseId="+$('#courseId').val(),
				  async:false,
				  dataType:'json',
				  success: function(rsult){
					  data = rsult;
				  },
			});
			/*
			 * data = {//ajax 成功后删除 action: "",//form表单action permission:
			 * "encrypt", encryptType: "passwordProtect", coursePwd: "123123123" }
			 */
			data.pageTitle = title;	// ajax 成功后删除
			$("#rightCont").html(accessRightFn(data));// ajax 成功后删除
			
			$("#formAccessRight").validate({
				rules: {					
					coursePwd: {
						required: "#passwordProtect:checked",
						minlength: 6
					}
				},
				messages: {
					coursePwd: {
						required: "请输入密码",
						minLength: "密码必须大于6位"
					}
				}
			});
			
			// 选择加密类型事件
			$("#formAccessRight #encrypt .radio").bind("click",function(){	
				if($(this).attr("for") == "passwordProtect"){
					$("#passwordProtect").attr("checked",true);// 兼容ie7
					$("#coursePwd").removeAttr("disabled");
				} else {
					$("#coursePwd").attr("disabled",true);
				}
				if($("#authorized").attr("CHECKED")=="checked"){
					$("#courseGroupDiv").addClass("hide");
				}else{
					$("#courseGroupDiv").removeClass("hide");
				}
			});	
			$('#formAccessRight a[data-toggle="tab"]').bind('click', function (e) {
				var href = 	e.target.href.split("#").pop();		
				$("#permission").val(href);
				$("#encrypt").find("input").not($("#passwordProtect").is(":checked") ? null : $("#coursePwd")).attr("disabled", href != "encrypt");								
				if($(this).attr("href")=="#open"){
					$("#courseGroupDiv").removeClass("hide");
				}else{
					if($("#authorized").attr("CHECKED")=="checked"){
						$("#courseGroupDiv").addClass("hide");
					}else{
						$("#courseGroupDiv").removeClass("hide");
					}
				}
			});
			
			$("#addGroup").autocomplete($("#ctx").val()+"/ajax/course/getGroupTop10",{
				formatMatch: function(item) { 
					return item.groupName; 
				},
				formatItem: function(item) { 
					$("#addGroup").next(".help-block").remove();
					return item.groupName; 
				},
				parse : function(data) {
					$("#addGroup").next(".help-block").remove();
					var rows = [];
					for ( var i = 0; i < data.length; i++) {
						rows[rows.length] = {
							data : data[i],
							value : data[i].groupName,
							result : data[i].groupName
						// 显示在输入文本框里的内容 ,
						};
					}
					return rows;
				},
			
				dataType : 'json',
				matchContains:true ,
				max: 10,
				scroll: false,
				width:748
			}).result(function(e,item){
				var flag = true;
				$("#addGroup").next(".help-block").remove();
				$("#list_group>tr").each(function(){
					if($(this).attr("data-fdid")==item.groupId){
						$("#addGroup").after('<label class="error help-block">不能添加重复的群组!</label>');
						$("#addGroup").val("");
						flag = false;
					}
				});
				if(flag){
					$(this).val(item.groupName);
					$("#list_group").append(listGroupFn(item))
					.sortable({
						handle: '.state-dragable',
						forcePlaceholderSize: true
					})
					.find("a.icon-remove-blue").bind("click",function(e){
						e.preventDefault();
						$(this).closest("tr").remove();
						if($("#list_group tr").length==0){
							var d = {};
							d.groupName="全体教职员工";
							d.groupId="all";
							$("#list_group").append(listGroupFn(d));
						}
					});
					$("#list_group [data-fdid='all']").remove();
					$("#addGroup").val("");
				}
			});
			if($("#passLi").hasClass("active")&&$("#authorized").attr("CHECKED")=="checked"){
				$("#courseGroupDiv").addClass("hide");
			}else{
				$("#courseGroupDiv").removeClass("hide");
			}
			$("#list_group").find("a.icon-remove-blue").bind("click",function(e){
				e.preventDefault();
				$(this).closest("tr").remove();
				if($("#list_group tr").length==0){
					var d = {};
					d.groupName="全体教职员工";
					d.groupId="all";
					$("#list_group").append(listGroupFn(d));
				}
			});
		}
		
		// 加载课程推广
		rightCont.loadPromotionPage = function (title){
			/*
			 * ============================================ ajax 加载JSON数据
			 * ================================================
			 */
			$.ajax({
				  url: $('#ctx').val()+"/ajax/course/showcover?courseId="+$('#courseId').val(),
				  async:false,
				  dataType:'json',
				  success: function(rsult){
					  if(rsult.coverUrl==""){
						  data.coverUrl = "";
					  }else{
						  data.coverUrl =  $('#ctx').val()+"/common/file/image/"+rsult.coverUrl;
					  }
					  if(rsult.courseSkinList==""){
						  data.courseSkinList = [
									                {fdId:"1",title: "默认皮肤", imgUrl:  $('#ctx').val()+"/resources/images/courseSkin-default.png"},
									     			{fdId:"2",title: "国外考试", imgUrl:  $('#ctx').val()+"/resources/images/courseSkin-01.png"},
									    			{fdId:"3",title: "国内考试", imgUrl:  $('#ctx').val()+"/resources/images/courseSkin-02.png"},
									    			{fdId:"4",title: "英语学习", imgUrl:  $('#ctx').val()+"/resources/images/courseSkin-03.png"},
									    			{fdId:"5",title: "优能中学", imgUrl:  $('#ctx').val()+"/resources/images/courseSkin-05.png"},
									    			{fdId:"6",title: "泡泡少儿", imgUrl:  $('#ctx').val()+"/resources/images/courseSkin-04.png"}
									    			];
						  data.courseSkinId= "1";
					  }else{
						  data.courseSkinList = [];
						  for(var i=0;i<rsult.courseSkinList.length;i++){
							  if((rsult.courseSkinList)[i].imgUrl!=''){
								  data.courseSkinList.push({
									  fdId: (rsult.courseSkinList)[i].fdId,
									  title: (rsult.courseSkinList)[i].title,
									  imgUrl: $('#ctx').val()+"/common/file/image/"+(rsult.courseSkinList)[i].imgUrl
			                        });
							  }else{
								  data.courseSkinList.push({
									  fdId: (rsult.courseSkinList)[i].fdId,
									  title: (rsult.courseSkinList)[i].title,
									  imgUrl:  $('#ctx').val()+"/resources/images/courseSkin-default.png"
			                        });
							  }
							  
							  
						  }
						  
						  data.courseSkinId = rsult.courseSkinId;
					  }
					  
					  data.coursePictureList = rsult.coursePictures;
					  data.coursePictureId= rsult.coursePictureId;
					  
					  data.pageTitle = title;	// ajax 成功后删除
					  $("#rightCont").html(promotionFn(data));// ajax 成功后删除
				  },
			});
			/*
			 * $.getJSON("url?load",function(rsult){ data = rsult;
			 * data.pageTitle = title; $("#rightCont").html(promotionFn(data));
			 * 
			 * });
			 */
			// data = {//ajax 成功后删除
			// action: "#",//form表单action
			// coverUrl: "",
			// courseSkin: {title: "国内考试"},
			// courseSkinList: [
			// {title: "国外考试", imgUrl: "images/courseSkin-01.png"},
			// {title: "国内考试", imgUrl: "images/courseSkin-02.png"},
			// {title: "英语学习", imgUrl: "images/courseSkin-03.png"},
			// {title: "优能中学", imgUrl: "images/courseSkin-04.png"},
			// {title: "优能小学", imgUrl: "images/courseSkin-05.png"}]
			// }
			//data.pageTitle = title;	// ajax 成功后删除
			//$("#rightCont").html(promotionFn(data));// ajax 成功后删除
			
			// 选择图片库事件
			$("#formPromotion .coursePicture>li>a").bind("click",function(e){
				e.preventDefault();
				$(this).parent().addClass("active").siblings().removeClass("active");
				$("#coursePicture").val($(this).attr("data-fdid"));
				$("#attIdID").val("");
				successSelectArea($(this).attr("data-img"));
			});	
			
			// 选择课程皮肤事件
			$("#formPromotion .courseSkin>li>a").bind("click",function(e){
				e.preventDefault();
				$(this).parent().addClass("active").siblings().removeClass("active");
				$("#courseSkin").val($(this).attr("data-fdid"));
			});		
			/* 课程推广 封页图片上传 */
			var $txt = $("#upMovie").prev(".txt"), 
	        $progress = $txt.prev(".progress").children(".bar"),
	        $pct = $txt.children(".pct"),
	        $countdown = $txt.children(".countdown"),
	    	flag = true,
	    	pct,interval,countdown = 0,byteUped = 0;

			jQuery("#upMovie").uploadify({
				        'height' : 40,
	                    'width' : 68,
	                    'multi' : false,
	                    'simUploadLimit' : 1,
	                    'swf' : $('#ctx').val()+'/resources/uploadify/uploadify.swf',
	                    "buttonClass": "btn btn-primary btn-large",
	                    'buttonText' : '上 传',
	                    'uploader' : $('#ctx').val()+'/common/file/o_upload',
	                    'auto' : true,// 选中后自动上传文件
	                    'fileTypeExts' : '*.jpg;*.png;',
	                    'fileSizeLimit':2048,// 限制文件大小为2m
	                    'onInit' : function(){
	                    	$("#upMovie").next(".uploadify-queue").remove();
	                    },
	                    'onUploadStart' : function (file) {},
	                    'onUploadSuccess' : function (file, data, Response) {
	                        if (Response) {
	                        	$countdown.text("00:00:00");
	                        	$progress.width("0");
	                        	$pct.text("0%");
	                            var objvalue = eval("(" + data + ")");
	                            jQuery("#attIdID").val(objvalue.attId);
	                            $("#coursePicture").val("");
	                            $("#formPromotion .coursePicture>li").siblings().removeClass("active");
                                $("#imgshow").hide();
                                $(".cutimg-box").removeClass("hide");
                                var preImg = $('#ctx').val()+'/common/file/image/' + objvalue.attId;
                                var imgSrc = escape(preImg);
                                $.get($('#ctx').val()+"/common/imageCut/page?imgSrcPath="+imgSrc+"&zoomWidth=584&zoomHeight=258&imgId="+objvalue.attId,function(data){
                                	$(".cutimg-box").html(data);
                                });
                                //$("#iframeimg").attr("src",$('#ctx').val()+"/common/imageCut/page?imgSrcPath="+imgSrc+"&zoomWidth=584&zoomHeight=258&imgId="+objvalue.attId);
	                          
	                        }
	                    },
	                    'onUploadProgress' : function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
	                    	pct = Math.round((bytesUploaded/bytesTotal)*100)+'%';
	                    	byteUped = bytesUploaded;
	                    	if(flag){
	                    		interval = setInterval(uploadSpeed,100);
	                    		flag = false;
	                    	}
	                    	if(bytesUploaded == bytesTotal){
	                    		clearInterval(interval);
	                    	}
	                    	
	                    	$progress.width(pct);
	                    	$pct.text(pct);
	                    	countdown>0 && $countdown.text(secTransform((bytesTotal-bytesUploaded)/countdown*10));
	                    }
	         });
			function uploadSpeed(){
	    		countdown = byteUped - countdown;
	    	}
	    	function secTransform(s){
	    		if( typeof s == "number"){
	    			s = Math.ceil(s);
	    			var t = "";
	    			if(s>3600){
	    				t= completeZero(Math.ceil(s/3600)) + ":" + completeZero(Math.ceil(s%3600/60)) + ":" + completeZero(s%3600%60) ;
	    			} else if(s>60){
	    				t= "00:" + completeZero(Math.ceil(s/60)) + ":" + completeZero(s%60) ;
	    			} else {
	    				t= "00:00:" + completeZero(s);
	    			}
	    			return t;
	    		}else{
	    			return null;
	    		}		
	    	}
	    	function completeZero(n){
	    		return n<10 ? "0"+n : n;
	    	}

		}
		
		// 加载详细信息
		rightCont.loadDetailInfoPage = function (title){
			/*
			 * ============================================ ajax 加载JSON数据
			 * ================================================
			 */
			$.ajax({
				  url: $('#ctx').val()+"/ajax/course/getDetailCourseInfoById?courseId="+$('#courseId').val(),
				  async:false,
				  dataType:'json',
				  success: function(rsult){
					  data = rsult;
						data.pageTitle = title;
						$("#rightCont").html(detailInfoFn(data));	
						var editor = KindEditor.create('textarea[id="courseAbstract"]', {
								resizeType : 1,
								cssData : 'body {font-size:14px;}',
								allowPreviewEmoticons : false,
								allowImageUpload : true,
								uploadJson : $('#ctx').val()+'/common/file/KEditor_uploadImg',
								items : ['source', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
									'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
									'insertunorderedlist', '|', 'undo', 'redo','link','image'],
								afterBlur: function(){this.sync();}
							});
						editor.html(data.courseAbstract);
						//ajax保存课程详细信息
						$("#saveDetailInfo").click(function(e) {
							if(!$("#formDetailInfo").valid()){
								return;
							}
							if(editor.isEmpty()){
								 $("#courseAbstract").val('');
							}
							saveDetailInfo();
						});
				  },
			});
			$("#courseAuthor").bind("change",function(){
				$("#courseAuthorId").val("");
			});
			$("#courseAuthor").autocomplete($("#ctx").val()+"/ajax/user/findByName",{
				formatMatch: function(item) { 
					return item.name + item.mail + item.org + item.department; 
				},
				formatItem: function(item) { 
					var photo;
					if(item.imgUrl.indexOf("http")>-1){
						photo=item.imgUrl;
					}else{
						photo=$("#ctx").val()+"/"+item.imgUrl;
					}
					$("#courseAuthor").next(".help-block").remove();
					return '<img src="' 
						+ (photo) + '" alt="">' 
						+ item.name + '（' + item.mail + '），' 
						+ item.org + '  ' + item.department; 
				},
				parse : function(data) {
					$("#courseAuthor").next(".help-block").remove();
					var rows = [];
					for ( var i = 0; i < data.length; i++) {
						rows[rows.length] = {
							data : data[i],
							value : data[i].name,
							result : data[i].name
						// 显示在输入文本框里的内容 ,
						};
					}
					return rows;
					
				},
			
				dataType : 'json',
				matchContains:true ,
				max: 10,
				scroll: false,
				width:748
			}).result(function(e,item){
				$("#courseAuthor").val(item.name);
				//$("#authorDescrip").html(item.selfIntroduction);
				$("#courseAuthorId").val(item.id);
			});
			
			
			
			/*
			 * data = {//ajax 成功后删除 action: "#",//form表单action courseAbstract:
			 * "", learnObjectives: ["了解雅思考试基本情况","了解英国留学基本情况"], suggestedGroup:
			 * ["新教师","学校主管"], courseRequirements:
			 * ["学习本课程的教师必须通过TELTS考试","准备好3-8教材"] } data.pageTitle = title;
			 * //ajax 成功后删除 $("#rightCont").html(detailInfoFn(data));//ajax
			 * 成功后删除
			 */		
			$("#formDetailInfo").validate();	
			
			// 添加字段列表项事件
			$("#formDetailInfo .formAdd>.btn").bind("click",function(e){
				e.preventDefault();
				if($(this).prevAll(":text").val()){
					var $field = $(this).parent().prev(".list_alert").prev(":hidden");
					var tit = $(this).prevAll(":text").val();
					var $item = $(itemDetailInfoFn(tit));
					$item.bind("closed",delItem);
					$(this).parent().removeClass("warning").prev(".list_alert").append($item);
					var _val = $field.val();
					$field.val(_val + "#" + tit);
					$(this).prevAll(":text").val("");
				} else {
					$(this).parent().addClass("warning");
				}	
					
			});
			// 删除字段列表项事件
			$("#formDetailInfo .list_alert>.alert").bind("click",delItem);
			function delItem(){
				var arr = '';
				$(this).siblings(".alert").each(function(){
					arr = arr + $(this).children("span").text()+'#';
				})
				$(this).parent().prev(":hidden").val(arr);
			}			
			
		}	
		
		// 加载基本信息
		rightCont.loadBasicInfoPage = function (title){
			/*
			 * ============================================ ajax 加载基本信息数据
			 * ================================================
			 */
			$.ajax({
				  url: $('#ctx').val()+"/ajax/course/getBaseCourseInfoById?courseId="+$('#courseId').val(),
				  async:false,
				  dataType:'json',
				  success: function(rsult){
						data = rsult ;	
						data.pageTitle = title;
						$("#rightCont").html(basicInfoFn(data));
						if($("#fdPrice").val()==""){
							$("#fdPrice").val("0.00");
						}
				  },
			});
			/*
			 * data = {//ajax 成功后删除 action: "#",//模板详情_基本信息 的form表单action
			 * courseTit: "集团英联邦项目雅思强化口语学习课程", subTit: "", keyword:
			 * ["雅思","新教师学习"], courseType: "1", courseTypeList: [ {title:
			 * "国外考试", id: "1"}, {title: "国内考试", id: "2"}, {title: "英语学习", id:
			 * "3"}, {title: "优能中学", id: "4"}, {title: "优能小学", id: "5"}] }
			 * data.pageTitle = title; //ajax 成功后删除
			 * $("#rightCont").html(basicInfoFn(data));//ajax 成功后删除
			 */
			$("#formBasicInfo").validate();	
			$("#formBasicInfo").find("#btns-radio-sectionOrder>.btn").on("click",function(){
                $("#sectionOrder").val(this.id);
            });
			$("#formBasicInfo").find("#btns-radio-isCompulsoryCourse>.btn").on("click",function(){
                $("#isCompulsoryCourse").val(this.id);
            });
			// 添加关键词事件
			$("#formBasicInfo .keywordWrap>.btn-add").bind("click",function(e){
				e.preventDefault();
				var $addBtn = $(this);			
				if(!$addBtn.next().hasClass("inpKeyword")){
					$addBtn.after(addKeywordFn())
					.next(".inpKeyword").children(".btn").bind("click",function(e){
						e.preventDefault();				
						if($(this).hasClass("btn-primary")){
							$(this).next().next(".help-block").remove();
							if($(this).prevAll(":text").val()){
								var tit = $(this).prevAll(":text").val();
								var _val = $("#keyword").val();
								var strs= new Array(); //定义一数组
								strs=_val.split(","); //字符分割 
								for (var i=0;i<strs.length ;i++ ) {
									var temp = strs[i];
									if(temp == tit){
										$(this).next().after('<label class="error" for="addKey">不能添加重复的关键词！</label>');
									    return;
									}
								}
								$addBtn.before(tagKeywordFn({keyword: tit})).prev().bind("click",delKeyword);
								$("#keyword").val(_val + "," + tit);
								$(this).parent().remove();
							} else {
								$(this).parent().addClass("warning");
							}					
						} else {
							$(this).parent().remove();	
						}							
					});
				}
				$("#addKey").autocomplete($("#ctx").val()+"/ajax/course/findTagInfosByKey",{
					formatMatch: function(item) {
						return item.fdName + item.fdDescription; 
					},
					formatItem: function(item) {
						$("#addKey").parent().removeClass("warning").find(".help-block").remove();
						return item.fdName; 
					},
					parse : function(data) {
						$("#addKey").parent().removeClass("warning").find(".help-block").remove();
						var rows = [];
						for ( var i = 0; i < data.length; i++) {
							rows[rows.length] = {
								data : data[i],
								value :  data[i].fdName,
								result :  data[i].fdName
							// 显示在输入文本框里的内容 ,
							};
						}
						return rows;
					},
					dataType : 'json',
					matchContains:true ,
					max: 10,
					scroll: false,
					width:387
				});
			});
			// 删除关键词事件
			$("#formBasicInfo .keywordWrap>.alert-tag").bind("click",delKeyword);
			function delKeyword(){
				var arr = [];
				$(this).siblings(".alert-tag").each(function(){
					arr.push($(this).children("span").text());
				})
				$("#keyword").val(arr);
			}
			// 选择课程类型事件
			$("#formBasicInfo .courseType>li>a").bind("click",function(e){
				e.preventDefault();
				$(this).parent().addClass("active").siblings().removeClass("active");
				$("#courseType").val($(this).next("input").val());
			});
			
		}
		
		// 加载章节目录
		rightCont.loadSectionDirectoryPage = function(title){
			/*
			 * ============================================ ajax 加载章节数据
			 * ================================================
			 */
			
			$.ajax({
				  url: $('#ctx').val()+"/ajax/catalog/getCatalogJsonByCourseId?courseId="+$('#courseId').val(),
				  async:false,
				  dataType:'json',
				  success: function(rsult){
						data = rsult ;	
						data.pageTitle = title;		
						$("#rightCont").html(loadsectionsDirectoryFn(data));
						updataProgressCourses($('#sortable').children(".lecture").length);	
				  },
			});
			/*
			 * data = { //ajax 成功后删除 chapter: [{ id: 'fdid3214321' , index: 0,
			 * num: 1, title: "业务学习" }, { id: 'fdid3221' , index: 4, num: 2,
			 * title: "学术准备" }], lecture: [{ id: 'fdid32210000' , index: 2, num:
			 * 2, title: "学习入门文档" , type: "doc" }, { id: 'fdid32210239', index:
			 * 1, num: 1, title: "学习入门视频" , type: "video" }, { id:
			 * 'fdid3frewf239', index: 3, num: 3, title: "参加在线考试" , type: "none" }, {
			 * id: 'fdid3frewf000111', index: 6, num: 5, title: "参加在线考试" , type:
			 * "task" }, { id: 'fdid3frewf000111', index: 5, num: 4, title:
			 * "参加学术考试" , type: "video" }] }
			 */
			var $sections = $('#sortable');
			$sections.sortable({
				handle: '.sortable-bar',
				forcePlaceholderSize: true
			})
			.bind("sortupdate",changIndex)// 绑定拖放元素改变次序事件
			// 绑定编辑标题按钮事件
			.delegate(".sortable-bar>.icon-pencil2","click",function(e){
				e.preventDefault();
				var $tit = $(this).prev(".title");
				var data = rtnSectionData($tit.closest("li").hasClass("chapter"),$tit.children(".index").text(),$tit.children(".name").text());		
				$(this).closest(".sortable-bar").addClass("hide").after(editTitleFn(data));
			})	
			// 绑定删除章节按钮事件
			.delegate(".sortable-bar>.icon-remove","click",function(e){
				e.preventDefault();
				var $li = $(this).closest("li");
				// =====================================================可在此 ajax
				// 删除章节数据==================================
				$.post($('#ctx').val()+'/ajax/catalog/deleteCatalogById',{fdid: $li.attr("data-fdid")})
				.success(function(){
					$li.remove();
					changIndex();
					updataProgressCourses($sections.children(".lecture").length);
				});
				/*
				 * $li.remove(); changIndex();
				 * updataProgressCourses($sections.children(".lecture").length);
				 */
			})
			// 绑定编辑节内容按钮事件
			.delegate(".sortable-bar>.btn-edit","click",function(e){
				e.preventDefault();
				if($(this).parent().next(".lecture-content").length){
					$(this).addClass("hide").parent().next(".lecture-content").removeClass("hide");
				}				
			})
			// 绑定节内容关闭按钮事件
			.delegate(".lecture-content>.hd>a.icon-remove-sign","click",function(e){
				e.preventDefault();		
				$(this).closest(".lecture-content").addClass("hide").prevAll(".sortable-bar").children(".btn-edit").removeClass("hide");
								
			})
			// 绑定保存章节标题按钮事件
			.delegate(".form-edit-title .btn-primary","click",function(e){
				e.preventDefault();
				var $form = $(this).closest(".form-edit-title");
				var $tit = $form.find("input.input-block-level");
				var $li = $form.parent("li");
				var data, fdId;
				if($tit.val()){			
					if($form.prev().hasClass("sortable-bar")){// 编辑已有章节
						// =====================================================可在此
						// ajax 更新章节名称数据==================================
						$.post($('#ctx').val()+'/ajax/catalog/updateCatalogNameById',{fdid: $li.attr("data-fdid"), title: $tit.val()},function(data){},"json")
						.success(function(){
							$li.children(".sortable-bar").removeClass("hide").find(".name").text($tit.val());
						});
						/*
						 * $li.children(".sortable-bar").removeClass("hide").find(".name").text($tit.val());
						 */
					} else {// 新加章节
						// =====================================================可在此
						// ajax 提交新加章节的数据, 返回fdId==============================
						$.ajax({
							  url: $('#ctx').val()+"/ajax/catalog/addCatalog",
							  async:false,
							  data:{courseid:$("#courseId").val(),ischapter:$li.hasClass("chapter"),fdtotalno:parseInt($sections.children("li").length)-1,fdno:$form.find(".index").text(),title:$tit.val()},
							  dataType:'json',
							  success: function(result){
								  var data = rtnSectionData($li.hasClass("chapter"),$form.find(".index").text(),$tit.val(),$li.length,"none",result.id);
									$("#courseId").val(result.courseid);
									$li.attr("data-fdid",result.id);
									$form.before(sectionsFn(data));	
									$sections.sortable("destroy").sortable({
										handle: '.sortable-bar',
										forcePlaceholderSize: true
									});
									var numAll = $form.find(".index").text();
									if(!$li.hasClass("chapter")){
										updataProgressCourses(numAll);
									}
									
							  },
						});
						/*
						 * var tempid = "sectionId0" + Math.random() ; var data =
						 * rtnSectionData($li.hasClass("chapter"),$form.find(".index").text(),$tit.val(),$li.length,"none");
						 * $li.attr("data-fdid",tempid)
						 * $form.before(sectionsFn(data)); $sections.sortable({
						 * handle: '.sortable-bar', forcePlaceholderSize: true
						 * }); var numAll = $form.find(".index").text();
						 * updataProgressCourses(numAll);
						 */
					}			
					$form.remove();
				} else {
					$form.find(".control-group:first").addClass("warning").find(":text").after('<span class="help-block">请填写标题！</span>');;
				}
			})
			// 绑定取消章节标题编辑按钮事件
			.delegate(".form-edit-title .btn-link","click",function(e){
				e.preventDefault();
				var $form = $(this).closest(".form-edit-title");
				var $li = $form.parent("li");
				if(!$li.attr("data-fdid")){
					$li.remove();
				}
				$(this).closest(".form-edit-title").prev(".sortable-bar").removeClass("hide").end().remove();		
			})	
			// 绑定输入框不为空时，去掉警告样式
			.delegate(":text","keydown",function(){
				if($(this).val() && $(this).closest(".control-group").hasClass("warning")){
					$(this).closest(".control-group").removeClass("warning").find(".help-block").remove();
				}		
			})
			// 绑定课程类型按钮事件
			.delegate(".lecture-content>.bd>.btn-type","click",function(e){
				if($(this).hasClass("disabled")){
					e.preventDefault();
				} else {
                    var $tit = $(this).closest(".lecture-content").prev().find(".title");
                    urlRouter(false,{
                        id: $(this).closest(".lecture").attr("data-fdid"),
                        title: $tit.find(".name").text(),
                        index: $tit.find(".index").text()
                    });
                    $("#goTop").trigger("click");
                }
			});
            // 更新章节顺序方法
            function changIndex(){
                var $items = $sections.children("li"),
                    i_ch = 1,
                    i_le = 1,
                    data = {
                		courseid : $("#courseId").val(),
                        chapter : [],
                        lecture : []
                    }, $item ;
                $items.each(function(i){
                    $item = $(this);
                    if($item.hasClass("chapter")){
                        $item.find(".title>.index").text(i_ch);
                        data.chapter.push({
                            index: i,
                            num: i_ch++,
                            id: $item.attr("data-fdid")
                        });
                    } else if($item.hasClass("lecture")){
                        $item.find(".title>.index").text(i_le);
                        data.lecture.push({
                            index: i,
                            num: i_le++,
                            id: $item.attr("data-fdid")
                        });
                    }
                });
                $.post($('#ctx').val()+"/ajax/catalog/updateCatalogOrder",{courseid : $("#courseId").val(),chapter:JSON.stringify(data.chapter),lecture:JSON.stringify(data.lecture)},function(res){},'json');// ajax
																																																				// 更新所有章节排序
            }
			// 绑定添加章事件
			$("#addChapter").bind("click",function(){
				if(!$sections.children(".lecture").last().children(".form-edit-title").length){
					if(!$sections.children(".chapter").last().children(".form-edit-title").length){
						var data = {
							chapter: {
								index: $sections.children("li").length, 
								num: $sections.children(".chapter").length + 1
							}			
						}		
						$sections.append(addSectionsFn(data));			
					} else {
						$sections.children(".chapter").last().find(".form-edit-title .control-group:first").removeClass("warning").find(".help-block").remove();
						$sections.children(".chapter").last().find(".form-edit-title .control-group:first").addClass("warning").find(":text").after('<span class="help-block">请先完成未保存的章！</span>');
					}		
				} else {
					$sections.children(".lecture").last().find(".form-edit-title .control-group:first").removeClass("warning").find(".help-block").remove();
					$sections.children(".lecture").last().find(".form-edit-title .control-group:first").addClass("warning").find(":text").after('<span class="help-block">请先完成未保存的节！</span>');
				}	
					
			});
			// 绑定添加节事件
			$("#addLecture").bind("click",function(){
				if(!$sections.children(".chapter").last().children(".form-edit-title").length){
					if(!$sections.children(".lecture").last().children(".form-edit-title").length){
						var data = {
							lecture: {
								index: $sections.children("li").length,
								num: $sections.children(".lecture").length + 1
							}
						}		
						$sections.append(addSectionsFn(data));			
					} else {
						$sections.children(".lecture").last().find(".form-edit-title .control-group:first").removeClass("warning").find(".help-block").remove();
						$sections.children(".lecture").last().find(".form-edit-title .control-group:first").addClass("warning").find(":text").after('<span class="help-block">请先完成未保存的节！</span>');
					}			
				} else {
					$sections.children(".chapter").last().find(".form-edit-title .control-group:first").removeClass("warning").find(".help-block").remove();
					$sections.children(".chapter").last().find(".form-edit-title .control-group:first").addClass("warning").find(":text").after('<span class="help-block">请先完成未保存的章！</span>');
				}
			});
		}
		
		// 格式化章节数据
		function rtnSectionData(isChapter,nm,tit,i,typ,fdid){
			var data = {
					chapter : undefined,
					lecture : undefined
			}
			if(isChapter){
				data.chapter = {
						id: fdid ,
						index: i,
						num: nm,
						title: tit		
				}			
			} else {
				data.lecture = {
						id: fdid ,
						index: i,
						num: nm,
						title: tit,
						type: typ
				}			
			}
			return data;
		}
		
		// 更新课程进度条
		function updataProgressCourses(numAll){
			var $prog = $("#progress_courses");
			var comp = numAll - $('#sortable').find(".sortable-bar>.title>.icon-none").length;
			$prog.find(".progress>.bar").width(comp/numAll*100 + "%");
			$prog.find(".num_comp").text(comp);
			$prog.find(".num_all").text(numAll);
			if(numAll > 0){
				if(numAll == comp){
					enabledPublish();
				} else {
					disabledPublish();
				}
			}
		}
		
		// 激活预览和发布按钮方法
		function enabledPublish(){
			$(window).scrollTop(0);
			$("#page-title>.btn-group>.btn").removeAttr("disabled");
		}
		
		// disabled预览和发布按钮方法
		function disabledPublish(){
			$("#page-title>.btn-group>.btn").attr("disabled","disabled");
		}
		
	}());
	//调用ajax保存课程模板的详细信息
	function saveDetailInfo(){
		$.post($('#ctx').val()+'/ajax/course/saveDetailInfo',{
			 courseId : $("#courseId").val(),
			 courseAbstract: $("#courseAbstract").val(),
			 learnObjectives:  $("#learnObjectives").val(),
			 suggestedGroup: $("#suggestedGroup").val(),
			 courseRequirements: $("#courseRequirements").val(),
			 courseAuthor: $("#courseAuthor").val(),
			 courseAuthorId: $("#courseAuthorId").val(),
			 authorDescrip: $("#authorDescrip").val()
			})
		.success(function(){
			//KindEditor.remove('textarea[name="courseAbstract"]');
			//提交成功跳转到详细信息
			jalert_tips("保存成功");
			$("#goTop").trigger("click");
       	   // urlRouter("promotion");
		});
	}	
	
	
