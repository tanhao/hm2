<template>
  <div class="eGXSwp">
    <div class="tc-loading-screen vh-center on">
      <div class="preloader-wrapper">
        <div class="svgimg">
          <div class="loader">
            <div class="timerWrap">
              <svg version="1.1" viewBox="131.623 175.5 120 160" preserveAspectRatio="xMinYMin meet" class="timer">
                <path fill="#FFFFFF" d="M212.922,255.45l36.855-64.492c1.742-3.069,1.742-6.836-0.037-9.896c-1.783-3.06-5.037-4.938-8.581-4.938
			h-99.158c-3.524,0-6.797,1.878-8.569,4.938c-1.773,3.06-1.792,6.827-0.03,9.896l36.846,64.491l-36.845,64.492
			c-1.762,3.068-1.743,6.836,0.03,9.896c1.772,3.061,5.044,4.938,8.569,4.938h99.158c3.544,0,6.798-1.878,8.581-4.938
			c1.779-3.06,1.779-6.827,0.037-9.896L212.922,255.45z M142.001,324.86l39.664-69.41l-39.664-69.41h99.158l-39.663,69.41
			l39.663,69.41H142.001z"/>
              </svg>
            </div>
          </div>
        </div>
        <div class="big">网络异常！</div>
        <div class="small">当前无网络连接或网络不正常<br>请检查您的网络!</div>
      </div>
    </div>
 </div>
</template>
<script>
  export default {
    data() {
      return {
      }
    },
    mounted:function(e){
      const { type } = e;
      if(type == "offline"){
        this.$router.push('/network/');
      }else {
        this.$router.push('/');
      }
    },
  }
