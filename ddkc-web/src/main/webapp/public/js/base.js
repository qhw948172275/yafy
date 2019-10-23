
//提示帮助类
var sweetalert = {
		// 基础提示
		basic:function(content){
			swal(content);
		},
		// 标题+内容
		titleWithTextUnder:function(title,content){
			swal(title,content);
		},
		// 成功提示
		success:function(title,content){
			swal(title,content,'success');
		},
		// 警告提示框
		warning:function(title,content,showCannelButton,confirmBtnTxt,callback){
			if(typeof(showCannelButton) == 'undefined'){
				showCannelButton = true;
			}
			if(typeof(confirmBtnTxt) == 'undefined'){
				confirmBtnTxt = "确认";
			}
			swal({   
                title: title,   
                text: content,   
                type: "warning",   
                showCancelButton: showCannelButton,   
                confirmButtonText: confirmBtnTxt,
            }).then(function(){
            	if(typeof(callback) == 'function'){
            		callback();
            	}
            });
		}
		
};


// 校验器
var validate = {
	// 校验当前运行环境是否是手机端
    isWap:function(){
    	var sUserAgent= navigator.userAgent.toLowerCase(); 
 	    var bIsIpad= sUserAgent.match(/ipad/i) == "ipad"; 
 	    var bIsIphoneOs= sUserAgent.match(/iphone os/i) == "iphone os"; 
 	    var bIsMidp= sUserAgent.match(/midp/i) == "midp"; 
 	    var bIsUc7= sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4"; 
 	    var bIsUc= sUserAgent.match(/ucweb/i) == "ucweb"; 
 	    var bIsAndroid= sUserAgent.match(/android/i) == "android"; 
 	    var bIsCE= sUserAgent.match(/windows ce/i) == "windows ce"; 
 	    var bIsWM= sUserAgent.match(/windows mobile/i) == "windows mobile"; 
 	    if (bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) { 
 	        return true;// 是手机端
 	    } else { 
 	        return false;// 不是手机端
 	    } 
    },
    // 身份证校验
    identityCodeValid:function(code){
        var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
        var pass= true;
        if (!code || !/^[1-9]\d{5}((1[89]|20)\d{2})(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{3}[\dx]$/i.test(code)) {
            console.log("身份证号格式错误");
            pass = false;
        }else if(!city[code.substr(0,2)]){
            console.log("地址编码错误");
            pass = false;
        }else{
            // 18位身份证需要验证最后一位校验位
            if(code.length == 18){
                code = code.split('');
                // ∑(ai×Wi)(mod 11)
                // 加权因子
                var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
                // 校验位
                var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
                var sum = 0;
                var ai = 0;
                var wi = 0;
                for (var i = 0; i < 17; i++)
                {
                    ai = code[i];
                    wi = factor[i];
                    sum += ai * wi;
                }
                var last = parity[sum % 11];
                if(parity[sum % 11] != code[17].toUpperCase()){
                    console.log("校验位错误");
                    pass =false;
                }
            }
        }
        return pass;// true 表示校验通过；false 表示校验失败
    },
	// 检查生日是否正确，如果正确则返回正确的生日
	checkBirthday : function(card)
	{
	    var len = card.length;
	    // 身份证15位时，次序为省（3位）市（3位）年（2位）月（2位）日（2位）校验位（3位），皆为数字
	    if(len == '15')
	    {
	        var re_fifteen = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/; 
	        var arr_data = card.match(re_fifteen);
	        var year = arr_data[2];
	        var month = arr_data[3];
	        var day = arr_data[4];
	        var birthday = new Date('19'+year+'-'+month+'-'+day);
	        return verifyBirthday('19'+year+"-"+month+"-"+day);
	    }
	    // 身份证18位时，次序为省（3位）市（3位）年（4位）月（2位）日（2位）校验位（4位），校验位末尾可能为X
	    if(len == '18')
	    {
	        var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
	        var arr_data = card.match(re_eighteen);
	        var year = arr_data[2];
	        var month = arr_data[3];
	        var day = arr_data[4];
	        var birthday = new Date(year+'-'+month+'-'+day);
	        return (year+"-"+month+"-"+day);
	    }
	    return false;
	},// 校验姓名
	isName:function(value){
		// if(value.match(/^([\u4e00-\u9fa5]|[0-9a-zA-Z]|[.])+$/)){
		// if(value.match(/^([\u4e00-\u9fa5]{2,10})+$/)){
			return true;
		// }
		// else {return false; }
	},// 校验英文
	isEnName:function(value){
		if(value.match(/^([0-9a-zA-Z]|[.]){1,30}$/)){
			return true;
		}else{
			return false;
		}
	},// 校验数字
	isNum:function(s){
		var patrn=/^[0-9]{1,20}$/; 
		if (!patrn.exec(s)) return false 
		return true ;
	},// 校验护照
	isPassport:function(value){
		// /(P\d{7})|(G\d{8})/
		/* if(value.match(/^([0-9a-zA-Z]{8}|[0-9a-zA-Z]{9})$/)){ */
			return true;
	/*
	 * }else{ return false; }
	 */
	},// 校验地址
	isAddress:function(value){
		if(value.match(/^([0-9a-zA-Z]|[\u4e00-\u9fa5]|[-.\(\)]){2,100}$/)){
			return true;
		}else{
			return false;
		}

	},// 校验手机号
	isMobile:function(value){
		if(value == ''){
			return false;
		}else if(value.trim().match(/^1[3|4|5|6|7|8|9][0-9]\d{8}$/)){
			return true;
		}else{
			return false;
		}
	},
	// 校验邮箱
	isEmail:function(email){
		if(email.match(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/)){
			return true;
		}else{
			return false;
		}
	},// 检查数组元素是否有重复，true表示重复；false表示不重复
	validateRep:function(arr){
		return /(\x0f[^\x0f]+)\x0f[\s\S]*\1/.test("\x0f"+arr.join("\x0f\x0f") +"\x0f");
	},isImage:function(imgPath){
		imgPath="abc123"+imgPath.substring(imgPath.lastIndexOf("."),imgPath.length);
		return validate.isNotNull(imgPath)&&/^.*[^a][^b][^c]\.(?:png|jpg|bmp|gif|jpeg)$/.test(imgPath.toLocaleLowerCase());
	}, isMoney:function(money){
		var reg= /^-?\d+\.?\d{0,2}$/;
		return reg.test(money);
	},isUndefined:function(obj){
		return "undefined" == typeof obj||obj==undefined;
	},isNotNull:function(value){
		return value!=null&&$.trim(value)!="";
	},isNull:function(value){
		return !validate.isNotNull(value);
	},isURL:function (str){
        return!!str.trim().match(/(((^https?:(?:\/\/)?)(?:[-;:&=\+\$,\w]+@)?[A-Za-z0-9.-]+|(?:www.|[-;:&=\+\$,\w]+@)[A-Za-z0-9.-]+)((?:\/[\+~%\/.\w-_]*)?\??(?:[-\+=&;%@.\w_]*)#?(?:[\w]*))?)$/g);
    },isInt:function(num){
    	num=$.trim(num);
    	if(num=="-"){
    		return true;
    	}
    	var reg=/^-?[0-9]\d*$/; 
    	return reg.test(num);
    }
}

// 分页器
var _page = {
		// 初始化分页
		init:function(item,callback){
			this.pageData = {page:item.attr('data-current-page'),limit:item.attr('data-page-size'),total:item.attr('data-total-page')};
			this.build(item);
			this.callbackMethod = callback;
		},goPage:function (){
			$("#pageSize").val("");
			$("#currentPage").val($("#pageCurrent").val() - 1);
			$("#pageForm").submit();
		},pageData:{ 
			page:0,limit:10,total:0
		},// 构造分页数据
		build:function(item){
			var html ='';
			if(_page.pageData.total > 0){
				var showPageNum = 6;
				var fromPageNum = 0;
				if(_page.pageData.page > 3){
					fromPageNum = (_page.pageData.page - 3 >= 0) ? (_page.pageData.page - 3):(_page.pageData.page);
				}else{
					fromPageNum = 0;
				}
				var toPageNum = 0;
				if(fromPageNum == 0){
					toPageNum = (_page.pageData.total > 6) ? 6 : _page.pageData.total ;
				}else
					toPageNum = ((fromPageNum + 6 ) >= _page.pageData.total) ?  _page.pageData.total : (fromPageNum + 6 ) ;
				html = this.addPageInfo(fromPageNum,toPageNum);
			}
			item.html(html);
		},// 构造分页代码信息
		addPageInfo:function(fromNum,toNum){
			var html = '';
			if(_page.pageData.page != 0){
				html+='<li><a href="javascript:;" aria-label="Previous" onclick="_page.prev()"><i class="zmdi zmdi-chevron-left"></i></a></li>';
			}
			for(var i = fromNum ; i < toNum ; i++) {
				if(i == _page.pageData.page){
					html+='<li class="active">';
				}else
					html+='<li>';
				html+='<a data-page="'+i+'" href="javascript:;" onclick="_page.toPageNum($(this))">'+(i+1)+'</a></li>';
			}
			if(toNum < _page.pageData.total){
				html+='<li><a href="javascript:;" aria-label="Next" onclick="_page.next()"><i class="zmdi zmdi-chevron-right"></i></a></li>';
			}
			return html;
		},// 上一页
		prev:function(){
			this.pageData.page--;
			_page.callback();
		},// 下一页
		next:function(){
			this.pageData.page++ ;
			_page.callback();
		},// 跳转到指定页面
		toPageNum:function(index){
			this.pageData.page = index.attr("data-page");
			_page.callback();
		},// 回调方法
		callbackMethod:null,
		callback:function(){
			if(typeof(_page.callbackMethod) === 'function'){
				_page.callbackMethod();
			}
		}
};

// 检查当前用户是否有新消息
var _validateMessage = {
		options:{is_continue:true},
		run:function(){
			_validateMessage.interval = _validateMessage.interval();
		},
		validate:function(){
			$.get(path+'/message/validateNewMsg',function(e){
				if(e.message == 'Y'){
					$('#header .message-icon').html('<span class="new_message"></span>');
				}else if(e.message == 'N'){
					$('#header .message-icon').html('');
				}else{
					$('#header .message-icon').html('');
					_validateMessage.stop();
				}
			});
		},
		interval:function(){
			return setInterval(_validateMessage.validate,5000);
		},
		stop:function(){
			clearInterval(_validateMessage.interval);
		}
};

// 判定是否是移动端
function isMobile() {
	var sUserAgent = navigator.userAgent.toLowerCase();
	var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";
	var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
	var bIsMidp = sUserAgent.match(/midp/i) == "midp";
	var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";
	var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";
	var bIsAndroid = sUserAgent.match(/android/i) == "android";
	var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";
	var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";
	if (bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid
			|| bIsCE || bIsWM) {
		return true;
	} else {
		return false;
	}
}
// 判定是否是微信浏览器
function isWeixin(){
	var ua = window.navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i) == 'micromessenger')
        return true;
    else
        return false;
}

/**
 * cookie 操作
 */
var _cookie = {
		// 获取cookie生命周期，i=1表示1天；
		getExpTime:function(i){
			var exp  = new Date(); 
			return new Date().getTime() + i*24*60*60*1000;
		},
		// 设值
		set:function(name,value,expTime){
		    var exp  = new Date();
			exp.setTime(expTime);
		    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
		},
		// 获取值
		get:function(name){
			var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
		    if(arr != null) return unescape(arr[2]); return null;
		},
		// 删除指定值
		del:function(name){
			var exp = new Date();
		    exp.setTime(exp.getTime() - 1);
		    var cval=_cookie.get(name);
		    if(cval!=null) {document.cookie= name + "="+cval+";expires="+exp.toGMTString()};
		}
}

var bindUtil={
		bindOnlyInputInt:function (obj,funObj){// 只能输入整数
			var tempMoneyValue=validate.isInt($(obj).val())?$(obj).val():"";
			$(obj).bind("input propertychange",function() {
				if(!validate.isInt(this.value)){
					if(!validate.isNotNull(this.value)&&this.value.length==0){
						tempMoneyValue="";
					}else{
						this.value=tempMoneyValue;
					}
				}else{
					tempMoneyValue=$.trim(this.value);
					if(this.value.length!=tempMoneyValue.length){
						this.value=tempMoneyValue;
					}
				}
				var thisObj=this;
				if(!validate.isUndefined(funObj)){
					funObj(thisObj);
				}
			});		
		},
		bindOnlyInputMoney: function (inputObj,funObj){// 只能输入金额
			var tempMoneyValue=validate.isMoney($(inputObj).val())?$(inputObj).val():"";;
			$(inputObj).bind("input propertychange",function() {
				if(!validate.isMoney(this.value)){
					if(!validate.isNotNull(this.value)&&this.value.length==0){
						tempMoneyValue=this.value;
					}else{
						this.value=tempMoneyValue;
					}
				}else{
					tempMoneyValue=$.trim(this.value);
					if(tempMoneyValue.length!=this.value.length){
						this.value=tempMoneyValue;
					}
				}
				var thisObj=this;
				if(!validate.isUndefined(funObj)){
					funObj(thisObj);
				}
			});
		}
}


$(function(){
	$('.hi-model a').click(function(){
		var modelId = $(this).parent('.hi-model').attr('id');
		localStorage.setItem("duoji-modal",modelId);
		return true;
	});
	if(null != localStorage.getItem("duoji-modal")){
		$('#'+ localStorage.getItem("duoji-modal")).addClass('active');
		$('#'+ localStorage.getItem("duoji-modal")+"Sidebar").removeClass('hide');
		
		var ahref=path+(window.location.href.toString().split(path)[1]);
		if(path==""){
			ahref=window.location.href.toString().split(window.location.origin)[1];
		}
		if(ahref.indexOf("?") > 0)
			ahref = ahref.substring(0,ahref.indexOf("?"));
		var aobj=$("a[href='"+ahref+"']");
		aobj.parent().parent().prev().click();
		aobj.addClass("black");
	}
});



/**
 * 时间格式化工具 example： new Date().format("yyyy-MM-dd") 输出为2014-10-22
 */
eval(function(p,a,c,k,e,d){e=function(c){return(c<a?'':e(parseInt(c/a)))+((c=c%a)>35?String.fromCharCode(c+29):c.toString(36))};if(!''.replace(/^/,String)){while(c--){d[e(c)]=k[c]||e(c)}k=[function(e){return d[e]}];e=function(){return'\\w+'};c=1};while(c--){if(k[c]){p=p.replace(new RegExp('\\b'+e(c)+'\\b','g'),k[c])}}return p}('t.A.0=p(0){b o={"B+":2.9()+1,"d+":2.j(),"h+":2.x(),"m+":2.l(),"s+":2.n(),"q+":r.i((2.9()+3)/3),"f":2.g()};a(/(y+)/.8(0)){0=0.7(5.$1,(2.C()+"").c(4-5.$1.6))}z(b k u o){a(v 5("("+k+")").8(0)){0=0.7(5.$1,5.$1.6==1?o[k]:("w"+o[k]).c((""+o[k]).6))}}e 0}',39,39,'format||this|||RegExp|length|replace|test|getMonth|if|var|substr||return|S|getMilliseconds||floor|getDate||getMinutes||getSeconds||function||Math||Date|in|new|00|getHours||for|prototype|M|getFullYear'.split('|'),0,{}))
String.prototype.replaceFromTemplate = function(obj) {return this.replace(/#\{\s*\w+\s*\}/gi, function(matchs) {var returns = obj[matchs.replace(/\#{\s*|\s*}/g, "")];return (returns + "") == "undefined" ? "" : returns;});};
Array.prototype.indexOf = function(val) {for (var i = 0; i < this.length; i++) {if (this[i] == val) return i;}return -1;};
Array.prototype.remove = function(val) {var index = this.indexOf(val);if (index > -1) {this.splice(index, 1);}};
function formatCurrency(num) {  num = num.toString().replace(/\$|\,/g,'');  if(isNaN(num))   num = "0";   sign = (num == (num = Math.abs(num)));  num = Math.floor(num*100+0.50000000001);   num = Math.floor(num/100).toString();  for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)   num = num.substring(0,num.length-(4*i+3))+','+  num.substring(num.length-(4*i+3)); return (((sign)?'':'-')  + num ); }

/**
 * $.ajax 包装方法
 * @param cOption
 */
function ajaxWrap(cOption) {
    var loading = null;
    var deOption = {

        async: false,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
//            contentType : "application/json; charset=utf-8",
        dataType: 'json',
        type: 'post',
        beforeSend: function () {
//                loading层
//             loading = layer.load(1, {
//                 shade: [0.3, '#fff'] //0.1透明度的白色背景
//             });
            return true;
        },
        success: function (data) {
            if (data.status === true) {
                alert(data.msg);
            } else {
                alert(data.msg);
            }
        },
        error: function (data) {
            alert("程序报错啦！快去报告程序猿");

        },
        complete: function () {
            // layer.close(loading);
        }
    };
    deOption = $.extend(deOption, cOption);
    return $.ajax(deOption);
}