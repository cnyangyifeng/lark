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
		};
		// 基本信息 模板函数
		var basicInfoFn = doT.template(document.getElementById("basicInfoTemplate").text, undefined, def);
		
		
		// 系列推广 模板函数
		var promotionFn = doT.template(document.getElementById("promotionTemplate").text, undefined, def);

		// 删除系列 模板函数
		var deleteSeriesFn = doT.template(document.getElementById("deleteSeriesTemplate").text, undefined, def);

		// 添加系列模板函数
		var addSectionsFn = doT.template(document.getElementById("addSectionsTemplate").text, undefined, {
			edittitle:document.getElementById("formEditSectionTitle").text		
		});
		// 展示系列模板函数
		var sectionsFn = doT.template(document.getElementById("sectionsTemplate").text + document.getElementById("sectionBarTemplate").text);
		// load系列目录模板函数
		var loadsectionsDirectoryFn = doT.template(document.getElementById("sectionDirectoryTemplate").text + document.getElementById("sectionBarTemplate").text, undefined, def);
		
		 // 系列课程页 模板函数
        var mediaPageFn = doT.template(document.getElementById("mediaPageTemplate").text);
        // 系列课程页 列表项 模板函数
        var mediaListFn = doT.template(document.getElementById("mediaListTemplate").text);
        // 编辑系列标题模板函数
        var editMediaTitleFn = doT.template(document.getElementById("formEditMediaTitle").text);

		// 编辑系列标题模板函数
		var editTitleFn = doT.template(document.getElementById("formEditSectionTitle").text);
		
		 function numParseCN(n){
	            var num = ["零","一","二","三","四","五","六","七","八","九","十"];
	            n = typeof n == "number" ? n : parseInt(n) ;
	            return (n/10)>1 ? (num[n/10] + num[10] + num[n%10]) : num[n];
	        }

        //加载系列课程选择列表
        rightCont.loadVideoPage = function (opt){
        	$.ajax({
				  url: $('#ctx').val()+"/ajax/series/getSeriesCourseById",
				  async:false,
				  data:{phasesId:opt.id},
				  dataType:'json',
				  success: function(rsult){
					 // alert(JSON.stringify(rsult));
					  data = rsult;
		              data.pageTitle = opt.title;
		              data.lectureIndex = numParseCN(opt.index);
		              $("#rightCont").html(mediaPageFn(data));
				  },
			});
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
                    if(listArr.length<1){
                    	//jalert("请选择课程信息后保存!");//添加课程为空错误提示;
                    	$("#showError").html("<font size='2' color='red'>请选择阶段的课程信息!</font>");
                    	return;
                    }
                   // alert(JSON.stringify(listArr));
                    $.post($('#ctx').val()+"/ajax/series/saveSeriesCourse",{
                    	phasesId:opt.id,
                    	phaseTitle:$("#seriesTitle").val(),
                        sectionsIntro: $("#sectionsIntro").val()==""?"系列作者很懒，没有写阶段说明。":$("#sectionsIntro").val(),
                        mediaList:JSON.stringify(listArr)
                    })
                        .success(function(){
                        	 if ($('#upMovie').length > 0) { //注意jquery下检查一个元素是否存在必须使用 .length >0 来判断
	                   		     $('#upMovie').uploadify('destroy'); 
	                   		}
                        	urlRouter("sectionsDirectory");
                        });
                }
            });
            var itemHtml,
                $listMedia = $("#listMedia");
            $("#addMedia").autocomplete($("#ctx").val()+"/ajax/series/getCourseBykey",{
                formatItem: function(item) {
                	$("#addMedia").next(".help-block").remove();
                	var fdName = item.name;
                	if(fdName.length>20){
                		fdName = fdName.substr(0,18)+"...";
                	}
                	 return fdName+"（发布者："+item.creator+"，发布时间："+item.createtime+"）";
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
                width:750
            }).result(function(e,item){
            		$("#showError").html("");//清除错误提示;
            	    var flag = true;
                    //item.typeTxt = data.name;
                    item.index = $listMedia.children("li").length + 1;
                    itemHtml = mediaListFn(item);
                    // addFlag = true;
                    $("#addMedia").next(".help-block").remove();
                    $("#listMedia>li").each(function(i){
                        if($(this).attr("data-fdid")==item.id){
                        	$("#addMedia").after('<span class="help-block">不能添加重复的课程！</span>');
                        	$("#addMedia").val("");
                        	flag = false;
                        }
                   });
                    if(flag){
                    	$listMedia.append(itemHtml)
                        .sortable();
                        $("#mediaCount").text($listMedia.children("li").length);
                        $("#addMedia").val("");
                    }
                });
            $("#gotoMaterial").bind("click",function(){
            	window.open($('#ctx').val()+"/course/findcourseInfos?fdType=12&order=fdcreatetime",'_blank');
            });
        };
		
		// 加载系列推广
		rightCont.loadPromotionPage = function (title){
			$.ajax({
				  url: $('#ctx').val()+"/ajax/series/showcover?seriesId="+$('#seriesId').val(),
				  async:false,
				  dataType:'json',
				  success: function(rsult){
					  if(rsult.coverUrl==""){
						  data.coverUrl = "";
					  }else{
						  data.coverUrl =  $('#ctx').val()+"/common/file/image/"+rsult.coverUrl;
					  }
					  data.pageTitle = title;	// ajax 成功后删除
					  data.courseSkinList = [
					                         {title: "默认皮肤", imgUrl:  $('#ctx').val()+"/resources/images/courseSkin-default.png"}];
					 data.courseSkin= {title: "默认皮肤"};
					  $("#rightCont").html(promotionFn(data));// ajax 成功后删除
				  },	
			});
			data.pageTitle = title;
			/* 系列推广封页图片上传 */
			var $txt = $("#upMovie").prev(".txt"), 
	        $progress = $txt.prev(".progress").children(".bar"),
	        $pct = $txt.children(".pct"),
	        $countdown = $txt.children(".countdown"),
	    	flag = true,pct,interval,countdown = 0,byteUped = 0;
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
	                    'onUploadStart' : function (file) {
	                    },
	                    'onUploadSuccess' : function (file, data, Response) {
	                        if (Response) {
	                        	$countdown.text("00:00:00");
	                        	$progress.width("0");
	                        	$pct.text("0%");
	                        	var objvalue = eval("(" + data + ")");
	                            jQuery("#attIdID").val(objvalue.attId);
	                         /*  if (jQuery("#imgshow")) {
                       			jQuery("#imgshow").attr('src',  $('#ctx').val()+'/common/file/image/' + objvalue.attId);
                   			   } */
	                           $("#imgshow").hide();
                               $(".cutimg-box").show();
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

		};
		// 加载系列基本信息
		rightCont.loadBasicInfoPage = function (title){
			$.ajax({
				  url: $('#ctx').val()+"/ajax/series/getBaseSeriesInfoById?seriesId="+$('#seriesId').val(),
				  async:false,
				  dataType:'json',
				  success: function(rsult){
						data = rsult ;	
						data.pageTitle = title;
						$("#rightCont").html(basicInfoFn(data));
				  },
			});
			$("#formBasicInfo").validate();	
			$("#formBasicInfo").find(".btns-radio>.btn").on("click",function(){
                $("#sectionIsava").val(this.id);
            });
			$("#seriesAuthor").bind("change",function(){
				$("#seriesAuthorId").val("");
			});
			$("#seriesAuthor").autocomplete($("#ctx").val()+"/ajax/user/findByName",{
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
					$("#seriesAuthor").next(".help-block").remove();
					return '<img src="' 
						+ (photo) + '" alt="">' 
						+ item.name + '（' + item.mail + '），' 
						+ item.org + '  ' + item.department; 
				},
				parse : function(data) {
					$("#seriesAuthor").next(".help-block").remove();
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
				$("#seriesAuthor").val(item.name);
				//$("#authorDescrip").html(item.selfIntroduction);
				$("#seriesAuthorId").val(item.id);
			});
		};
		
		// 加载阶段目录
		rightCont.loadSectionDirectoryPage = function(title){
			/*
			 * ============================================ ajax 加载阶段目录
			 */
			$.ajax({
				  url: $('#ctx').val()+"/ajax/series/getSeriesBySeriesId?seriesId="+$('#seriesId').val(),
				  async:false,
				  dataType:'json',
				  success: function(rsult){
						data = rsult ;	
						data.pageTitle = title;		
						$("#rightCont").html(loadsectionsDirectoryFn(data));
						//alert(JSON.stringify(data));
						updataProgressCourses($('#sortable').children(".chapter").length);	
				  },
			});
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
				var $index=$tit.children(".index");
				var data = rtnSectionData($tit.closest("li").hasClass("chapter"),$index.text(),$tit.children(".name").text(),$index.text());		
				//alert(JSON.stringify(data))
				$(this).closest(".sortable-bar").addClass("hide").after(editTitleFn(data));
			})	
			// 绑定编辑节内容按钮事件
			.delegate(".sortable-bar>.btn-edit","click",function(e){
				if($(this).hasClass("disabled")){
					e.preventDefault();
				} else {
                    var $tit = $(this).closest(".sortable-bar").find(".title");
                    urlRouter(false,{
                        id: $(this).closest(".chapter").attr("data-fdid"),
                        title: $tit.find(".name").text(),
                        index: $tit.find(".index").text()
                    });
                }
			})
			
			// 绑定删除阶段按钮事件
			.delegate(".sortable-bar>.icon-remove","click",function(e){
					e.preventDefault();
					var $li = $(this).closest("li");
					// 删除阶段数据==================================
					$.post($('#ctx').val()+'/ajax/series/deletePhasesById',{phasesId: $li.attr("data-fdid")})
					.success(function(){
						$li.remove();
						changIndex();
						updataProgressCourses($sections.children(".chapter").length);
						
					});
			})
			// 绑定保存阶段标题按钮事件
			.delegate(".form-edit-title .btn-primary","click",function(e){
				e.preventDefault();
				var $form = $(this).closest(".form-edit-title");
				var $tit = $form.find("input.input-block-level");
				var $li = $form.parent("li");
				if($tit.val()){			
					if($form.prev().hasClass("sortable-bar")){// 编辑已有阶段
						$.post($('#ctx').val()+'/ajax/series/updateSeriesFdNameById',{fdid: $li.attr("data-fdid"), title: $tit.val()},function(data){},"json")
						.success(function(){
							$li.children(".sortable-bar").removeClass("hide").find(".name").text($tit.val());
						});
					} else {// 新加阶段
						$.ajax({
							  url: $('#ctx').val()+"/ajax/series/saveSeries",
							  async:false,
							  data:{seriesId:$("#seriesId").val(),ischapter:$li.hasClass("chapter"),fdtotalno:parseInt($sections.children("li").length)-1,fdno:$form.find(".index").text(),title:$tit.val()},
							  dataType:'json',
							  success: function(result){
								  var data = rtnSectionData($li.hasClass("chapter"),$li.length,$tit.val(),$form.find(".index").text(),"none",result.id);
									$("#seriesId").val(result.seriesId);
									$li.attr("data-fdid",result.id);
									$form.before(sectionsFn(data));	
									$sections.sortable("destroy").sortable({
										handle: '.sortable-bar',
										forcePlaceholderSize: true
									});
									updataProgressCourses($sections.children(".chapter").length);
							  },
						});
					}			
					$form.remove();
				} else {
					$form.find(".control-group:first").addClass("warning").find(":text").after('<span class="help-block">请填写标题！</span>');;
				}
			})
			// 绑定取消阶段标题编辑按钮事件
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
			});
            // 更新阶段顺序方法
            function changIndex(){
                var $items = $sections.children("li"),
                    i_ch = 1,
                    data = {
                		seriesId : $("#seriesId").val(),
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
                    }
                });
                $.post($('#ctx').val()+"/ajax/series/updateSeriesOrder",{seriesId : $("#seriesId").val(),chapter:JSON.stringify(data.chapter),lecture:JSON.stringify(data.lecture)},function(res){},'json');
            }
			// 绑定添加阶段事件
			$("#addSeries").bind("click",function(){
					if(!$sections.children(".chapter").last().children(".form-edit-title").length){
						var data = {
							chapter: {
								index: $sections.children("li").length+1, 
								num: $sections.children(".chapter").length + 1
							}			
						};		
						$sections.append(addSectionsFn(data));	
						
					} else {
						$sections.children(".chapter").last().find(".form-edit-title .control-group:first").removeClass("warning").find(".help-block").remove();
						$sections.children(".chapter").last().find(".form-edit-title .control-group:first").addClass("warning").find(":text").after('<span class="help-block">请先完成未保存的阶段！</span>');
					}
			});
		};
		
		// 格式化章节数据
		function rtnSectionData(isChapter,nm,tit,i,typ,fdid){
			var data = {
					chapter : undefined
			};
			if(isChapter){
				data.chapter = {
						id: fdid ,
						index: i,
						num: nm,
						title: tit		
				};			
			} 
			return data;
		}
		// 更新课程进度条
		function updataProgressCourses(numAll){
			var $prog = $("#progress_courses");
			var comp=$prog.find(".num_comp").html();
			//var comp = numAll - $('#sortable').find(".sortable-bar>.title>.icon-none").length;
			if(comp>numAll){
				comp=numAll;
			}
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
		// 加载删除课程
		rightCont.loadDeleteCoursePage = function (title, fdid){	
			data.pageTitle = title;	
			$("#rightCont").html(deleteSeriesFn(data));	
			
			$("#deleteSeriesCourse").click(function(){confirmDel();});
			function confirmDel(){
				jalert("您确认要删除当前系列课程？",deleteseriesInfo);
			}
			// 调用ajax删除当前课程
			function deleteseriesInfo(){
				 $.post($('#ctx').val()+"/ajax/series/deleteSeries",{
					 seriesId:fdid
		         })
		             .success(function(){
		                 // 提交成功
		            	 window.location.href=$('#ctx').val()+"/series/findSeriesInfos?fdType=11&order=fdcreatetime";
		             });
			}
		};
	}());
	
	
	