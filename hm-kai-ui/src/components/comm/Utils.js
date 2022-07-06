const utils = {
  formatDate(date,fmt){
    if(/(y+)/.test(fmt)) {
      fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    let o = {
      'M+': date.getMonth() + 1,
      'd+': date.getDate(),
      'h+': date.getHours(),
      'm+': date.getMinutes(),
      's+': date.getSeconds()
    };
    for(let k of Object.keys(o)){
      if(new RegExp("("+ k +")").test(fmt)){
        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
      }
    }
    return fmt;
  },
  formatDate1(date){
    let formatObj = {};
    let myDay = date.getDay();
    let myHours = date.getHours();
    let myMinutes = date.getMinutes();
    let mySeconds = date.getSeconds();
    let xingqi = '';
    let dayTime ='';
    let my12Hours ='';
    let minutes ='';
    let seconds ='';
    switch(myDay)
    {
      case 0:xingqi="Sun";break;
      case 1:xingqi="Mon";break;
      case 2:xingqi="Tue";break;
      case 3:xingqi="Wed";break;
      case 4:xingqi="Thu";break;
      case 5:xingqi="Fri";break;
      case 6:xingqi="Sat";break;
      default:xingqi="Error！"
    }
    if(myHours<6){
      dayTime = 'morning';//凌晨
    }else if(myHours<11){
      dayTime = 'forenoon';//上午
    }else if(myHours<13){
      dayTime = 'noon';//中午
    }else if(myHours<19){
      dayTime = 'afternoon';//下午
    }else {
      dayTime = 'night';//晚上
    }
    let hours = myHours>12?(myHours-12):myHours;
    my12Hours = hours>9?hours:'0'+hours;
    minutes = myMinutes>9?myMinutes:'0'+myMinutes;
    seconds = mySeconds>9?mySeconds:'0'+mySeconds;
    formatObj.xingqi = xingqi;
    formatObj.dayTime =dayTime;
    formatObj.timeStr = my12Hours+':'+minutes+':'+seconds;
    return formatObj;
  },
  getWeek(dateString){
    let date;
    let dateArray = dateString.split("/");
    date = new Date(dateArray[0], parseInt(dateArray[1] - 1), dateArray[2]);
    let weeks = new Array('Sun','Mon','Tue','Wed','Thu','Fri','Sat');
    return weeks[date.getDay()];
  },
  getMore8(){//返回一个时间（小于早上8点就返回前一天）
    return (new Date().getHours()<8?new Date(new Date().getTime()-24*60*60*1000):new Date());
  },
  formatMoney(s,n){
    let sign = '';
    let money = s.toString();
    if(money.indexOf('-')!==-1){
      s = money.replace('-','');
      sign = '-';
    }
    let num = n>0 && n<=20?n:2;//保留小数位,默认2位
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(num) + "";
    let l = s.split(".")[0].split("").reverse(),r = s.split(".")[1];
    let t = "";
    for (let i = 0; i < l.length; i++) {
      t += l[i] + ((i + 1) % 3 === 0 && (i + 1) !== l.length ? "," : "");
    }
    if(n===0){
      return t.split("").reverse().join("");
    }
    return sign+t.split("").reverse().join("") + "." + r;
  },

  chunk(array, size) {
    // #1
    size = Math.max(size, 0)
    const length = array == null ? 0 : array.length
    if (!length || size < 1) {
      return []
    }

    // #2
    let index = 0
    let resIndex = 0
    const result = new Array(Math.ceil(length / size))

    // #3
    while (index < length) {
      result[resIndex++] = utils.slice(array, index, (index += size))
    }
    return result
  },
  slice(array, start, end) {
    // #1
    let length = array == null ? 0 : array.length
    if (!length) {
      return []
    }
    // #2
    start = start == null ? 0 : start
    end = end === undefined ? length : end

    // #3
    if (start < 0) {
      start = -start > length ? 0 : (length + start)
    }
    end = end > length ? length : end
    if (end < 0) {
      end += length
    }
    // #4
    length = start > end ? 0 : ((end - start) >>> 0)
    start >>>= 0

    // #5
    let index = -1
    const result = new Array(length)
    while (++index < length) {
      result[index] = array[index + start]
    }
    return result
  },
  initOddsBetInfo(odds){//初始化play数据
    let len = odds.length;
    for(let i=0;i<len;i++){
      let leng = odds[i].playOddsDatas.length;
      for(let j=0;j<leng;j++){
        let obj = odds[i].playOddsDatas[j];
        obj.betAmount = '';     //加入下注的金额
        obj.choose = false;   //加入是否被选择属性
        obj.isShowFastDiv = false;//是否显示快选金额
      }
    }
  },
  getRandomString(){
    let s = Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
    return s+s+'-'+s;
  },
  getSmzResult(sum){
    let re = '';
    switch(sum){
      case 5:
      case 9:
      case 13:
      case 17:
        re = "js";
        break;
      case 6:
      case 10:
      case 14:
      case 18:
        re = "lh";
        break;
      case 3:
      case 7:
      case 11:
      case 15:
        re = "cm";
        break;
      case 4:
      case 8:
      case 12:
      case 16:
        re = "ql";
        break;
    }
    return re;
  },
  getQueryString(name) {
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    let r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
  },
  //浮点型乘法计算
  NumberMul(arg1, arg2) {
    var m = 0;
    var s1 = arg1.toString();
    var s2 = arg2.toString();
    try {
      m += s1.split(".")[1].length;
    } catch (e) {}
    try {
      m += s2.split(".")[1].length;
    } catch (e) {}
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
  },
  //浮点型加法计算
  NumberAdd(arg1, arg2) {
    var r1, r2, m, n;
    try {
      r1 = arg1.toString().split(".")[1].length
    } catch (e) {
      r1 = 0
    }
    try {
      r2 = arg2.toString().split(".")[1].length
    } catch (e) {
      r2 = 0
    }
    m = Math.pow(10, Math.max(r1, r2))
    n = (r1 >= r2) ? r1 : r2;
    if(n===0){
      n=2;
    }
    return((arg1 * m + arg2 * m) / m).toFixed(n);
  },
  //浮点型减法计算
  NumberSub(arg1, arg2) {
    var re1, re2, m, n;
    try {
      re1 = arg1.toString().split(".")[1].length;
    } catch (e) {
      re1 = 0;
    }
    try {
      re2 = arg2.toString().split(".")[1].length;
    } catch (e) {
      re2 = 0;
    }
    m = Math.pow(10, Math.max(re1, re2));
    n = (re1 >= re2) ? re1 : re2;
    return ((arg1 * m - arg2 * m) / m).toFixed(n);
  },
  //浮点型除法计算
  NumberDiv (arg1,arg2,digit){
    var t1=0,t2=0,r1,r2;
    try{t1=arg1.toString().split(".")[1].length}catch(e){}
    try{t2=arg2.toString().split(".")[1].length}catch(e){}
    r1=Number(arg1.toString().replace(".",""))
    r2=Number(arg2.toString().replace(".",""))
    //获取小数点后的计算值
    var result= ((r1/r2)*Math.pow(10,t2-t1)).toString()
    var result2=result.split(".")[1];
    if(!result2){
      result2 = '00';
    }
    result2=result2.substring(0,digit>result2.length?result2.length:digit);
    return Number(result.split(".")[0]+"."+result2);
  },
  changeArray(valArray,isVal){
    let arr = [];
    valArray.forEach((val)=>{
      let col = [];
      if(arr.length!==0){
        col = arr[arr.length-1];

      }else{
        arr.push(col);
      }
      if(col.length!==0){
        if(col[0]===val){
          col.push(val);
        }else{
          col = [];
          col.push(val);
          arr.push(col);
        }
      }else{
        col.push(val);
      }
    });
    arr = arr.reverse();
    let maxLength = 0;
    for(let i=0;i<arr.length;i++){
      if(arr[i].length>maxLength){
        maxLength = arr[i].length;
      }
    }
    let _table = [];
    for(let i=0;i<maxLength;i++){
      for(let j=0;j<arr.length;j++){
        if(!_table[i]){
          _table[i] = [];
        }
        _table[i][j]=arr[j][i];
      }
    }
    let tableArr = [];
    for(let i=0;i<_table.length;i++){
      let rowArr = [];
      for(let j=0;j<35;j++){
        if(_table[i][j]){
          if(isNaN(_table[i][j])){
            if(isVal){
              _table[i][j] = _table[i][j];
            }else{

                _table[i][j] = _table[i][j];

            }
          }
          rowArr.push(_table[i][j]);
        }else{
          rowArr.push('');
        }
      }
      tableArr.push(rowArr);
    }
    return tableArr;
  },
  getArr(arr) {
    let rowArr = [];
    let tableArr = [];
    let tableTwoArr = [];
    for(let i =0;i<arr.length;i++){
      let one = arr[i];
      if(tableArr.length!==0){
        if(tableArr[0].toString()==one.toString()){
          tableArr.push(one);
        }else{
          rowArr.push(tableArr);
          if(rowArr.length==10){
            tableTwoArr.push(rowArr);
            rowArr = [];
          }
          tableArr = [];
          tableArr.push(one);
        }
      }else{
        tableArr.push(one);
      }
      if(i==arr.length-1){
        rowArr.push(tableArr);
        tableTwoArr.push(rowArr);
      }
    }
    return tableTwoArr;
  }
};
export default utils;
