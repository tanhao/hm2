import moment from "moment";

export default {
    //整数
    getAnsZ(n) {
        if (!n) n = 0;
        let pattern = /(?=((?!\b)\d{3})+$)/g;
        let f_x = (Math.round(n * 100) / 100) + '';
        let v = f_x.split(".")[0];
        let z = v.replace(pattern, ',');
        return z;
    },
    //一位小数
    getAnsG(n) {
        if (!n) n = 0;
        let pattern = /(?=((?!\b)\d{3})+$)/g;
        let f_x = (Math.round(n * 10) / 10).toFixed(1) + '';
        let v = f_x.split(".")[0];
        let x = f_x.split(".")[1] || '0';
        let z = v.replace(pattern, ',');
        let a = z + "." + x;
        return a;
    },
    //2位小数
    getAnsS(n) {
        if (!n) n = 0;
        let pattern = /(?=((?!\b)\d{3})+$)/g;
        let f_x = (Math.round(n * 100) / 100).toFixed(2) + '';
        let v = f_x.split(".")[0];
        let x = f_x.split(".")[1] || '00';
        let z = v.replace(pattern, ',');
        let a = z + "." + x;
        return a;
    },
    //3位小数
    getAnsB(n) {
        if (!n) n = 0;
        let pattern = /(?=((?!\b)\d{3})+$)/g;
        let f_x = (Math.round(n * 100) / 100).toFixed(3) + '';
        let v = f_x.split(".")[0];
        let x = f_x.split(".")[1] || '000';
        let z = v.replace(pattern, ',');
        let a = z + "." + x;
        return a;
    },
    //4位小数
    getAnsQ(n) {
        if (!n) n = 0;
        let pattern = /(?=((?!\b)\d{3})+$)/g;
        let f_x = (Math.round(n * 10000) / 10000).toFixed(4) + '';
        let v = f_x.split(".")[0];
        let x = f_x.split(".")[1] || '0000';
        let z = v.replace(pattern, ',');
        let a = z + "." + x;
        return a;
    },
    //判断数值返回颜色
    getColorCss(n) {
        if (n < 0) {
            return "red"
        } else {
            return "blue"
        }
    },
    getColorCssG(n) {
        if (!n) n = 0;
        let f_x = (Math.round(n * 10) / 10).toFixed(1) + '';
        if (f_x < 0) {
            return "red"
        } else {
            return "blue"
        }
    },
    dateDiffDay(date) {
        let m2 = moment();
        let m1 = moment(date);
        let days = m2.diff(m1, 'day');
        return days;
    },
    handleThen(res, vue, successMsg = '操作成功!', showButton = true) {
        if (res.success) {
            vue.$swal({
                    title: successMsg,
                    icon: 'success',
                    confirmButtonText: '确定',
                    timer: 1000,
                    showConfirmButton: showButton,
                }
            )
        } else {
            vue.$swal({
                    title: res.message || '操作失败!',
                    icon: 'error',
                    confirmButtonText: '确定'
                }
            )
        }
    },
    checkIP(ip, msg) {
        //验证ip是否符合规则
        let flag = true;
        if (ip !== "") {
            let reg = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/
            let ips = ip.split(","), i = 0;
            if (ips.length > 5) {
                return false;
            }
            while (i < ips.length && flag) {
                flag = reg.test(ips[i])
                i++;
            }
        }
        if (!flag) {
            throw new Error(msg);
        }
    },
    checkPassword(str, msg) {
        /*登入密碼 8-16位,最少帶二個英文字母,不用分大小寫(可不可以加符號"@")*/
        let val = new RegExp('^[a-zA-Z]{2}[a-zA-Z0-9@]{6,16}');
        if (!val.test(str)) {
            throw new Error(msg);
        }
    },
    checkSecondary(str, msg) {
        /*安保密碼 8位,最少帶二個英文字母 */
        let val = new RegExp('^[a-zA-Z]{2}[a-zA-Z0-9@]{6}');
        //let val = new RegExp('^([a-zA-Z]){2}([a-zA-Z0-9])\\2{2}');
        if (!val.test(str)) {
            throw new Error(msg);
        }
    },
    checkSecurityCode(str, msg) {
        /*5位 数字组合*/
        let val = new RegExp('^[0-9]{5}$');
        if (!val.test(str)) {
            throw new Error(msg);
        }
    },
    checkUserName(str, msg) {
        /*登録名2-10位(可不可以加符號"@")*/
        let val = new RegExp('^[a-zA-Z0-9@]{2,10}$');
        if (!val.test(str)) {
            throw new Error(msg);
        }
    },
    checkNickName(str, msg) {
        /*名称2-8位(可不可以加符號"@")*/
        let val = new RegExp('^[\u4e00-\u9fa5_a-zA-Z0-9@/-]{2,8}$');
        if (!val.test(str)) {
            throw new Error(msg);
        }
    },
    checkSiteName(str, msg) {
        /*网站名称1-5位(可不可以加符號"@")*/
        let val = new RegExp('^[\u4e00-\u9fa5_a-zA-Z0-9@]{1,5}$');
        if (!val.test(str)) {
            throw new Error(msg);
        }
    },
    /**
     *对Date的扩展，将 Date 转化为指定格式的String
     *月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
     *年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
     *例子：
     *(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
     *(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
     */
}
Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
