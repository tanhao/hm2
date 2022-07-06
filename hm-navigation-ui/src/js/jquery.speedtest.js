
(function($) {
  $.fn._speedTest = function(options) {
    function foo(_this){
      startTime = (new Date()).getTime();
      var stest = $.extend({
          fileSize: null,
          fileType: null,
          fileUrl: null,
          waitingText: null,
          errorText: null
        }, options),
        duration, download, startTime, endTime, bitsLoaded,cacheBuster,speedMbps,speedKbps,speedBps,p;
      if (stest.fileType == "text") {
        download = new Document();
      } else if (stest.fileType == "image") {
        download = new Image();
      }
      if (stest.errorText) {
        download.onerror = function(err, msg) {
          $(this).text(stest.errorText);
        };
      }
      cacheBuster = "?spdt=" + startTime;
      download.src = stest.fileUrl + cacheBuster;
      download.onload=function see(){
        endTime = (new Date()).getTime()+1;
        duration = (endTime - startTime) / 1000;
        speedBps = duration.toFixed(2);
        $(_this).text(speedBps)
      };
    }
    return this.each(function() {
       foo(this);
    });
  };
}(jQuery));