</script>
<style>
  body {
    padding: 0px;
    margin: 0px;
  }
  .tc-loading-screen {
    text-align: center;
  }
  .vh-center {
    display: -webkit-box;
    display: -ms-flexbox;
    display: -webkit-flex;
    display: flex;
    -webkit-box-pack: center;
    -ms-flex-pack: center;
    -webkit-justify-content: center;
    justify-content: center;
    -webkit-box-align: center;
    -ms-flex-align: center;
    -webkit-align-items: center;
    align-items: center;
  }
  .tc-loading-screen .big {
    font-size: 17px;
    font-weight: 500;
    opacity: .87;
  }
  .tc-loading-screen .small {
    margin: 10px 0 10px;
    opacity: .87;
  }
  .tc-loading-screen {
    position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    background-color: #123866;
    z-index: 9999;
    color: #FFF;
    opacity: 0;
    visibility: hidden;
    -webkit-transition: all .25s;
    transition: all .25s;
  }
  .tc-loading-screen.on {
    opacity: 1;
    visibility: visible;
  }
  .svgimg {
    margin: 15px;
    display: inline-block;
    position: relative;
  }
  .svgimg img {
    padding-bottom: 50px;
  }
  .button {
    line-height: 40px;
    height: 40px;
    border-radius: 50px;
    text-decoration: none;
    text-align: center;
    display: block;
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
    padding: 0 10px;
    margin: 0;
    white-space: nowrap;
    text-overflow: ellipsis;
    font-size: 14px;
    position: relative;
    overflow: hidden;
    font-family: inherit;
    cursor: pointer;
    outline: 0;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    vertical-align: middle;
  }
  .homebnt {
    background: -moz-linear-gradient(45deg, rgb(27, 217, 179) 0%, rgb(145, 210, 116) 100%);
    background: -webkit-linear-gradient(45deg, rgb(27, 217, 179) 0%, rgb(145, 210, 116) 100%);
    background: -ms-linear-gradient(45deg, rgb(27, 217, 179) 0%, rgb(145, 210, 116) 100%);
    color: #fff;
  }
  .tc-loading-screen .big span, .tc-loading-screen .small span {
    font-size: 16px;
    color: red;
  }
  @-webkit-keyframes timerRotate {
    0% {
      transform: rotateZ(0deg);
    }
    50% {
      transform: rotateZ(0deg);
    }
    100% {
      transform: rotateZ(-180deg);
    }
  }
  @-moz-keyframes timerRotate {
    0% {
      transform: rotateZ(0deg);
    }
    50% {
      transform: rotateZ(0deg);
    }
    100% {
      transform: rotateZ(-180deg);
    }
  }
  @-ms-keyframes timerRotate {
    0% {
      transform: rotateZ(0deg);
    }
    50% {
      transform: rotateZ(0deg);
    }
    100% {
      transform: rotateZ(-180deg);
    }
  }
  @keyframes timerRotate {
    0% {
      transform: rotateZ(0deg);
    }
    50% {
      transform: rotateZ(0deg);
    }
    100% {
      transform: rotateZ(-180deg);
    }
  }
  @-webkit-keyframes bottomFill {
    0% {
      border-bottom-width: 0px;
    }
    50% {
      border-bottom-width: 60px;
    }
    100% {
      border-bottom-width: 60px;
    }
  }
  @-moz-keyframes bottomFill {
    0% {
      border-bottom-width: 0px;
    }
    50% {
      border-bottom-width: 60px;
    }
    100% {
      border-bottom-width: 60px;
    }
  }
  @-ms-keyframes bottomFill {
    0% {
      border-bottom-width: 0px;
    }
    50% {
      border-bottom-width: 60px;
    }
    100% {
      border-bottom-width: 60px;
    }
  }
  @keyframes bottomFill {
    0% {
      border-bottom-width: 0px;
    }
    50% {
      border-bottom-width: 60px;
    }
    100% {
      border-bottom-width: 60px;
    }
  }
  @-webkit-keyframes topEmpty {
    0% {
      top: 19px;
      border-top-width: 60px;
      border-left-width: 35px;
      border-right-width: 35px;
    }
    50% {
      top: 80px;
      border-top-width: 0px;
      border-left-width: 0px;
      border-right-width: 0px;
    }
    100% {
      top: 80px;
      border-top-width: 0px;
      border-left-width: 0px;
      border-right-width: 0px;
    }
  }
  @-moz-keyframes topEmpty {
    0% {
      top: 19px;
      border-top-width: 60px;
      border-left-width: 35px;
      border-right-width: 35px;
    }
    50% {
      top: 80px;
      border-top-width: 0px;
      border-left-width: 0px;
      border-right-width: 0px;
    }
    100% {
      top: 80px;
      border-top-width: 0px;
      border-left-width: 0px;
      border-right-width: 0px;
    }
  }
  @-ms-keyframes topEmpty {
    0% {
      top: 19px;
      border-top-width: 60px;
      border-left-width: 35px;
      border-right-width: 35px;
    }
    50% {
      top: 80px;
      border-top-width: 0px;
      border-left-width: 0px;
      border-right-width: 0px;
    }
    100% {
      top: 80px;
      border-top-width: 0px;
      border-left-width: 0px;
      border-right-width: 0px;
    }
  }
  @keyframes topEmpty {
    0% {
      top: 19px;
      border-top-width: 60px;
      border-left-width: 35px;
      border-right-width: 35px;
    }
    50% {
      top: 80px;
      border-top-width: 0px;
      border-left-width: 0px;
      border-right-width: 0px;
    }
    100% {
      top: 80px;
      border-top-width: 0px;
      border-left-width: 0px;
      border-right-width: 0px;
    }
  }
  .loader {
    width: 50%;
    height: 200px;
    -webkit-transform: translateZ(0);
    -webkit-backface-visibility: hidden;
    backface-visibility: hidden;
    -webkit-perspective: 1000;
  }
  .loader .timerWrap {
    position: relative;
    animation: timerRotate 1s infinite ease;
    -webkit-animation: timerRotate 1s infinite ease;
    -moz-animation: timerRotate 1s infinite ease;
    -ms-animation: timerRotate 1s infinite ease;
    width: 120px;
    height: 160px;
    margin: auto;
  }
  .loader .timerWrap:before {
    content: "";
    width: 0;
    height: 0;
    border-style: solid;
    border-width: 60px 35px 0 35px;
    border-color: #e08f24 transparent transparent transparent;
    position: absolute;
    top: 19px;
    left: 0;
    right: 0;
    margin: auto;
    animation: topEmpty 1s infinite ease;
    -webkit-animation: topEmpty 1s infinite ease;
    -moz-animation: topEmpty 1s infinite ease;
    -ms-animation: topEmpty 1s infinite ease;
  }
  .loader .timerWrap:after {
    content: "";
    width: 0;
    height: 0;
    border-style: solid;
    border-width: 0 35px 60px 35px;
    border-color: transparent transparent #e08f24 transparent;
    position: absolute;
    bottom: 19px;
    left: 0;
    right: 0;
    margin: auto;
    animation: bottomFill 1s infinite ease;
    -webkit-animation: bottomFill 1s infinite ease;
    -moz-animation: bottomFill 1s infinite ease;
    -ms-animation: bottomFill 1s infinite ease;
  }
  .loader .timerWrap .timer {
    width: 120px;
    max-width: 100%;
    height: 160px;
    position: relative;
    top: 0;
    left: 0;
  }
  .loader .timerWrap .timer path {
    fill: #3f6b9d;
  }
</style>
